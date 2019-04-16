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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.Test;

import com.mytest.common.utils.LogUtil;

/**
 * @author liqingyu
 * @since 2019/04/15
 */
public class FairAndUnfairTest {
    private static Lock fairLock   = new MyReentrantLock(true);
    private static Lock unfairLock = new MyReentrantLock(true);
    private static CountDownLatch start;

    /**
     * π´∆ΩÀ¯≤‚ ‘¿‡
     */
    @Test
    public void fairLockTest() throws InterruptedException {
        Thread.sleep(1000);
    }

    private void testLock(MyReentrantLock lock) {
    }

    private static class Job extends Thread {
        private MyReentrantLock lock;

        public Job(MyReentrantLock lock, String name) {
            this.lock = lock;
            setName(name);
        }

        public void run() {
            Thread thread = Thread.currentThread();
            LogUtil.digest("currentThread=%s,queueThreads=%s", thread, lock.getQueueThreads());
        }
    }

    private static class MyReentrantLock extends ReentrantLock {
        /**
         * @see super#ReentrantLock(boolean)
         * @param fair
         */
        public MyReentrantLock(boolean fair) {
            super(fair);
        }

        /**
         * @see super#getQueuedThreads()
         */
        public Collection<Thread> getQueueThreads() {
            return super.getQueuedThreads();
        }
    }

}
