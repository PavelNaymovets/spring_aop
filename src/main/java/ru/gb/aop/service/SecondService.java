package ru.gb.aop.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.gb.aop.aspect.DoNotThrowNullPointerException;

@Slf4j
@Service
@DoNotThrowNullPointerException
public class SecondService {

    public String bar(String arg) {
        if (arg == null) {
            throw new NullPointerException("Аргумент не должен быть null");
        }

        log.info("ВЫПОЛНЕНИЕ КОДА ВНУТРИ МЕТОДА");
        return "SecondService_" + arg;
    }

}
