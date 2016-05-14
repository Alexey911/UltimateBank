package com.zhytnik.bank.backend.manager.impl;

import com.zhytnik.bank.domain.Currency;

public class CurrencyManagerTest extends ManagerTest<Currency> {

    @Override
    protected Class<Currency> getEntityClass() {
        return Currency.class;
    }

    @Override
    protected void updateEntity(Currency c) {
        c.setValue(10d);
    }
}
