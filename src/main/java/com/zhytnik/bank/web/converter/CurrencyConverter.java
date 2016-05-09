package com.zhytnik.bank.web.converter;

import com.zhytnik.bank.domain.Currency;
import com.zhytnik.bank.service.impl.CurrencyService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public class CurrencyConverter implements Converter {

    private CurrencyService service;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        return service.findByName(s);
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        final Currency c = (Currency) o;
        return c.getName();
    }

    public void setService(CurrencyService service) {
        this.service = service;
    }
}
