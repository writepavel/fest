/*
 * Created on Mar 26, 2008
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
 * Copyright @2008-2009 the original author or authors.
 */
package org.fest.swing.keystroke;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.KeyStroke;

import static java.awt.event.KeyEvent.*;

/**
 * Understands a default mapping of characters and <code>{@link KeyStroke}</code>s.
 * 
 * @author Alex Ruiz
 */
public class DefaultKeyStrokeMappingProvider implements KeyStrokeMappingProvider {

  /**
   * Returns the default mapping of characters and <code>{@link KeyStroke}</code>s. This provider will only return
   * the mappings for following keys:
   * <ul>
   * <li>Escape</li>
   * <li>Backspace</li>
   * <li>Delete</li>
   * <li>Enter</li>
   * </ul>
   * @return the default mapping of characters and <code>KeyStroke</code>s
   */
  public Collection<KeyStrokeMapping> keyStrokeMappings() {
    List<KeyStrokeMapping> mappings = new ArrayList<KeyStrokeMapping>();
    mappings.add(new KeyStrokeMapping('\b', VK_BACK_SPACE, NO_MASK));
    mappings.add(new KeyStrokeMapping('', VK_DELETE, NO_MASK));
    mappings.add(new KeyStrokeMapping('', VK_ESCAPE, NO_MASK));
    mappings.add(new KeyStrokeMapping('\n', VK_ENTER, NO_MASK));
    mappings.add(new KeyStrokeMapping('\r', VK_ENTER, NO_MASK));
    return mappings;
  }
}
