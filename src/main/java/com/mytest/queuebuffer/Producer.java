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
package com.mytest.queuebuffer;

import com.mytest.common.utils.LogUtil;

/**
 * @author liqingyu
 * @since 2018/06/06
 */
public class Producer implements Runnable {
    /** queueBuffer */
    private QueueBuffer q;

    public Producer(QueueBuffer q) {
        this.q = q;
        new Thread(this, "Producer").start();
    }

    @Override
    public void run() {
        int i = 1;
        while (true) {
            //LogUtil.digest("beforePut:%s", i);
            q.put(i);
            ++i;
        }
    }
}