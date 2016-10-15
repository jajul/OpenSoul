package com.gym.db;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by Julia on 14.10.2016.
 */
public class MyDataSourceFactory {
    private static MysqlDataSource dataSource;

    public MyDataSourceFactory() {
        dataSource = getDataSource();
    }

    private static MysqlDataSource getDataSource() {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        Properties props = new Properties();
        try (InputStream fis = (InputStream) MyDataSourceFactory.class.getClassLoader().getResourceAsStream("db.properties")) {
            props.load(fis);
            mysqlDataSource.setURL(props.getProperty("MYSQL_DB_URL"));
            mysqlDataSource.setUser(props.getProperty("MYSQL_DB_USERNAME"));
            mysqlDataSource.setPassword(props.getProperty("MYSQL_DB_PASSWORD"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mysqlDataSource;
    }

    public static Connection getConnection() throws SQLException {
        if (dataSource == null) {
            dataSource = getDataSource();
        }
        return dataSource.getConnection();
    }
}
