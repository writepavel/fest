/*
 * Created on Mar 8, 2008
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
package org.fest.swing.demo.view;

import java.awt.*;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;

import org.jdesktop.swingx.JXBusyLabel;

import static javax.swing.BorderFactory.createLineBorder;

import static org.fest.swing.demo.view.Swing.center;

/**
 * Understands a window that shows progress when a time-consuming process is being executed.
 *
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
abstract class ProgressWindow extends JWindow {

  ProgressWindow(Window owner, String message) {
    super(owner);
    setLayout(new BorderLayout());
    add(content(message), BorderLayout.CENTER);
    setPreferredSize(new Dimension(200, 100));
    pack();
  }

  private JPanel content(String message) {
    JPanel panel = new JPanel(new GridBagLayout());
    panel.setBorder(createLineBorder(darkerBackground(4), 2));
    GridBagConstraints c = new GridBagConstraints();
    c.gridx = c.gridy = 0;
    panel.add(busyLabel(), c);
    c.gridy++;
    c.insets = new Insets(10, 0, 0, 0);
    panel.add(messageLabel(message), c);
    return panel;
  }

  private Color darkerBackground(int times) {
    Color c = getBackground();
    for (int i = 0; i < times; i++) c = c.darker();
    return c;
  }

  private JXBusyLabel busyLabel() {
    JXBusyLabel busyLabel = new JXBusyLabel(new Dimension(26,26));
    busyLabel.setBusy(true);
    return busyLabel;
  }

  private JLabel messageLabel(String message) {
    return new JLabel(message);
  }

  /** @see java.awt.Window#setVisible(boolean) */
  @Override public void setVisible(boolean visible) {
    if (visible) center(this);
    super.setVisible(visible);
  }
}
