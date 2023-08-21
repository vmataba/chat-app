package com.tabaapps.chat.controllers;

import com.tabaapps.chat.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public class MainController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String showIndexPage(){
        return ("<h1 style='text-align:center; font-family: Tahoma'>You are welcome to Chat APP</h1>");
    }

    @GetMapping("/all-users")
    public ResponseEntity<?> showUsers(){
        return ResponseEntity.ok(this.userRepository.findAll());
    }
}
