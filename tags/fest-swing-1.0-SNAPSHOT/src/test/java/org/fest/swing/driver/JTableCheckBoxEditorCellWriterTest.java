/*
 * Created on Jun 10, 2008
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

import org.testng.annotations.Test;

import org.fest.swing.cell.JTableCellWriter;
import org.fest.swing.core.EventMode;
import org.fest.swing.core.EventModeProvider;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.core.TestGroups.GUI;

/**
 * Tests for <code>{@link JTableCheckBoxEditorCellWriter}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class JTableCheckBoxEditorCellWriterTest extends JTableCellWriterTestCase {

  protected JTableCellWriter createWriter() {
    return new JTableCheckBoxEditorCellWriter(robot());
  }

  @Test(groups = GUI, dataProvider = "eventModes", dataProviderClass = EventModeProvider.class)
  public void shouldSelectItemInCheckBoxEditor(EventMode eventMode) {
    robot().settings().eventMode(eventMode);
    int row = 0;
    int column = 4;
    writer().enterValue(table(), row, column, "false");
    assertThat(valueAt(row,column)).isEqualTo(false);
    writer().enterValue(table(), row, column, "true");
    assertThat(valueAt(row,column)).isEqualTo(true);
    writer().enterValue(table(), row, column, "false");
    assertThat(valueAt(row,column)).isEqualTo(false);
    writer().enterValue(table(), row, column, "false");
    assertThat(valueAt(row,column)).isEqualTo(false);
  }
}