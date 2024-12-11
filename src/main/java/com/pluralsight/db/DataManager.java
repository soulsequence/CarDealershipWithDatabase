package com.pluralsight.db;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;

public class DataManager {
    private BasicDataSource dataSource;

    public DataSource dataSource() { return dataSource; }

}
