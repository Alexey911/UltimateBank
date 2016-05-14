package com.zhytnik.bank.backend.manager.impl;

import com.zhytnik.bank.domain.Banker;

public class BankerManagerTest extends ManagerTest<Banker> {

    @Override
    protected Class<Banker> getEntityClass() {
        return Banker.class;
    }

    @Override
    protected void updateEntity(Banker b) {
        b.setPrivilege(10);
    }
}
