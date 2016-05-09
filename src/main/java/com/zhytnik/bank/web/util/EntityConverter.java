package com.zhytnik.bank.web.util;

import com.zhytnik.bank.backend.types.IEntity;
import com.zhytnik.bank.service.IEntityService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public class EntityConverter<T extends IEntity> implements Converter {

    private IEntityService service;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        return service.findById(Integer.valueOf(s));
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        final T entity = (T) o;
        return Integer.toString(entity.getId());
    }

    public void setService(IEntityService<T> service) {
        this.service = service;
    }
}
