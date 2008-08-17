/*
 * Created on Aug 11, 2008
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

import javax.swing.JComponent;

import org.fest.swing.core.GuiQuery;

import static org.fest.swing.core.GuiActionRunner.execute;

/**
 * Understands an action, executed in the event dispatch thread, that returns the origin of a
 * <code>{@link JComponent}</code>.
 * 
 * @author Alex Ruiz
 */
class JComponentOriginQuery extends GuiQuery<Point> {

  private final JComponent component;

  static Point originOf(JComponent component) {
    return execute(new JComponentOriginQuery(component));
  }
  
  private JComponentOriginQuery(JComponent component) {
    this.component = component;
  }
  
  protected Point executeInEDT() throws Throwable {
    return new Point(component.getX(), component.getY());
  }
}
