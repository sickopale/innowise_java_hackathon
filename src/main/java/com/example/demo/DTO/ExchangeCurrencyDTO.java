package com.example.demo.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ExchangeCurrencyDTO {
    @JsonProperty("symbol")
    private String symbol;

    @JsonProperty("price")
    private String price;

    // Геттеры и сеттеры
}
