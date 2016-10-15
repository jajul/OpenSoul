package com.gym.db;

import org.junit.Test;

import java.sql.Connection;

import static org.junit.Assert.*;

/**
 * Created by Gochan on 15.10.2016.
 */
public class MyDataSourceFactoryTest {

    //@Test
    public void testGetConnection() throws Exception {
        Connection conn = MyDataSourceFactory.getConnection();
        assert(conn != null);
    }
}