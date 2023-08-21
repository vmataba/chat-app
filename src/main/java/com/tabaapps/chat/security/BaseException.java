package com.tabaapps.chat.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class BaseException extends java.lang.Exception {

    private int statusCode;

    private String message;
}
