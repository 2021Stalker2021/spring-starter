package com.dmdev.spring.database.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor // он обязателен в hibernate
@AllArgsConstructor
@Builder
@Entity // всегда помечаем сущность как @Entity
@Table(name = "chat")
public class Chat implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false) // Эти аннотации используются в случае автогенерирования схемы БД
    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "chat")
    private List<UserChat> userChats = new ArrayList<>();

    /*
    Если бы не было `@Builder.Default`, при создании объекта через `Chat.builder()`
    поле `userChats` осталось бы `null`, что может привести к ошибкам при
     использовании этого объекта. Аннотация позволяет избежать
    необходимости вручную устанавливать значение для этого
     поля каждый раз, когда создается объект через билдер.
     */
}
