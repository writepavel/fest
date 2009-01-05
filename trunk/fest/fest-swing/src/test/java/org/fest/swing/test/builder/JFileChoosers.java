/*
 * Created on Aug 28, 2008
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
package org.fest.swing.test.builder;

import javax.swing.JFileChooser;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiQuery;

import static org.fest.swing.edt.GuiActionRunner.execute;

/**
 * Understands creation of <code>{@link JFileChooser}</code>s.
 *
 * @author Alex Ruiz
 */
public final class JFileChoosers {

  private JFileChoosers() {}

  public static JFileChooserFactory fileChooser() {
    return new JFileChooserFactory();
  }
  
  public static class JFileChooserFactory {
    String name;

    public JFileChooserFactory withName(String newName) {
      name = newName;
      return this;
    }
    
    @RunsInEDT
    public JFileChooser createNew() {
      return execute(new GuiQuery<JFileChooser>() {
        protected JFileChooser executeInEDT() {
          JFileChooser fileChooser = new JFileChooser();
          fileChooser.setName(name);
          return fileChooser;
        }
      });
    }
  }
}