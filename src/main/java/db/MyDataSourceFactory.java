package db;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

import javax.sql.DataSource;
import java.io.FileInputStream;
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
            dataSource = new MysqlDataSource();
            dataSource.setURL(props.getProperty("MYSQL_DB_URL"));
            dataSource.setUser(props.getProperty("MYSQL_DB_USERNAME"));
            dataSource.setPassword(props.getProperty("MYSQL_DB_PASSWORD"));
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
