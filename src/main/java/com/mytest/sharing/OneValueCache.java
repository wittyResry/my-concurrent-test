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

import java.math.BigInteger;
import java.util.Arrays;

import com.mytest.common.annotation.Immutable;

/**
 *
 * @author liqingyu
 * @since 2018/07/21
 */
@Immutable
public class OneValueCache {
    /** 数值 */
    private final BigInteger   lastNumber;
    /** 因子 */
    private final BigInteger[] lastFactors;

    /**
     * 构造函数，创建一个不可变类来防止更新cache
     *
     * @param i
     * @param factors
     */
    public OneValueCache(BigInteger i, BigInteger[] factors) {
        lastNumber = i;
        lastFactors = Arrays.copyOf(factors, factors.length);
    }

    /**
     * 获取缓存
     *
     * @param i
     * @return 如果不存在返回null
     */
    public BigInteger[] getFactors(BigInteger i) {
        if (lastNumber == null || !lastNumber.equals(i)) {
            return null;
        } else {
            return Arrays.copyOf(lastFactors, lastFactors.length);
        }
    }
}
