package com.mytest.sharing;

import org.junit.Assert;
import org.junit.Test;

import com.mytest.common.builder.ToSensitiveStringBuilder;

/**
 * @author liqingyu
 * @since 2018/07/07
 */
public class UnsafeStatesTest {

    /**
     * 对象字段逸出
     */
    @Test
    public void getStates() {
        UnsafeStates unsafeStates = new UnsafeStates();
        String[] states = unsafeStates.getStates();
        states = new String[] { "BK", "BL" };
        Assert.assertEquals("", "String[][{AK,AL}]",
            ToSensitiveStringBuilder.reflectionToString(unsafeStates.getStates()));
        String[] states2 = unsafeStates.getStates();
        if (states2 != null && states2.length > 0) {
            states2[0] = "MODIFY";
        }
        Assert.assertEquals("     * 对象字段逸出", "String[][{MODIFY,AL}]",
            ToSensitiveStringBuilder.reflectionToString(unsafeStates.getStates()));
    }
}