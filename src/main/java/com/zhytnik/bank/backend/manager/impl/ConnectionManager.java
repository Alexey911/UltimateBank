package com.zhytnik.bank.backend.manager.impl;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManager {

    @Autowired
    private BasicDataSource dataSource = getDefaultConnection();

    public static BasicDataSource getDefaultConnection() {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("oracle.jdbc.OracleDriver");
        ds.setUrl("jdbc:oracle:thin:@//localhost:1521/XE");
        ds.setUsername("admin");
        ds.setPassword("password");
        return ds;
    }

    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void close(Connection c) {
        try {
            c.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}
