package com.zhytnik.bank.service.impl;

import com.zhytnik.bank.domain.card.Card;

import java.util.Random;

public abstract class CardService<T extends Card> extends EntityService<T> {

    private Random random = new Random();

    public Integer generateCVC() {
        return 100 + random.nextInt(900);
    }

    public Integer generateValidationCode() {
        return 1000 + random.nextInt(9000);
    }
}
