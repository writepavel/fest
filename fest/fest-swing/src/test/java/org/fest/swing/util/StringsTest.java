/*
 * Created on Jan 14, 2008
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 * 
 * Copyright @2008 the original author or authors.
 */
package org.fest.swing.util;

import static org.fest.assertions.Assertions.assertThat;

import org.testng.annotations.Test;

/**
 * Tests for <code>{@link Strings}</code>.
 *
 * @author Alex Ruiz
 */
public class StringsTest {

  @Test public void shouldReturnFalseIfDefaultStringIsNull() {
    assertThat(Strings.isDefaultToString(null)).isFalse();
  }
  
  @Test public void shouldReturnFalseIfDefaultStringIsEmpty() {
    assertThat(Strings.isDefaultToString("")).isFalse();
  }

  @Test public void shouldReturnFalseIfAtIsNotFollowedByHash() {
    assertThat(Strings.isDefaultToString("abc@xyz"));
  }
  
  @Test public void shouldReturnFalseIfThereIsNotAt() {
    assertThat(Strings.isDefaultToString("abc"));
  }

  @Test public void shouldReturnTrueIfDefaultToString() {
    class Person {}
    assertThat(Strings.isDefaultToString(new Person().toString())).isTrue();
  }
}