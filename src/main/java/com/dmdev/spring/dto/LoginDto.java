package com.dmdev.spring.dto;

import lombok.Data;
import lombok.Value;

@Value
public class LoginDto {
    String username; // должен совпадать с name="username" из login.html
    String password; // должен совпадать с name="password" из login.html
}
