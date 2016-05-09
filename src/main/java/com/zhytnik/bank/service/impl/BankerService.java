package com.zhytnik.bank.service.impl;

import com.zhytnik.bank.domain.Banker;

public class BankerService extends EntityService<Banker> {

    @Override
    public Banker instantiate() {
        return new Banker();
    }
}
