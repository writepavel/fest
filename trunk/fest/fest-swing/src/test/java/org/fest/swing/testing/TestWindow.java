/*
 * Created on Sep 11, 2007
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
 * Copyright @2007-2008 the original author or authors.
 */
package org.fest.swing.testing;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.UIManager;

import org.fest.swing.core.GuiQuery;
import org.fest.swing.core.GuiTask;

import static org.fest.swing.core.GuiActionRunner.execute;

/**
 * Understands the base window for all GUI tests.
 *
 * @author Alex Ruiz
 */
public class TestWindow extends JFrame {

  private static final long serialVersionUID = 1L;

  public static TestWindow showNewInTest(final Class<?> testClass) {
    TestWindow window = execute(new GuiQuery<TestWindow>() {
      protected TestWindow executeInEDT() {
        return new TestWindow(testClass);
      }
    });
    window.display();
    return window;
  }
  
  public TestWindow(Class<?> testClass) {
    setTitle(testClass.getSimpleName());
    setLayout(new FlowLayout());
    chooseLookAndFeel();
  }

  public void addComponents(Component...components) {
    for (Component c : components) add(c);
  }
  
  public void display() {
    display(new Dimension(400, 200));
  }
  
  public void display(final Dimension size) {
    execute(new GuiTask() {
      protected void executeInEDT() {
        beforeDisplayed();
        setPreferredSize(size);
        pack();
        setLocation(100, 100);
        setVisible(true);
      }
    });
  }
  
  protected void beforeDisplayed() {}

  protected void chooseLookAndFeel() {
    lookNative();
  }
  
  private void lookNative() {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception ignored) {
      ignored.printStackTrace();
    }
  }
  
  public void destroy() {
    execute(new GuiTask() {
      protected void executeInEDT() {
        setVisible(false);
        dispose();
      }
    });
  }
}