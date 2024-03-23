package com.example.demo.Repository;

import com.example.demo.Entity.UserSubscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserSubscriptionRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<UserSubscription> findAll() {
        return jdbcTemplate.query("SELECT * FROM user_subscription", this::mapRowToUserSubscription);
    }


    public UserSubscription findById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM user_subscription WHERE id = ?", this::mapRowToUserSubscription, id);
    }


    public void save(UserSubscription userSubscription) {
        jdbcTemplate.update("INSERT INTO user_subscription (user_id, cryptocurrency_id, threshold_percent, created_at) VALUES (?, ?, ?, ?)",
                userSubscription.getUserId(), userSubscription.getCryptocurrencyId(), userSubscription.getThresholdPercent(), userSubscription.getCreatedAt());
    }


    public void update(UserSubscription userSubscription) {
        jdbcTemplate.update("UPDATE user_subscription SET user_id = ?, cryptocurrency_id = ?, threshold_percent = ?, created_at = ? WHERE id = ?",
                userSubscription.getUserId(), userSubscription.getCryptocurrencyId(), userSubscription.getThresholdPercent(), userSubscription.getCreatedAt(), userSubscription.getId());
    }


    public void delete(UserSubscription userSubscription) {
        jdbcTemplate.update("DELETE FROM user_subscription WHERE id = ?", userSubscription.getId());
    }

    private UserSubscription mapRowToUserSubscription(ResultSet rs, int rowNum) throws SQLException {
        UserSubscription userSubscription = new UserSubscription();
        userSubscription.setId(rs.getInt("id"));
        userSubscription.setUserId(rs.getString("user_id"));
        userSubscription.setCryptocurrencyId(rs.getString("cryptocurrency_id"));
        userSubscription.setThresholdPercent(rs.getInt("threshold_percent"));
        userSubscription.setCreatedAt(rs.getTimestamp("created_at"));
        return userSubscription;
    }
}
