package com.dmdev.logging.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class CommonPointcuts {

    /*
            @within - проверяет анотацию над классом
         */
    @Pointcut("@within(org.springframework.stereotype.Controller)")
    public void isControllerLayer() {
    }

    /*
        within без символа @ - проверяет не аннотацию над классом а именно Класс.
        com.dmdev.spring.service.* - * означает что мы хотим проверять все 3 сервиса в пакете service.
        Если у нас есть ещё и подкаталоги, то мы должны добавлять две точки ..*
        C мопощью * мы можем задавать префиксы и суфиксы, например *Service будет искать все
        классы которые заканчиваются на Service, или Service* будет искать классы которые будут
        начинатся на Service.
     */
    @Pointcut("within(com.dmdev.*.service.*Service)")
    public void isServiceLayer() {
    }
}
