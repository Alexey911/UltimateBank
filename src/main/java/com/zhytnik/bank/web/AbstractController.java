package com.zhytnik.bank.web;

import com.zhytnik.bank.backend.manager.IEntityManager;
import com.zhytnik.bank.backend.manager.impl.ManagerContainer;
import com.zhytnik.bank.backend.types.IEntity;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public abstract class AbstractController<T extends IEntity> implements Serializable {

    protected IEntityManager<T> manager;

    protected List<T> entities;

    protected List<T> filtered;

    protected T selected;

    public abstract Class<T> getEntityClass();

    @PostConstruct
    public void setUp() {
        manager = ManagerContainer.getEntityManager(getEntityClass());
        entities = newArrayList();
        filtered = newArrayList();
        reset();
        loadAll();
    }

    private void loadAll() {
        entities.addAll(manager.loadAll());
    }

    public abstract void reset();

    public void save() {
        final T entity = getPrepareForSave();
        manager.save(entity);
        entities.add(entity);
        reset();
    }

    protected abstract T getPrepareForSave();

    public abstract void select();

    public void update() {
        if (selected == null) return;

        prepareForUpdate(selected);
        manager.update(selected);
        reset();
    }

    protected abstract void prepareForUpdate(T entity);

    public void remove() {
        if (selected == null) return;

        manager.remove(selected);
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
}
