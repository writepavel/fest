/*
 * Created on Jan 31, 2008
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright @2008 the original author or authors.
 */
package org.fest.swing.driver;

import java.awt.Point;

import javax.swing.JSplitPane;

import org.fest.swing.core.Robot;

import static javax.swing.JSplitPane.VERTICAL_SPLIT;

import static org.fest.swing.core.MouseButton.LEFT_BUTTON;
import static org.fest.swing.driver.JSplitPaneMoveDividerTask.moveDividerOf;
import static org.fest.swing.driver.JSplitPaneSizeAndDividerLocationQuery.sizeAndDividerLocationOf;
import static org.fest.swing.query.ComponentEnabledQuery.isEnabled;

/**
 * Understands simulation of user input on a <code>{@link JSplitPane}</code>. Unlike <code>JSplitPaneFixture</code>,
 * this driver only focuses on behavior present only in <code>{@link JSplitPane}</code>s. This class is intended for
 * internal use only.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JSplitPaneDriver extends JComponentDriver {

  /**
   * Creates a new </code>{@link JSplitPaneDriver}</code>.
   * @param robot the robot to use to simulate user input.
   */
  public JSplitPaneDriver(Robot robot) {
    super(robot);
  }

  /**
   * Set the divider position to an absolute position.
   * @param splitPane the target <code>JSplitPane</code>.
   * @param location the location to move the divider to.
   */
  public void moveDividerTo(JSplitPane splitPane, int location) {
    if (!isEnabled(splitPane)) return;
    // Move as close as possible, then set the position
    simulateMovingDivider(splitPane, location);
    robot.invokeAndWait(splitPane, moveDividerOf(splitPane, location));
  }

  private void simulateMovingDivider(JSplitPane split, int location) {
    if (split.getOrientation() == VERTICAL_SPLIT) {
      simulateMovingDividerVertically(split, location);
      return;
    }
    simulateMovingDividerHorizontally(split, location);
  }

  private void simulateMovingDividerVertically(JSplitPane splitPane, int location) {
    JSplitPaneSizeAndDividerLocation sizeAndDividerLocation = sizeAndDividerLocationOf(splitPane);
    int width = sizeAndDividerLocation.size().width;
    int dividerLocation = sizeAndDividerLocation.dividerLocation();
    int x = width / 2;
    simulateMovingDivider(splitPane, new Point(x, dividerLocation), new Point(x, location));
  }

  private void simulateMovingDividerHorizontally(JSplitPane splitPane, int location) {
    JSplitPaneSizeAndDividerLocation sizeAndDividerLocation = sizeAndDividerLocationOf(splitPane);
    int height = sizeAndDividerLocation.size().height;
    int dividerLocation = sizeAndDividerLocation.dividerLocation();
    int y = height / 2;
    simulateMovingDivider(splitPane, new Point(dividerLocation, y), new Point(location, y));
  }

  private void simulateMovingDivider(JSplitPane splitPane, Point start, Point end) {
    robot.moveMouse(splitPane, start);
    robot.pressMouse(LEFT_BUTTON);
    robot.moveMouse(splitPane, end);
    robot.releaseLeftMouseButton();
  }
}
