package com.mytest.monitor;

import org.junit.Test;

/**
 * @author liqingyu
 * @since 2018/06/07
 */
public class ProducerConsumerImplTest {
    @Test
    public void test() {
        ProducerConsumer producerConsumer = new ProducerConsumerImpl();

        Producer producer = new Producer(producerConsumer);
        Consumer consumer1 = new Consumer(producerConsumer, "Consumer1");
        Consumer consumer2 = new Consumer(producerConsumer, "Consumer2");
        Consumer consumer3 = new Consumer(producerConsumer, "Consumer3");
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            //ignore
        }
    }
}