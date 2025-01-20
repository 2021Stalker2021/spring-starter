package com.dmdev.spring.config;

import com.dmdev.spring.database.entity.Role;
import com.dmdev.spring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.lang.reflect.Proxy;
import java.util.Set;

@Configuration
@EnableMethodSecurity // нужна для работы @@PreAuthorize
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                .csrf().disable() // Отключение CSRF защиты
                .authorizeHttpRequests(urlConfig -> urlConfig
                        .antMatchers("/login", "/users/registration", "/v3/api-docs/**", "/swagger-ui/**").permitAll() // Разрешение доступа к определенным URL без аутентификации
                        // permitAll() - разрешает всем пользователям иметь доступ к этим страницам
                        .antMatchers(HttpMethod.POST, "/users").permitAll()
                        .antMatchers("/users/{\\d+}/delete").hasAuthority(Role.ADMIN.getAuthority())
                        .antMatchers("/admin/**").hasAuthority(Role.ADMIN.getAuthority())
                        .anyRequest().authenticated()
                )
//                .httpBasic(Customizer.withDefaults());
                .logout(logout -> logout
                        .logoutUrl("/logout") //  указывает URL, по которому будет выполняться выход из системы
                        .logoutSuccessUrl("/login") // указывает URL, на который будет перенаправлен пользователь после успешного выхода
                        .deleteCookies("JSESSIONID")) // удаляет cookie с именем JSESSIONID после выхода из системы. Это важно для очистки сессии пользователя
                .formLogin(login -> login
                        .loginPage("/login") // указывает URL страницы входа. Если пользователь пытается получить доступ к защищенному ресурсу без аутентификации, он будет перенаправлен на эту страницу.
                        .defaultSuccessUrl("/users")) // указывает URL, на который будет перенаправлен пользователь после успешного входа.
                .oauth2Login(config -> config
                        .loginPage("/login")
                        .defaultSuccessUrl("/users") // Аутентификация через google
                        .userInfoEndpoint(userInfo -> userInfo.oidcUserService(oidcUserService()))
                );

    }

    private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
        return userRequest -> {
            String email = userRequest.getIdToken().getClaim("email"); // получение почты по токену
            // TODO: 05.01.2025 create user userService.create
            var userDetails = userService.loadUserByUsername(email); // получение пользователя UserDetails по email
//            new OidcUserService().loadUser()
            var oidcUser = new DefaultOidcUser(userDetails.getAuthorities(), userRequest.getIdToken());

            var userDetailsMethods = Set.of(UserDetails.class.getMethods());

            return (OidcUser) Proxy.newProxyInstance(SecurityConfiguration.class.getClassLoader(),
                    new Class[]{UserDetails.class, OidcUser.class},
                    (proxy, method, args) -> userDetailsMethods.contains(method) // Проверяет, принадлежит ли метод интерфейсу UserDetails.
                            ? method.invoke(userDetails, args) // Если метод принадлежит UserDetails, он вызывается у объекта userDetails.
                            : method.invoke(oidcUser, args)); // Если метод не принадлежит UserDetails, он вызывается у объекта oidcUser.
        };
    }
}
