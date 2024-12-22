package com.dmdev.spring.http.controller;

import com.dmdev.spring.database.entity.Role;
import com.dmdev.spring.dto.UserReadDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/api/v1") // общий префикс для всех методов нашего контроллера
@SessionAttributes({"user"}) // добавление атрибута user в Массив session
public class GreetingController {

    @ModelAttribute("roles") // добавит возвращаемые значения в атрибуты
    public List<Role> roles() {
        return Arrays.asList(Role.values());
    }

    @GetMapping("/hello")
    public String hello(Model model,
                        HttpServletRequest request,
                        @ModelAttribute("userReadDto") UserReadDto userReadDto) {
//        modelAndView.setViewName("greeting/hello");
        model.addAttribute("user", userReadDto); // установка атрибута

        return "greeting/hello";
    }

    @GetMapping("/hello/{id}")
    public ModelAndView hello2(ModelAndView modelAndView, HttpServletRequest request,
                              @RequestParam("age") Integer age,
                              @RequestHeader("accept") String accept,
                              @CookieValue("JSESSIONID") String JSESSIONID,
                              @PathVariable("id") Integer id) {

        var ageParamValue = request.getParameter("age");
        var acceptHeader = request.getHeader("accept");
        var cookies = request.getCookies();

        modelAndView.setViewName("greeting/hello");

        return modelAndView;
    }

    @GetMapping("/bye")
    public String bye(@SessionAttribute("user") UserReadDto user, Model model) {
        // @SessionAttribute("user") UserReadDto user - вызов атрибута который мы добавили при вызове /hello
        return "greeting/bye";
    }
}