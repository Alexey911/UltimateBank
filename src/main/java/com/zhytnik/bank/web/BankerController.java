package com.zhytnik.bank.web;

import com.zhytnik.bank.backend.manager.IEntityManager;
import com.zhytnik.bank.domain.Banker;
import com.zhytnik.bank.domain.Department;

import javax.faces.bean.ViewScoped;
import java.util.List;

import static com.google.common.base.Strings.repeat;
import static com.google.common.collect.Lists.newArrayList;

@ViewScoped
public class BankerController extends EntityController<Banker> {

    private String name;
    private String surname;
    private String address;
    private String password;
    private Integer privilege;
    private Department department;

    private List<Department> departments;

    private IEntityManager<Department> departmentManager;

    @Override
    public void setUp() {
        super.setUp();
        departments = newArrayList();
        loadDepartments();
    }

    private void loadDepartments() {
        departments.clear();
        departments.addAll(departmentManager.loadAll());
    }

    @Override
    public void reset() {
        name = "";
        surname = "";
        address = "";
        password = "";
        privilege = 0;
        department = null;
    }

    @Override
    protected void fill(Banker b) {
        b.setName(name);
        b.setSurname(surname);
        b.setAddress(address);
        b.setPrivilege(privilege);
        b.setPassword(password);
        b.setDepartment(department);
    }

    @Override
    public void select() {
        name = selected.getName();
        surname = selected.getSurname();
        address = selected.getAddress();
        password = repeat("*", selected.getPassword().length());
        privilege = selected.getPrivilege();
        department = selected.getDepartment();
    }

    @Override
    public void refresh() {
        super.refresh();
        loadDepartments();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getPrivilege() {
        return privilege;
    }

    public void setPrivilege(Integer privilege) {
        this.privilege = privilege;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public void setDepartmentManager(IEntityManager<Department> departmentManager) {
        this.departmentManager = departmentManager;
    }
}
