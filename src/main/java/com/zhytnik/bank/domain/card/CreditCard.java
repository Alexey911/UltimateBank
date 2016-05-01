package com.zhytnik.bank.domain.card;

import com.zhytnik.bank.backend.domain.Reference;
import com.zhytnik.bank.domain.Credit;

public class CreditCard extends Card {

    @Reference(type = Credit.class, single = true)
    private Credit credit;

    public Credit getCredit() {
        return credit;
    }

    public void setCredit(Credit credit) {
        this.credit = credit;
    }
}
