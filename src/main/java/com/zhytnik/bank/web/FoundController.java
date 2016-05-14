package com.zhytnik.bank.web;

import com.zhytnik.bank.domain.Currency;
import com.zhytnik.bank.domain.Found;
import com.zhytnik.bank.service.IEntityService;

import javax.faces.bean.ViewScoped;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@ViewScoped
public class FoundController extends EntityController<Found> {

    private String code;
    private Double balance;
    private Currency currency;

    private List<Currency> currencies;

    private IEntityService<Currency> currencyService;

    @Override
    public void setUp() {
        currencies = newArrayList();
        super.setUp();
        refreshCurrencies();
    }

    private void refreshCurrencies() {
        currencies.clear();
        currencies.addAll(currencyService.loadAll());
    }

    @Override
    public void reset() {
        code = "";
        balance = 0.0d;
        currency = null;
        refreshCurrencies();
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
        refreshCurrencies();
        currency = selected.getCurrency();
    }

    @Override
    public void refresh() {
        super.refresh();
        refreshCurrencies();
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

    public void setCurrencyService(IEntityService<Currency> currencyService) {
        this.currencyService = currencyService;
    }
}
