package com.zhytnik.bank.web;

import com.zhytnik.bank.domain.Bill;
import com.zhytnik.bank.domain.Client;
import com.zhytnik.bank.domain.Currency;
import com.zhytnik.bank.domain.card.BillCard;
import com.zhytnik.bank.service.IEntityService;
import com.zhytnik.bank.web.util.Formatter;

import javax.faces.bean.ViewScoped;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static java.lang.Boolean.TRUE;

@ViewScoped
public class BillController extends EntityController<Bill> {

    private Double balance;
    private Boolean isActive;
    private Currency currency;
    private Client client;
    private BillCard card;

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
        balance = 0.0d;
        isActive = TRUE;
        currency = null;
        client = null;
        card = null;
    }

    @Override
    protected void fill(Bill b) {
        b.setBalance(balance);
        b.setIsActive(isActive);
        b.setCurrency(currency);
        b.setClient(client);
        b.setCard(card);
    }

    @Override
    public void select() {
        balance = selected.getBalance();
        isActive = selected.getIsActive();
        currency = selected.getCurrency();
        client = selected.getClient();
        card = selected.getCard();
    }

    @Override
    public void refresh() {
        super.refresh();
        refreshClients();
    }

    public String clientInfo(Client client) {
        return Formatter.toString(client);
    }

    public String cardInfo(BillCard billCard) {
        return Formatter.toString(billCard);
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

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public void setClientService(IEntityService<Client> clientService) {
        this.clientService = clientService;
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
