package com.pluralsight.db;

import com.pluralsight.dao.impl.JdbcContractDAO;
import com.pluralsight.dao.impl.JdbcDealershipDAO;
import com.pluralsight.dao.impl.JdbcVehicleDAO;
import lombok.Getter;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;

@Getter
public class DataManager {
    private BasicDataSource dataSource;

    public DataManager(String url, String username, String password) {
        dataSource = new BasicDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
    }
}