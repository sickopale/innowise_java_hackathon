package com.example.demo.Service;


import com.example.demo.Entity.BotUser;
import com.example.demo.Exception.RepoException;
import com.example.demo.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    public void UserRegistry(String telegramId,String userName) throws RepoException {
        repository.save(makeUser(telegramId,userName));
    }

    private BotUser makeUser(String telegramId,String userName){

        BotUser currentUser=new BotUser();
        currentUser.setUsername(userName);
        currentUser.setTelegramId(telegramId);
        return currentUser;
    }
}
