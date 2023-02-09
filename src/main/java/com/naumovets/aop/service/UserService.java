package com.naumovets.aop.service;

import com.naumovets.aop.aspect.annotation.Timer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Timer //самописная аннотация для измерения времени работы метода. Работает, если ее поставить и перед классом и перед методом
public class UserService {

    public List<String> foo(String arg) {
        if(arg == null) {
            throw new NullPointerException("Аргумент нулевой");
        }

        log.info("ВЫПОЛНЕНИЕ КОДА ВНУТРИ МЕТОДА");

        List<String> arr = new ArrayList<>();
        arr.add(arg);

        return arr;
    }

    public String bar(String arg) throws InterruptedException {
        return "UserService_args: " + arg;
    }

}
