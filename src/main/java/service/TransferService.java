package service;

import dao.TransferDAO;
import entity.Transfer;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TransferService {

    private TransferDAO transferDAO;

    public int createTransfer(Transfer transfer) {
        return transferDAO.createTransfer(transfer);
    }

    public Transfer getTransferById(int id) {
        return transferDAO.getTransferById(id);
    }
}
