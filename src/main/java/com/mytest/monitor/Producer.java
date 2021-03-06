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
package com.mytest.monitor;

/**
 * @author liqingyu
 * @since 2018/06/07
 */
public class Producer implements Runnable {
    /** class */
    private ProducerConsumer p;

    /**
     * constructor
     *
     * @param p
     */
    public Producer(ProducerConsumer p) {
        this.p = p;
        new Thread(this, "Producer").start();
    }

    @Override
    public void run() {
        while (true) {
            p.produce();
        }
    }
}
