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
 * LockSupport��AQS���õ�������̽������ԭ��
 * ʹ�ó�����
 * 1. unpark������������park���á������߳�B����unpark���������߳�A����һ������ɡ�����ô���߳�A����parkʱ���������Ѿ��С���ɡ��ˣ���ô���������ټ�������
 *
 * @author liqingyu
 * @since 2019/03/07
 */
public class LockSupportTest {
    /**
     * ���ڻ�ȡ���������߳̽�һֱ���У����߳�һֱ��������״̬
     * ��Ϊ���Ĭ���Ǳ�ռ�õģ�����park()ʱ��ȡ������ɣ����Խ�������״̬
     */
    @Test
    public void test01() {
        LockSupport.park();
        LogUtil.digest("�̻߳�ȡ������ɣ�����");
    }

    /**
     * ���ͷ���ɣ��ٻ�ȡ���
     *LockSupport.park() ��ȡȥ�ǲ�������
     */
    @Test
    public void test02() {
        Thread thread = Thread.currentThread();
        LockSupport.unpark(thread);
        LockSupport.park();
        LockSupport.unpark(thread);//ȥ��������һֱ�ȴ���ȥ��˵����������
        LockSupport.park();
    }

    /**
     * �߳������Ϊ����park�������Ļ�
     * �ܹ���Ӧ�ж�����(�ж�״̬�����ó�true)�����ǲ����׳�InterruptedException��
     */
    @Test
    public void test03() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                LogUtil.digest("��ʼִ��thread");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                LogUtil.digest("��ʼִ��park���߳̽�������");
                LockSupport.park();
                LogUtil.digest("��Ӧ�ж�,isInterrupted=" + Thread.currentThread().isInterrupted());
            }
        });
        thread.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //�ж�
        thread.interrupt();

    }
}
