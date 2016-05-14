package com.zhytnik.bank.web;

import com.zhytnik.bank.domain.Bill;
import com.zhytnik.bank.domain.card.BillCard;
import com.zhytnik.bank.service.IEntityService;
import com.zhytnik.bank.service.impl.BillCardService;

import javax.faces.bean.ViewScoped;
import java.util.Date;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@ViewScoped
public class BillCardController extends EntityController<BillCard> {

    private String code;
    private Date validity;
    private Bill bill;
    private Integer cvc;
    private Integer validationCode;

    private List<Bill> bills;

    private IEntityService<Bill> billService;

    private BillCardService billCardService;

    @Override
    public void setUp() {
        bills = newArrayList();
        super.setUp();
        refreshBills();
    }

    private void refreshBills() {
        bills.clear();
        bills.addAll(billService.loadAll());
    }

    @Override
    public void reset() {
        code = "";
        validity = new Date();
        bill = null;
        cvc = null;
        validationCode = null;
    }

    @Override
    protected void fill(BillCard c) {
        c.setCode(code);
        c.setValidity(validity);

        c.setCvc(cvc != null ? c.getCvc() : billCardService.generateCVC());

        c.setValidationCode(validationCode != null ?
                c.getValidationCode() : billCardService.generateValidationCode());

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

    public Date getValidity() {
        return validity;
    }

    public void setValidity(Date validity) {
        this.validity = validity;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public void setBillService(IEntityService<Bill> billService) {
        this.billService = billService;
    }

    public List<Bill> getBills() {
        return bills;
    }

    public void setBills(List<Bill> bills) {
        this.bills = bills;
    }

    public void setBillCardService(BillCardService billCardService) {
        this.billCardService = billCardService;
    }
}
