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

package org.apache.james.mailbox.model.search;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

public class PrefixedWildcardTest {
    public static final String NAME = "toto";

    @Test
    void shouldMatchBeanContract() {
        EqualsVerifier.forClass(PrefixedWildcard.class)
            .verify();
    }

    @Test
    void constructorShouldThrowOnNullName() {
        assertThatThrownBy(() -> new PrefixedWildcard(null))
            .isInstanceOf(NullPointerException.class);
    }

    @Test
    void isWildShouldReturnTrue() {
        assertThat(new PrefixedWildcard(NAME).isWild())
            .isTrue();
    }

    @Test
    void getCombinedNameShouldReturnName() {
        assertThat(new PrefixedWildcard(NAME).getCombinedName())
            .isEqualTo(NAME + MailboxNameExpression.FREEWILDCARD);
    }

    @Test
    void isExpressionMatchShouldReturnTrueWhenName() {
        assertThat(new PrefixedWildcard(NAME).isExpressionMatch(NAME))
            .isTrue();
    }

    @Test
    void isExpressionMatchShouldReturnTrueWhenNameAndPostfix() {
        assertThat(new PrefixedWildcard(NAME).isExpressionMatch(NAME + "any"))
            .isTrue();
    }

    @Test
    void isExpressionMatchShouldReturnFalseWhenOtherValue() {
        assertThat(new PrefixedWildcard(NAME).isExpressionMatch("other"))
            .isFalse();
    }

    @Test
    void isExpressionMatchShouldThrowOnNullValue() {
        assertThatThrownBy(() -> new PrefixedWildcard(NAME).isExpressionMatch(null))
            .isInstanceOf(NullPointerException.class);
    }
}