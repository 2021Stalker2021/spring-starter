package com.dmdev.spring.database.repository;

import com.dmdev.spring.database.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

    //    Optional, Entity, Future
    //    @Query(name = "Company.findByName")
    @Query("select c from Company c " +
            "join fetch c.locales" +
            " where c.name = :name2")
    Optional<Company> findByName(@Param("name2") String name);

    //  Collection, Stream (batch, close)
    List<Company> findAllByNameContainingIgnoreCase(String name);
    // findAll - желательно использовать для колекций сущностей
    // Containing - это аналог Like, только с процентов слева и справа: %Like%
    // IgnoreCase - это без учета регистра
}
