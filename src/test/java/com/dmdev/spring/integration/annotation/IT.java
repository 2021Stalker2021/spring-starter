package com.dmdev.spring.integration.annotation;

import com.dmdev.spring.integration.TestApplicationRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ActiveProfiles("test") // Используем application-test.yml в качестве конфигурационного файла в тестах,
// он также подтягивает оригинальный application.yml
@SpringBootTest(classes = TestApplicationRunner.class) // переиспользуем каждый раз этот класс в интеграционных тестах
@Transactional
public @interface IT {
}
