package com.naumovets.aop.aspect.annotation;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Slf4j
@Component
public class TimerAspect {

    @Pointcut("@within(com.naumovets.aop.aspect.annotation.Timer)")
    public void classesAnnotated() {

    }

    @Pointcut("@annotation(Timer)")
    public void methodAnnotated() {

    }

    @Pointcut("classesAnnotated() || methodAnnotated()")
    public void classesAndMethods() {

    }

    @Around("classesAndMethods()")
    public Object takeTheTime(ProceedingJoinPoint pjp) {
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

    //Достать аргументы из аннотации и отпечатать их, если исполняемые метод выбросил исключение
//    @Around("targetPointcut()")
//    public Object aroundAnnotatedMethodsPointcut(ProceedingJoinPoint pjp) throws Throwable {
//        try {
//            return pjp.proceed();
//        } catch (NullPointerException e) {
//            String className = pjp.getTarget().getClass().getName();
//            String methodName = pjp.getSignature().getName();
//
//            String errorMessage = null;
//            String defaultErrorMessage = extractAnnotationValue(pjp);
//            if (defaultErrorMessage != null && !defaultErrorMessage.isBlank()) {
//                errorMessage = defaultErrorMessage;
//            } else {
//                errorMessage = e.getMessage();
//            }
//            log.error("Во время исполнения {}#{} произошла ошибка: {}", className, methodName, errorMessage);
//            return null;
//        }
//    }
//
//    //Достать аргументы из аннотации
//    private String extractAnnotationValue(ProceedingJoinPoint pjp) {
//        MethodSignature signature = (MethodSignature) pjp.getSignature();
//        Method method = signature.getMethod();
//        Timer annotationOnMethod =
//                method.getAnnotation(Timer.class);
//
//        if (annotationOnMethod != null) {
//            return annotationOnMethod.value();
//        }
//
//        Class<?> beanClass = pjp.getTarget().getClass();
//        Timer annotationOnClass =
//                beanClass.getAnnotation(Timer.class);
//
//        return annotationOnClass.value();
//    }
}
