package com.naumovets.aop;

import com.naumovets.aop.service.AuthService;
import com.naumovets.aop.service.OrderService;
import com.naumovets.aop.service.TakeTime;
import com.naumovets.aop.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@SpringBootApplication
public class AopApplication {

	public static void main(String[] args) throws InterruptedException {
		ConfigurableApplicationContext context = SpringApplication.run(AopApplication.class, args);

		UserService userService = context.getBean(UserService.class);
		OrderService orderService = context.getBean(OrderService.class);
		AuthService authService = context.getBean(AuthService.class);
		TakeTime takeTime = context.getBean(TakeTime.class);

		//@Before, @After, @AfterReturning, @AfterThrowing, @Around
//		log.info(String.valueOf(userService.foo("UserService аргумент")));
//		log.info(orderService.foo("OrderService аргумент"));
//		log.info(authService.foo(null));
//		log.info(userService.bar("UserService аргумент"));

		//@Time
		log.info(takeTime.takeTheTime("Возьми свое время"));
		log.info(userService.bar("Возьми свое время"));
		log.info(orderService.foo("Возьми свое время"));

	}

}
