/*
 * Created on Aug 13, 2008
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

import java.awt.Component;
import java.awt.Point;

import org.fest.swing.core.GuiQuery;

import static org.fest.swing.core.GuiActionRunner.execute;

/**
 * Understands an action, executed in the event dispatch thread, that returns the location (top-left corner) of a
 * <code>{@link Component}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
class ComponentLocationQuery extends GuiQuery<Point> {

  private final Component component;

  static Point locationOf(Component component) {
    return execute(new ComponentLocationQuery(component));
  }
  
  private ComponentLocationQuery(Component component) {
    this.component = component;
  }

  protected Point executeInEDT() throws Throwable {
    return component.getLocation();
  }
}
