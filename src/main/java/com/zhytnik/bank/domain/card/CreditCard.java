package com.zhytnik.bank.domain.card;

import com.zhytnik.bank.backend.types.relation.OneToOne;
import com.zhytnik.bank.domain.Credit;

public class CreditCard extends Card {

    @OneToOne
    private Credit credit;

    public Credit getCredit() {
        return credit;
    }

    public void setCredit(Credit credit) {
        this.credit = credit;
    }
}
