package com.zhytnik.bank.backend.domain;

import static java.lang.String.format;

public class Entity implements IEntity {

    private Integer id;

    public Entity() {

    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return format("Entity of %s[id=%d]", getClass(), getId());
    }
}
