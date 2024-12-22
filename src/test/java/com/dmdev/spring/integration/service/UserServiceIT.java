package com.dmdev.spring.integration.service;

import com.dmdev.spring.database.entity.Role;
import com.dmdev.spring.dto.UserCreateEditDto;
import com.dmdev.spring.dto.UserReadDto;
import com.dmdev.spring.integration.IntegrationTestBase;
import com.dmdev.spring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD) // делает контест dirty(мы не можем переиспользовать наш контекст после каждого метода)
public class UserServiceIT extends IntegrationTestBase {

    private static final Long USER_1 = 1L;
    private static final Integer COMPANY_1 = 1;

    private final UserService userService;

    @Test
    void findAll() {
        var result = userService.findAll();
        assertThat(result).hasSize(5);
    }

    @Test
    void findById() {
        var maybeUser = userService.findById(USER_1);
        assertTrue(maybeUser.isPresent());
        maybeUser.ifPresent(user -> assertEquals("ivan@gmail.com", user.getUsername()));
    }

//    @Test
//    void create() {
//        UserCreateEditDto userDto = new UserCreateEditDto(
//                "test@gmail.com",
//                LocalDate.now(),
//                "Test",
//                "Test",
//                Role.ADMIN,
//                COMPANY_1
//        );
//        var actualResult = userService.create(userDto);
//        assertEquals(userDto.getUsername(), actualResult.getUsername());
//        assertEquals(userDto.getBirthDate(), actualResult.getBirthDate());
//        assertEquals(userDto.getFirstname(), actualResult.getFirstname());
//        assertEquals(userDto.getLastname(), actualResult.getLastname());
//        assertSame(userDto.getRole(), actualResult.getRole());
//        assertEquals(userDto.getCompanyId(), actualResult.getCompany().id());
//    }

//    @Test
//    void update() {
//        UserCreateEditDto userDto = new UserCreateEditDto(
//                "test@gmail.com",
//                LocalDate.now(),
//                "Test",
//                "Test",
//                Role.ADMIN,
//                COMPANY_1
//        );
//
//        var actualResult = userService.update(USER_1, userDto);
//
//        assertTrue(actualResult.isPresent());
//        actualResult.ifPresent(user -> {
//            assertEquals(userDto.getUsername(), user.getUsername());
//            assertEquals(userDto.getBirthDate(), user.getBirthDate());
//            assertEquals(userDto.getFirstname(), user.getFirstname());
//            assertEquals(userDto.getLastname(), user.getLastname());
//            assertSame(userDto.getRole(), user.getRole());
//            assertEquals(userDto.getCompanyId(), user.getCompany().id());
//        });
//    }

    @Test
    void delete() {
        assertFalse(userService.delete(-124L));
        assertTrue(userService.delete(USER_1));
    }
}