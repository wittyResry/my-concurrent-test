package com.mytest.monitor;

import org.junit.Assert;
import org.junit.Test;

import com.mytest.common.utils.MyReflectUtils;

import static org.junit.Assert.*;

/**
 * @author liqingyu
 * @since 2018/06/07
 */
public class MonitorTest {
    @Test
    public void test1() {
        Monitor monitor = new Monitor();
        Throwable executeThrowable = null;
        try {
            monitor.notify();
        } catch (Throwable e) {
            executeThrowable = e;
        }
        Assert.assertEquals("not get monitor, expect throw MonitorException",
            IllegalMonitorStateException.class, MyReflectUtils.getClass(executeThrowable));
    }

    @Test
    public void test2() {
        Monitor monitor = new Monitor();
        Monitor monitor2 = new Monitor();
        Throwable executeThrowable = null;
        try {
            synchronized (monitor2) {
                monitor.wait();
            }
        } catch (Throwable e) {
            executeThrowable = e;
        }
        Assert.assertEquals("not get current object monitor, expect throw MonitorException",
            IllegalMonitorStateException.class, MyReflectUtils.getClass(executeThrowable));
    }

}