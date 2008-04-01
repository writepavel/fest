/*
 * Created on Apr 1, 2008
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

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import org.fest.swing.exception.ScreenLockException;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static org.fest.swing.core.Pause.pause;

/**
 * Tests for <code>{@link ScreenLock}</code>.
 *
 * @author Alex Ruiz
 */
public class ScreenLockTest {

  private Object owner;
  private ScreenLock lock;
  
  @BeforeMethod public void setUp() {
    owner = new Object();
    lock = new ScreenLock();
  }
  
  @Test public void shouldAcquireLockAndQueueOthersWantingLock() {
    lock.acquire(owner);
    assertThat(lock.owner()).isSameAs(owner);
    final Object o = new Object();
    new Thread() {
      @Override public void run() {
        lock.acquire(o);
        assertThat(lock.owner()).isSameAs(owner);
      }
    }.start();
    lock.release(owner);
    pause(200);
    assertThat(lock.owner()).isSameAs(o);
    lock.release(o);
  }
  
  @Test(expectedExceptions = ScreenLockException.class)
  public void shouldThrowErrorIfNoLockOwnerWhenReleasing() {
    lock.release(owner);
  }

  @Test public void shouldThrowErrorIfReleasingWithWrongOwner() {
    lock.acquire(owner);
    try {
      lock.release(new Object());
      fail();
    } catch (ScreenLockException expected) {}
    lock.release(owner);
  }
}
