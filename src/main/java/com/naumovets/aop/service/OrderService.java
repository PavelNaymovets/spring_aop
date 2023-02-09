package com.naumovets.aop.service;

import com.naumovets.aop.aspect.annotation.Timer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Timer //самописная аннотация для измерения времени работы метода. Работает, если ее поставить и перед классом и перед методом
public class OrderService {
    public String foo(String arg) {
        if(arg == null) {
            throw new NullPointerException("Аргумент нулевой");
        }

        log.info("ВЫПОЛНЕНИЕ КОДА ВНУТРИ МЕТОДА");

        return "UserService_args: " + arg;
    }
}
