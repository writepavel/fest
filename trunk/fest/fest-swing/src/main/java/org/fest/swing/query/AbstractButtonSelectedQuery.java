/*
 * Created on Jul 29, 2008
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
package org.fest.swing.query;

import javax.swing.AbstractButton;

import org.fest.swing.core.GuiQuery;

import static org.fest.swing.core.GuiActionRunner.execute;

/**
 * Understands an action, executed in the event dispatch thread, that indicates whether an 
 * <code>{@link AbstractButton}</code> is selected or not.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public final class AbstractButtonSelectedQuery extends GuiQuery<Boolean> {
  
  private final AbstractButton button;

  /**
   * Indicates whether the given <code>{@link AbstractButton}</code> is selected or not. This action is executed in the
   * event dispatch thread.
   * @param button the given <code>AbstractButton</code>.
   * @return <code>true</code> if the given <code>AbstractButton</code> is selected, <code>false</code> otherwise.
   */
  public static boolean isSelected(AbstractButton button) {
    return execute(new AbstractButtonSelectedQuery(button));
  }

  AbstractButtonSelectedQuery(AbstractButton button) {
    this.button = button;
  }

  /**
   * Indicates whether this query's <code>{@link AbstractButton}</code> is selected or not. This action is executed in
   * the event dispatch thread.
   * @return <code>true</code> if this query's <code>AbstractButton</code> is selected, <code>false</code> otherwise.
   */
  protected Boolean executeInEDT() {
    return button.isSelected();
  }
}