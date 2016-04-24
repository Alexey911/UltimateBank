package com.zhytnik.bank;

import com.zhytnik.bank.backend.manager.impl.ConnectionManager;
import com.zhytnik.bank.backend.manager.impl.ScriptManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "lol")
public class Item {
    public String message = "Win!";

    @Autowired
    private ConnectionManager manager;

    @Autowired
    private ScriptManager scriptManager;

    public String getMessage() {
        return manager.getConnection().toString();
    }
}
