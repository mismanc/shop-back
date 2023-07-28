package com.simple.webshop.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CardDTO {

    @NotBlank
    private String cardNumber;

    @NotBlank
    private String expirationDate;

}
