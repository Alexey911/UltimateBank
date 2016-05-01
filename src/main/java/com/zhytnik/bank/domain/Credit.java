package com.zhytnik.bank.domain;

import com.zhytnik.bank.backend.types.relation.ManyToOne;
import com.zhytnik.bank.backend.types.Entity;
import com.zhytnik.bank.domain.card.CreditCard;

import java.util.Date;

public class Credit extends Entity {

    @ManyToOne
    private Client client;

    @ManyToOne
    private CreditCard creditCard;

    @ManyToOne
    private Currency currency;

    private Double amount;
    private Date validFor;
    private Double margin;
    private Integer fee;
    private Boolean isActive;

    private Double penalty;

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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getValidFor() {
        return validFor;
    }

    public void setValidFor(Date validFor) {
        this.validFor = validFor;
    }

    public Double getMargin() {
        return margin;
    }

    public void setMargin(Double margin) {
        this.margin = margin;
    }

    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Double getPenalty() {
        return penalty;
    }

    public void setPenalty(Double penalty) {
        this.penalty = penalty;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }
}
