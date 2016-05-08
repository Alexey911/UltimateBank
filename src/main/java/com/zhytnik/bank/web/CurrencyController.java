package com.zhytnik.bank.web;

import com.zhytnik.bank.domain.Currency;
import org.springframework.stereotype.Component;

import javax.faces.bean.ViewScoped;

@Component(value = "currencies")
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
    public Currency instantiate() {
        return new Currency();
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

    @Override
    public Class<Currency> getEntityClass() {
        return Currency.class;
    }
}
