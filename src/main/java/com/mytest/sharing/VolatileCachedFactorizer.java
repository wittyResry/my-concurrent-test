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

import com.mytest.common.annotation.GuardedBy;
import com.mytest.common.annotation.ThreadSafe;

/**
 * @author liqingyu
 * @since 2018/07/22
 */
@ThreadSafe
@GuardedBy(value = "")
public class VolatileCachedFactorizer {
    private volatile OneValueCache cache;

    public void service(String servletRequest, String servletResponse) {
        BigInteger i = extractFromRequest(servletRequest);
        BigInteger[] factors = (cache != null ? cache.getFactors(i) : null);
        if (factors == null) {
            factors = factor(i);
            cache = new OneValueCache(i, factors);
        }
        encodeIntoResponse(servletResponse, factors);
    }

    void encodeIntoResponse(String resp, BigInteger[] factors) {
    }

    BigInteger extractFromRequest(String req) {
        return new BigInteger(req);
    }

    BigInteger[] factor(BigInteger i) {
        // Doesn't really factor
        return new BigInteger[] { i };
    }
}
