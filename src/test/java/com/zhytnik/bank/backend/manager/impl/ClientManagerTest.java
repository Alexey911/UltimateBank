package com.zhytnik.bank.backend.manager.impl;

import com.zhytnik.bank.domain.Client;

public class ClientManagerTest extends ManagerTest<Client> {

    @Override
    protected Class<Client> getEntityClass() {
        return Client.class;
    }

    @Override
    protected void updateEntity(Client c) {
        c.setPassword("pass");
    }
}
