package com.example.demo.Config;

import com.example.demo.Service.RoflanBot;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
@RequiredArgsConstructor
public class BotInitializer {

    private final RoflanBot bot;

    @EventListener(ContextRefreshedEvent.class)
    public void init() throws TelegramApiException {

        TelegramBotsApi telegramBotsApi=new TelegramBotsApi(DefaultBotSession.class);

        try {
            telegramBotsApi.registerBot(bot);
        }
        catch (TelegramApiException e){

        }
    }
}
