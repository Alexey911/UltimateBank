package com.zhytnik.bank.domain.card;

import com.zhytnik.bank.backend.types.relation.OneToMany;
import com.zhytnik.bank.domain.Bill;

public class BillCard extends Card {

    @OneToMany(type = Bill.class, single = true)
    private Bill bill;

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }
}
