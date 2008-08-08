/*
 * Created on Aug 7, 2008
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
package org.fest.swing.driver;

import javax.swing.JList;
import javax.swing.ListModel;

import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;

import static org.easymock.EasyMock.expect;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests for <code>{@link GetJListElementAtIndexTask}</code>.
 *
 * @author Alex Ruiz
 */
@Test public class GetJListElementAtIndexTaskTest {

  public void shouldReturnElementAtIndexInJList() {
    final JList list = createMock(JList.class);
    final ListModel model = createMock(ListModel.class);
    final int index = 8;
    final Object value = "Hello";
    new EasyMockTemplate(list, model) {
      protected void expectations() {
        expect(list.getModel()).andReturn(model);
        expect(model.getElementAt(index)).andReturn(value);
      }

      protected void codeToTest() {
        assertThat(GetJListElementAtIndexTask.elementAt(list, index)).isSameAs(value);
      }
    }.run();
  }
}
