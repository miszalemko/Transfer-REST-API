package service

import entity.Transfer
import entity.TransferRequest
import entity.enums.Currency
import spock.lang.Specification

class TransferAdapterTest extends Specification {

    private TransferAdapter transferAdapter

    void setup() {
        transferAdapter = new TransferAdapter()
    }

    def "shouldMapTransferRequestToTransfer"() {
        given:
        TransferRequest transferRequest = TransferRequest.builder()
                .sourceAccountId(1).targetAccountId(2)
                .amount("100.00").currency("EUR").reference("Test").build()

        when:
        Transfer transfer  = transferAdapter.mapTransferRequestToTransfer(transferRequest)

        then:
        assert transfer.sourceAccountId == 1
        assert transfer.targetAccountId == 2
        assert transfer.amount == new BigDecimal("100.00")
        assert transfer.currency == Currency.valueOf("EUR")
        assert transfer.reference == "Test"
    }

    def "shouldReturnNull"() {
        given:
        TransferRequest transferRequest = null

        when:
        Transfer transfer  = transferAdapter.mapTransferRequestToTransfer(transferRequest)

        then:
        assert transfer == null
    }
}
