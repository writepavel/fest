/*
 * Created on Jul 28, 2008
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
package org.fest.swing.driver;

import javax.swing.JSlider;

import org.fest.swing.core.GuiQuery;

import static org.fest.swing.core.GuiActionRunner.execute;

/**
 * Understands an action, executed in the event dispatch thread, that returns the value of a 
 * <code>{@link JSlider}</code>.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
final class JSliderValueQuery extends GuiQuery<Integer> {

  private final JSlider slider;

  static int valueOf(JSlider slider) {
    return execute(new JSliderValueQuery(slider));
  }

  private JSliderValueQuery(JSlider slider) {
    this.slider = slider;
  }

  protected Integer executeInEDT() {
    return slider.getValue();
  }
}