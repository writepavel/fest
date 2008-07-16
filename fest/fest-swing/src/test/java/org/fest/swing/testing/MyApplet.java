/*
 * Created on Jun 5, 2008
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
package org.fest.swing.testing;

import java.awt.FlowLayout;
import java.awt.HeadlessException;

import javax.swing.JApplet;
import javax.swing.JButton;

/**
 * Understands a simple applet.
 *
 * @author Alex Ruiz
 */
public class MyApplet extends JApplet {

  private static final long serialVersionUID = 1L;
  
  private boolean initialized;
  private boolean destroyed;
  private boolean started;
  private boolean stopped;

  /**
   * Creates a new </code>{@link MyApplet}</code>.
   * @throws HeadlessException
   */
  public MyApplet() throws HeadlessException {
    setLayout(new FlowLayout());
    JButton button = new JButton("Click Me");
    button.setName("clickMe");
    add(button);
  }
  
  @Override public void init() {
    initialized = true;
    super.init();
  }

  @Override public void destroy() {
    destroyed = true;
    super.destroy();
  }

  @Override public void start() {
    started = true;
    super.start();
  }

  @Override public void stop() {
    stopped = true;
    super.stop();
  }

  public boolean initialized() { return initialized; }

  public boolean destroyed() { return destroyed; }

  public boolean started() { return started; }

  public boolean stopped() { return stopped; }
}