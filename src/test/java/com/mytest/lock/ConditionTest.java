/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mytest.lock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.Test;

import com.mytest.common.utils.LogUtil;

/**
 * 1. 
 *
 * @author liqingyu
 * @since 2019/04/15
 */
public class ConditionTest {
    private static final String         SCENE     = "[验证Condition同步]";
    private static final String         COMMA     = ",";
    private              Lock           lock      = new ReentrantLock();
    private              Condition      condition = lock.newCondition();
    private              CountDownLatch latch     = new CountDownLatch(1);

    /**
     * 测试await功能
     *
     * @throws InterruptedException
     */
    @Test
    public void test() throws InterruptedException {
        final ConditionTest test = new ConditionTest();
        Thread threadPre = new Thread(new Runnable() {
            @Override
            public void run() {
                LogUtil.info(SCENE, COMMA, Thread.currentThread(), COMMA, "加锁");
                test.getLock().lock();
                try {
                    try {
                        LogUtil.info(SCENE, COMMA, Thread.currentThread(), COMMA, "等待另一个线程处理完毕");
                        test.getCondition().await();
                    } catch (InterruptedException e) {
                        LogUtil.info(SCENE, COMMA, Thread.currentThread(), COMMA, "响应中断");
                    }
                } finally {
                    LogUtil.info(SCENE, COMMA, Thread.currentThread(), COMMA, "解锁");
                    test.getLock().unlock();
                    LogUtil.info(SCENE, COMMA, Thread.currentThread(), COMMA, "执行结束");
                    latch.countDown();
                }
            }
        });
        threadPre.setName("前置处理线程");
        Thread threadPost = new Thread(new Runnable() {
            @Override
            public void run() {
                LogUtil.info(SCENE, COMMA, Thread.currentThread(), COMMA, "加锁");
                test.getLock().lock();
                try {
                    LogUtil.info(SCENE, COMMA, Thread.currentThread(), COMMA, "通知另一个线程继续处理");
                    test.getCondition().signal();
                } finally {
                    LogUtil.info(SCENE, COMMA, Thread.currentThread(), COMMA, "解锁");
                    test.getLock().unlock();
                }
            }
        });
        threadPost.setName("后置处理线程");
        threadPost.start();
        Thread.sleep(100);
        threadPre.start();

        //防止先执行threadPost，然后再执行threadPre，这样情况下就没有线程调用过wakeup一个线程的方法，故这里再次调用一次wakeup
        Thread.sleep(100);
        wakeup(test);

        threadPost.join();
        threadPre.join();

    }

    /**
     * Wakes up one waiting thread for threadPre because threadPre is awaiting
     */
    private void wakeup(ConditionTest test) {
        test.getLock().lock();
        test.getCondition().signal();
        test.getLock().unlock();
    }

    /**
     * Getter method for property <tt>lock</tt>.
     *
     * @return property value of lock
     */
    public Lock getLock() {
        LogUtil.info(SCENE, COMMA, Thread.currentThread(), COMMA, "获取锁当前线程为");
        return lock;
    }

    /**
     * Getter method for property <tt>condition</tt>.
     *
     * @return property value of condition
     */
    public Condition getCondition() {
        return condition;
    }
}
