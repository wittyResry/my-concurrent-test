package com.mytest.queuebuffer;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author liqingyu
 * @since 2018/06/06
 */
public class QueueBufferTest {
    @Test
    public void test() {
        QueueBuffer q = new QueueBuffer();
        Producer producer = new Producer(q);
        Consumer consumer = new Consumer(q);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            //ignore
        }
    }

}