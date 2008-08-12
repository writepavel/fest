/*
 * Created on Jul 26, 2008
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
package org.fest.swing.query;

import javax.swing.AbstractButton;

import org.fest.swing.core.GuiQuery;

/**
 * Understands an action, executed in the event dispatch thread, that returns the text of an
 * <code>{@link AbstractButton}</code>.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public final class AbstractButtonTextQuery extends GuiQuery<String> {
  
  private final AbstractButton button;

  /**
   * Returns the text of the given <code>{@link AbstractButton}</code>. This action is executed in the event dispatch
   * thread.
   * @param button the given <code>AbstractButton</code>.
   * @return the text of the given <code>AbstractButton</code>.
   */
  public static String textOf(AbstractButton button) {
    return new AbstractButtonTextQuery(button).run();
  }
  
  private AbstractButtonTextQuery(AbstractButton button) {
    this.button = button;
  }

  /**
   * Returns the text in this query's <code>{@link AbstractButton}</code>. This action is executed in the event dispatch
   * thread.
   * @return the text in this query's <code>AbstractButton</code>.
   */
  protected String executeInEDT() {
    return button.getText();
  }
}