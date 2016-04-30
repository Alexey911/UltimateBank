package com.zhytnik.bank;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public abstract class AbstractController {

    protected void message() {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Registration failure : passwords must match", ""));
//        FacesContext.getCurrentInstance().addMessage(null,
//                new FacesMessage(FacesMessage.SEVERITY_INFO, "Congratulations, registration has been successful", ""));
    }

}
