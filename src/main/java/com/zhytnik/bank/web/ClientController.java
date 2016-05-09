package com.zhytnik.bank.web;

import com.zhytnik.bank.backend.manager.IEntityManager;
import com.zhytnik.bank.domain.*;

import java.util.List;

import static com.google.common.base.Strings.repeat;
import static com.google.common.collect.Lists.newArrayList;
import static java.lang.Boolean.TRUE;
import static java.lang.String.format;

public class ClientController extends EntityController<Client> {

    private String name;
    private String surname;
    private String address;
    private String password;
    private Boolean enable;
    private Banker banker;

    private List<Banker> bankers;
    private List<Bill> bills;
    private List<Credit> credits;
    private List<Deposit> deposits;

    private IEntityManager<Banker> bankerManager;

    @Override
    public void setUp() {
        bankers = newArrayList();
        super.setUp();
        loadBankers();
    }

    private void loadBankers() {
        bankers.clear();
        bankers.addAll(bankerManager.loadAll());
    }

    @Override
    public void reset() {
        name = "";
        surname = "";
        address = "";
        password = "";
        enable = TRUE;
        banker = null;
        bills = newArrayList();
        credits = newArrayList();
        deposits = newArrayList();
    }

    @Override
    protected void fill(Client c) {
        c.setName(name);
        c.setSurname(surname);
        c.setAddress(address);
        c.setPassword(password);
        c.setEnable(enable);
        c.setBanker(banker);
    }

    @Override
    public void select() {
        name = selected.getName();
        surname = selected.getSurname();
        address = selected.getAddress();
        password = repeat("*", selected.getPassword().length());
        enable = selected.getEnable();
        banker = selected.getBanker();

        service.initialize(selected);
        bills = newArrayList(selected.getBills());
        credits = newArrayList(selected.getCredits());
        deposits = newArrayList(selected.getDeposits());
    }

    @Override
    public void refresh() {
        super.refresh();
        loadBankers();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Banker getBanker() {
        return banker;
    }

    public void setBanker(Banker banker) {
        this.banker = banker;
    }

    public List<Bill> getBills() {
        return bills;
    }

    public void setBills(List<Bill> bills) {
        this.bills = bills;
    }

    public List<Credit> getCredits() {
        return credits;
    }

    public void setCredits(List<Credit> credits) {
        this.credits = credits;
    }

    public List<Deposit> getDeposits() {
        return deposits;
    }

    public void setDeposits(List<Deposit> deposits) {
        this.deposits = deposits;
    }

    public List<Banker> getBankers() {
        return bankers;
    }

    public String bankerInfo(Banker b){
        return format("%s %s. from %s department", b.getName(), b.getSurname().charAt(0), b.getDepartment().getNumber());
    }

    public void setBankers(List<Banker> bankers) {
        this.bankers = bankers;
    }

    public void setBankerManager(IEntityManager<Banker> bankerManager) {
        this.bankerManager = bankerManager;
    }
}
