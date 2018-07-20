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
package com.mytest.sharing;

import org.apache.commons.lang.StringUtils;

import com.mytest.common.annotation.ThreadSafe;
import com.mytest.common.utils.LogUtil;

/**
 * @author liqingyu
 * @since 2018/07/07
 */
@ThreadSafe
public class SafeListener {
    // listener不逸出
    private final EventListener listener;

    private SafeListener() {
        listener = new EventListener() {
            public void onEvent(Event e) {
                doSomething(e);
            }
        };
    }

    // 通过工厂方法来防止this引用在构造过程中的逸出
    public static SafeListener newInstance(EventSource source) {
        SafeListener safe = new SafeListener();
        source.registerListener(safe.listener);
        return safe;
    }

    boolean doSomething(Event e) {
        if (StringUtils.isNotBlank(e.getEventCode()) && StringUtils.isNotBlank(e.getTopic())) {
            LogUtil.digest("Event:topic=%s,eventCode=%s", e.getTopic(), e.getEventCode());
            return true;
        }
        return false;
    }

    interface EventSource {
        void registerListener(EventListener e);
    }

    interface EventListener {
        void onEvent(Event e);
    }

    interface Event {
        String getTopic();

        String getEventCode();
    }
}
