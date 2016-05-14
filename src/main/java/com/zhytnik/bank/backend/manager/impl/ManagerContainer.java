package com.zhytnik.bank.backend.manager.impl;

import com.zhytnik.bank.backend.manager.IEntityManager;
import com.zhytnik.bank.backend.types.IEntity;
import com.zhytnik.bank.domain.*;
import com.zhytnik.bank.domain.card.BillCard;
import com.zhytnik.bank.domain.card.CreditCard;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

public class ManagerContainer {

    public static final Set<Class<? extends IEntity>> MANAGED_ENTITIES;

    private static Map<Class, EntityManager> managers = new HashMap<>();

    static {
        MANAGED_ENTITIES = newHashSet(
                Currency.class,
                Found.class,
                Department.class,
                Banker.class,
                Client.class,
                Deposit.class,
                Credit.class,
                CreditCard.class,
                Bill.class,
                BillCard.class);
    }

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
            save(clazz, createManager(clazz));
        }
        return manager;
    }

    private static EntityManager createManager(Class<? extends IEntity> clazz) {
        EntityManager manager;
        if (clazz.equals(Currency.class)) {
            manager = new CurrencyManager();
        } else {
            manager = new EntityManager<>(clazz);
        }
        return manager;
    }

    private static boolean contains(Class clazz) {
        return managers.containsKey(clazz);
    }

    public static void drop() {
        MANAGED_ENTITIES.forEach(ManagerContainer::drop);
    }

    private static void drop(Class<? extends IEntity> clazz) {
        getManager(clazz).clear();
    }
}
