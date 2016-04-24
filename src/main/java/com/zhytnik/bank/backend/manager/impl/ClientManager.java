package com.zhytnik.bank.backend.manager.impl;

import com.zhytnik.bank.domain.Client;

public class ClientManager extends EntityManager<Client> {
    public ClientManager() {
        super(Client.class);
    }
}
