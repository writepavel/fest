/*
 * Created on Mar 1, 2007
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
 * Copyright @2007 the original author or authors.
 */
package org.fest.assertions;

import org.testng.annotations.Test;

import static org.fest.util.Arrays.array;

/**
 * Tests for <code>{@link ObjectArrayAssert}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class ObjectArrayAssertTest {

  @Test public void shouldPassIfGivenObjectIsInArray() {
    new ObjectArrayAssert("Luke", "Leia").contains("Luke");
  }

  @Test(dependsOnMethods = "shouldPassIfGivenObjectIsInArray") 
  public void shouldPassIfGivenObjectsAreInArray() {
    new ObjectArrayAssert("Luke", "Leia", "Anakin").contains("Luke", "Leia");
  }
  
  @Test(dependsOnMethods = "shouldPassIfGivenObjectIsInArray", expectedExceptions = AssertionError.class) 
  public void shouldFailIfGivenObjectIsNotInArray() {
    new ObjectArrayAssert(new Object[0]).contains("Luke");
  }

  @Test public void shouldPassIfGivenObjectIsNotInArray() {
    new ObjectArrayAssert("Luke", "Leia").excludes("Anakin");
  }

  @Test(dependsOnMethods = "shouldPassIfGivenObjectIsNotInArray") 
  public void shouldPassIfGivenObjectsAreNotInArray() {
    new ObjectArrayAssert("Luke", "Leia", "Anakin").excludes("Han", "Yoda");
  }
  
  @Test(dependsOnMethods = "shouldPassIfGivenObjectIsNotInArray", expectedExceptions = AssertionError.class) 
  public void shouldFailIfGivenObjectIsInArray() {
    new ObjectArrayAssert("Luke").excludes("Luke");
  }

  @Test public void shouldPassIfArrayIsNull() {
    new ObjectArrayAssert((Object[])null).isNull();
  }
  
  @Test(dependsOnMethods = "shouldPassIfArrayIsNull", expectedExceptions = AssertionError.class) 
  public void shouldFailIfArrayIsNotNull() {
    new ObjectArrayAssert(new Object[0]).isNull();
  }

  @Test public void shouldPassIfArrayIsNotNull() {
    new ObjectArrayAssert(new Object[0]).isNotNull();
  }
  
  @Test(dependsOnMethods = "shouldPassIfArrayIsNotNull", expectedExceptions = AssertionError.class) 
  public void shouldFailIfArrayIsNull() {
    new ObjectArrayAssert((Object[])null).isNotNull();
  }

  @Test public void shouldPassIfArrayIsEmpty() {
    new ObjectArrayAssert(new Object[0]).isEmpty();
  }
  
  @Test(dependsOnMethods = "shouldPassIfArrayIsEmpty" , expectedExceptions = AssertionError.class) 
  public void shouldFailIfArrayIsNotEmpty() {
    new ObjectArrayAssert("Luke", "Leia").isEmpty();
  }

  @Test public void shouldPassIfArrayIsNotEmpty() {
    new ObjectArrayAssert("Luke", "Leia").isNotEmpty();
  }
  
  @Test(dependsOnMethods = "shouldPassIfArrayIsNotEmpty", expectedExceptions = AssertionError.class) 
  public void shouldFailIfArrayIsEmpty() {
    new ObjectArrayAssert(new Object[0]).isNotEmpty();
  }

  @Test public void shouldPassIfEqualArrays() {
    new ObjectArrayAssert("Luke", "Leia").isEqualTo(array("Luke", "Leia"));
  }
  
  @Test(dependsOnMethods = "shouldPassIfEqualArrays", expectedExceptions = AssertionError.class) 
  public void shouldFailIfNotEqualArrays() {
    new ObjectArrayAssert("Luke", "Leia").isEqualTo(array("Anakin"));
  }

  @Test public void shouldPassIfNotEqualArrays() {
    new ObjectArrayAssert("Luke", "Leia").isNotEqualTo(array("Yoda"));
  }
  
  @Test(dependsOnMethods = "shouldPassIfNotEqualArrays", expectedExceptions = AssertionError.class) 
  public void shouldFailIfEqualArrays() {
    new ObjectArrayAssert("Luke", "Leia").isNotEqualTo(array("Luke", "Leia"));
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfArrayIsEmptyWhenLookingForSpecificElements() {
    new ObjectArrayAssert(new Object[0]).containsOnly("Yoda");
  }

  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfArrayHasExtraElements() {
    new ObjectArrayAssert("Luke", "Leia", "Anakin").containsOnly("Luke", "Leia");
  }
  
  @Test(expectedExceptions = AssertionError.class)
  public void shouldFailIfArrayIsMissingElements() {
    new ObjectArrayAssert("Luke", "Leia").containsOnly("Luke", "Leia", "Anakin");
  }

  @Test public void shouldPassIfArrayHasOnlySpecifiedElements() {
    new ObjectArrayAssert("Luke", "Leia").containsOnly("Luke", "Leia");    
  }
}
