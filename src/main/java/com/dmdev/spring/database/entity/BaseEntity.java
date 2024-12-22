package com.dmdev.spring.database.entity;

import java.io.Serializable;

public interface BaseEntity<T extends Serializable> {
    T getId(); // возвращаем Id

    void setId(T id); // установка Id
}
