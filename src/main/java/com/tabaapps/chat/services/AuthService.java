package com.tabaapps.chat.services;

import com.tabaapps.chat.models.User;
import com.tabaapps.chat.repositories.UserRepository;
import com.tabaapps.chat.security.BaseException;
import com.tabaapps.chat.security.SecurityConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public User signup(User user){
        return this.userRepository.save(user);
    }
    public User login(String username, String password) throws BaseException {
        User user = this.userRepository.findByEmail(username).orElseThrow(() -> new BaseException(HttpStatus.BAD_REQUEST.value(),"Incorrect username or password"));
        if (!SecurityConfiguration.passwordEncoder.matches(password,user.getPassword())) {
            throw new BaseException(HttpStatus.BAD_REQUEST.value(), "Incorrect username or password");
        }
        user.setOnline(true);
        user.setUpdatedAt(LocalDateTime.now());
        user.setUpdatedBy(user);
        return this.userRepository.save(user);
    }


    public boolean logout(String username) throws BaseException{
        User user = this.userRepository.findByEmail(username).orElseThrow(() -> new BaseException(HttpStatus.BAD_REQUEST.value(),"User does not exist"));
        user.setOnline(false);
        user.setUpdatedAt(LocalDateTime.now());
        user.setUpdatedBy(user);
        userRepository.save(user);
        return true;
    }

}
