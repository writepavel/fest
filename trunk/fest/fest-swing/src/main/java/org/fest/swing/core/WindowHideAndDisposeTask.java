/*
 * Created on Sep 2, 2008
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
package org.fest.swing.core;

import java.awt.Window;


/**
 * Understands a task that hides and disposes a <code>{@link Window}</code>. This task should be executed in the event
 * dispatch thread.
 *
 * @author Alex Ruiz
 */
class WindowHideAndDisposeTask extends GuiTask {

  private final Window w;

  static WindowHideAndDisposeTask hideAndDisposeTask(Window w) {
    return new WindowHideAndDisposeTask(w);
  }
  
  WindowHideAndDisposeTask(Window w) {
    this.w = w;
  }
  
  protected void executeInEDT() {
    w.setVisible(false);
    w.dispose();
  }

}