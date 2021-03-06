/*
 * Created on Dec 1, 2007
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
package org.fest.swing.remote.core;

import static org.fest.swing.remote.core.Response.failure;
import static org.fest.swing.remote.core.Response.success;

/**
 * Understands processing of a ping request.
 *
 * @author Alex Ruiz
 */
public final class PingRequestHandler extends RequestHandler {

  private final TestServer server;

  /**
   * Creates a new </code>{@link PingRequestHandler}</code>.
   * @param server the server to ping.
   */
  public PingRequestHandler(TestServer server) {
    this.server = server;
  }

  /**
   * Indicates that this handle supports request of type <code>{@link PingRequest}</code>.
   * @return <code>PingRequest.class</code>.
   */
  @Override public Class<? extends Request> supportedType() {
    return PingRequest.class;
  }

  /**
   * Processes a request of type "ping".
   * @param request the request to process.
   * @return a response indicating that pinging the server was successful or not.
   */
  @Override protected Response doProcess(Request request) {
    return server.isRunning() ? success() : failure();
  }
}
