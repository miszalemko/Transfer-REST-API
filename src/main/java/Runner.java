import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpServer;
import controller.TransferController;
import dao.TransferInMemoryDAO;
import entity.Transfer;
import entity.enums.Currency;
import exception.ExceptionHandler;
import service.TransferAdapter;
import service.TransferService;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.InetSocketAddress;

public class Runner {

    public static void main(String[] args) throws IOException {
        addTestData();
        int serverPort = 8000;
        HttpServer server = HttpServer.create(new InetSocketAddress(serverPort), 0);

        TransferController transferController = new TransferController(new TransferService(new TransferInMemoryDAO()), new ObjectMapper(),
                new ExceptionHandler(new ObjectMapper()), new TransferAdapter());
        server.createContext("/transfers", transferController::handle);
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    private static void addTestData() {
        Transfer transfer = Transfer.builder()
                .sourceAccountId(2)
                .targetAccountId(1)
                .amount(new BigDecimal("50.00"))
                .currency(Currency.valueOf("PLN"))
                .reference("desc").build();
        TransferService transferService = new TransferService(new TransferInMemoryDAO());
        transferService.createTransfer(transfer);
    }

}
