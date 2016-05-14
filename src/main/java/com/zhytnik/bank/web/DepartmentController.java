package com.zhytnik.bank.web;

import com.zhytnik.bank.domain.Banker;
import com.zhytnik.bank.domain.Department;
import com.zhytnik.bank.domain.Found;
import com.zhytnik.bank.service.IEntityService;

import javax.faces.bean.ViewScoped;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@ViewScoped
public class DepartmentController extends EntityController<Department> {

    private Integer number;
    private String address;
    private Found found;

    private IEntityService<Found> foundService;

    private List<Found> founds;
    private List<Banker> bankers;

    @Override
    public void setUp() {
        super.setUp();
        founds = newArrayList();
        bankers = newArrayList();
        refreshFounds();
    }

    private void refreshFounds() {
        founds.clear();
        founds.addAll(foundService.loadAll());
    }

    @Override
    public void reset() {
        number = 0;
        address = "";
        found = null;
    }

    @Override
    protected void fill(Department d) {
        d.setNumber(number);
        d.setAddress(address);
        d.setFound(found);
    }

    @Override
    public void select() {
        number = selected.getNumber();
        address = selected.getAddress();
        found = selected.getFound();
        bankers = newArrayList(selected.getBankers());
    }

    @Override
    public void refresh() {
        super.refresh();
        refreshFounds();
    }

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

    public List<Found> getFounds() {
        return founds;
    }

    public void setFounds(List<Found> founds) {
        this.founds = founds;
    }

    public void setFoundService(IEntityService<Found> foundService) {
        this.foundService = foundService;
    }

    public List<Banker> getBankers() {
        return bankers;
    }

    public void setBankers(List<Banker> bankers) {
        this.bankers = bankers;
    }
}
