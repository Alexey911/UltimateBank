package com.zhytnik.bank.backend.manager.impl;

import com.zhytnik.bank.domain.Bill;

public class BillManagerTest extends ManagerTest<Bill> {

    @Override
    protected Class<Bill> getEntityClass() {
        return Bill.class;
    }

    @Override
    protected void updateEntity(Bill b) {
        b.setIsActive(false);
    }
}
