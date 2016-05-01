package com.zhytnik.bank.domain;

import com.zhytnik.bank.backend.types.Entity;
import com.zhytnik.bank.backend.types.relation.ManyToOne;
import com.zhytnik.bank.backend.types.relation.OneToOne;
import com.zhytnik.bank.domain.card.BillCard;

public class Bill extends Entity {

    private Double balance;
    private Boolean isActive;

    @ManyToOne
    private Currency currency;

    @ManyToOne
    private Client client;

    @OneToOne
    private BillCard card;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public BillCard getCard() {
        return card;
    }

    public void setCard(BillCard card) {
        this.card = card;
    }
}
