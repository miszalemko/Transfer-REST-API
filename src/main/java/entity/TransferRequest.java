package entity;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferRequest {

    private int sourceAccountId;
    private int targetAccountId;
    private String amount;
    private String currency;
    private String reference;
}
