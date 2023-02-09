package ru.gb.aop.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Aspect
@Component
public class DoNotThrowNullPointerExceptionAspect {

    @Pointcut("@within(ru.gb.aop.aspect.DoNotThrowNullPointerException)")
    public void classesAnnotatedWithPointcut() {

    }

    @Pointcut("@annotation(DoNotThrowNullPointerException)")
    public void methodAnnotatedWithPointcut() {

    }

    @Pointcut("classesAnnotatedWithPointcut() || methodAnnotatedWithPointcut()")
    public void targetPointcut() {

    }

    @Around("targetPointcut()")
    public Object aroundAnnotatedMethodsPointcut(ProceedingJoinPoint pjp) throws Throwable {
        try {
            return pjp.proceed();
        } catch (NullPointerException e) {
            String className = pjp.getTarget().getClass().getName();
            String methodName = pjp.getSignature().getName();

            String errorMessage = null;
            String defaultErrorMessage = extractAnnotationValue(pjp);
            if (defaultErrorMessage != null && !defaultErrorMessage.isBlank()) {
                errorMessage = defaultErrorMessage;
            } else {
                errorMessage = e.getMessage();
            }
            log.error("Во время исполнения {}#{} произошла ошибка: {}", className, methodName, errorMessage);
            return null;
        }
    }

    private String extractAnnotationValue(ProceedingJoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        DoNotThrowNullPointerException annotationOnMethod =
                method.getAnnotation(DoNotThrowNullPointerException.class);

        if (annotationOnMethod != null) {
            return annotationOnMethod.value();
        }

        Class<?> beanClass = pjp.getTarget().getClass();
        DoNotThrowNullPointerException annotationOnClass =
                beanClass.getAnnotation(DoNotThrowNullPointerException.class);

        return annotationOnClass.value();
    }

}
