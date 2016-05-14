package com.zhytnik.bank.backend.manager.impl;

import com.zhytnik.bank.domain.Department;

public class DepartmentManagerTest extends ManagerTest<Department> {

    @Override
    protected Class<Department> getEntityClass() {
        return Department.class;
    }

    @Override
    protected void updateEntity(Department d) {
        d.setNumber(5);
    }
}
