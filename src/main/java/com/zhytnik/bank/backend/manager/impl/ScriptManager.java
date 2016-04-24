package com.zhytnik.bank.backend.manager.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.sort;

public class ScriptManager {

    @Autowired
    private ConnectionManager manager;

    private Logger logger = Logger.getLogger(ScriptManager.class);

    private String ddcPath;
    private String ddlPath;
    private String ddmPath;
    private boolean dropCreate;

    public void setDdcPath(String ddcPath) {
        this.ddcPath = ddcPath;
    }

    public void setDdlPath(String ddlPath) {
        this.ddlPath = ddlPath;
    }

    public void setDropCreate(boolean dropCreate) {
        this.dropCreate = dropCreate;
    }

    public void setDdmPath(String ddmPath) {
        this.ddmPath = ddmPath;
    }

    public void update() {
        if (dropCreate) {
            logger.info("Start DropCreate");
            executeScripts(getScripts(ddcPath));
            executeScripts(getScripts(ddlPath));
            executeScripts(getScripts(ddmPath));
            logger.info("Finish DropCreate");
        }
    }

    public void executeScripts(Resource[] scripts) {
        final ResourceDatabasePopulator p = new ResourceDatabasePopulator(scripts);
        p.setIgnoreFailedDrops(true);
        p.setContinueOnError(true);
        p.setSeparator("/");
        p.execute(manager.getDataSource());
    }

    private Resource[] getScripts(String path) {
        final List<Resource> scripts = new ArrayList<>();
        for (File file : getRoot(path).listFiles()) {
            scripts.add(new FileSystemResource(file.getPath()));
        }
        sort(scripts, (a, b) -> a.getFilename().compareTo(b.getFilename()));
        return scripts.toArray(new Resource[scripts.size()]);
    }

    private File getRoot(String path) {
        try {
            final URL resource = this.getClass().getClassLoader().getResource(path);
            return new File(resource.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
