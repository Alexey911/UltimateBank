package com.zhytnik.bank.backend.manager;

import com.zhytnik.bank.backend.domain.IEntity;

import java.util.Set;

public interface IEntityManager<T extends IEntity> {

    Integer save(T entity);

    T load(Integer id);

    void update(T entity);

    void remove(T entity);

    void remove(Integer id);

    void clear();

    int getCount();

    Set<T> loadAll();

    Set<T> findByFieldValue(String field, Object value);

    Class<T> getEntityClass();
}
