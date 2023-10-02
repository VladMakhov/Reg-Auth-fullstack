package api.security.model.dto;

import lombok.Data;

@Data
public class ExchangeRequest {
    private String userId;
    private String amount;
}
