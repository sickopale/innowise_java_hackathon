package com.example.demo.Repository;

import com.example.demo.Entity.BotUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<BotUser> findAll() {
        return jdbcTemplate.query("SELECT * FROM bot_user", (rs, rowNum) -> mapUser(rs));
    }

    public BotUser findById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM bot_user WHERE id = ?",
                new Object[]{id}, (rs, rowNum) -> mapUser(rs));
    }

    public BotUser findByTelegramId(String telegramId) {
        return jdbcTemplate.queryForObject("SELECT * FROM bot_user WHERE telegram_id = ?",
                new Object[]{telegramId}, (rs, rowNum) -> mapUser(rs));
    }

    public void save(BotUser user) {
        jdbcTemplate.update("INSERT INTO bot_user (telegram_id, username) VALUES (?, ?)",
                user.getTelegramId(), user.getUsername());
    }

    public void update(BotUser user) {
        jdbcTemplate.update("UPDATE bot_user SET telegram_id = ?, username = ? WHERE id = ?",
                user.getTelegramId(), user.getUsername(), user.getId());
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM bot_user WHERE id = ?", id);
    }

    private BotUser mapUser(ResultSet rs) throws SQLException {
        if(rs==null) return null;
        else{
        BotUser user = new BotUser();
        user.setId(rs.getInt("id"));
        user.setTelegramId(rs.getString("telegram_id"));
        user.setUsername(rs.getString("username"));
        return user;}
    }
}
