package com.zhytnik.bank.service.impl;

import com.zhytnik.bank.backend.manager.impl.CurrencyManager;
import com.zhytnik.bank.domain.Currency;

public class CurrencyService extends EntityService<Currency> {

    public double convert(Currency a, Currency b, double sum) {
        return getManager().callConverter(a, b, sum);
    }

    @Override
    public CurrencyManager getManager() {
        return (CurrencyManager) manager;
    }
}
