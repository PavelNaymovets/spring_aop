package com.naumovets.aop.service;

import com.naumovets.aop.aspect.annotation.Timer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Timer
public class TakeTime {

    @Timer
    public String takeTheTime(String arg) {
        return "TakeTheTimeMethod " + arg;
    }
}
