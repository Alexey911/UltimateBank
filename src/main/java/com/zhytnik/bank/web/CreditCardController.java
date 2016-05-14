package com.zhytnik.bank.web;

import com.zhytnik.bank.domain.Credit;
import com.zhytnik.bank.domain.card.CreditCard;
import com.zhytnik.bank.service.IEntityService;
import com.zhytnik.bank.service.impl.CreditCardService;

import javax.faces.bean.ViewScoped;
import java.util.Date;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@ViewScoped
public class CreditCardController extends EntityController<CreditCard> {

    private String code;
    private Date validity;
    private Credit credit;
    private Integer cvc;
    private Integer validationCode;

    private List<Credit> credits;

    private IEntityService<Credit> creditService;

    @Override
    public void setUp() {
        credits = newArrayList();
        super.setUp();
        refreshCredits();
    }

    private void refreshCredits() {
        credits.clear();
        credits.addAll(creditService.loadAll());
    }

    @Override
    public void reset() {
        code = "";
        validity = new Date();
        credit = null;
        cvc = null;
        validationCode = null;
    }

    @Override
    protected void fill(CreditCard c) {
        c.setCode(code);
        c.setValidity(validity);

        c.setCvc(cvc != null ? c.getCvc() : getService().generateCVC());

        c.setValidationCode(validationCode != null ?
                c.getValidationCode() : getService().generateValidationCode());

        c.setCredit(credit);
    }

    @Override
    public void select() {
        code = selected.getCode();
        validity = selected.getValidity();
        cvc = selected.getCvc();
        validationCode = selected.getValidationCode();
        credit = selected.getCredit();
    }

    @Override
    public void refresh() {
        super.refresh();
        refreshCredits();
    }

    @Override
    public CreditCardService getService() {
        return (CreditCardService) service;
    }

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

    public Credit getCredit() {
        return credit;
    }

    public void setCredit(Credit credit) {
        this.credit = credit;
    }

    public List<Credit> getCredits() {
        return credits;
    }

    public void setCredits(List<Credit> credits) {
        this.credits = credits;
    }

    public void setCreditService(IEntityService<Credit> creditService) {
        this.creditService = creditService;
    }
}
