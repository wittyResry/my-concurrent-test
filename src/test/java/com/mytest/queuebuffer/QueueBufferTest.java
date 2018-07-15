package com.mytest.queuebuffer;

import java.util.concurrent.CountDownLatch;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author liqingyu
 * @since 2018/06/06
 */
public class QueueBufferTest {
    @Test
    public void test() throws InterruptedException {
        QueueBuffer q = new QueueBuffer();
        CountDownLatch c = new CountDownLatch(2);
        Producer producer = new Producer(q, c);
        Consumer consumer = new Consumer(q, c);
        c.await();
    }

}