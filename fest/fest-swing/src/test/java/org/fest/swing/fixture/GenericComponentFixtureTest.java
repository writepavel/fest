/*
 * Created on Jul 18, 2008
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
 * Copyright @2008-2009 the original author or authors.
 */
package org.fest.swing.fixture;

import java.awt.Component;

import org.testng.annotations.Test;

import org.fest.assertions.Assertions;
import org.fest.swing.driver.ComponentDriver;

import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.swing.test.builder.JButtons.button;

/**
 * Tests for <code>{@link GenericComponentFixture}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class GenericComponentFixtureTest extends CommonComponentFixtureTestCase<Component> {
  
  private GenericComponentFixture<Component> fixture;
  private Component target;
  private ComponentDriver driver;
  
  void onSetUp() {
    driver = createMock(ComponentDriver.class);
    target = button().createNew();
    fixture = new GenericComponentFixture<Component>(robot(), driver, target) {};
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void shouldThrowErrorIfDriverIsNull() {
    new GenericComponentFixture<Component>(robot(), null, target) {};
  }

  public void shouldCreateComponentDriver() {
    fixture = new GenericComponentFixture<Component>(robot(), target) {};
    Assertions.assertThat(fixture.driver()).isInstanceOf(ComponentDriver.class);
  }
  
  CommonComponentFixture fixture() { return fixture; }

  ComponentDriver driver() { return driver; }

  Component target() { return target; }
}
