package com.zhytnik.bank.service.impl;

import com.zhytnik.bank.backend.manager.IEntityManager;
import com.zhytnik.bank.backend.types.IEntity;
import com.zhytnik.bank.service.IEntityService;

import java.util.Set;

abstract class EntityService<T extends IEntity> implements IEntityService<T> {

    protected IEntityManager<T> manager;

    public abstract T instantiate();

    @Override
    public T findById(Integer id) {
        return manager.load(id);
    }

    @Override
    public void initialize(T entity) {
        manager.initialize(entity);
    }

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
}
