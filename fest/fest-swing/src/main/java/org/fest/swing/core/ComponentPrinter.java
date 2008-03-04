/*
 * Created on Mar 4, 2008
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

import java.awt.Component;
import java.awt.Container;
import java.io.PrintStream;

import org.fest.swing.format.Formatting;

/**
 * Understands printing the <code>String</code> representation of <code>{@link java.awt.Component}</code>s to
 * facilitate debugging.
 * 
 * @author Alex Ruiz
 */
public interface ComponentPrinter {

  /**
   * Prints all the components (as <code>String</code>s) in the hierarchy.
   * @param out the output stream where to print the components to.
   * @see Formatting#format(Component)
   */
  void printComponents(PrintStream out);

  /**
   * Prints all the components (as <code>String</code>s) in the hierarchy under the given root.
   * @param out the output stream where to print the components to.
   * @param root the root used as the starting point of the search.
   * @see Formatting#format(Component)
   */
  void printComponents(PrintStream out, Container root);

  /**
   * Prints only the components of the given type (as <code>String</code>s) in the hierarchy.
   * @param out the output stream where to print the components to.
   * @param type the type of components to print.
   * @see Formatting#format(Component)
   */
  void printComponents(PrintStream out, Class<? extends Component> type);

  /**
   * Prints all the components of the given type (as <code>String</code>s) in the hierarchy under the given root.
   * @param out the output stream where to print the components to.
   * @param type the type of components to print.
   * @param root the root used as the starting point of the search.
   * @see Formatting#format(Component)
   */
  void printComponents(PrintStream out, Class<? extends Component> type, Container root);

}