package com.zhytnik.bank.backend.manager.impl;

import com.zhytnik.bank.domain.card.CreditCard;

public class CreditCardManagerTest extends ManagerTest<CreditCard> {

    @Override
    protected Class<CreditCard> getEntityClass() {
        return CreditCard.class;
    }

    @Override
    protected void updateEntity(CreditCard c) {
        c.setValidationCode(7778);
    }
}
