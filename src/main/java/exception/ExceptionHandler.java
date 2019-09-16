package exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import service.Constant;

import java.io.IOException;
import java.io.OutputStream;
import java.util.function.Function;
import java.util.function.Supplier;

public class ExceptionHandler {

    private final ObjectMapper objectMapper;

    public ExceptionHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void handle(Throwable throwable, HttpExchange exchange) {
        try {
            throwable.printStackTrace();
            exchange.getResponseHeaders().set(Constant.CONTENT_TYPE, Constant.APPLICATION_JSON);
            String response = getErrorResponse(throwable, exchange);
            OutputStream responseBody = exchange.getResponseBody();
            responseBody.write(objectMapper.writeValueAsBytes(response));
            responseBody.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getErrorResponse(Throwable throwable, HttpExchange exchange) throws IOException {
        StringBuilder sb = new StringBuilder();
        if (throwable instanceof InvalidRequestException) {
            InvalidRequestException exc = (InvalidRequestException) throwable;
            sb.append(exc.getCode());
            exchange.sendResponseHeaders(400, 0);
        } else if (throwable instanceof MethodNotAllowedException) {
            MethodNotAllowedException exc = (MethodNotAllowedException) throwable;
            sb.append(exc.getCode());
            exchange.sendResponseHeaders(405, 0);
        } else {
            sb.append(500);
            exchange.sendResponseHeaders(500, 0);
        }
        sb.append("\n").append(throwable.getMessage());
        return sb.toString();
    }

    public static Function<? super Throwable, RuntimeException> invalidRequest() {
        return thr -> new InvalidRequestException(400, thr.getMessage());
    }

    public static Supplier<RuntimeException> methodNotAllowed(String message) {
        return () -> new MethodNotAllowedException(405, message);
    }
}
