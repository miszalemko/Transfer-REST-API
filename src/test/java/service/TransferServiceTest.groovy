package service

import dao.TransferInMemoryDAO
import entity.Transfer
import entity.enums.Currency
import spock.lang.Specification

class TransferServiceTest extends Specification {

    private TransferService transferService
    private Transfer transfer

    void setup() {
        transferService = new TransferService(new TransferInMemoryDAO())
        transfer = Transfer.builder()
                .sourceAccountId(1).targetAccountId(2)
                .amount(new BigDecimal("100.00")).currency(Currency.valueOf("EUR")).reference("test")
                .createdAt(new Date(2019, 9, 14, 14, 10, 10)).build()
    }

    def "shouldCreateTransfer"() {
        given:
        Transfer transfer = transfer

        when:
        int transferId = transferService.createTransfer(transfer)

        then:
        assert transferId != 0
    }

    def "shouldNotCreateTransfer"() {
        given:
        Transfer transfer = null

        when:
        int transferId = transferService.createTransfer(transfer)

        then:
        assert transferId == 0
    }

    def "shouldGetTransferById"() {
        given:
        Transfer transf = transfer
        int transferId = transferService.createTransfer(transf)

        when:
        Transfer transfer = transferService.getTransferById(transferId)

        then:
        assert transfer.id == transferId
        assert transfer.sourceAccountId == 1
        assert transfer.targetAccountId == 2
        assert transfer.amount == new BigDecimal("100.00")
        assert transfer.currency == Currency.valueOf("EUR")
        assert transfer.reference == "test"
        assert transfer.createdAt == new Date(2019, 9, 14, 14, 10, 10)
    }

    def "shouldNotGetTransferById"() {
        given:
        int transferId = Integer.MAX_VALUE

        when:
        Transfer transfer = transferService.getTransferById(transferId)

        then:
        assert transfer == null
    }
}
