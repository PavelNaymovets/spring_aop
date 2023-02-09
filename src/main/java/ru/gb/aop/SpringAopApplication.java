package ru.gb.aop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.gb.aop.service.BusinessService;
import ru.gb.aop.service.SecondService;

@SpringBootApplication
public class SpringAopApplication {

	// AOP - Aspect Oriented Programming

	// Домашнее задание:
	// 0. Разобраться в теме
	// 1. Написать аспект, который логирует в режиме debug вызовы всех методов с их аргументами
	// 2. Создать аннотацию @Timer, которую можно ставить над методами или классами.
	// Написать аспект, который будет обрабатывать все методы, аннотированные данной аннотацией
	// (или все методы класса, над которым стоит эта аннотация) и будет замерять время выполнение этого метода.
	// System.currentTimeMillis() -> pjp.proceed() -> System.currentTimeMillis() -> логируем время исполнения -> return
	// com.package.your-package.YourClassName#methodName: 1200ms
	// ru.gb.store.service.ProductService#createProduct: 580ms

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(SpringAopApplication.class, args);
		BusinessService bean = context.getBean(BusinessService.class);
		System.out.println(bean.foo("argument"));
		System.out.println(bean.foo(null));

		SecondService secondService = context.getBean(SecondService.class);
		System.out.println(secondService.bar(null));

	}

}
