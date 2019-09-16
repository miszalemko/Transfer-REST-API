package entity;

import entity.enums.Currency;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transfer {

    private int id;
    private int sourceAccountId;
    private int targetAccountId;
    private BigDecimal amount;
    private Currency currency;
    private String reference;
    private Date createdAt;
}