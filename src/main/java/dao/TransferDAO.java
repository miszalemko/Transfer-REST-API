package dao;

import entity.Transfer;

public interface TransferDAO {

    int createTransfer(Transfer transfer);

    Transfer getTransferById(int id);
}
