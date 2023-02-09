package com.naumovets.aop.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthService {
    public String foo(String arg) {
        if(arg == null) {
            throw new NullPointerException("Аргумент нулевой");
        }

        log.info("ВЫПОЛНЕНИЕ КОДА ВНУТРИ МЕТОДА");

        return "UserService_args: " + arg;
    }
}
