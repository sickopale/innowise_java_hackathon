package com.example.demo.Config;

import com.example.demo.Service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@EnableScheduling
public class ExchangeRateUpdateTask {

    @Autowired
    private ExchangeRateService exchangeRateUpdateService;

    @Scheduled(fixedRate = 10000)
    public void updateExchangeRates() {
        System.out.println("Updated");
        exchangeRateUpdateService.fetchAndSaveExchangeCurrencyData();
    }
}
