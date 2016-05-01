package com.zhytnik.bank.backend.types;

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
    public boolean isSaved() {
        return id != null;
    }

    @Override
    public String toString() {
        return format("Entity of %s[id=%d]", getClass(), getId());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof IEntity)) return false;
        final IEntity entity = (IEntity) o;
        return id.equals(entity.getId());
    }
}
