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
     * ���ܲ����쳣
     */
    @Test
    public void test01() {
        ExecutorService ex = Executors.newCachedThreadPool();
        try {
            ex.execute(new ExceptionThread());
            LogUtil.digest("ҵ��ִ�����");
        } catch (Exception e) {
            LogUtil.digest("���ܲ����쳣");
        } finally {
            ex.shutdown();
        }
    }

    /**
     * ��Ȼ�޷������쳣
     * ��Ȼ������UncaughtExceptionHandler�������̳߳��л��������µ�Thread���󣬶����Thread����û�������κ��쳣�����������仰˵���������̳߳�����߳�����
     * �κβ�������û���õ�
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
            task.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());//������������û��Ч������Ҫ��ThreadFactory������
            ex.execute(task);
            //ex.execute(new ExceptionThread());
            LogUtil.digest("ҵ��ִ�����");
        } catch (Exception e) {
            LogUtil.digest("���ܲ����쳣");
        } finally {
            ex.shutdown();
        }
    }

    /**
     * ͨ��ThreadFactory����
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
            LogUtil.digest("ҵ��ִ�����");
        } catch (Exception e) {
            LogUtil.digest("���ܲ����쳣");
        } finally {
            ex.shutdown();
        }
    }

    /**
     * Thread�ľ�̬����
     */
    @Test
    public void test04() {
        //ʹ��Thread�ľ�̬������������Thread�ľ�̬���������ַ�ʽӰ��ϴ󣬲�����
        UncaughtExceptionHandler handler = Thread.getDefaultUncaughtExceptionHandler();
        LogUtil.digest("ԭ�ȵ�UncaughtExceptionHandler��%d", handler);

        Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        ExecutorService ex = Executors.newCachedThreadPool();
        try {
            ex.execute(new ExceptionThread());
            LogUtil.digest("ҵ��ִ�����");
        } catch (Exception e) {
            LogUtil.digest("���ܲ����쳣");
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
            LogUtil.digest("��дUncaughtExceptionHandler�����ܲ����쳣:%s", e);
        }
    }

    public class ExceptionThread implements Runnable {
        @Override
        public void run() {
            Thread t = Thread.currentThread();
            LogUtil.digest("getUncaughtExceptionHandler:%s", t.getUncaughtExceptionHandler());
            throw new RuntimeException("����߳̾͸�����ôһ���£��׳�һ������ʱ�쳣");
        }
    }
}
