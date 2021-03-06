/*
 * Created on Jul 9, 2007
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
 * Copyright @2007-2009 the original author or authors.
 */
package org.fest.swing.fixture;

import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;

import org.testng.annotations.Test;

import org.fest.mocks.EasyMockTemplate;
import org.fest.swing.driver.ComponentDriver;
import org.fest.swing.driver.JFileChooserDriver;

import static org.easymock.EasyMock.*;
import static org.easymock.classextension.EasyMock.createMock;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.test.builder.JButtons.button;
import static org.fest.swing.test.builder.JFileChoosers.fileChooser;
import static org.fest.swing.test.builder.JTextFields.textField;
import static org.fest.util.Arrays.array;

/**
 * Tests for <code>{@link JFileChooserFixture}</code>.
 *
 * @author Yvonne Wang
 */
@Test public class JFileChooserFixtureTest extends CommonComponentFixtureTestCase<JFileChooser> {

  private JFileChooserDriver driver;
  private JFileChooser target;
  private JFileChooserFixture fixture;
  
  void onSetUp() {
    driver = createMock(JFileChooserDriver.class);
    target = fileChooser().createNew();
    fixture = new JFileChooserFixture(robot(), target);
    fixture.updateDriver(driver);
  }
  
  public void shouldCreateFixtureWithGivenComponentName() {
    String name = "fileChooser";
    expectLookupByName(name, targetType());
    verifyLookup(new JFileChooserFixture(robot(), name));
  }

  public void shouldCreateFixtureByType() {
    expectLookupByType(targetType());
    verifyLookup(new JFileChooserFixture(robot()));
  }

  private Class<JFileChooser> targetType() {
    return JFileChooser.class;
  }

  public void shouldReturnFileNameTextBox() {
    final JTextField fileNameTextBox = textField().createNew();
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.fileNameTextBox(target)).andReturn(fileNameTextBox);
      }

      protected void codeToTest() {
        JTextComponentFixture fileNameTextBoxFixture = fixture.fileNameTextBox();
        assertThat(fileNameTextBoxFixture.component()).isSameAs(fileNameTextBox);
      }
    }.run();
  }
  
  public void shouldClickApproveButton() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.clickApproveButton(target);
        expectLastCall().once();
      }

      protected void codeToTest() {
        fixture.approve();
      }
    }.run();
  }
  
  public void shouldReturnApproveButton() {
    final JButton approveButton = button().createNew();
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.approveButton(target)).andReturn(approveButton);
      }

      protected void codeToTest() {
        JButtonFixture result = fixture.approveButton();
        assertThat(result.target).isSameAs(approveButton);
      }
    }.run();
  }

  public void shouldClickCancelButton() {
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.clickCancelButton(target);
        expectLastCall().once();
      }

      protected void codeToTest() {
        fixture.cancel();
      }
    }.run();
  }

  public void shouldReturnCancelButton() {
    final JButton cancelButton = button().createNew();
    new EasyMockTemplate(driver) {
      protected void expectations() {
        expect(driver.cancelButton(target)).andReturn(cancelButton);
      }

      protected void codeToTest() {
        JButtonFixture result = fixture.cancelButton();
        assertThat(result.target).isSameAs(cancelButton);
      }
    }.run();
  }
  
  public void shouldSelectFile() {
    final File file = new File("fake");
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.selectFile(target, file);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.selectFile(file));
      }
    }.run();
  }
  
  public void shouldSelectFiles() {
    final File[] files = array(new File("Fake1"), new File("Fake2"));
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.selectFiles(target, files);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.selectFiles(files));
      }
    }.run();
  }

  public void shouldSetCurrentDirectory() {
    final File file = new File("fake");
    new EasyMockTemplate(driver) {
      protected void expectations() {
        driver.setCurrentDirectory(target(), file);
        expectLastCall().once();
      }

      protected void codeToTest() {
        assertThatReturnsThis(fixture.setCurrentDirectory(file));
      }
    }.run();
  }

  ComponentDriver driver() { return driver; }
  JFileChooser target() { return target; }
  JFileChooserFixture fixture() { return fixture; }
}
