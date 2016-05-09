package com.zhytnik.bank.web;

import com.zhytnik.bank.domain.Client;
import com.zhytnik.bank.domain.Currency;
import com.zhytnik.bank.domain.Deposit;
import com.zhytnik.bank.service.IEntityService;
import com.zhytnik.bank.web.util.Formatter;

import javax.faces.bean.ViewScoped;
import java.util.Date;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@ViewScoped
public class DepositController extends EntityController<Deposit> {

    private Double sum;
    private Double percent;
    private Date expiryDate;
    private Client client;
    private Currency currency;

    private List<Client> clients;
    private List<Currency> currencies;

    private IEntityService<Client> clientService;
    private IEntityService<Currency> currencyService;

    @Override
    public void setUp() {
        clients = newArrayList();
        currencies = newArrayList();
        super.setUp();
        refreshClients();
        refreshCurrencies();
    }

    private void refreshClients() {
        clients.clear();
        clients.addAll(clientService.loadAll());
    }

    private void refreshCurrencies() {
        currencies.clear();
        currencies.addAll(currencyService.loadAll());
    }

    @Override
    public void reset() {
        sum = 0.0d;
        percent = 0.0d;
        expiryDate = new Date();
        client = null;
        currency = null;
    }

    @Override
    protected void fill(Deposit d) {
        d.setSum(sum);
        d.setPercent(percent);
        d.setExpiryDate(expiryDate);
        d.setClient(client);
        d.setCurrency(currency);
    }

    @Override
    public void select() {
        sum = selected.getSum();
        percent = selected.getPercent();
        expiryDate = selected.getExpiryDate();
        client = selected.getClient();
        currency = selected.getCurrency();
    }

    @Override
    public void refresh() {
        super.refresh();
        refreshClients();
        refreshCurrencies();
    }

    public String clientInfo(Client c) {
        return Formatter.toString(c);
    }

    public void setClientService(IEntityService<Client> clientService) {
        this.clientService = clientService;
    }

    public void setCurrencyService(IEntityService<Currency> currencyService) {
        this.currencyService = currencyService;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

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

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }
}
