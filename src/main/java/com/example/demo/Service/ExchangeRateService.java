package com.example.demo.Service;

import com.example.demo.DTO.ExchangeCurrencyDTO;
import com.example.demo.Entity.ExchangeRate;
import com.example.demo.Repository.ExchangeRateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExchangeRateService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    public String findAll( ){
        return exchangeRateRepository.findAll().toString();
    }

    public void fetchAndSaveExchangeCurrencyData() {

        String apiUrl = "https://api.mexc.com/api/v3/ticker/price";
        String json = restTemplate.getForObject(apiUrl, String.class);

        try {
            ObjectMapper mapper = new ObjectMapper();
            List<ExchangeCurrencyDTO> data = mapper.readValue(json, new TypeReference<List<ExchangeCurrencyDTO>>() {});

            for (ExchangeCurrencyDTO currencyData : data) {
                ExchangeRate exchangeCurrency = new ExchangeRate();
                exchangeCurrency.setName(currencyData.getSymbol());
                exchangeCurrency.setPriceUsd(new BigDecimal(currencyData.getPrice()));
                exchangeCurrency.setTimestamp(new Timestamp(System.currentTimeMillis()));
                exchangeRateRepository.update(exchangeCurrency);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
