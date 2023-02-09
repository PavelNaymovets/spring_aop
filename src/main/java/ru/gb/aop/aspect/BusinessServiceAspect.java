package ru.gb.aop.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Slf4j
@Aspect
//@Component
public class BusinessServiceAspect {

    // JoinPoint - какое-то место исполнения в программе (например, метод)
    // Pointcut - предикат, по которому отфильтровываются нужные join-point'ы
    // Advice (совет) - описание того, как нужно обработать join-point
    // Advice: before, afterThrowing, afterReturning, after; around
    // before -> [afterThrowing || afterReturning] -> after
    // around

    @Pointcut("execution (* ru.gb.aop.service.BusinessService.*(..))")
    public void businessServiceFooMethodPointcut() {

    }

    @Around("businessServiceFooMethodPointcut()")
    public Object aroundBusinessServiceFooMethod(ProceedingJoinPoint pjp) throws Throwable {
        try {
            return pjp.proceed();
        } catch (NullPointerException e) {
            log.error("Во время вызова метода #foo произошла ошибка: {}", e.getMessage());
            return null;
        }
    }

    @Before("businessServiceFooMethodPointcut()")
    public void beforeBusinessServiceFooMethod(JoinPoint jp) {
        String arg = (String) jp.getArgs()[0];
        log.info("Метод #foo вызван с аргументом {}", arg);
    }

    @AfterThrowing("businessServiceFooMethodPointcut()")
    public void afterThrowingBusinessServiceFooMethod() {
        log.info("Выполнени метода #foo завершено с ошибкой");
    }

    @AfterReturning("businessServiceFooMethodPointcut()")
    public void afterReturningBusinessServiceFooMethod() {
        log.info("Выполнени метода #foo завершено успешно");
    }

    @After("businessServiceFooMethodPointcut()")
    public void afterBusinessServiceFooMethod() {
        log.info("Выполнени метода #foo завершено");
    }


}
