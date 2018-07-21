package com.mytest.sharing;

import java.math.BigInteger;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author liqingyu
 * @since 2018/07/21
 */
public class OneValueCacheTest {

    @Test
    public void getFactors() {
        BigInteger i = new BigInteger("30");
        BigInteger[] factors = new BigInteger[] { new BigInteger("2"), new BigInteger("3"),
                                                  new BigInteger("5") };

        OneValueCache oneValueCache = new OneValueCache(i, factors);
        Assert.assertEquals("", factors, oneValueCache.getFactors(new BigInteger("30")));
    }
}