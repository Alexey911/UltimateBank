package com.zhytnik.bank.service.impl;

import com.zhytnik.bank.domain.Client;

public class ClientService extends EntityService<Client> {

    @Override
    public Client instantiate() {
        return new Client();
    }
}
