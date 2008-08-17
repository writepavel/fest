/*
 * Created on Jul 22, 2008
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

import org.fest.swing.exception.ActionFailedException;

import static javax.swing.SwingUtilities.isEventDispatchThread;

import static org.fest.swing.exception.ActionFailedException.actionFailure;

/**
 * Understands executing an action, in the event dispatch thread, that returns a value.
 * @param <T> the return type of the action to execute.
 *
 * @author Alex Ruiz
 */
public abstract class GuiQuery<T> extends GuiAction {

  private T result;
  
  /**
   * Executes the query in the event dispatch thread. This method waits until the action has finish its execution.
   * @throws ActionFailedException if this task is not executed in the event dispatch thread.
   */
  public final void run() {
    if (!isEventDispatchThread())
      throw actionFailure("Task should be executed in the event dispatch thread");
    try {
      result = executeInEDT();
    } catch (Throwable t) {
      catchedException(t);
    }
  }

  /**
   * Specifies the action to execute in the event dispatch thread.
   * @return the result of the execution of the action.
   * @throws Throwable any error thrown when executing an action in the event dispatch thread.
   */
  protected abstract T executeInEDT() throws Throwable;

  final T result() { return result; }
}
