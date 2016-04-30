package com.zhytnik.bank.domain;

import com.zhytnik.bank.backend.domain.Entity;
import com.zhytnik.bank.backend.domain.Reference;

import java.util.Set;

import static com.google.common.collect.Sets.newHashSet;

public class Department extends Entity {

    private Integer number;
    private String address;

    @Reference(type = Client.class)
    private Set<Client> clients = newHashSet();

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

    public Set<Client> getClients() {
        return clients;
    }

    public void setClients(Set<Client> clients) {
        this.clients = clients;
    }
}
