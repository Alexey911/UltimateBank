package com.zhytnik.bank.service;

import com.zhytnik.bank.backend.types.IEntity;

import java.util.Set;

public interface IService<T extends IEntity> {

    T instantiate();

    T initialize(T entity);

    void save(T entity);

    void remove(T entity);

    void update(T entity);

    Set<T> loadAll();
}
