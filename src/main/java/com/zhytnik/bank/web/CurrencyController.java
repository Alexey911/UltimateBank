package com.zhytnik.bank.web;

import com.zhytnik.bank.domain.Currency;

import javax.faces.bean.ViewScoped;

@ViewScoped
public class CurrencyController extends EntityController<Currency> {

    private String name;
    private Double value;

    @Override
    public void reset() {
        name = "";
        value = 0d;
    }

    @Override
    public void select() {
        name = selected.getName();
        value = selected.getValue();
    }

    @Override
    protected void fill(Currency c) {
        c.setName(name);
        c.setValue(value);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
