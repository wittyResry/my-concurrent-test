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

import com.mytest.common.annotation.NotThreadSafe;
import com.mytest.common.utils.LogUtil;

/**
 * @author liqingyu
 * @since 2018/07/07
 */
@NotThreadSafe
public class ThisEscape {
    /**
     * 构造函数发布EventListener
     *
     * @param source
     */
    public ThisEscape(EventSource source) {
        // 隐含发布了EventListener的引用
        // this引用会被新线程共享，在未完成构造之前，新的线程就可以看到它
        source.registerListener(new EventListener() {
            public void onEvent(Event e) {
                doSomething(e);
            }
        });
    }

    public boolean doSomething(Event e) {
        if (StringUtils.isNotBlank(e.getEventCode()) && StringUtils.isNotBlank(e.getTopic())) {
            LogUtil.digest("Event:topic=%s,eventCode=%s", e.getTopic(), e.getEventCode());
            return true;
        }
        return false;
    }


    public interface EventSource {
        void registerListener(EventListener e);
    }

    public interface EventListener {
        void onEvent(Event e);
    }

    public interface Event {
        String getTopic();

        String getEventCode();
    }
}
