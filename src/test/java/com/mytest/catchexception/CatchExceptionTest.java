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
package com.mytest.catchexception;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.junit.Test;

import com.mytest.common.utils.LogUtil;

/**
 * @author liqingyu
 * @since 2019/03/05
 */
public class CatchExceptionTest {

    /**
     * 不能捕获到异常
     */
    @Test
    public void test01() {
        ExecutorService ex = Executors.newCachedThreadPool();
        try {
            ex.execute(new ExceptionThread());
            LogUtil.digest("业务执行完成");
        } catch (Exception e) {
            LogUtil.digest("不能捕获到异常");
        } finally {
            ex.shutdown();
        }
    }

    /**
     * 仍然无法捕获到异常
     * 虽然设置了UncaughtExceptionHandler，但是线程池中会重设置新的Thread对象，而这个Thread对象没有设置任何异常处理器，换句话说，我们在线程池外对线程做的
     * 任何操作都是没有用的
     */
    @Test public void test02() {
        ExecutorService ex = Executors.newCachedThreadPool(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t =  new Thread(r);
                //t.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
                return t;
            }
        });
        try {
            Thread task = new Thread(new ExceptionThread());
            task.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());//这里增加这行没有效果，需要用ThreadFactory来设置
            ex.execute(task);
            //ex.execute(new ExceptionThread());
            LogUtil.digest("业务执行完成");
        } catch (Exception e) {
            LogUtil.digest("不能捕获到异常");
        } finally {
            ex.shutdown();
        }
    }

    /**
     * 通过ThreadFactory传入
     */
    @Test
    public void test03() {
        ExecutorService ex = Executors.newCachedThreadPool(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t =  new Thread(r);
                t.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
                return t;
            }
        });
        try {
            ex.execute(new ExceptionThread());
            LogUtil.digest("业务执行完成");
        } catch (Exception e) {
            LogUtil.digest("不能捕获到异常");
        } finally {
            ex.shutdown();
        }
    }

    /**
     * Thread的静态方法
     */
    @Test
    public void test04() {
        //使用Thread的静态方法设置所有Thread的静态方法，这种方式影响较大，不优雅
        UncaughtExceptionHandler handler = Thread.getDefaultUncaughtExceptionHandler();
        LogUtil.digest("原先的UncaughtExceptionHandler：%d", handler);

        Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        ExecutorService ex = Executors.newCachedThreadPool();
        try {
            ex.execute(new ExceptionThread());
            LogUtil.digest("业务执行完成");
        } catch (Exception e) {
            LogUtil.digest("不能捕获到异常");
        } finally {
            ex.shutdown();
        }
    }

    @Test
    public void test05() {
        //Callable callable = Executors.callable(new ExceptionThread(), )
    }

    public class MyUncaughtExceptionHandler implements UncaughtExceptionHandler {

        @Override
        public void uncaughtException(Thread t, Throwable e) {
            LogUtil.digest("重写UncaughtExceptionHandler，才能捕获到异常:%s", e);
        }
    }

    public class ExceptionThread implements Runnable {
        @Override
        public void run() {
            Thread t = Thread.currentThread();
            LogUtil.digest("getUncaughtExceptionHandler:%s", t.getUncaughtExceptionHandler());
            throw new RuntimeException("这个线程就干了这么一件事，抛出一个运行时异常");
        }
    }
}
