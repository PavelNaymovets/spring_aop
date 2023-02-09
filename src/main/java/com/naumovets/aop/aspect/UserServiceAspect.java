package com.naumovets.aop.aspect;

import jdk.jshell.MethodSnippet;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

@Aspect
@Slf4j
//@Component
public class UserServiceAspect {

    //Aspect - класс, модуль, который содержит сквозную логику.
    //PointCut - (фильтр, срез) набор JoinPoint, к которым будет применен аспект.
    //JoinPoint - точка в работе программы (атрибут, метод, исключение).
    //Advice - совет, в какое время, до, после, или вместо, работы методы вставить сквозную (дополнительную) логику.
    //Возможные Advice: @Before, @After, @AfterThrowing, @AfterReturning, @Around.


    //Определяю поинткат. Пишу фильтр по которому к методам применится сквозная логика
    @Pointcut("execution (* com.naumovets.aop.service.*.*(..))")
    public void UserServiceMethodFoo() {

    }

    //Сквозная логика применится до срабатывания метода
    @Before("UserServiceMethodFoo()")
    public void beforeUserServiceMethodFoo(JoinPoint joinPoint) {
        String method = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());

        log.info("Сработало перед вызовом метода: {}, с аргументами: {}", method, args);
    }

    //Сквозная логика применится после срабатывания метода
    @After("UserServiceMethodFoo()")
    public void afterUserServiceMethodFoo() {
        log.info("Сработало после вызова метода foo()");
    }

    //Сквозная логика применится после возврата методом какого-то результата
    @AfterReturning(
            pointcut = "UserServiceMethodFoo()",
            returning = "result"
    )
    public void afterReturningMethod(JoinPoint joinPoint, List<String> result) {
        result.set(0, "Новая строка из AfterBefore");
    }

    //Сквозная логика применится после выбрасывания исключения
    @AfterThrowing("UserServiceMethodFoo()")
    public void afterReturningUserServiceMethodFoo(JoinPoint pjp) {
        log.error("Метод foo() сработал с ошибкой");
    }

    //Сквозная логика применится после выбрасывания исключения с дальнешим логированием этого исключения
    @AfterThrowing(
            pointcut = "UserServiceMethodFoo()",
            throwing = "result")
    public void afterThrowingUserServiceMethodFoo(JoinPoint jp, Throwable exec) {
        log.error("Метод foo() сработал с ошибкой: {}", exec.getMessage());
    }

    //Сквозная логика применится во время работы метода
    @Around("UserServiceMethodFoo()")
    public Object aroundUserServiceMethodFoo(ProceedingJoinPoint pjp) {
        try {
            Object obj = pjp.proceed();
            return obj;
        } catch (Throwable e) {
            log.error("Метод foo() отработал с ошибкой: {}", e.getMessage());
            return null;
        }
    }

    //Сквозная логика применится во время работы метода
    @Around("UserServiceMethodFoo()")
    public Object aroundUserServiceMethodBar(ProceedingJoinPoint pjp) {
        try {
            //Reflection
            MethodSignature signature = (MethodSignature) pjp.getSignature();
            Method method = signature.getMethod();

            Long start = System.currentTimeMillis();
            //Вызов метода, который попал в поинткат(фильтр)
            Object obj = pjp.proceed();
            Long finish = System.currentTimeMillis();
            Long duration = finish - start;

            log.info("Время работы метода:{}, завершилось со временем (мс): {}", method, duration);

            //Всегда нужно указывать return, даже если метод ничего не возвращает (void)
            return obj;
        } catch (Throwable e) {
            log.error("Метод bar() отработал с ошибкой: {}", e.getMessage());

            return null;
        }
    }

    //Сквозная логика применится во время работы метода
    @Around("UserServiceMethodFoo()")
    public Object aroundUserServiceMethodBarAnother(ProceedingJoinPoint pjp) {
        try {
            //ProceedJoinPont
            String className = pjp.getTarget().getClass().getName();
            String method = pjp.getSignature().getName();

            Long start = System.currentTimeMillis();
            Object obj = pjp.proceed();
            Long finish = System.currentTimeMillis();
            Long duration = finish - start;

            log.info("Время работы метода: {}#{}, завершилось со временем (мс): {}", className, method, duration);

            return obj;
        } catch (Throwable e) {
            log.error("Метод bar() отработал с ошибкой: {}", e.getMessage());

            return null;
        }
    }

}
