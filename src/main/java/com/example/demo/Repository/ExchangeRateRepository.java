package com.example.demo.Repository;

import com.example.demo.Entity.ExchangeRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class ExchangeRateRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<ExchangeRate> findAll() {
        return jdbcTemplate.query("SELECT * FROM exchange_rate", (rs, rowNum) -> mapExchangeRate(rs));
    }

    public ExchangeRate findById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM exchange_rate WHERE id = ?", new Object[]{id}, (rs, rowNum) -> mapExchangeRate(rs));
    }

    public ExchangeRate findByName(String name) {
        return jdbcTemplate.queryForObject("SELECT * FROM exchange_rate WHERE name = ?", new Object[]{name}, (rs, rowNum) -> mapExchangeRate(rs));
    }
    public int save(ExchangeRate exchangeRate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO exchange_rate (name, price_usd, timestamp) VALUES (?, ?, ?)", new String[]{"id"});
            ps.setString(1, exchangeRate.getName());
            ps.setBigDecimal(2, exchangeRate.getPriceUsd());
            ps.setTimestamp(3, exchangeRate.getTimestamp());
            return ps;
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    public void update(ExchangeRate exchangeRate) {
        jdbcTemplate.update("UPDATE exchange_rate SET name = ?, price_usd = ?, timestamp = ? WHERE name = ?",
                exchangeRate.getName(), exchangeRate.getPriceUsd(), exchangeRate.getTimestamp(), exchangeRate.getName());
    }

    private ExchangeRate mapExchangeRate(ResultSet rs) throws SQLException {
        if(rs==null)return null;
        else{
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setId(rs.getInt("id"));
        exchangeRate.setName(rs.getString("name"));
        exchangeRate.setPriceUsd(rs.getBigDecimal("price_usd"));
        exchangeRate.setTimestamp(rs.getTimestamp("timestamp"));
        return exchangeRate;}
    }
}
