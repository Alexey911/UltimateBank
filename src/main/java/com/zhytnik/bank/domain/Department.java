package com.zhytnik.bank.domain;

import com.zhytnik.bank.backend.domain.Depends;
import com.zhytnik.bank.backend.domain.Entity;
import com.zhytnik.bank.backend.domain.Reference;

import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

public class Department extends Entity {

    private Integer number;
    private String address;

    @Depends
    private Found found;

    @Reference(type = Banker.class)
    private Set<Banker> bankers = newHashSet();

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Found getFound() {
        return found;
    }

    public void setFound(Found found) {
        this.found = found;
    }

    public Set<Banker> getBankers() {
        return bankers;
    }

    public void setBankers(Set<Banker> bankers) {
        this.bankers = bankers;
    }
}
