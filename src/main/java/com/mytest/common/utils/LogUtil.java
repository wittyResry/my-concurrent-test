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
package com.mytest.common.utils;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志工具类
 *
 * @author liqingyu
 * @since 2018/05/25
 */
public class LogUtil {
    /** 日志 */
    private final static Logger LOGGER = LoggerFactory.getLogger("TEST");

    /** 调用栈下标 */
    public static final int     INDEX  = 2;

    /**
     * 日志
     * 
     * @param format
     * @param args
     */
    public static void digest(String format, Object... args) {
        if (format == null) {
            return;
        }
        LOGGER.warn(String.format(format, args));
    }

    /**
     * 打印结果
     *
     * @param args
     */
    public static void info(Object... args) {
        if (args == null) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (Object o : args) {
            sb.append(o);
        }
        LOGGER.info(sb.toString());
    }

    /**
     * 日志（打印线程）
     *
     * @param format
     * @param args
     */
    public static void digestWithThread(String format, Object... args) {
        if (format == null) {
            return;
        }
        StringBuilder sb = new StringBuilder(format.length() * 2);
        sb.append(String.format(format, args));
        sb.append(String.format("[threadName:%s,threadId:%s]", Thread.currentThread().getName(),
            Thread.currentThread().getId()));
        LOGGER.warn(sb.toString());
    }

    /**
     * 打印调用日志
     */
    public static void digestLog() {
        StackTraceElement[] ste = Thread.currentThread().getStackTrace();
        if (ste.length < 3) {
            LOGGER.warn("skip digest method name");
            return;
        }
        LOGGER.warn(String.format("%s.%s,processing,date:%s",
            Thread.currentThread().getStackTrace()[INDEX].getClassName(),
            Thread.currentThread().getStackTrace()[INDEX].getMethodName(),
            DateUtils.getNewFormatDateString(new Date(System.currentTimeMillis()))));
        sleepOneSecond();
    }

    /**
     * 延迟1S
     */
    private static void sleepOneSecond() {
        try {
            Thread.sleep(1000);
        } catch (Exception ignore) {
        }
    }
}
