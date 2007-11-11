/*
 * Created on Oct 24, 2007
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
 * Copyright @2007 the original author or authors.
 */
package org.fest.swing.hierarchy;

import static org.fest.swing.util.ComponentCollections.empty;
import static org.fest.util.Collections.list;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Understands how to find children components in a <code>{@link Container}</code>.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
class ChildrenFinder {
  
  private static List<ChildrenFinderStrategy> strategies = new ArrayList<ChildrenFinderStrategy>();
  
  static {
    strategies.add(new JDesktopPaneChildrenFinder());
    strategies.add(new JMenuChildrenFinder());
    strategies.add(new WindowChildrenFinder());
  }

  Collection<Component> childrenOf(Component c) {
    if (!(c instanceof Container)) return empty();
    Container container = (Container)c;
    Collection<Component> children = empty();
    children.addAll(list(container.getComponents()));
    children.addAll(nonExplicitChildrenOf(container));
    return children;
  }

  private Collection<Component> nonExplicitChildrenOf(Container c) {
    Collection<Component> children = empty();
    for (ChildrenFinderStrategy s : strategies) 
      children.addAll(s.nonExplicitChildrenOf(c));
    return children;
  }
}
