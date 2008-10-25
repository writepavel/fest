/*
 * Created on Oct 24, 2008
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
package org.fest.swing.edt;

/**
 * Understands different types of execution of actions to be performed on a GUI component.
 *
 * @author Alex Ruiz
 */
public enum GuiActionExecutionType {

  RUN_IN_EDT, RUN_IN_CURRENT_THREAD
}
