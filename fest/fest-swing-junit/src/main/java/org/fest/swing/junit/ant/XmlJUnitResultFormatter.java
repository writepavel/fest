/*
 * Created on Jun 6, 2007
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * 
 * Copyright @2007-2009 the original author or authors.
 */
package org.fest.swing.junit.ant;

import static org.apache.tools.ant.taskdefs.optional.junit.XMLConstants.ATTR_CLASSNAME;
import static org.apache.tools.ant.taskdefs.optional.junit.XMLConstants.ATTR_ERRORS;
import static org.apache.tools.ant.taskdefs.optional.junit.XMLConstants.ATTR_FAILURES;
import static org.apache.tools.ant.taskdefs.optional.junit.XMLConstants.ATTR_MESSAGE;
import static org.apache.tools.ant.taskdefs.optional.junit.XMLConstants.ATTR_NAME;
import static org.apache.tools.ant.taskdefs.optional.junit.XMLConstants.ATTR_TESTS;
import static org.apache.tools.ant.taskdefs.optional.junit.XMLConstants.ATTR_TIME;
import static org.apache.tools.ant.taskdefs.optional.junit.XMLConstants.ATTR_TYPE;
import static org.apache.tools.ant.taskdefs.optional.junit.XMLConstants.ATTR_VALUE;
import static org.apache.tools.ant.taskdefs.optional.junit.XMLConstants.ERROR;
import static org.apache.tools.ant.taskdefs.optional.junit.XMLConstants.FAILURE;
import static org.apache.tools.ant.taskdefs.optional.junit.XMLConstants.HOSTNAME;
import static org.apache.tools.ant.taskdefs.optional.junit.XMLConstants.PROPERTIES;
import static org.apache.tools.ant.taskdefs.optional.junit.XMLConstants.PROPERTY;
import static org.apache.tools.ant.taskdefs.optional.junit.XMLConstants.SYSTEM_ERR;
import static org.apache.tools.ant.taskdefs.optional.junit.XMLConstants.SYSTEM_OUT;
import static org.apache.tools.ant.taskdefs.optional.junit.XMLConstants.TESTCASE;
import static org.apache.tools.ant.taskdefs.optional.junit.XMLConstants.TESTSUITE;
import static org.apache.tools.ant.taskdefs.optional.junit.XMLConstants.TIMESTAMP;
import static org.fest.util.Strings.isEmpty;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import junit.framework.AssertionFailedError;
import junit.framework.Test;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.optional.junit.JUnitResultFormatter;
import org.apache.tools.ant.taskdefs.optional.junit.JUnitTest;
import org.apache.tools.ant.taskdefs.optional.junit.JUnitTestRunner;
import org.apache.tools.ant.taskdefs.optional.junit.JUnitVersionHelper2;
import org.apache.tools.ant.util.DOMElementWriter;
import org.apache.tools.ant.util.DateUtils;
import org.apache.tools.ant.util.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

/**
 * Understands a copy of the original <code>XMLJUnitResultFormatter</code>, with flexibility for extension.
 * 
 * @author Alex Ruiz
 */
public class XmlJUnitResultFormatter implements JUnitResultFormatter {

  /** Constant for unnnamed test suites/cases */
  private static final String UNKNOWN = "unknown";

  private static DocumentBuilder documentBuilder() {
    try {
      return DocumentBuilderFactory.newInstance().newDocumentBuilder();
    } catch (Exception exc) {
      throw new ExceptionInInitializerError(exc);
    }
  }

  /** The XML document. */
  private Document document;
  
  /** The wrapper for the whole test suite. */
  private Element rootElement;
  
  /** Element for the current test. */
  private ConcurrentMap<Test, Element> testElements = new ConcurrentHashMap<Test, Element>();
  
  /** Tests that failed. */
  private ConcurrentMap<Test, Test> failedTests = new ConcurrentHashMap<Test, Test>();
  
  /** Timing helper. */
  private ConcurrentMap<Test, Long> testStarts = new ConcurrentHashMap<Test, Long>();
  
  /** Where to write the log to. */
  private OutputStream out;

  /** {@inheritDoc}. */
  public final void setOutput(OutputStream out) { this.out = out; }

  /** {@inheritDoc}. */
  public final void setSystemOutput(String out) { formatOutput(SYSTEM_OUT, out); }

  /** {@inheritDoc}. */
  public final void setSystemError(String out) { formatOutput(SYSTEM_ERR, out); }

  private void formatOutput(String type, String output) {
    Element nested = document.createElement(type);
    rootElement.appendChild(nested);
    nested.appendChild(document.createCDATASection(output));
  }
  
  protected final Document document() { return document; }
  protected final Element rootElement() { return rootElement; }

  /**
   * The whole test suite started.
   * @param suite the test suite.
   */
  public final void startTestSuite(JUnitTest suite) {
    document = documentBuilder().newDocument();
    rootElement = document.createElement(TESTSUITE);
    writeSuiteName(suite.getName());
    writeTimestamp();
    writeHostname();
    writeProperties(suite);
    onStartTestSuite(suite);
  }

  private void writeSuiteName(String suiteName) {
    rootElement.setAttribute(ATTR_NAME, suiteName == null ? UNKNOWN : suiteName);
  }
  
  private void writeHostname() {
    String hostName = null;
    try {
      hostName = InetAddress.getLocalHost().getHostName();
    } catch (UnknownHostException e) {
      hostName = "localhost";
    }
    rootElement.setAttribute(HOSTNAME, hostName);
  }
  
