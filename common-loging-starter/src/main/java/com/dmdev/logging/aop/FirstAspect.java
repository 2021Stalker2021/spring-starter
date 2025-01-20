package com.dmdev.logging.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Aspect
public class FirstAspect {

    /*
        execution(modifiers-pattern? ret-type-pattern declaring-type-pattern?name-pattern(param-pattern) throws-pattern?)
        execution - ключевое слово.
        modifiers-pattern? - private, public, protected, а также другие модификаторы, например static, final и т.д. Его можно опустить если его нет.
        ? - может быть а может и не быть.
        ret-type-pattern - тип возвращаемого значения, он обязателеный. Если не интересует тип возвр.значения - можно поставить *
        declaring-type-pattern — шаблон для типа, в котором объявлен метод (например, полное имя класса). Может быть опущен.
        name-pattern — шаблон для имени метода (например, get* для всех методов, начинающихся с "get").
        param-pattern — шаблон для параметров метода (например, (..) для любого количества параметров, (int, String) для метода с двумя параметрами: int и String).
        throws-pattern — шаблон для исключений, которые может выбрасывать метод. Может быть опущен.


     */
    @Pointcut("execution(public * com.dmdev.*.service.*Service.findById(*))")
    public void anyFindByIdServiceMethod() {
    }

    @Before(value = "anyFindByIdServiceMethod() " +
            "&& args(id) " +
            "&& target(service) " +
            "&& this(serviceProxy)" +
            "&& @within(transactional)",
            argNames = "joinPoint,id,service,serviceProxy,transactional")
    public void addLogging(JoinPoint joinPoint,
                           Object id,
                           Object service,
                           Object serviceProxy,
                           Transactional transactional) {
        log.info("before - invoked findById method in class {}, with id {}", service, id);
    }

    // JoinPoint всегда должен быть первым параметром если он нам нужен

    @AfterReturning(value = "anyFindByIdServiceMethod() " +
            "&& target(service) ",
            returning = "result", argNames = "result,service")
    public void addLoggingAfterReturning(Object result, Object service) {
        log.info("after returning - invoked findById method in class {}, result {}", service, result);
    }

    @AfterThrowing(value = "anyFindByIdServiceMethod() " +
            "&& target(service) ",
            throwing = "ex", argNames = "ex,service")
    public void addLoggingAfterThrowing(Throwable ex, Object service) {
        log.info("after throwing - invoked findById method in class {}, exception {}: {}", service, ex.getClass(), ex.getMessage());
    }

    @After("anyFindByIdServiceMethod() && target(service)")
    public void addLoggingAfterFinally(Object service) {
        log.info("after (finally) - invoked findById method in class {}", service);
    }


}
