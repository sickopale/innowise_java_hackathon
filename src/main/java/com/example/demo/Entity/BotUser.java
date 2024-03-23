package com.example.demo.Entity;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Data
public class BotUser {
    private int id;
    private String telegramId;
    private String username;

}
