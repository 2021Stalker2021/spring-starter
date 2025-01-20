package com.dmdev.spring.config;

import com.dmdev.spring.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.envers.repository.config.EnableEnversRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

@EnableJpaAuditing
@EnableEnversRepositories(basePackageClasses = ApplicationRunner.class)
/*
    Аннотация @EnableEnversRepositories используется в Spring Data JPA для активации поддержки Envers в репозиториях.
    Envers — это библиотека, которая предоставляет механизм аудита и версионирования для сущностей в Hibernate.
     basePackageClasses: Указывает, что Envers должен быть активирован для всех репозиториев,
        находящихся в том же пакете, что и класс ApplicationRunner. (com.dmdev.spring)
     Активация Envers: После этого Envers начнет отслеживать изменения в сущностях, помеченных
        аннотацией @Audited, и сохранять их в специальных таблицах.
     Настройка таблиц: Envers создает дополнительные таблицы
        для хранения истории изменений. Эти таблицы имеют префикс REVINFO и REV.
 */
@Configuration
public class AuditConfiguration {

    @Bean
    public AuditorAware<String> auditorAware() {
        // SecurityContext.getCurrentUser().getEmail()

        return () -> Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(authentication -> (UserDetails) authentication.getPrincipal())
                .map(UserDetails::getUsername); // получение имени через которое было установлено в UserDetails
        // authentication.getPrincipal() - получение пользователя
    }
}
