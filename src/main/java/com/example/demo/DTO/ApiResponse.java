package com.example.demo.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ApiResponse {

    @JsonProperty("coin_id")
    private int cryptocurrencyId;

    @JsonProperty("last")
    private BigDecimal price;

    @JsonProperty("time")
    private long timestamp;

    // Геттеры и сеттеры
}
