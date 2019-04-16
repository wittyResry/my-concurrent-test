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
    private static final String         SCENE     = "[��֤Conditionͬ��]";
    private static final String         COMMA     = ",";
    private              Lock           lock      = new ReentrantLock();
    private              Condition      condition = lock.newCondition();
    private              CountDownLatch latch     = new CountDownLatch(1);

    /**
     * ����await����
     *
     * @throws InterruptedException
     */
    @Test
    public void test() throws InterruptedException {
        final ConditionTest test = new ConditionTest();
        Thread threadPre = new Thread(new Runnable() {
            @Override
            public void run() {
                LogUtil.info(SCENE, COMMA, Thread.currentThread(), COMMA, "����");
                test.getLock().lock();
                try {
                    try {
                        LogUtil.info(SCENE, COMMA, Thread.currentThread(), COMMA, "�ȴ���һ���̴߳������");
                        test.getCondition().await();
                    } catch (InterruptedException e) {
                        LogUtil.info(SCENE, COMMA, Thread.currentThread(), COMMA, "��Ӧ�ж�");
                    }
                } finally {
                    LogUtil.info(SCENE, COMMA, Thread.currentThread(), COMMA, "����");
                    test.getLock().unlock();
                    LogUtil.info(SCENE, COMMA, Thread.currentThread(), COMMA, "ִ�н���");
                    latch.countDown();
                }
            }
        });
        threadPre.setName("ǰ�ô����߳�");
        Thread threadPost = new Thread(new Runnable() {
            @Override
            public void run() {
                LogUtil.info(SCENE, COMMA, Thread.currentThread(), COMMA, "����");
                test.getLock().lock();
                try {
                    LogUtil.info(SCENE, COMMA, Thread.currentThread(), COMMA, "֪ͨ��һ���̼߳�������");
                    test.getCondition().signal();
                } finally {
                    LogUtil.info(SCENE, COMMA, Thread.currentThread(), COMMA, "����");
                    test.getLock().unlock();
                }
            }
        });
        threadPost.setName("���ô����߳�");
        threadPost.start();
        Thread.sleep(100);
        threadPre.start();

        //��ֹ��ִ��threadPost��Ȼ����ִ��threadPre����������¾�û���̵߳��ù�wakeupһ���̵߳ķ������������ٴε���һ��wakeup
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
        LogUtil.info(SCENE, COMMA, Thread.currentThread(), COMMA, "��ȡ����ǰ�߳�Ϊ");
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
