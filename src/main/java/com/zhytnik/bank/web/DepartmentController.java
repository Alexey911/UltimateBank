package com.zhytnik.bank.web;

import com.zhytnik.bank.backend.manager.impl.ManagerContainer;
import com.zhytnik.bank.domain.Department;
import com.zhytnik.bank.domain.Found;
import org.springframework.stereotype.Component;

import javax.faces.bean.ViewScoped;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Component(value = "departments")
@ViewScoped
public class DepartmentController extends EntityController<Department> {

    private Integer number;
    private String address;
    private Found found;

    private List<Found> founds;

    @Override
    public void setUp() {
        super.setUp();
        founds = newArrayList();
        loadFounds();
    }

    private void loadFounds() {
        founds.clear();
        founds.addAll(ManagerContainer.getEntityManager(Found.class).loadAll());
    }

    @Override
    public void reset() {
        number = 0;
        address = "";
        found = null;
    }

    @Override
    public Department instantiate() {
        return new Department();
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
    }

    @Override
    public void refresh() {
        super.refresh();
        loadFounds();
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

    @Override
    public Class<Department> getEntityClass() {
        return Department.class;
    }
}