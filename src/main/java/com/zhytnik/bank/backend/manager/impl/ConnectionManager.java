package com.zhytnik.bank.backend.manager.impl;

import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManager {

    private BasicDataSource dataSource;

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

    public void close(CallableStatement s) {
        try {
            s.getConnection().close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }
}
