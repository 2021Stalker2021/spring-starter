package com.dmdev.spring.database.repository;

import com.dmdev.spring.database.entity.Role;
import com.dmdev.spring.database.entity.User;
import com.dmdev.spring.dto.PersonalInfo2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends
        JpaRepository<User, Long>,
        FilterUserRepository,
        RevisionRepository<User, Long, Integer>,
        QuerydslPredicateExecutor<User> {

    @Query("select u from User u " +
        "where u.firstname like %:firstname% and u.lastname like %:lastname%")
    List<User> findAllBy(@Param("firstname") String firstName, @Param("lastname") String lastName);

    @Query(value = "SELECT u.* FROM users u WHERE u.username = :username",
            nativeQuery = true)
    List<User> findAllByUsername(String username);

    @Modifying(clearAutomatically = true, flushAutomatically = true) // По умолчанию Query используется только для select.
    // (данная аннотация позволяет делать update, insert, delete, DDL операции).
    // clearAutomatically = true (автоматически чистит наш persistentContext),
    // т.к мы уже работали с сущностями у которых id указан в аргументах, мы должны очистить их из persistentContext
    // чтобы он не брал старое значение firsLevelCache в persistentContext
    @Query("update User u " +
            "set u.role = :role " +
            "where u.id in (:ids)")
    int updateRole(Role role, Long... ids); // varargs

    Optional<User> findTopByOrderByIdDesc();
    /*
    findTopBy — это метод, который используется в Spring Data JPA для создания
     запросов к базе данных, особенно в контексте репозиториев (repositories). Он позволяет
      находить один элемент, который соответствует заданным критериям, при этом возвращая
       только верхний (первый) результат. Этот метод часто используется в сочетании с группами данных и сортировкой.
       В нашем случае будет найден пользователь первый с конца.
     */

    @QueryHints(@QueryHint(name = "org.hibernate.fetchSize", value = "50")) // только 50 записей за раз
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<User> findTop3ByBirthDateBefore(LocalDate birthDate, Sort sort);

//    @EntityGraph("User.company")
    @EntityGraph(attributePaths = {"company", "company.locales"})
    @Query(value = "select u from User u",
            countQuery = "select count(distinct u.firstname) from User u")
    Page<User> findAllBy(Pageable pageable);
    // оператор distinct исключит дубликаты по firstname

//    List<PersonalInfo> findAllByCompanyId(Long companyId);

//    <T> List<T> findAllByCompanyId(Long companyId, Class<T> clazz);

    @Query(value = "SELECT firstname, " +
            "lastname, " +
            "birth_date BirthDate " +
            "FROM users " +
            "WHERE company_id = :companyId",
            nativeQuery = true)
    List<PersonalInfo2> findAllByCompanyId(@Param("companyId") Long companyId);

    Optional<User> findByUsername(String username);

}
