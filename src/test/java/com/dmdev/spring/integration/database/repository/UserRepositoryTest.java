package com.dmdev.spring.integration.database.repository;

import com.dmdev.spring.database.entity.Role;
import com.dmdev.spring.database.entity.User;
import com.dmdev.spring.database.repository.UserRepository;
import com.dmdev.spring.dto.PersonalInfo;
import com.dmdev.spring.dto.UserFilter;
import com.dmdev.spring.integration.IntegrationTestBase;
import com.dmdev.spring.integration.annotation.IT;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
class UserRepositoryTest extends IntegrationTestBase {

    private final UserRepository userRepository;

    @Test
    void checkPageable() {
        var pageable = PageRequest.of(0, 2, Sort.by("id"));
        // Номер страницы указывает, какую именно «страницу» данных вы хотите извлечь из общего набора (в нашем случае 2-ю стр. из 2 эл-ов)
        var slice = userRepository.findAllBy(pageable);
        slice.forEach(user -> System.out.println(user.getCompany().getName()));

        while (slice.hasNext()) { // есть ли ещё следующая пачка в нашей выборке
            slice = userRepository.findAllBy(slice.nextPageable()); // В slice хронится Pageable. Переходим на страницу 2
            slice.forEach(user -> System.out.println(user.getCompany().getName()));
        }
    }

    @Test
    void checkSort() {
        var sortBy = Sort.sort(User.class);
        var sort = sortBy.by(User::getFirstname).and(sortBy.by(User::getLastname));

        var sortById = Sort.by("firstname").and(Sort.by("lastname"));
        var allUsers = userRepository.findTop3ByBirthDateBefore(LocalDate.now(), sort);
        assertThat(allUsers).hasSize(3);
    }

    @Test
    void checkFirstTop() {
        var topUser = userRepository.findTopByOrderByIdDesc();
        assertThat(topUser.isPresent());
        topUser.ifPresent(user -> assertEquals(5L, user.getId()));
    }

//    @Test
//    void checkUpdate() {
//        var ivan = userRepository.getReferenceById(1L);
//        assertSame(Role.ADMIN, ivan.getRole());
//        ivan.setBirthDate(LocalDate.now());
//
//        var resultCount = userRepository.updateRole(Role.USER, 1L, 5L);
//        assertEquals(2, resultCount);
//
//        var theSameIvan = userRepository.getReferenceById(1L);
//        assertSame(Role.USER, theSameIvan.getRole());
//    }

    @Test
    void checkQueries() {
        var users = userRepository.findAllBy("a", "ov");
        assertThat(users).hasSize(3);
    }

    @Test
    void checkProjections() {
        var users = userRepository.findAllByCompanyId(1L);
        assertThat(users).hasSize(2);
    }

    @Test
    void checkCustomImplementation() {
        UserFilter filter = new UserFilter(
          null, "ov", LocalDate.now()
        );
        var users = userRepository.findAllByFilter(filter);
        assertThat(users).hasSize(4);
    }

    @Test
    void checkAuditing() {
        var ivan = userRepository.findById(1L).get();
        ivan.setBirthDate(ivan.getBirthDate().plusYears(1L));
        userRepository.flush();
        System.out.println();
    }

    @Test
    void checkJdbcTemplate() {
        var users = userRepository.findAllByCompanyIdAndRole(1, Role.ADMIN);
        assertThat(users).hasSize(1);
    }

    @Test
    void checkBatch() {
        var users = userRepository.findAll();
        userRepository.updateCompanyAndRole(users);
        System.out.println();
    }
}