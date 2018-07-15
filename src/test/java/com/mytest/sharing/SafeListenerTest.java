package com.mytest.sharing;

import org.junit.Assert;
import org.junit.Test;

import com.mytest.common.utils.LogUtil;

/**
 * @author liqingyu
 * @since 2018/07/07
 */
public class SafeListenerTest {

    @Test
    public void test() {
        final SafeListener.Event e = new SafeListener.Event() {
            @Override
            public String getTopic() {
                return "TP_TEST";
            }

            @Override
            public String getEventCode() {
                return "EC_TEST";
            }
        };
        SafeListener.EventSource source = new SafeListener.EventSource() {
            @Override
            public void registerListener(SafeListener.EventListener eventListener) {
                LogUtil.digest("Ö´ÐÐ×¢²á");
                eventListener.onEvent(e);
            }
        };
        SafeListener safeListener = SafeListener.newInstance(source);
        Assert.assertEquals("", true, safeListener.doSomething(e));;
    }
}