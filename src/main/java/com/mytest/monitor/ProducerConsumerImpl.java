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

import com.mytest.common.utils.LogUtil;

/**
 * @author liqingyu
 * @since 2018/06/07
 */
public class ProducerConsumerImpl implements ProducerConsumer {
    private static final int MAX_PRODUCT = 10;
    private static final int MIN_PRODUCT = 0;
    private int              product;

    @Override
    public synchronized void produce() {
        if (this.product >= MAX_PRODUCT) {
            try {
                wait();
                LogUtil.digestWithThread("产品已满,请稍候再生产");
            } catch (InterruptedException e) {
                //ignore
            }
            return;
        }
        this.product++;
        LogUtil.digestWithThread("生产者生产第%s个产品.", this.product);
        notifyAll();

    }

    @Override
    public synchronized void consume() {
        if(this.product <= MIN_PRODUCT) {
            try {
                wait();
                LogUtil.digestWithThread("缺货,稍候再取");
            } catch (InterruptedException e) {
                //ignore
            }
            return;
        }
        LogUtil.digestWithThread("生产者取走了第%s个产品.", this.product);
        this.product--;
        notifyAll();
    }
}
