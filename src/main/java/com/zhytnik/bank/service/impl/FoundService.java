package com.zhytnik.bank.service.impl;

import com.zhytnik.bank.domain.Found;

public class FoundService extends EntityService<Found> {

    @Override
    public Found instantiate() {
        return new Found();
    }
}
