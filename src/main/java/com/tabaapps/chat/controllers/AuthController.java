package com.tabaapps.chat.controllers;

import com.tabaapps.chat.models.User;
import com.tabaapps.chat.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@RestController
@RequestMapping(path = "/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @PostMapping(path = "/signup")
    public ResponseEntity<?> signup(@Validated @RequestBody User user){
        return ResponseEntity.ok(authService.signup(user));
    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> login(@RequestHeader("Authorization") String credentials) throws Exception{
        byte[] credentialsBytes = Base64.getDecoder().decode(credentials.replace("Basic ",""));
        String textCredentials = new String(credentialsBytes);
        String[] textCredentialsBundle = textCredentials.split(":");
        return ResponseEntity.ok(this.authService.login(textCredentialsBundle[0],textCredentialsBundle[1]));
    }

}
