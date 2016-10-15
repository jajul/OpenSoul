package com.gym.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.sql.Connection;

import static org.junit.Assert.*;

/**
 * Created by Gochan on 15.10.2016.
 */
public class MyDataSourceFactoryTest {
    private static final Logger logger = LogManager.getLogger(MyDataSourceFactoryTest.class);

    @Test
    public void testGetConnection() throws Exception {
        Connection conn = MyDataSourceFactory.getConnection();
        assert(conn != null);
    }

    @Test
    public void test() throws Exception {
        logger.info("Test1");
        logger.error("Test2");
        logger.debug("Test3");
    }
}