package com.zhytnik.bank.service.impl;

import com.zhytnik.bank.backend.exception.NotFoundException;
import com.zhytnik.bank.backend.manager.IEntityManager;
import com.zhytnik.bank.backend.types.IEntity;
import com.zhytnik.bank.service.IService;

import java.util.Set;

import static com.google.common.collect.Iterables.getOnlyElement;

abstract class EntityService<T extends IEntity> implements IService<T> {

    protected IEntityManager<T> manager;

    public abstract T instantiate();

    @Override
    public void save(T entity) {
        manager.save(entity);
    }

    @Override
    public void remove(T entity) {
        manager.remove(entity);
    }

    @Override
    public void update(T entity) {
        manager.update(entity);
    }

    @Override
    public Set<T> loadAll() {
        return manager.loadAll();
    }

    public IEntityManager<T> getManager() {
        return manager;
    }

    public void setManager(IEntityManager<T> manager) {
        this.manager = manager;
    }

    protected T findByParameter(String param, String name) {
        final Set<T> entities = manager.findByFieldValue(param, name);
        if (!entities.isEmpty()) return getOnlyElement(entities);
        throw new NotFoundException();
    }
}
