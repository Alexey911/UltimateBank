package com.zhytnik.bank.service.impl;

import com.zhytnik.bank.domain.card.BillCard;

import java.util.Random;

public class BillCardService extends EntityService<BillCard> {

    private Random random = new Random();

    public Integer generateCVC() {
        return 100 + random.nextInt(900);
    }

    public Integer generateValidationCode() {
        return 1000 + random.nextInt(9000);
    }
}
