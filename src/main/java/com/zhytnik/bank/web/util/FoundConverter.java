package com.zhytnik.bank.web.util;

import com.zhytnik.bank.backend.manager.IEntityManager;
import com.zhytnik.bank.domain.Found;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import static com.google.common.collect.Iterables.getOnlyElement;
import static com.zhytnik.bank.backend.manager.impl.ManagerContainer.getEntityManager;

@FacesConverter("foundConverter")
public class FoundConverter implements Converter {

    private IEntityManager<Found> manager = getEntityManager(Found.class);

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        return getOnlyElement(manager.findByFieldValue("code", s));
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        final Found f = (Found) o;
        return f.getCode();
    }
}
