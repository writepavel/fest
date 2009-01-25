/*
 * Created on Jan 24, 2009
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
 * Copyright @2009 the original author or authors.
 */
package org.fest.reflect.field;

import org.fest.reflect.exception.ReflectionError;
import org.fest.reflect.reference.TypeRef;

/**
 * Understands the type of a field to access using Java Reflection. This implementation supports Java generics.
 * <p>
 * The following is an example of proper usage of this class:
 * <pre>
 *   // Retrieves the value of the field "powers"
 *   List&lt;String&gt; powers = {@link org.fest.reflect.core.Reflection#field(String) field}("powers").{@link FieldName#ofType(TypeRef) ofType}(new {@link TypeRef TypeReference}&lt;List&lt;String&gt;&gt;() {}).{@link #in(Object) in}(jedi).{@link Invoker#get() get}();
 *   
 *   // Sets the value of the field "powers"
 *   List&lt;String&gt; powers = new ArrayList&lt;String&gt;();
 *   powers.add("heal");
 *   {@link org.fest.reflect.core.Reflection#field(String) field}("powers").{@link FieldName#ofType(TypeRef) ofType}(new {@link TypeRef TypeReference}&lt;List&lt;String&gt;&gt;() {}).{@link #in(Object) in}(jedi).{@link Invoker#set(Object) set}(powers);
 * </pre>
 * </p>
 *
 * @param <T> the generic type of the field. 
 *
 * @author Alex Ruiz
 */
public class FieldTypeReference<T> extends TypeReferenceTemplate<T> {

  FieldTypeReference(TypeRef<T> type, FieldName fieldName) {
    super(type, fieldName);
  }

  /**
   * Returns a new field invoker. A field invoker is capable of accessing (read/write) the underlying field.
   * @param target the object containing the field of interest.
   * @return the created field invoker.
   * @throws ReflectionError if a field with a matching name and type cannot be found.
   */
  public Invoker<T> in(Object target) {
    return fieldInvoker(target, target.getClass());
  }
}