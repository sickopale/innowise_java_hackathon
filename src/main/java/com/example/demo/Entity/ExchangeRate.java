package com.example.demo.Entity;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class ExchangeRate {
    private int id;
    private String name;
    private BigDecimal priceUsd;
    private Timestamp timestamp;
}
