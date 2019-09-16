package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import exception.ExceptionHandler;
import io.vavr.control.Try;

import java.io.InputStream;

public abstract class Controller {

    private final ObjectMapper objectMapper;
    private final ExceptionHandler exceptionHandler;

    public Controller(ObjectMapper objectMapper,
                   ExceptionHandler exceptionHandler) {
        this.objectMapper = objectMapper;
        this.exceptionHandler = exceptionHandler;
    }

    public void handle(HttpExchange exchange) {
        Try.run(() -> execute(exchange))
                .onFailure(thr -> exceptionHandler.handle(thr, exchange));
    }

    protected abstract void execute(HttpExchange exchange) throws Exception;


    protected <T> T readRequest(InputStream is, Class<T> type) {
        return Try.of(() -> objectMapper.readValue(is, type))
                .getOrElseThrow(ExceptionHandler.invalidRequest());
    }

    protected <T> byte[] writeResponse(T response) {
        return Try.of(() -> objectMapper.writeValueAsBytes(response))
                .getOrElseThrow(ExceptionHandler.invalidRequest());
    }

    protected static Headers getHeaders(String key, String value) {
        Headers headers = new Headers();
        headers.set(key, value);
        return headers;
    }
}
