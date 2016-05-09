package com.zhytnik.bank.web;

import com.zhytnik.bank.backend.types.IEntity;
import com.zhytnik.bank.service.IService;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public abstract class EntityController<T extends IEntity> implements Serializable {

    protected IService<T> service;

    protected List<T> entities;
    protected List<T> filtered;

    protected T selected;

    @PostConstruct
    public void setUp() {
        entities = newArrayList();
        filtered = newArrayList();
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
        this.selected = selected;
    }

    public List<T> getFiltered() {
        return filtered;
    }

    public void setFiltered(List<T> filtered) {
        this.filtered = filtered;
    }

    public void setService(IService<T> service) {
        this.service = service;
    }

    public IService<T> getService() {
        return service;
    }
}
