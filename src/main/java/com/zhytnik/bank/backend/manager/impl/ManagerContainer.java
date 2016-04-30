package com.zhytnik.bank.backend.manager.impl;

import com.zhytnik.bank.backend.domain.IEntity;
import com.zhytnik.bank.backend.manager.IEntityManager;

import java.util.HashMap;
import java.util.Map;

public class ManagerContainer {

    private static Map<Class, EntityManager> managers = new HashMap<>();

    public static void save(Class clazz, EntityManager manager) {
        if (!contains(clazz)) {
            managers.put(clazz, manager);
        }
    }

    public static <T extends IEntity> IEntityManager<T> getEntityManager(Class<T> clazz) {
        return (IEntityManager<T>) getManager(clazz);
    }

    static EntityManager<IEntity> getManager(Class<? extends IEntity> clazz) {
        EntityManager manager = managers.get(clazz);
        if (manager == null) {
            manager = new EntityManager<>(clazz);
            save(clazz, manager);
        }
        return manager;
    }

    private static boolean contains(Class clazz) {
        return managers.containsKey(clazz);
    }
}
