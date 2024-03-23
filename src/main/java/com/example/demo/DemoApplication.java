package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) throws TelegramApiException {
		SpringApplication.run(DemoApplication.class, args);

	}

}
