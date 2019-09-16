package entity;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransferResponse {

    private int id;
    private Date createdAt;
}
