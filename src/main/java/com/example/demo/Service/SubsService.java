package com.example.demo.Service;


import com.example.demo.Entity.BotUser;
import com.example.demo.Entity.UserSubscription;
import com.example.demo.Exception.RepoException;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Repository.UserSubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class SubsService {
    private String currentName;

    private final UserSubscriptionRepository repository;

    public void doSub(long chatId,String currencyName,int percent){
        repository.save(makeSubs(chatId,currencyName,percent));

    }

    public String getCurrentName() {
        return currentName;
    }

    public void setCurrentName(String currentName) {
        this.currentName = currentName;
    }
    UserSubscription makeSubs(long chatId, String currencyName, int percent){

        UserSubscription subscription=new UserSubscription();
        subscription.setUserId(String.valueOf(chatId));
        subscription.setCryptocurrencyId(currencyName);
        subscription.setThresholdPercent(percent);
        subscription.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        return subscription;
    }
}
