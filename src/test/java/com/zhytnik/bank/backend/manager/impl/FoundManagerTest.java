package com.zhytnik.bank.backend.manager.impl;

import com.zhytnik.bank.domain.Found;

public class FoundManagerTest extends ManagerTest<Found> {

    @Override
    protected Class<Found> getEntityClass() {
        return Found.class;
    }

    @Override
    protected void updateEntity(Found f) {
        f.setCode("New code");
    }
}
