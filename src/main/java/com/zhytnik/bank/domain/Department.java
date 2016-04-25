package com.zhytnik.bank.domain;

import com.zhytnik.bank.backend.domain.Entity;

import static com.google.common.base.Objects.equal;

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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final Department o = (Department) obj;
        return equal(number, o.number) && equal(address, o.address);
    }
}
