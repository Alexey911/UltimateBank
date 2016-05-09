package com.zhytnik.bank.service.impl;

import com.zhytnik.bank.domain.Currency;

public class CurrencyService extends EntityService<Currency> {

    @Override
    public Currency instantiate() {
        return new Currency();
    }

    public Currency findByName(String name) {
        return findByParameter("name", name);
    }
}