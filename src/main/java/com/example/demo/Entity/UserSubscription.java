package com.example.demo.Entity;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class UserSubscription {
    private int id;
    private String userId;
    private String cryptocurrencyId;
    private int thresholdPercent;
    private Timestamp createdAt;
}
