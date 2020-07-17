package com.db.awmd.challenge.domain;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class FundTransfer {

    @NotNull
    @NotEmpty
    private String accountFromId;

    @NotNull
    @NotEmpty
    private String accountToId;

    @NotNull
    @Min(value = 1, message = "Transfer amount value should not be negative.")
    private BigDecimal amount;

    @JsonCreator
    public FundTransfer(@JsonProperty("accountFromId") String accountFromId,
                    @JsonProperty("accountToId") String accountToId,
                    @JsonProperty("amount") BigDecimal amount){
        this.accountFromId = accountFromId;
        this.accountToId = accountToId;
        this.amount = amount;
    }

}
