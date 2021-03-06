/*
 * Copyright 2016-2017 Axioma srl.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.holonplatform.datastore.jpa.jpql.expression;

import java.util.Map;

import com.holonplatform.core.Expression;
import com.holonplatform.datastore.jpa.internal.jpql.expression.DefaultJPQLStatement;

/**
 * JPQL statement expression, with statement parameters support.
 *
 * @since 5.1.0
 */
public interface JPQLStatement extends Expression {

	/**
	 * Get the statement JPQL.
	 * @return the statement JPQL (not null)
	 */
	String getJPQL();

	/**
	 * Get the optional statement named parameters.
	 * @return the statement parameters name-value map, empty if none
	 */
	Map<String, JPQLParameter<?>> getParameters();

	/**
	 * Create a new {@link JPQLStatement}.
	 * @param jpql JPQL statement (not null)
	 * @param parameters Optional statement parameters
	 * @return A new {@link JPQLStatement}
	 */
	static JPQLStatement create(String jpql, Map<String, JPQLParameter<?>> parameters) {
		return new DefaultJPQLStatement(jpql, parameters);
	}

}
