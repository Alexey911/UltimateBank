package com.zhytnik.bank.backend.manager.impl;

import java.util.HashMap;
import java.util.Map;

public class ManagerContainer {

    private static Map<Class, EntityManager> managers = new HashMap<>();

    public void save(Class clazz, EntityManager manager) {
        if (!contains(clazz)) {
            managers.put(clazz, manager);
        }
    }

    public EntityManager get(Class clazz) {
        EntityManager manager = managers.get(clazz);
        if (manager == null) {
            manager = new EntityManager<>(clazz);
            save(clazz, manager);
        }
        return manager;
    }

    private boolean contains(Class clazz) {
        return managers.containsKey(clazz);
    }
}
