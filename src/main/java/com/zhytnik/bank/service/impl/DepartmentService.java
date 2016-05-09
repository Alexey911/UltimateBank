package com.zhytnik.bank.service.impl;

import com.zhytnik.bank.domain.Banker;
import com.zhytnik.bank.domain.Department;

import java.util.Set;

public class DepartmentService extends EntityService<Department> {

    @Override
    public Department instantiate() {
        return new Department();
    }

    public Department findByNumber(Integer number) {
        return findByParameter("number", number);
    }

    @Override
    public Department initialize(Department d) {
        final Set<Banker> bankers = manager.load(d.getId()).getBankers();
        d.setBankers(bankers);
        return d;
    }
}
