package com.zhytnik.bank.tool;

import com.zhytnik.bank.backend.manager.impl.ScriptManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class StartUpListener implements ApplicationListener<ContextRefreshedEvent> {
    private boolean startUp = true;

    @Autowired
    private ScriptManager manager;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (startUp) {
            startUp = false;
            manager.update();
        }
    }
}
