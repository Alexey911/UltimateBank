package com.zhytnik.bank.web;

import com.zhytnik.bank.backend.types.IEntity;
import com.zhytnik.bank.service.IEntityService;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public abstract class EntityController<T extends IEntity> implements Serializable {

    protected IEntityService<T> service;

    protected List<T> entities;

    protected T selected;

    @PostConstruct
    public void setUp() {
        entities = newArrayList();
        reset();
        loadAll();
    }

    private void loadAll() {
        entities.clear();
        entities.addAll(service.loadAll());
    }

    public abstract void reset();

    public void save() {
        final T entity = service.instantiate();
        fill(entity);
        service.save(entity);
        entities.add(entity);
        reset();
    }

    public void refresh() {
        reset();
        loadAll();
    }

    protected abstract void fill(T entity);

    public abstract void select();

    public void update() {
        if (selected == null) return;

        fill(selected);
        service.update(selected);
        reset();
    }

    public void remove() {
        if (selected == null) return;

        service.remove(selected);
        entities.remove(selected);
        reset();
    }

    public List<T> getEntities() {
        return entities;
    }

    public void setEntities(List<T> entities) {
        this.entities = entities;
    }

    public T getSelected() {
        return selected;
    }

    public void setSelected(T selected) {
        if (selected != null) {
            service.initialize(selected);
        }
        this.selected = selected;
    }

    public IEntityService<T> getService() {
        return service;
    }

    public void setService(IEntityService<T> service) {
        this.service = service;
    }
}
