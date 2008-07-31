/*
 * Created on Apr 12, 2008
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

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import org.fest.swing.cell.JComboBoxCellReader;
import org.fest.swing.core.GuiTask;

/**
 * Understands the default implementation of <code>{@link JComboBoxCellReader}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class BasicJComboBoxCellReader extends BaseValueReader implements JComboBoxCellReader {

  private static final JList REFERENCE_JLIST = new JList();

  /**
   * Returns the internal value of a cell in a <code>{@link JComboBox}</code> as expected in a test. This method first
   * tries to get the value from the <code>toString</code> implementation of the object stored in the
   * <code>JComboBox</code>'s model at the specified index. If it fails, it returns the value displayed in the
   * <code>JComboBox</code>'s cell renderer.
   * @param comboBox the given <code>JComboBox</code>.
   * @param index the index of the cell.
   * @return the internal value of a cell in a <code>JComboBox</code> as expected in a test.
   * @see BaseValueReader#valueFrom(Component)
   * @see BaseValueReader#valueFrom(Object)
   */
  public String valueAt(JComboBox comboBox, int index) {
    String value = valueFrom(cellRendererComponent(comboBox, index));
    if (value != null) return value;
    return valueFrom(itemAt(comboBox, index));
  }

  /**
   * Returns the <code>{@link Component}</code> used by the <code>{@link ListCellRenderer}</code> in the given
   * <code>{@link JComboBox}</code>.
   * @param comboBox the given <code>JComboBox</code>.
   * @param index the index of the cell.
   * @return the <code>Component</code> used by the <code>ListCellRenderer</code> in the given <code>JComboBox</code>.
   */
  protected final Component cellRendererComponent(JComboBox comboBox, int index) {
    Component renderer = new GetListCellRendererComponentTask(comboBox, index).run();
    return renderer;
  }

  private Object itemAt(JComboBox comboBox, int index) {
    return new GetItemAtIndexTask(comboBox, index).run();
  }

  private static class GetItemAtIndexTask extends GuiTask<Object> {
    private final JComboBox comboBox;
    private final int index;

    GetItemAtIndexTask(JComboBox comboBox, int index) {
      this.index = index;
      this.comboBox = comboBox;
    }

    protected Object executeInEDT() {
      return comboBox.getItemAt(index);
    }
  }

  private static class GetListCellRendererComponentTask extends GuiTask<Component> {
    private final JComboBox comboBox;
    private final int index;

    GetListCellRendererComponentTask(JComboBox comboBox, int index) {
      this.index = index;
      this.comboBox = comboBox;
    }

    protected Component executeInEDT() {
      Object item = comboBox.getItemAt(index);
      return comboBox.getRenderer().getListCellRendererComponent(REFERENCE_JLIST, item, index, true, true);
    }
  }
}