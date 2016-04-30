package com.zhytnik.bank;

import com.zhytnik.bank.backend.manager.IEntityManager;
import com.zhytnik.bank.backend.manager.impl.EntityManager;
import com.zhytnik.bank.domain.Client;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.*;

import static com.zhytnik.bank.backend.tool.ReflectionUtil.instantiate;

@Component(value = "clients")
@SessionScoped
public class ClientsController extends AbstractController implements Serializable {

    private IEntityManager<Client> manager = new EntityManager<>(Client.class);

    private Integer id;
    private String name;
    private String surname;
    private String address;
    private String password;

    private List<Client> clients = loadClients();

    private List<Client> loadClients() {
        return new ArrayList<>(manager.loadAll());
    }

    public static void addInfoMessage(String str) {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle = context.getApplication().
                getResourceBundle(context, "msg");
        String message = bundle.getString(str);
        FacesContext.getCurrentInstance().addMessage(null, new
                FacesMessage(FacesMessage.SEVERITY_INFO, message, ""));
    }

    public Integer getCount() {
        return manager.getCount();
    }

    public List<Client> getClients() {
        return clients;
    }

    private String txt1 = "";



    public List<String> completeText(String query) {
        System.out.println(query);
        long t = System.currentTimeMillis();
        List<String> some = new ArrayList<>();
        for(Client c : manager.loadAll()){
            if(c.getName().contains(query)){
                some.add(c.getName());
            }
        }
        t = System.currentTimeMillis() - t;
        some.add(Long.toString(t));
        return some;
    }

    public List<Client> sort(){
        return getClients();
    }

    public String save() {
        final Client c = instantiate(Client.class);
        c.setName(name);
        c.setSurname(surname);
        c.setAddress(address);
        c.setPassword(password);
        manager.save(c);
        return "home";
    }

    public void reset() {
        name = "";
        surname = "";
        address = "";
        password = "";
        message();
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String addMessage() {
        addInfoMessage("broadcast.message");
        return null;
    }


    public String getTxt1() {
        return txt1;
    }

    public void setTxt1(String txt1) {
        this.txt1 = txt1;
    }
}
