package com.zhytnik.bank.domain;

import com.zhytnik.bank.backend.domain.Entity;

public class Department extends Entity {

    private Integer number;
    private String address;

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
}
