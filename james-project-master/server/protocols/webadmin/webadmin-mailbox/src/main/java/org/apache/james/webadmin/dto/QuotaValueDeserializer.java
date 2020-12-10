/****************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one   *
 * or more contributor license agreements.  See the NOTICE file *
 * distributed with this work for additional information        *
 * regarding copyright ownership.  The ASF licenses this file   *
 * to you under the Apache License, Version 2.0 (the            *
 * "License"); you may not use this file except in compliance   *
 * with the License.  You may obtain a copy of the License at   *
 *                                                              *
 *   http://www.apache.org/licenses/LICENSE-2.0                 *
 *                                                              *
 * Unless required by applicable law or agreed to in writing,   *
 * software distributed under the License is distributed on an  *
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY       *
 * KIND, either express or implied.  See the License for the    *
 * specific language governing permissions and limitations      *
 * under the License.                                           *
 ****************************************************************/
package org.apache.james.webadmin.dto;

import java.io.IOException;
import java.util.function.Function;

import org.apache.james.core.quota.QuotaLimitValue;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class QuotaValueDeserializer<T extends QuotaLimitValue<T>> extends JsonDeserializer<T> {

    private final T unlimited;
    private final Function<Long, T> quotaFactory;

    public QuotaValueDeserializer(T unlimited, Function<Long, T> quotaFactory) {
        this.unlimited = unlimited;
        this.quotaFactory = quotaFactory;
    }

    @Override
    public T deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException {
        return deserialize(parser.getValueAsLong());
    }

    private T deserialize(Long value) {
        if (value == -1) {
            return unlimited;
        }
        return quotaFactory.apply(value);
    }

}
