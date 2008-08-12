/*
 * Created on Feb 23, 2008
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

import javax.accessibility.AccessibleAction;

import org.fest.swing.core.GuiQuery;

/**
 * Understands an action, executed in the event dispatch thread, that finds <code>{@link AccessibleAction}</code>s
 * associated to <code>{@link Component}</code>s.
 * 
 * @author Alex Ruiz
 */
class ComponentAccessibleActionQuery extends GuiQuery<AccessibleAction> {

  private final Component component;

  static AccessibleAction accessibleActionFrom(Component component) {
    return new ComponentAccessibleActionQuery(component).run();
  }

  private ComponentAccessibleActionQuery(Component component) {
    this.component = component;
  }
  
  protected AccessibleAction executeInEDT() throws Throwable {
    return component.getAccessibleContext().getAccessibleAction();
  }
}