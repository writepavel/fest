/*
 * Created on Feb 24, 2008
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

import java.awt.Dimension;

import javax.swing.JScrollBar;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.core.EventMode;
import org.fest.swing.core.EventModeProvider;
import org.fest.swing.core.Robot;
import org.fest.swing.edt.CheckThreadViolationRepaintManager;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.task.ComponentSetEnabledTask;
import org.fest.swing.testing.TestWindow;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.RobotFixture.robotWithNewAwtHierarchy;
import static org.fest.swing.driver.JScrollBarSetValueTask.setValue;
import static org.fest.swing.driver.JScrollBarValueQuery.valueOf;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.testing.CommonAssertions.*;
import static org.fest.swing.testing.TestGroups.GUI;
import static org.fest.util.Strings.concat;

/**
 * Tests for <code>{@link JScrollBarDriver}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class JScrollBarDriverTest {

  private static final int MINIMUM = 10;
  private static final int MAXIMUM = 80;
  private static final int EXTENT = 10;

  private Robot robot;
  private JScrollBar scrollBar;
  private JScrollBarDriver driver;

  @BeforeClass public void setUpOnce() {
    CheckThreadViolationRepaintManager.install();
  }

  @BeforeMethod public void setUp() {
    robot = robotWithNewAwtHierarchy();
    driver = new JScrollBarDriver(robot);
    MyWindow window = MyWindow.createNew();
    scrollBar = window.scrollBar;
    robot.showWindow(window);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  public void shouldPassIfValueIsEqualToExpected() {
    setValue(scrollBar, 30);
    robot.waitForIdle();
    driver.requireValue(scrollBar, 30);
  }

  public void shouldFailIfValueIsNotEqualToExpected() {
    setValue(scrollBar, 30);
    robot.waitForIdle();
    try {
      driver.requireValue(scrollBar, 20);
      failWhenExpectingException();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'value'")
                             .contains("expected:<20> but was:<30>");
    }
  }

  @Test(groups = GUI, dataProvider = "zeroAndNegative", dataProviderClass = ZeroAndNegativeProvider.class)
  public void shouldThrowErrorIfTimesToScrollUnitUpIsZeroOrNegative(int times) {
    try {
      driver.scrollUnitUp(scrollBar, times);
      failWhenExpectingException();
    } catch (IllegalArgumentException expected) {
      String message = concat(
          "The number of times to scroll up one unit should be greater than zero, but was <", times, ">");
      assertThat(expected).message().isEqualTo(message);
    }
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldScrollUnitUpTheGivenNumberOfTimes(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    driver.scrollUnitUp(scrollBar, 6);
    assertThatScrollBarValueIsEqualTo(36);
  }

  public void shouldThrowErrorWhenScrollingDisabledJScrollBarUnitUpTheGivenNumberOfTimes() {
    disableScrollBar();
    try {
      driver.scrollUnitUp(scrollBar, 6);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldScrollUnitUp(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    driver.scrollUnitUp(scrollBar);
    assertThatScrollBarValueIsEqualTo(31);
  }

  public void shouldThrowErrorWhenScrollingDisabledJScrollBarUnitUp() {
    disableScrollBar();
    try {
      driver.scrollUnitUp(scrollBar);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }

  @Test(dataProvider = "zeroAndNegative", dataProviderClass = ZeroAndNegativeProvider.class)
  public void shouldThrowErrorIfTimesToScrollUnitDownIsZeroOrNegative(int times) {
    try {
      driver.scrollUnitDown(scrollBar, times);
      failWhenExpectingException();
    } catch (IllegalArgumentException expected) {
      String message = concat(
          "The number of times to scroll down one unit should be greater than zero, but was <", times, ">");
      assertThat(expected).message().isEqualTo(message);
    }
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldScrollUnitDownTheGivenNumberOfTimes(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    driver.scrollUnitDown(scrollBar, 8);
    assertThatScrollBarValueIsEqualTo(22);
  }

  public void shouldThrowErrorWhenScrollingDisabledJScrollBarUnitDownTheGivenNumberOfTimes() {
    disableScrollBar();
    try {
      driver.scrollUnitDown(scrollBar, 8);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldScrollUnitDown(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    driver.scrollUnitDown(scrollBar);
    assertThatScrollBarValueIsEqualTo(29);
  }

  public void shouldThrowErrorWhenScrollingDisabledJScrollBarUnitDown() {
    disableScrollBar();
    try {
      driver.scrollUnitDown(scrollBar);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }

  @Test(groups = GUI, dataProvider = "zeroAndNegative", dataProviderClass = ZeroAndNegativeProvider.class)
  public void shouldThrowErrorIfTimesToScrollBlockUpIsZeroOrNegative(int times) {
    try {
      driver.scrollBlockUp(scrollBar, times);
      failWhenExpectingException();
    } catch (IllegalArgumentException expected) {
      String message = concat(
          "The number of times to scroll up one block should be greater than zero, but was <", times, ">");
      assertThat(expected).message().isEqualTo(message);
    }
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldScrollBlockUpTheGivenNumberOfTimes(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    driver.scrollBlockUp(scrollBar, 2);
    assertThatScrollBarValueIsEqualTo(50);
  }

  public void shouldThrowErrorWhenScrollingDisabledJScrollBarBlockUpTheGivenNumberOfTimes() {
    disableScrollBar();
    try {
      driver.scrollBlockUp(scrollBar, 2);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldScrollBlockUp(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    driver.scrollBlockUp(scrollBar);
    assertThatScrollBarValueIsEqualTo(40);
  }

  public void shouldThrowErrorWhenScrollingDisabledJScrollBarBlockUp() {
    disableScrollBar();
    try {
      driver.scrollBlockUp(scrollBar);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }

  @Test(groups = GUI, dataProvider = "zeroAndNegative", dataProviderClass = ZeroAndNegativeProvider.class)
  public void shouldThrowErrorIfTimesToScrollBlockDownIsZeroOrNegative(int times) {
    try {
      driver.scrollBlockDown(scrollBar, times);
      failWhenExpectingException();
    } catch (IllegalArgumentException expected) {
      String message = concat(
          "The number of times to scroll down one block should be greater than zero, but was <", times, ">");
      assertThat(expected).message().isEqualTo(message);
    }
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldScrollBlockUpDownTheGivenNumberOfTimes(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    driver.scrollBlockDown(scrollBar, 2);
    assertThatScrollBarValueIsEqualTo(10);
  }

  public void shouldThrowErrorWhenScrollingDisabledJScrollBarBlockUpDown() {
    disableScrollBar();
    try {
      driver.scrollBlockDown(scrollBar, 2);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldScrollBlockDown(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    driver.scrollBlockDown(scrollBar);
    assertThatScrollBarValueIsEqualTo(20);
  }

  public void shouldThrowErrorWhenScrollingDisabledJScrollBarBlockDown() {
    disableScrollBar();
    try {
      driver.scrollBlockDown(scrollBar);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldScrollToGivenPosition(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    driver.scrollTo(scrollBar, 68);
    assertThatScrollBarValueIsEqualTo(68);
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldScrollToMaximum(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    driver.scrollToMaximum(scrollBar);
    assertThatScrollBarValueIsEqualTo(MAXIMUM - EXTENT); // JScrollBar value cannot go to maximum
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldScrollToMinimum(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    driver.scrollToMinimum(scrollBar);
    assertThatScrollBarValueIsEqualTo(MINIMUM);
  }

  public void shouldThrowErrorWhenScrollingDisabledJScrollBarToGivenPosition() {
    disableScrollBar();
    try {
      driver.scrollTo(scrollBar, 68);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertActionFailureDueToDisabledComponent(e);
    }
  }

  private void assertThatScrollBarValueIsEqualTo(int expected) {
    assertThat(valueOf(scrollBar)).isEqualTo(expected);
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldThrowErrorIfPositionIsLessThanMinimum(EventMode eventMode) {
    robot.settings().eventMode(eventMode);
    try {
      driver.scrollTo(scrollBar, 0);
      failWhenExpectingException();
    } catch (IllegalArgumentException expected) {
      assertThat(expected).message().isEqualTo("Position <0> is not within the JScrollBar bounds of <10> and <80>");
    }
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldThrowErrorIfPositionIsGreaterThanMaximum(EventMode eventMode) {
    try {
      robot.settings().eventMode(eventMode);
      driver.scrollTo(scrollBar, 90);
      failWhenExpectingException();
    } catch (IllegalArgumentException expected) {
      assertThat(expected).message().isEqualTo("Position <90> is not within the JScrollBar bounds of <10> and <80>");
    }
  }

  @RunsInEDT
  private void disableScrollBar() {
    ComponentSetEnabledTask.disable(scrollBar);
    robot.waitForIdle();
  }

  private static class MyWindow extends TestWindow {
    private static final long serialVersionUID = 1L;

    final JScrollBar scrollBar = new JScrollBar();

    static MyWindow createNew() {
      return execute(new GuiQuery<MyWindow>() {
        protected MyWindow executeInEDT() {
          return new MyWindow();
        }
      });
    }

    private MyWindow() {
      super(JScrollBarDriverTest.class);
      add(scrollBar);
      scrollBar.setPreferredSize(new Dimension(20, 100));
      scrollBar.setBlockIncrement(EXTENT);
      scrollBar.setValue(30);
      scrollBar.setMinimum(MINIMUM);
      scrollBar.setMaximum(MAXIMUM);
      setPreferredSize(new Dimension(60, 200));
    }
  }
}
