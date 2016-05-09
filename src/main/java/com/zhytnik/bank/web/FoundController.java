package com.zhytnik.bank.web;

import com.zhytnik.bank.domain.Currency;
import com.zhytnik.bank.domain.Found;
import org.springframework.stereotype.Component;

import javax.faces.bean.ViewScoped;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.zhytnik.bank.backend.manager.impl.ManagerContainer.getEntityManager;

@Component(value = "founds")
@ViewScoped
public class FoundController extends EntityController<Found> {

    private String code;
    private Double balance;
    private Currency currency;

    private List<Currency> currencies;

    @Override
    public void setUp() {
        currencies = newArrayList();
        super.setUp();
        loadCurrencies();
    }

    private void loadCurrencies() {
        currencies.clear();
        currencies.addAll(getEntityManager(Currency.class).loadAll());
    }

    @Override
    public void reset() {
        code = "";
        balance = 0.0d;
        currency = null;
        loadCurrencies();
    }

    @Override
    public Found instantiate() {
        return new Found();
    }

    @Override
    protected void fill(Found f) {
        f.setCode(code);
        f.setBalance(balance);
        f.setCurrency(currency);
    }

    @Override
    public void select() {
        code = selected.getCode();
        balance = selected.getBalance();
        loadCurrencies();
        currency = selected.getCurrency();
    }

    @Override
    public void refresh() {
        super.refresh();
        loadCurrencies();
    }

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

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }

    @Override
    public Class<Found> getEntityClass() {
        return Found.class;
    }
}
