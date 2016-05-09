package com.zhytnik.bank.web.converter;

import com.zhytnik.bank.domain.Found;
import com.zhytnik.bank.service.impl.FoundService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("foundConverter")
public class FoundConverter implements Converter {

    private FoundService service;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        return service.findByCode(s);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        final Found f = (Found) o;
        return f.getCode();
    }

    public void setService(FoundService service) {
        this.service = service;
    }
}
