package com.zhytnik.bank.domain.card;

import com.zhytnik.bank.backend.types.Entity;

import java.util.Date;

public abstract class Card extends Entity {

    private String code;
    private Date validity;
    private Integer cvc;
    private Integer validationCode;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getValidity() {
        return validity;
    }

    public void setValidity(Date validity) {
        this.validity = validity;
    }

    public Integer getCvc() {
        return cvc;
    }

    public void setCvc(Integer cvc) {
        this.cvc = cvc;
    }

    public Integer getValidationCode() {
        return validationCode;
    }

    public void setValidationCode(Integer validationCode) {
        this.validationCode = validationCode;
    }
}
