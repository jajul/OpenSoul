package db;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by Julia on 14.10.2016.
 */
public class MyDataSourceFactory {
    public DataSource getMySQLDataSource() {
        Properties props = new Properties();
        InputStream fis = null;
        MysqlDataSource mysqlDS = null;
        try {
            fis = (InputStream) this.getClass().getClassLoader().getResourceAsStream("db.properties");
            props.load(fis);
            mysqlDS = new MysqlDataSource();
            mysqlDS.setURL(props.getProperty("MYSQL_DB_URL"));
            mysqlDS.setUser(props.getProperty("MYSQL_DB_USERNAME"));
            mysqlDS.setPassword(props.getProperty("MYSQL_DB_PASSWORD"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mysqlDS;
    }
}
