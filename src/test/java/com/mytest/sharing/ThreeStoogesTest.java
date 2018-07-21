package com.mytest.sharing;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author liqingyu
 * @since 2018/07/21
 */
public class ThreeStoogesTest {

    @Test
    public void isStooge() {
        ThreeStooges threeStooges = new ThreeStooges();
        boolean exist = threeStooges.isStooge("Curly");
        Assert.assertEquals("contains", true, exist);
    }

    @Test
    public void getStoogeNames() {
        ThreeStooges threeStooges = new ThreeStooges();
        String res = threeStooges.getStoogeNames();
        Assert.assertEquals("–Ú¡–ªØ", "[Moe, Larry, Curly]", res);
    }
}