/*
 * Created on Feb 8, 2007
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
package org.fest.swing.fixture;

import java.awt.Point;

import javax.swing.JButton;
import javax.swing.JPopupMenu;

import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.core.Robot;
import org.fest.swing.driver.AbstractButtonDriver;
import org.fest.swing.driver.ComponentDriver;

import static org.easymock.EasyMock.*;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests for <code>{@link JButtonFixture}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JButtonFixtureTest extends ComponentFixtureTestCase<JButton> {

  private AbstractButtonDriver driver;
  private JButton target;
  private JButtonFixture fixture;
  
  void onSetUp(Robot robot) {
    driver = createMock(AbstractButtonDriver.class);
    target = new JButton("A Button");
    fixture = new JButtonFixture(robot, target);
    fixture.updateDriver(driver);
  }

  @Test public void shouldReturnText() {
    assertThat(fixture.text()).isEqualTo(target.getText());
  }
  
  @Test public void shouldRequireText() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.requireText(target, "A Button");
        expectLastCall().once();
      }
      
      protected void codeToTest() {
        assertThatReturnsThis(fixture.requireText("A Button"));
      }
    }.run();
  }
  
  @Test public void shouldShowJPopupMenu() {
    final JPopupMenu popup = new JPopupMenu(); 
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.showPopupMenu(target)).andReturn(popup);
      }
      
      protected void codeToTest() {
        JPopupMenuFixture result = fixture.showPopupMenu();
        assertThat(result.target).isSameAs(popup);
      }
    }.run();
  }
  
  @Test public void shouldShowJPopupMenuAtPoint() {
    final Point p = new Point(8, 6);
    final JPopupMenu popup = new JPopupMenu(); 
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.showPopupMenu(target, p)).andReturn(popup);
      }
      
      protected void codeToTest() {
        JPopupMenuFixture result = fixture.showPopupMenuAt(p);
        assertThat(result.target).isSameAs(popup);
      }
    }.run();
  }

  ComponentDriver driver() { return driver; }
  JButton target() { return target; }
  ComponentFixture<JButton> fixture() { return fixture; }
}
