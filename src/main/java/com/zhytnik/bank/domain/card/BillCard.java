package com.zhytnik.bank.domain.card;

import com.zhytnik.bank.backend.domain.Reference;
import com.zhytnik.bank.domain.Bill;

public class BillCard extends Card {

    @Reference(type = Bill.class, single = true)
    private Bill bill;

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }
}
