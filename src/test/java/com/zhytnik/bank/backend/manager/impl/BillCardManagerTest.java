package com.zhytnik.bank.backend.manager.impl;

import com.zhytnik.bank.domain.card.BillCard;

public class BillCardManagerTest extends ManagerTest<BillCard> {

    @Override
    protected Class<BillCard> getEntityClass() {
        return BillCard.class;
    }

    @Override
    protected void updateEntity(BillCard c) {
        c.setCode("new code");
    }
}
