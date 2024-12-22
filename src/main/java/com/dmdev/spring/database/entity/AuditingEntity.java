package com.dmdev.spring.database.entity;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@MappedSuperclass // позволяет нашей сущ. User унаследовать 4 поля из этого класса
@EntityListeners(AuditingEntityListener.class) // нужен для работы с полями которые мы хотим обновлять
public abstract class AuditingEntity<T extends Serializable> implements BaseEntity<T> {

    @CreatedDate // аннотация которая помогает нашей @EntityListeners(AuditingEntityListener.class) опеределять какое поле использовать
    private Instant createdAt; // когда была создана сущность

    @LastModifiedDate // // аннотация которая помогает нашей @EntityListeners(AuditingEntityListener.class) опеределять какое поле использовать
    private Instant modifiedAt; // когда была обновлена

    @CreatedBy // кто создал
    private String createdBy;

    @LastModifiedBy // кто изменил
    private String modifiedBy;
}
