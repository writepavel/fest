/*
 * Created on Aug 18, 2008
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

import java.awt.Point;

import javax.swing.JTree;

import org.fest.swing.core.GuiQuery;

import static org.fest.swing.core.GuiActionRunner.execute;

/**
 * Understands an action, executed in the event dispatch thread, that returns the row in a <code>{@link JTree}</code>
 * for the specified location.
 *
 * @author Yvonne Wang
 */
class JTreeRowAtPointQuery extends GuiQuery<Integer> {

  private final Point location;
  private final JTree tree;

  static int rowAtPoint(JTree tree, Point location) {
    return execute(new JTreeRowAtPointQuery(tree, location));
  }

  JTreeRowAtPointQuery(JTree tree, Point location) {
    this.location = location;
    this.tree = tree;
  }

  protected Integer executeInEDT() {
    return tree.getRowForLocation(location.x, location.y);
  }
}