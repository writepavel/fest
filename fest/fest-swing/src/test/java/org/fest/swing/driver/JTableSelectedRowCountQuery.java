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
 * Copyright @2008-2009 the original author or authors.
 */
package org.fest.swing.driver;

import javax.swing.JTable;

import org.fest.swing.edt.GuiQuery;

import static org.fest.swing.edt.GuiActionRunner.execute;

/**
 * Understands an action, executed in the event dispatch thread, that returns the number of selected rows in a
 * <code>{@link JTable}</code>.
 * @see JTable#getSelectedRowCount()
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
final class JTableSelectedRowCountQuery {

  static int selectedRowCountOf(final JTable table) {
    return execute(new GuiQuery<Integer>() {
      protected Integer executeInEDT() {
        return table.getSelectedRowCount();
      }
    });
  }

  private JTableSelectedRowCountQuery() {}
}
