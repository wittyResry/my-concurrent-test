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

import java.util.concurrent.locks.LockSupport;

import org.junit.Test;

import com.mytest.common.utils.LogUtil;

/**
 * LockSupport在AQS中用到，这里探究下其原理
 * 使用场景：
 * 1. unpark函数可以先于park调用。比如线程B调用unpark函数，给线程A发了一个“许可”，那么当线程A调用park时，它发现已经有“许可”了，那么它会马上再继续运行
 *
 * @author liqingyu
 * @since 2019/03/07
 */
public class LockSupportTest {
    /**
     * 由于获取不到锁，线程将一直运行，主线程一直处于阻塞状态
     * 因为许可默认是被占用的，调用park()时获取不到许可，所以进入阻塞状态
     */
    @Test
    public void test01() {
        LockSupport.park();
        LogUtil.digest("线程获取不到许可，阻塞");
    }

    /**
     * 先释放许可，再获取许可
     *LockSupport.park() 获取去壳不可重入
     */
    @Test
    public void test02() {
        Thread thread = Thread.currentThread();
        LockSupport.unpark(thread);
        LockSupport.park();
        LockSupport.unpark(thread);//去掉这行则将一直等待下去，说明不可重入
        LockSupport.park();
    }

    /**
     * 线程如果因为调用park而阻塞的话
     * 能够响应中断请求(中断状态被设置成true)，但是不会抛出InterruptedException。
     */
    @Test
    public void test03() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                LogUtil.digest("开始执行thread");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                LogUtil.digest("开始执行park，线程进入阻塞");
                LockSupport.park();
                LogUtil.digest("响应中断,isInterrupted=" + Thread.currentThread().isInterrupted());
            }
        });
        thread.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //中断
        thread.interrupt();

    }
}
