package com.zhytnik.bank.web;

import com.zhytnik.bank.backend.manager.IEntityManager;
import com.zhytnik.bank.domain.Bill;
import com.zhytnik.bank.domain.card.BillCard;

import javax.faces.bean.ViewScoped;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@ViewScoped
public class BillCardController extends EntityController<BillCard> {

    private String code;
    private String validity;
    private Integer cvc;
    private Integer validationCode;
    private Bill bill;

    private List<Bill> bills;

    private IEntityManager<Bill> billManager;

    @Override
    public void setUp() {
        bills = newArrayList();
        super.setUp();
        refreshBills();
    }

    private void refreshBills() {
        bills.clear();
        bills.addAll(billManager.loadAll());
    }

    @Override
    public void reset() {
        code = "";
        validity = "";
        cvc = null;
        validationCode = null;
        bill = null;
    }

    @Override
    protected void fill(BillCard c) {
        c.setCode(code);
        c.setValidity(validity);
        c.setCvc(cvc);
        c.setValidationCode(validationCode);
        c.setBill(bill);
    }

    @Override
    public void select() {
        code = selected.getCode();
        validity = selected.getValidity();
        cvc = selected.getCvc();
        validationCode = selected.getValidationCode();
        bill = selected.getBill();
    }

    @Override
    public void refresh() {
        super.refresh();
        refreshBills();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
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

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public void setBillManager(IEntityManager<Bill> billManager) {
        this.billManager = billManager;
    }
}
