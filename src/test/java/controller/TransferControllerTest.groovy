package controller

import com.fasterxml.jackson.databind.ObjectMapper
import entity.Transfer
import entity.TransferResponse
import entity.enums.Currency
import spock.lang.Specification

class TransferControllerTest extends Specification {

    private ObjectMapper mapper

    void setup() {
        mapper = new ObjectMapper()
    }

    def "shouldDoPost"() {
        given:
        String command = "curl -X POST localhost:8000/transfers -d \"{\"\"\"sourceAccountId\"\"\" : \"\"\"1\"\"\" , \"\"\"targetAccountId\"\"\" : \"\"\"2\"\"\" , \"\"\"amount\"\"\" : \"\"\"100.00\"\"\" , \"\"\"currency\"\"\" : \"\"\"EUR\"\"\" , \"\"\"reference\"\"\" : \"\"\"test\"\"\"}\""

        when:
        Process process = Runtime.getRuntime().exec(command)
        TransferResponse response = mapper.readValue(process.getInputStream(), TransferResponse.class)
        process.destroy()

        then:
        response != null
    }

    def "shouldDoGet"() {
        given:
        String command = "curl -X GET localhost:8000/transfers/1"

        when:
        Process process = Runtime.getRuntime().exec(command)
        Transfer tr = mapper.readValue(process.getInputStream(), Transfer.class)
        process.destroy()

        then:
        assert tr.sourceAccountId == 2
        assert tr.targetAccountId == 1
        assert tr.amount == new BigDecimal("50.00")
        assert tr.currency == Currency.valueOf("PLN")
        assert tr.reference == "desc"
    }

    def "shouldReturnMethodNotAllowedExceptionMessage"() {
        given:
        String command = "curl -X PUT localhost:8000/transfers"

        when:
        Process process = Runtime.getRuntime().exec(command)
        byte[] result = new byte[1024]
        process.getInputStream().read(result)
        String responseMsg = new String(result)
        process.destroy()

        then:
        assert responseMsg.contains("405")
        assert responseMsg.contains("Method PUT is not allowed for /transfers")
    }
}
