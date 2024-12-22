package com.dmdev.spring.integration.service;

import com.dmdev.spring.ApplicationRunner;
import com.dmdev.spring.config.DatabaseProperties;
import com.dmdev.spring.database.entity.Company;
import com.dmdev.spring.dto.CompanyReadDto;
import com.dmdev.spring.integration.annotation.IT;
import com.dmdev.spring.listener.entity.EntityEvent;
import com.dmdev.spring.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@IT
@RequiredArgsConstructor
//@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL) // позволяет внедрить зависимости через конструктор для всех полей
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = ApplicationRunner.class, initializers = ConfigDataApplicationContextInitializer.class)
public class CompanyServiceIT {

    private static final Integer COMPANY_ID = 1;


    private final CompanyService companyService;
    private final DatabaseProperties databaseProperties;

    @Test
    void findById() {


        var actualResult = companyService.findById(COMPANY_ID);

        assertTrue(actualResult.isPresent()); // актуальный результат присутствует в переменной

        var expectedResult = new CompanyReadDto(COMPANY_ID, null); // ожидаемый резаультат
        actualResult.ifPresent(actual -> assertEquals(expectedResult, actual));
    }
}

/*
- Используя `@ExtendWith(SpringExtension.class)`, вы указываете JUnit 5 запускать тесты в контексте Spring. Это означает, что
 Spring будет обрабатывать внедрение зависимостей,
  управление транзакциями и другие функции управления контекстом во время тестов
  -----------------------------------------------------------------------------------
  Аннотация `@ContextConfiguration` используется для указания конфигурации контекста Spring, который должен быть загружен для тестов. Она может
   быть полезна, когда вам нужно настроить специфический контекст вместо
   автоматической загрузки полного контекста приложения, что может ускорить
    время тестирования и изолировать тестируемые компоненты.
    1. **`classes = ApplicationRunner.class`**:
- Этот параметр указывает классы конфигурации Spring, которые должны быть использованы
 для загрузки контекста. В данном случае, это указание `ApplicationRunner.class` означает, что этот
  класс содержит конфигурацию, которую Spring должен использовать для настройки ApplicationContext.

2. **`initializers = ConfigDataApplicationContextInitializer.class`**:
- Этот параметр указывает инициализаторы контекста, которые будут
 выполнены перед тем, как ApplicationContext будет полностью создан. `ConfigDataApplicationContextInitializer` – это один из стандартных
  инициализаторов Spring, который загружает конфигурационные данные (например, свойства из файлов приложений). В нашем случае - .yml
 */
