package com.zhytnik.bank.backend.manager.impl;

import com.zhytnik.bank.domain.Currency;

import java.sql.CallableStatement;

import static com.zhytnik.bank.backend.tool.ScriptUtil.getFunctionCall;
import static com.zhytnik.bank.backend.tool.statement.CallableStatementUtil.*;

public class CurrencyManager extends EntityManager<Currency> {

    public CurrencyManager() {
        super(Currency.class);
    }

    public double callConverter(Currency from, Currency to, double sum) {
        CallableStatement s = build(connection, getFunctionCall("CONVERT_CURRENCY", 3));
        registerParameter(s, 1, Double.class);
        putInteger(s, 2, from.getId());
        putInteger(s, 3, to.getId());
        putDouble(s, 4, sum);
        execute(s);
        return loadDouble(s, 1);
    }
}
