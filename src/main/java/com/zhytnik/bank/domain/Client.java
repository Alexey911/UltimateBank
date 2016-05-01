package com.zhytnik.bank.domain;

import com.zhytnik.bank.backend.domain.Depends;
import com.zhytnik.bank.backend.domain.Entity;
import com.zhytnik.bank.backend.domain.Reference;

import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

public class Client extends Entity {

    private String name;
    private String surname;
    private String address;
    private String password;
    private Boolean enable;

    @Depends
    private Banker banker;

    @Reference(type = Bill.class)
    private Set<Bill> bills = newHashSet();

    @Reference(type = Credit.class)
    private Set<Credit> credits = newHashSet();

    @Reference(type = Deposit.class)
    private Set<Deposit> deposits = newHashSet();

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

    public Banker getBanker() {
        return banker;
    }

    public void setBanker(Banker banker) {
        this.banker = banker;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Set<Bill> getBills() {
        return bills;
    }

    public void setBills(Set<Bill> bills) {
        this.bills = bills;
    }

    public Set<Credit> getCredits() {
        return credits;
    }

    public void setCredits(Set<Credit> credits) {
        this.credits = credits;
    }

    public Set<Deposit> getDeposits() {
        return deposits;
    }

    public void setDeposits(Set<Deposit> deposits) {
        this.deposits = deposits;
    }
}
