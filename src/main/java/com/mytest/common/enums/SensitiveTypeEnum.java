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
package com.mytest.common.enums;

/**
 * @author liqingyu
 * @since 2018/06/01
 */
public enum SensitiveTypeEnum {

    /** 手机号类型 */
    MOBILE("MOBILE", "手机号类型"),

    /** email类型 */
    EMAIL("EMAIL", "email类型"),

    /** 非敏感信息 */
    NONE("NONE", "全部不隐藏");

    private final String code;

    private final String desc;

    SensitiveTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
