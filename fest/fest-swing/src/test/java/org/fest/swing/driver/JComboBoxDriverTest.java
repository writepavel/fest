/*
 * Created on Feb 24, 2008
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

import javax.swing.*;
import javax.swing.text.JTextComponent;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.core.Robot;
import org.fest.swing.core.RobotFixture;
import org.fest.swing.exception.LocationUnavailableException;
import org.fest.swing.testing.TestFrame;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.core.Pause.pause;
import static org.fest.swing.testing.TestGroups.GUI;
import static org.fest.util.Arrays.array;

/**
 * Tests for <code>{@link JComboBoxDriver}</code>.
 *
 * @author Alex Ruiz
 */
@Test(groups = GUI)
public class JComboBoxDriverTest {

  private Robot robot;
  private JComboBox comboBox;
  private JComboBoxDriver driver;

  @BeforeMethod public void setUp() {
    robot = RobotFixture.robotWithNewAwtHierarchy();
    driver = new JComboBoxDriver(robot);
    MyFrame frame = new MyFrame();
    comboBox = frame.comboBox;
    robot.showWindow(frame);
  }

  @AfterMethod public void tearDown() {
    robot.cleanUp();
  }

  @Test public void shouldReturnComboBoxContents() {
    String[] contents = driver.contentsOf(comboBox);
    assertThat(contents).isEqualTo(array("first", "second", "third"));
  }

  @Test public void shouldSelectItemAtGivenIndex() {
    driver.selectItem(comboBox, 2);
    assertThat(comboBox.getSelectedItem()).isEqualTo("third");
  }

  @Test public void shouldSelectItemWithGivenText() {
    driver.selectItem(comboBox, "second");
    assertThat(comboBox.getSelectedItem()).isEqualTo("second");
  }

  @Test public void shouldNotSelectItemWithGivenTextIfAlreadySelected() {
    comboBox.setSelectedIndex(1);
    driver.selectItem(comboBox, "second");
    assertThat(comboBox.getSelectedItem()).isEqualTo("second");
  }

  @Test(expectedExceptions = LocationUnavailableException.class)
  public void shouldThrowErrorIfTextOfItemToSelectDoesNotExist() {
    driver.selectItem(comboBox, "hundred");
  }

  @Test public void shouldReturnTextAtGivenIndex() {
    String text = driver.text(comboBox, 2);
    assertThat(text).isEqualTo("third");
  }

  @Test public void shouldReturnTextFromListCellRenderer() {
    DefaultComboBoxModel model = new DefaultComboBoxModel(array(new Object()));
    comboBox.setModel(model);
    comboBox.setRenderer(new ListCellRendererStub("Hi"));
    robot.click(comboBox);
    assertThat(driver.text(comboBox, 0)).isEqualTo("Hi");
  }

  @Test public void shouldReturnDropDownList() {
    driver.click(comboBox);
    JList dropDownList = driver.dropDownList();
    assertThatListContains(dropDownList, "first", "second", "third");
  }
  
  private void assertThatListContains(JList list, Object...expected) {
    int expectedSize = expected.length;
    ListModel model = list.getModel();
    assertThat(model.getSize()).isEqualTo(expectedSize);
    for (int i = 0; i < expectedSize; i++)
      assertThat(model.getElementAt(i)).isEqualTo(expected[i]);
  }

  @Test public void shouldPassIfHasExpectedSelection() {
    comboBox.setSelectedIndex(0);
    driver.requireSelection(comboBox, "first");
  }

  @Test public void shouldFailIfDoesNotHaveExpectedSelection() {
    comboBox.setSelectedIndex(0);
    try {
      driver.requireSelection(comboBox, "second");
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'selectedIndex'")
                             .contains("expected:<'second'> but was:<'first'>");
    }
  }

  @Test public void shouldFailIfDoesNotHaveAnySelectionAndExpectingSelection() {
    comboBox.setSelectedIndex(-1);
    try {
      driver.requireSelection(comboBox, "second");
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'selectedIndex'")
                             .contains("No selection");
    }
  }

  @Test public void shouldPassIfComboBoxIsEditable() {
    comboBox.setEditable(true);
    driver.requireEditable(comboBox);
  }

