package com.zhytnik.bank.domain;

import com.zhytnik.bank.backend.types.relation.ManyToOne;
import com.zhytnik.bank.backend.types.Entity;

public class Found extends Entity {

    private String code;
    private Double balance;

    @ManyToOne
    private Currency currency;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
