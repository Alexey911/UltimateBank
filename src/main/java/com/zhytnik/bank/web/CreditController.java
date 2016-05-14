package com.zhytnik.bank.web;

import com.zhytnik.bank.domain.Client;
import com.zhytnik.bank.domain.Credit;
import com.zhytnik.bank.domain.Currency;
import com.zhytnik.bank.domain.card.CreditCard;
import com.zhytnik.bank.service.IEntityService;
import com.zhytnik.bank.web.util.Formatter;

import javax.faces.bean.ViewScoped;
import java.util.Date;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@ViewScoped
public class CreditController extends EntityController<Credit> {

    private Double amount;
    private Date validFor;
    private Double margin;
    private Integer fee;
    private Boolean isActive;
    private Double penalty;

    private CreditCard creditCard;
    private Currency currency;
    private Client client;

    private List<Currency> currencies;
    private List<Client> clients;

    private IEntityService<Currency> currencyService;
    private IEntityService<Client> clientService;

    @Override
    public void setUp() {
        currencies = newArrayList();
        clients = newArrayList();
        super.setUp();
        refreshCurrencies();
        refreshClients();
    }

    private void refreshCurrencies() {
        currencies.clear();
        currencies.addAll(currencyService.loadAll());
    }

    private void refreshClients() {
        clients.clear();
        clients.addAll(clientService.loadAll());
    }

    @Override
    public void reset() {
        amount = null;
        validFor = new Date();
        margin = null;
        fee = null;
        isActive = true;
        penalty = null;
        creditCard = null;
        currency = null;
        client = null;
    }

    @Override
    public void refresh() {
        super.refresh();
        refreshCurrencies();
        refreshClients();
    }

    @Override
    protected void fill(Credit c) {
        c.setAmount(amount);
        c.setValidFor(validFor);
        c.setMargin(margin != null ? margin : 0);
        c.setFee(fee);
        c.setIsActive(isActive);
        c.setPenalty(penalty != null ? penalty : 0);
        c.setCreditCard(creditCard);
        c.setCurrency(currency);
        c.setClient(client);
    }

    @Override
    public void select() {
        amount = selected.getAmount();
        validFor = selected.getValidFor();
        margin = selected.getMargin();
        fee = selected.getFee();
        isActive = selected.getIsActive();
        penalty = selected.getPenalty();
        creditCard = selected.getCreditCard();
        currency = selected.getCurrency();
        client = selected.getClient();
    }

    public String clientInfo(Client c) {
        return Formatter.toString(c);
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

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    public Integer getFee() {
        return fee;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public void setCurrencyService(IEntityService<Currency> currencyService) {
        this.currencyService = currencyService;
    }

    public void setClientService(IEntityService<Client> clientService) {
        this.clientService = clientService;
    }
}
