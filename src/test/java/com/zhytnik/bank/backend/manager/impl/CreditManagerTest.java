package com.zhytnik.bank.backend.manager.impl;

import com.zhytnik.bank.domain.Credit;

public class CreditManagerTest extends ManagerTest<Credit> {

    @Override
    protected Class<Credit> getEntityClass() {
        return Credit.class;
    }

    @Override
    protected void updateEntity(Credit c) {
        c.setAmount(78d);
    }
}
