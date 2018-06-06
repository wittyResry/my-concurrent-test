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

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author liqingyu
 * @since 2018/06/06
 */
public class QueueBuffer {
    // The runnables queue
    private final BlockingDeque<Integer> deque = new LinkedBlockingDeque<Integer>();

    // todo: Main lock
    //private final Object                 LOCK  = new StringBuilder("InvokeLock");

    public Integer get() {
        try {
            Integer i = deque.takeFirst();
            return i;
        } catch (InterruptedException ex) {
            // OK, let's stop this thread
            deque.clear();
            return null;
        }
    }

    public void put(Integer i) {
        if (i != null) {
            deque.addLast(i);
        }
    }

    /**
     * Getter method for property <tt>deque</tt>.
     *
     * @return property value of deque
     */
    public BlockingDeque<Integer> getDeque() {
        return deque;
    }
}
