package com.mytest.sharing;

import java.sql.Connection;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author liqingyu
 * @since 2018/07/19
 */
public class ConnectionDispenserTest {

    @Test
    public void getConnection() {
        ConnectionDispenser connectionDispenser = new ConnectionDispenser();
        try {
            Connection connection = connectionDispenser.getConnection();
            Assert.assertNotNull(connection);
        } catch (Exception e) {
            Assert.assertEquals("test db not start", "Unable to acquire Connection",
                e.getMessage());
        }
    }
}