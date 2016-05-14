package com.zhytnik.bank.web;

import com.zhytnik.bank.domain.Currency;
import com.zhytnik.bank.service.impl.CurrencyService;

import javax.faces.bean.ViewScoped;

import static java.lang.String.format;

@ViewScoped
public class CurrencyController extends EntityController<Currency> {

    private String name;
    private Double value;

    private Currency from;
    private Currency to;
    private Double sum;

    private Double result;

    @Override
    public void reset() {
        name = "";
        value = 0d;
        from = null;
        to = null;
        sum = null;
        result = null;
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

    public void convert() {
        result = getService().convert(from, to, sum);
    }

    @Override
    public CurrencyService getService() {
        return (CurrencyService) service;
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

    public Currency getFrom() {
        return from;
    }

    public void setFrom(Currency from) {
        this.from = from;
    }

    public Currency getTo() {
        return to;
    }

    public void setTo(Currency to) {
        this.to = to;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public String getResult() {
        return result == null ? "" : format("%.3f", result);
    }

    public void setResult(Double result) {
        this.result = result;
    }
}
