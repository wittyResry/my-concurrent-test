package com.mytest.sharing;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author liqingyu
 * @since 2018/07/22
 */
public class VolatileCachedFactorizerTest {

    @Test
    public void service() {
        VolatileCachedFactorizer volatileCachedFactorizer = new VolatileCachedFactorizer();
        String req = "7";
        volatileCachedFactorizer.service(req, "");
    }
}