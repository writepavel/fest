/*
 * Created on Jan 17, 2009
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
 * Copyright @2009 the original author or authors.
 */
package org.fest.swing.junit.testcase;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

import org.fest.swing.core.Robot;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.testing.FestSwingTestCaseTemplate;

/**
 * Understands a template for test cases that use FEST-Swing and JUnit. This template installs a 
 * <code>{@link FailOnThreadViolationRepaintManager}</code> to verify violations of Swing thread rules and manages both
 * creation and clean up of a <code>{@link Robot}</code>.
 * @since 1.1
 *
 * @author Alex Ruiz
 */
public abstract class FestSwingJUnitTestCase extends FestSwingTestCaseTemplate {

  @BeforeClass public final void setUpOnce() {
    FailOnThreadViolationRepaintManager.install();
  }
  
  @Before public final void setUp() {
    setUpRobot();
    onSetUp();
  }

  /**
   * Implementations set up their own test fixture in this method. This method is called after this test's 
   * <code>{@link Robot}</code> has been created. 
   */
  protected abstract void onSetUp();
  
  @After public final void tearDown() {
    try {
      onTearDown();
    } finally {
      robot().cleanUp();
    }
  }

  /**
   * Implementations clean up resources in this method. This method is called before cleaning up resources used by this
   * fixture's <code>{@link Robot}</code>.
   */
  protected void onTearDown() {}
}
