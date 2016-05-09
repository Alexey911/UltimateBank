package com.zhytnik.bank.web.converter;

import com.zhytnik.bank.domain.Department;
import com.zhytnik.bank.service.impl.DepartmentService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import static java.lang.Integer.valueOf;

public class DepartmentConverter implements Converter {

    private DepartmentService service;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        return service.findByNumber(valueOf(s));
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        final Department d = (Department) o;
        return Integer.toString(d.getNumber());
    }

    public void setService(DepartmentService service) {
        this.service = service;
    }
}
