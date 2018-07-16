package com.mytest.sharing;

import org.junit.Assert;
import org.junit.Test;

import com.mytest.common.utils.LogUtil;

/**
 * @author liqingyu
 * @since 2018/07/16
 */
public class ThisEscapeTest {
    @Test
    public void test() {
        final ThisEscape.Event e = new ThisEscape.Event() {

            @Override
            public String getTopic() {
                return "TEST_TC";
            }

            @Override
            public String getEventCode() {
                return "TEST_EC";
            }
        };

        ThisEscape thisEscape = new ThisEscape(new ThisEscape.EventSource() {
            @Override
            public void registerListener(ThisEscape.EventListener eventListener) {
                LogUtil.digest("Ö´ÐÐ×¢²á");
                eventListener.onEvent(e);
            }
        });

        Assert.assertEquals("²âÊÔ", true, thisEscape.doSomething(e));
    }

}