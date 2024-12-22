package com.dmdev.spring.integration;

import com.dmdev.spring.database.pool.ConnectionPool;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.SpyBean;

@TestConfiguration // сканирует ApplicationRunner( и ищет аннотацию @SpringBootApplication)
public class TestApplicationRunner {

    @SpyBean(name = "pool1")
    private ConnectionPool pool1;

    /*
    теперь наш ConnectionPool используется в интеграционных тестах благодаря аннотации @IT
     */
}
