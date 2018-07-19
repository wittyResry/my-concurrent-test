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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author liqingyu
 * @since 2018/07/19
 */
public class ConnectionDispenser {
    private static final String DB_URL = "jdbc:mysql://localhost/mydatabase";
    private ThreadLocal<Connection> connectionHolder
            = new ThreadLocal<Connection>() {
        /**
         * 初始化连接器
         *
         * @return
         */
        @Override
        protected Connection initialValue() {
            try {
                return DriverManager.getConnection(DB_URL);
            } catch (SQLException e) {
                throw new RuntimeException("Unable to acquire Connection", e);
            }
        }
    };

    /**
     * 获取db连接
     *
     * @return
     */
    public Connection getConnection() {
        return connectionHolder.get();
    }

}
