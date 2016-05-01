package com.zhytnik.bank.domain;

import com.zhytnik.bank.backend.types.relation.ManyToOne;
import com.zhytnik.bank.backend.types.Entity;

import java.util.Date;

public class Deposit extends Entity {

    private Double sum;
    private Double percent;
    private Date expiryDate;

    @ManyToOne
    private Client client;

    @ManyToOne
    private Currency currency;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }
}
