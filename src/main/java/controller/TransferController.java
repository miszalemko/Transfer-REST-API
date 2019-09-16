package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import entity.Response;
import entity.Transfer;
import entity.TransferRequest;
import entity.TransferResponse;
import entity.enums.StatusCode;
import exception.ExceptionHandler;
import service.Constant;
import service.TransferAdapter;
import service.TransferService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

public class TransferController extends  Controller {

    private final TransferService transferService;
    private final TransferAdapter transferAdapter;

    public TransferController(TransferService transferService, ObjectMapper objectMapper,
                              ExceptionHandler exceptionHandler, TransferAdapter transferAdapter) {
        super(objectMapper, exceptionHandler);
        this.transferService = transferService;
        this.transferAdapter = transferAdapter;
    }

    @Override
    protected void execute(HttpExchange exchange) throws IOException {
        byte[] response;
        if ("POST".equals(exchange.getRequestMethod())) {
            Response e = doPost(exchange.getRequestBody());
            exchange.getResponseHeaders().putAll(e.getHeaders());
            exchange.sendResponseHeaders(e.getStatusCode().getCode(), 0);
            response = super.writeResponse(e.getBody());
        } else if ("GET".equals(exchange.getRequestMethod())) {
            Response e = doGet(exchange.getRequestURI().getPath());
            exchange.getResponseHeaders().putAll(e.getHeaders());
            exchange.sendResponseHeaders(e.getStatusCode().getCode(), 0);
            response = super.writeResponse(e.getBody());
        } else {
            throw ExceptionHandler.methodNotAllowed(
                    "Method " + exchange.getRequestMethod() + " is not allowed for " + exchange.getRequestURI()).get();
        }

        OutputStream os = exchange.getResponseBody();
        os.write(response);
        os.close();
    }

    private Response doPost(InputStream is) {
        TransferRequest transferReq = super.readRequest(is, TransferRequest.class);
        Transfer transfer = transferAdapter.mapTransferRequestToTransfer(transferReq);
        transfer.setCreatedAt(new Date());
        int transferId = transferService.createTransfer(transfer);

        TransferResponse response = new TransferResponse(transferId, transfer.getCreatedAt());
        return new Response<>(response,
                getHeaders(Constant.CONTENT_TYPE, Constant.APPLICATION_JSON), StatusCode.OK);
    }

    private Response doGet(String path) {
        String idStr = path.substring(path.lastIndexOf("/") + 1);
        Transfer transfer = transferService.getTransferById(Integer.parseInt(idStr));
        return new Response<>(transfer,
                getHeaders(Constant.CONTENT_TYPE, Constant.APPLICATION_JSON), StatusCode.OK);
    }
}
