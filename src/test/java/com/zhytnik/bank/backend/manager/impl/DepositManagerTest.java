package com.zhytnik.bank.backend.manager.impl;

import com.zhytnik.bank.domain.Deposit;

public class DepositManagerTest extends ManagerTest<Deposit> {

    @Override
    protected Class<Deposit> getEntityClass() {
        return Deposit.class;
    }

    @Override
    protected void updateEntity(Deposit d) {
        d.setSum(135.1d);
    }
}
