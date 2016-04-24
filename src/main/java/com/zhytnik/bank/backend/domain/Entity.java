package com.zhytnik.bank.backend.domain;

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
}
