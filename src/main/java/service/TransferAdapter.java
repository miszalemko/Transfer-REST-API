package service;

import entity.Transfer;
import entity.TransferRequest;
import entity.enums.Currency;

import java.math.BigDecimal;

public class TransferAdapter {

    public Transfer mapTransferRequestToTransfer(TransferRequest transferReq) {
        return transferReq != null ? Transfer.builder()
                .sourceAccountId(transferReq.getSourceAccountId())
                .targetAccountId(transferReq.getTargetAccountId())
                .amount(new BigDecimal(transferReq.getAmount()))
                .currency(Currency.valueOf(transferReq.getCurrency()))
                .reference(transferReq.getReference()).build() : null;
    }
}
