/*
 * Created on Aug 10, 2008
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
package org.fest.swing.driver;

import javax.swing.JToolBar;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;

import static javax.swing.SwingConstants.*;
import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests for <code>{@link JToolBarOrientationQuery}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class JToolBarOrientationQueryTest {

  private JToolBar toolBar;

  @BeforeMethod public void setUp() {
    toolBar = createMock(JToolBar.class);
  }
  
  public void shouldReturnIsHorizontalIfJToolBarOrientationIsHorizontal() {
    new EasyMockTemplate(toolBar) {
      protected void expectations() {
        expect(toolBar.getOrientation()).andReturn(HORIZONTAL);
      }

      protected void codeToTest() {
        assertThat(JToolBarOrientationQuery.isHorizontal(toolBar)).isTrue();
      }
    }.run();
  }

  public void shouldReturnFalseIfToolBarOrientationIsVerticalAndExpectingHorizontal() {
    new EasyMockTemplate(toolBar) {
      protected void expectations() {
        expect(toolBar.getOrientation()).andReturn(VERTICAL);
      }

      protected void codeToTest() {
        assertThat(JToolBarOrientationQuery.isHorizontal(toolBar)).isFalse();
      }
    }.run();
  }

  public void shouldReturnIsVerticalIfJToolBarOrientationIsVertical() {
    new EasyMockTemplate(toolBar) {
      protected void expectations() {
        expect(toolBar.getOrientation()).andReturn(VERTICAL);
      }

      protected void codeToTest() {
        assertThat(JToolBarOrientationQuery.isVertical(toolBar)).isTrue();
      }
    }.run();
  }

  public void shouldReturnFalseIfToolBarOrientationIsHorizontalAndExpectingVerical() {
    new EasyMockTemplate(toolBar) {
      protected void expectations() {
        expect(toolBar.getOrientation()).andReturn(HORIZONTAL);
      }

      protected void codeToTest() {
        assertThat(JToolBarOrientationQuery.isVertical(toolBar)).isFalse();
      }
    }.run();
  }
  
  @Test(dataProvider = "orientations")
  public void shouldReturnOrientationOfJToolBar(final int orientation) {
    new EasyMockTemplate(toolBar) {
      protected void expectations() {
        expect(toolBar.getOrientation()).andReturn(orientation);
      }

      protected void codeToTest() {
        assertThat(JToolBarOrientationQuery.orientationOf(toolBar)).isEqualTo(orientation);
      }
    }.run();
  }
  
  @DataProvider(name = "orientations") public Object[][] orientations() {
    return new Object[][] { { HORIZONTAL }, { VERTICAL } };
  }
}