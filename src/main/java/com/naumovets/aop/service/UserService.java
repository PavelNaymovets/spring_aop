package com.naumovets.aop.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
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
