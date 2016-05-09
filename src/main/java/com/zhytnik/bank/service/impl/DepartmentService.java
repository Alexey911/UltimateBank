package com.zhytnik.bank.service.impl;

import com.zhytnik.bank.domain.Department;

public class DepartmentService extends EntityService<Department> {

    @Override
    public Department instantiate() {
        return new Department();
    }
}
