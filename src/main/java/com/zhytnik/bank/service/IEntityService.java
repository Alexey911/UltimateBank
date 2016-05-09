package com.zhytnik.bank.service;

import com.zhytnik.bank.backend.types.IEntity;

import java.util.Set;

public interface IEntityService<T extends IEntity> {

    T findById(Integer id);

    T instantiate();

    void initialize(T entity);

    void save(T entity);

    void remove(T entity);

    void update(T entity);

    Set<T> loadAll();
}