  @Test public void shouldFailIfComboBoxIsNotEditableAndExpectingEditable() {
    comboBox.setEditable(false);
    try {
      driver.requireEditable(comboBox);
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'editable'").contains("expected:<true> but was:<false>");
    }
  }

  @Test public void shouldNotSelectAllTextIfComboBoxIsNotEditable() {
    comboBox.setSelectedIndex(0);
    comboBox.setEditable(false);
    driver.selectAllText(comboBox);
    assertThat(comboBox.getSelectedIndex()).isEqualTo(0);
  }

  @Test public void shouldSelectAllText() {
    comboBox.setSelectedIndex(0);
    comboBox.setEditable(true);
    driver.selectAllText(comboBox);
    Component editor = comboBox.getEditor().getEditorComponent();
    assertThat(editor).isInstanceOf(JTextComponent.class);
    JTextComponent textBox = (JTextComponent)editor;
    assertThat(textBox.getSelectedText()).isEqualTo("first");
  }

  @Test public void shouldNotEnterTextIfComboBoxIsNotEditable() {
    comboBox.setSelectedIndex(0);
    comboBox.setEditable(false);
    driver.enterText(comboBox, "Hello");
    assertThat(comboBox.getSelectedIndex()).isEqualTo(0);
  }

  @Test public void shouldEnterText() {
    comboBox.setEditable(true);
    driver.enterText(comboBox, "Hello");
    assertThat(textIn(comboBox)).contains("Hello");
  }

  @Test public void shouldNotReplaceTextIfComboBoxIsNotEditable() {
    comboBox.setSelectedIndex(0);
    comboBox.setEditable(false);
    driver.replaceText(comboBox, "Hello");
    assertThat(comboBox.getSelectedIndex()).isEqualTo(0);
  }
  
  @Test public void shouldReplaceText() {
    comboBox.setSelectedIndex(0);
    comboBox.setEditable(true);
    driver.replaceText(comboBox, "Hello");
    assertThat(textIn(comboBox)).isEqualTo("Hello");
  }

  private String textIn(JComboBox comboBox) {
    Component editor = comboBox.getEditor().getEditorComponent();
    if (editor instanceof JTextComponent) return ((JTextComponent)editor).getText();
    if (editor instanceof JLabel) return ((JLabel)editor).getText();
    return null;
  }

  @Test public void shouldPassIfComboBoxIsNotEditable() {
    comboBox.setEditable(false);
    driver.requireNotEditable(comboBox);
  }

  @Test public void shouldFailIfComboBoxIsEditableAndExpectingNotEditable() {
    comboBox.setEditable(true);
    try {
      driver.requireNotEditable(comboBox);
      fail();
    } catch (AssertionError e) {
      assertThat(e).message().contains("property:'editable'").contains("expected:<false> but was:<true>");
    }
  }
  
  @Test public void shouldFailIfItemIndexIsNegative() {
    try {
      driver.validatedIndex(comboBox, -1);
      fail();
    } catch (LocationUnavailableException e) {
      assertThat(e).message().contains("Item index (-1) should be between [0] and [2]");
    }    
  }

  @Test public void shouldFailIfItemIndexIsGreaterThanLastItemIndex() {
    try {
      driver.validatedIndex(comboBox, 6);
      fail();
    } catch (LocationUnavailableException e) {
      assertThat(e).message().contains("Item index (6) should be between [0] and [2]");
    }    
  }

  @Test public void shouldShowDropDownListWhenComboBoxIsNotEditable() {
    comboBox.setEditable(false);
    driver.showDropDownList(comboBox);
    pause(200);
    assertDropDownVisible();
  }
  
  @Test public void shouldShowDropDownListWhenComboBoxIsEditable() {
    comboBox.setEditable(true);
    driver.showDropDownList(comboBox);
    pause(200);
    assertDropDownVisible();
  }

  private void assertDropDownVisible() {
    assertThat(comboBox.getUI().isPopupVisible(comboBox)).isTrue();
  }

  private static class MyFrame extends TestFrame {
    private static final long serialVersionUID = 1L;

    final JComboBox comboBox = new JComboBox(array("first", "second", "third"));

    public MyFrame() {
      super(JComboBoxDriverTest.class);
      add(comboBox);
    }
  }
}