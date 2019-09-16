package dao;

import entity.Transfer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TransferInMemoryDAO implements TransferDAO {

    private static final Map<Integer, Transfer> TRANSFER_STORAGE = new ConcurrentHashMap();
    private static int maxId = 0;

    @Override
    public int createTransfer(Transfer transfer) {
        if(transfer != null) {
            maxId++;
            Transfer transf = Transfer.builder()
                    .id(maxId)
                    .sourceAccountId(transfer.getSourceAccountId())
                    .targetAccountId(transfer.getTargetAccountId())
                    .amount(transfer.getAmount())
                    .currency(transfer.getCurrency())
                    .reference(transfer.getReference())
                    .createdAt(transfer.getCreatedAt()).build();
            TRANSFER_STORAGE.put(maxId, transf);
            return maxId;
        }
        return 0;
    }

    @Override
    public Transfer getTransferById(int id) {
        return TRANSFER_STORAGE.get(id);
    }
}
