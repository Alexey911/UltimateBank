package com.zhytnik.bank.backend.manager.impl;

import com.zhytnik.bank.backend.types.IEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Maps.newHashMap;

public class ManagerFactory {

    private Map<Class<? extends IEntity>, EntityManager> managers = new HashMap<>();

    public EntityManager<IEntity> getEntityManager(Class clazz) {
        assertEntity(clazz);
        return managers.get(clazz);
    }

    private void assertEntity(Class clazz) {
        if (clazz.isAssignableFrom(IEntity.class)) {
            throw new RuntimeException();
        }
    }

    public void setManagers(Set<EntityManager<? extends IEntity>> managers) {
        this.managers = convert(managers);
    }

    private Map<Class<? extends IEntity>, EntityManager> convert(Set<EntityManager<? extends IEntity>> managers) {
        final Map<Class<? extends IEntity>, EntityManager> converted = newHashMap();
        for (EntityManager<? extends IEntity> manager : managers) {
            converted.put(manager.clazz, manager);
        }
        return converted;
    }

    public Set<Class<? extends IEntity>> getSupportedClasses(){
        return managers.keySet();
    }
}
