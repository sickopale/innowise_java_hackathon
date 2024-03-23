package com.example.demo.Service;

import com.example.demo.Exception.RepoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
@RequiredArgsConstructor
public class MessageService {

    private enum BotState {
        DEFAULT,
        AWAITING_CURRENCY_NAME,
        AWAITING_PERCENT
    }

    private BotState currentState = BotState.DEFAULT;

    private final UserService userService;

    private final SubsService subsService;
    private final ExchangeRateService rateService;

    public SendMessage makeMessage(long chatId,String text) throws TelegramApiException {
        SendMessage message=new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        return message;
    }

    public SendMessage requestPercent(long chatId,String name) throws TelegramApiException {
        subsService.setCurrentName(name);
        return makeMessage(chatId,"Please send the percent");
    }
    public SendMessage requestCurrencyName(long chatId) throws TelegramApiException {
        return makeMessage(chatId,"Please send name of currency");
    }
    public SendMessage onStart(long chatId) throws TelegramApiException {
        return makeMessage(chatId,"Hello<3");
    }
    public SendMessage notRecognizedMessage(long chatId) throws TelegramApiException {
        return makeMessage(chatId,"Sorry, command is not found");
    }
    public SendMessage onRegister(long chatId,String userName) throws TelegramApiException {
        try{
            userService.UserRegistry(""+chatId,userName);
            return makeMessage(chatId,"You are registered successfully");
        }
        catch (RepoException e) {
            return makeMessage(chatId,"You are already registered");
            //throw new RuntimeException(e);
        }
    }

    public SendMessage onCurrencyRate(long chatId) throws TelegramApiException {

        return makeMessage(chatId,"All rates "+"\n"+rateService.findAll());
    }

    public SendMessage onCurrencyFollow(long chatId,int percent) throws TelegramApiException {

            subsService.doSub(chatId,subsService.getCurrentName(),percent);
            return makeMessage(chatId,"Now you follow " + subsService.getCurrentName());
    }
    public SendMessage onUpdate(Update update) throws TelegramApiException {
        if(update.hasMessage()&&update.getMessage().hasText()){

            String textMessage=update.getMessage().getText();
            long chatId=update.getMessage().getChatId();

            switch (textMessage){
                case "/start":
                    try {
                        return onStart(chatId);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                case "/current":
                    try{
                        return onCurrencyRate(chatId);

                    }catch (TelegramApiException e){

                    }
                case "/follow":try{
                    currentState = BotState.AWAITING_CURRENCY_NAME; // Установка состояния ожидания названия валюты
                    return requestCurrencyName(chatId);

                }catch (TelegramApiException e){

                }
                case "/register":try {
                    return onRegister(chatId,update.getMessage().getChat().getFirstName());
                } catch (TelegramApiException e){
                    //throw new RuntimeException(e);
                }
                break;
                default:
                    try {
                    if (currentState == BotState.AWAITING_CURRENCY_NAME) {
                        currentState = BotState.AWAITING_PERCENT;
                        return requestPercent(chatId,textMessage);
                    } else
                    if (currentState == BotState.AWAITING_PERCENT) {
                        int percent = Integer.valueOf(textMessage);
                        currentState = BotState.DEFAULT;
                        return onCurrencyFollow(chatId,percent);
                    }
                        return notRecognizedMessage(chatId);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }

            }
        }
        return null;
    }
}
