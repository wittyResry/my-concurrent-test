package com.mytest.sharing;

import java.util.concurrent.CountDownLatch;

import org.junit.Assert;
import org.junit.Test;

import com.mytest.common.utils.LogUtil;

/**
 * @author liqingyu
 * @since 2018/07/07
 */
public class MutableIntegerTest {

    /** �����߳��� */
    private static final int SIZE = 1000;

    @Test
    public void setTest() throws InterruptedException {

        MutableInteger mutableInteger = new MutableInteger();
        CountDownLatch countDownLatch = new CountDownLatch(SIZE);
        for (int i = 0; i < SIZE; ++i) {
            new MyMutableIntegerTest(mutableInteger, countDownLatch, i);
        }
        countDownLatch.await();
        System.out.println(mutableInteger.get());
        Assert.assertEquals("��������", true, mutableInteger.get() <= 100000);
    }

    public class MyMutableIntegerTest implements Runnable {
        private MutableInteger mutableInteger;
        private CountDownLatch countDownLatch;

        public MyMutableIntegerTest(MutableInteger mutableInteger, CountDownLatch countDownLatch,
                                    int id) {
            this.mutableInteger = mutableInteger;
            this.countDownLatch = countDownLatch;
            new Thread(this, this.getClass().getSimpleName() + id).start();
        }

        @Override
        public void run() {
            for (int i = 0; i < 100; ++i) {
                mutableInteger.set(mutableInteger.get() + 1);
            }
            //ִ�����
            countDownLatch.countDown();
        }
    }
}