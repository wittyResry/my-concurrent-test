package com.mytest.sharing;

import java.util.concurrent.CountDownLatch;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author liqingyu
 * @since 2018/07/07
 */
public class SynchronizedIntegerTest {

    /** 并发线程数 */
    private static final int SIZE = 100;

    /**
     * 并发测试
     */
    @Test
    public void setTest() throws InterruptedException {

        SynchronizedInteger synchronizedInteger = new SynchronizedInteger();
        CountDownLatch countDownLatch = new CountDownLatch(SIZE);
        for (int i = 0; i < SIZE; ++i) {
            new MySyncTest(synchronizedInteger, countDownLatch, i);
        }
        countDownLatch.await();
        Assert.assertEquals("并发测试", 10000, synchronizedInteger.get());
    }

    public class MySyncTest implements Runnable {
        private SynchronizedInteger synchronizedInteger;
        private CountDownLatch      countDownLatch;

        public MySyncTest(SynchronizedInteger synchronizedInteger, CountDownLatch countDownLatch,
                          int id) {
            this.synchronizedInteger = synchronizedInteger;
            this.countDownLatch = countDownLatch;
            new Thread(this, this.getClass().getSimpleName() + id).start();
        }

        @Override
        public void run() {
            for (int i = 0; i < 100; ++i) {
                synchronized (synchronizedInteger) {
                    // 可重入锁：加锁是为了保证CAS不满足
                    synchronizedInteger.set(synchronizedInteger.get() + 1);
                }
            }
            //执行完成
            countDownLatch.countDown();
        }
    }
}