  private void writeTimestamp() {
    String timestamp = DateUtils.format(new Date(), DateUtils.ISO8601_DATETIME_PATTERN);
    rootElement.setAttribute(TIMESTAMP, timestamp);
  }

  private void writeProperties(JUnitTest suite) {
    Element propertiesElement = document.createElement(PROPERTIES);
    rootElement.appendChild(propertiesElement);
    Properties properties = suite.getProperties();
    if (properties == null) return;
    Enumeration<?> propertyNames = properties.propertyNames();
    while (propertyNames.hasMoreElements()) {
      String name = (String) propertyNames.nextElement();
      Element propertyElement = document.createElement(PROPERTY);
      propertyElement.setAttribute(ATTR_NAME, name);
      propertyElement.setAttribute(ATTR_VALUE, properties.getProperty(name));
      propertiesElement.appendChild(propertyElement);
    }
  }

  protected void onStartTestSuite(JUnitTest suite) {}
  
  /**
   * The whole test suite ended.
   * @param suite the test suite.
   * @throws BuildException on error.
   */
  public final void endTestSuite(JUnitTest suite) throws BuildException {
    rootElement.setAttribute(ATTR_TESTS, "" + suite.runCount());
    rootElement.setAttribute(ATTR_FAILURES, "" + suite.failureCount());
    rootElement.setAttribute(ATTR_ERRORS, "" + suite.errorCount());
    rootElement.setAttribute(ATTR_TIME, "" + (suite.getRunTime() / 1000.0));
    if (out == null) return;
    Writer writer = null;
    try {
      writer = new BufferedWriter(new OutputStreamWriter(out, "UTF8"));
      writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
      (new DOMElementWriter()).write(rootElement, writer, 0, "  ");
      writer.flush();
    } catch (IOException e) {
      throw new BuildException("Unable to write log file", e);
    } finally {
      if (out != System.out && out != System.err) FileUtils.close(writer);
    }
  }

  /**
   * A new test is started.
   * @param test the test.
   */
  public final void startTest(Test test) {
    testStarts.put(test, System.currentTimeMillis());
  }

  /**
   * A test is finished.
   * @param test the test.
   */
  public final void endTest(Test test) {
    if (!testStarts.containsKey(test)) startTest(test);
    Element currentTest = null;
    if (!failedTests.containsKey(test)) currentTest = createAndAddCurrentTest(test);
    else currentTest = testElements.get(test);
    writeExecutionTime(test, currentTest);
  }

  private Element createAndAddCurrentTest(Test test) {
    Element currentTest = document.createElement(TESTCASE);
    String methodName = JUnitVersionHelper2.testMethodName(test);
    currentTest.setAttribute(ATTR_NAME, methodName == null ? UNKNOWN : methodName);
    currentTest.setAttribute(ATTR_CLASSNAME, JUnitVersionHelper2.testClassName(test));
    rootElement.appendChild(currentTest);
    testElements.put(test, currentTest);    
    return currentTest;
  }
  
  private void writeExecutionTime(Test test, Element currentTest) {
    long startTime = testStarts.get(test);
    double executionTime = (System.currentTimeMillis() - startTime) / 1000.0;
    currentTest.setAttribute(ATTR_TIME, String.valueOf(executionTime));
  }

  /**
   * A test failed.
   * @param test the test.
   * @param error the exception.
   */
  public final void addFailure(Test test, Throwable error) {
    Element errorElement = formatError(FAILURE, test, error);
    onFailureOrError(test, error, errorElement);
  }

  /**
   * A test failed.
   * @param test the test.
   * @param failedAssertion the failed assertion.
   */
  public final void addFailure(Test test, AssertionFailedError failedAssertion) {
    addFailure(test, (Throwable)failedAssertion);
  }

  /**
   * An error occurred while running the test.
   * @param test the test.
   * @param error the error.
   */
  public final void addError(Test test, Throwable error) {
    Element errorElement = formatError(ERROR, test, error);
    onFailureOrError(test, error, errorElement);
  }

  private Element formatError(String type, Test test, Throwable error) {
    if (test != null) {
      endTest(test);
      failedTests.put(test, test);
    }
    Element errorElement = document.createElement(type);
    Element currentTest = null;
    if (test != null) currentTest = testElements.get(test);
    else currentTest = rootElement;
    currentTest.appendChild(errorElement);
    writeErrorAndStackTrace(error, errorElement);
    return errorElement;
  }

  protected final void writeErrorAndStackTrace(Throwable error, Element errorElement) {
    writeError(error, errorElement);
    writeStackTrace(error, errorElement);
  }
  
  private void writeError(Throwable error, Element destination) {
    String message = error.getMessage();
    if (!isEmpty(message)) destination.setAttribute(ATTR_MESSAGE, error.getMessage());
    destination.setAttribute(ATTR_TYPE, error.getClass().getName());
  }

  private final void writeStackTrace(Throwable error, Element destination) {
    String stackTrace = JUnitTestRunner.getFilteredTrace(error);
    writeText(stackTrace, destination);
  }

  protected final void writeText(String text, Element destination) {
    Text textNode = document.createTextNode(text);
    destination.appendChild(textNode);
  }
    
  protected void onFailureOrError(Test test, Throwable error, Element errorElement) {}
}
