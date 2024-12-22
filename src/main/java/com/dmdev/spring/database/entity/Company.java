package com.dmdev.spring.database.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NamedQuery(
        name = "Company.findByName",
        query = "select c from Company c where lower(c.name) = lower(:name2)"
)
/*
    name = "Company.findByName" - сначала пишем название сущности, потом метод
    который будем использовать.@NamedQuery имеет преимущество над регулярным выражением,
    следовательно, будет использован @NamedQuery метод если будут два одинаковых по
    названию метода. Именование параметров(:name2) должны совпадать с параметром из CompanyRepository,
    Пример: Optional<Company> findByName(@Param("name2") String name);
 */
@Data
@NoArgsConstructor // он обязателен в hibernate
@AllArgsConstructor
@Builder
@Entity // всегда помечаем сущность как @Entity
@Table(name = "company")
public class Company implements BaseEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false) // Эти аннотации используются в случае автогенерирования схемы БД
    private String name;

    @Builder.Default
    /*
    Это гарантирует, что если объект `Company` будет создан с использованием
     построителя (`builder`), то `locales` по умолчанию будет инициализирован пустым `HashMap`.
     */
    @ElementCollection
    @CollectionTable(name = "company_locales", joinColumns = @JoinColumn(name = "company_id"))
    /*
    @CollectionTable(name = "company_locales", joinColumns = @JoinColumn(name = "company_id"))
    - `name`: Имя таблицы, в которой будет храниться коллекция.
    - `joinColumns`: Определяет столбцы, используемые для связи с основной
     таблицей, к которой принадлежит коллекция.
     */
    @MapKeyColumn(name = "lang") // в качестве ключа "lang"
    @Column(name = "description") // в качестве значения "description"
    private Map<String, String> locales = new HashMap<>();
    // используем new HashMap для избежания NullPointerException
}
