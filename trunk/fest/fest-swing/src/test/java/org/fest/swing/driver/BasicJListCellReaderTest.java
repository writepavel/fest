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

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JToolBar;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.GuiActionRunner;
import org.fest.swing.core.GuiQuery;
import org.fest.swing.core.GuiTask;
import org.fest.swing.testing.CustomCellRenderer;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.core.GuiActionRunner.execute;
import static org.fest.swing.testing.TestGroups.GUI;

/**
 * Tests for <code>{@link BasicJListCellReader}</code>.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
@Test(groups = GUI)
public class BasicJListCellReaderTest {

  private JList list;
  private BasicJListCellReader reader;

  @BeforeMethod public void setUp() {
    list = newList();
    reader = new BasicJListCellReader();
  }

  private static JList newList() {
    return execute(new GuiQuery<JList>() { 
      protected JList executeInEDT() { return new JList(); }
    });
  }

  public void shouldReturnModelValueToString() {
    setModelValue(list, new Jedi("Yoda"));
    Object value = reader.valueAt(list, 0);
    assertThat(value).isEqualTo("Yoda");
  }

  public void shouldReturnNullIfRendererNotRecognizedAndModelValueIsNull() {
    setModelValue(list, null);
    setRendererComponent(list, notRecognizedRenderer());
    Object value = reader.valueAt(list, 0);
    assertThat(value).isNull();
  }

  private static Component notRecognizedRenderer() {
    return execute(new GuiQuery<Component>() {
      protected Component executeInEDT() {
        return new JToolBar();
      }
    });
  }

  public void shouldReturnTextFromCellRendererIfRendererIsJLabelAndToStringFromModelReturnedNull() {
    setModelValue(list, new Jedi(null));
    JLabel renderer = labelWithText("First");
    setRendererComponent(this.list, renderer);
    Object value = reader.valueAt(this.list, 0);
    assertThat(value).isEqualTo("First");
  }

  private static JLabel labelWithText(final String text) {
    return execute(new GuiQuery<JLabel>() {
      protected JLabel executeInEDT() {
        return new JLabel(text);
      }
    });
  }

  private static void setModelValue(final JList list, final Object value) {
    GuiActionRunner.execute(new GuiTask() {
      protected void executeInEDT() {
        list.setModel(new MyListModel(value));
      }
    });
  }
  
  private static void setRendererComponent(final JList list, final Component renderer) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        list.setCellRenderer(new CustomCellRenderer(renderer));
      }
    });
  }
  
  private static class MyListModel extends DefaultListModel {
    private static final long serialVersionUID = 1L;

    MyListModel(Object... values) {
      for (Object value : values) addElement(value);
    }
  }
}
