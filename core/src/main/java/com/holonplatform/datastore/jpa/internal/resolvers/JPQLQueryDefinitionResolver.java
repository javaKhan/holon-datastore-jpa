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
package com.holonplatform.datastore.jpa.internal.resolvers;

import java.util.Optional;

import com.holonplatform.core.Expression.InvalidExpressionException;
import com.holonplatform.datastore.jpa.jpql.context.JPQLContextExpressionResolver;
import com.holonplatform.datastore.jpa.jpql.context.JPQLResolutionContext;
import com.holonplatform.datastore.jpa.jpql.expression.JPQLExpression;
import com.holonplatform.datastore.jpa.jpql.expression.JPQLQueryDefinition;

/**
 * {@link JPQLQueryDefinition} resolver.
 *
 * @since 5.1.0
 */
public enum JPQLQueryDefinitionResolver implements JPQLContextExpressionResolver<JPQLQueryDefinition, JPQLExpression> {

	/**
	 * Singleton instance
	 */
	INSTANCE;

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.core.ExpressionResolver#getExpressionType()
	 */
	@Override
	public Class<? extends JPQLQueryDefinition> getExpressionType() {
		return JPQLQueryDefinition.class;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.core.ExpressionResolver#getResolvedType()
	 */
	@Override
	public Class<? extends JPQLExpression> getResolvedType() {
		return JPQLExpression.class;
	}

	/*
	 * (non-Javadoc)
	 * @see com.holonplatform.datastore.jpa.resolvers.JPQLContextExpressionResolver#resolve(com.holonplatform.core.
	 * Expression, com.holonplatform.datastore.jpa.context.JPQLResolutionContext)
	 */
	@Override
	public Optional<JPQLExpression> resolve(JPQLQueryDefinition expression, JPQLResolutionContext context)
			throws InvalidExpressionException {

		// validate
		expression.validate();

		final StringBuilder query = new StringBuilder();

		query.append("SELECT ");

		if (expression.isDistinct()) {
			query.append("DISTINCT ");
		}

		query.append(expression.getSelect());
		query.append(" FROM ");
		query.append(expression.getFrom());

		expression.getWhere().ifPresent(c -> {
			query.append(" WHERE ");
			query.append(c);
		});

		expression.getGroupBy().ifPresent(c -> {
			query.append(" GROUP BY ");
			query.append(c);
		});

		expression.getOrderBy().ifPresent(c -> {
			query.append(" ORDER BY ");
			query.append(c);
		});

		return Optional.of(JPQLExpression.create(query.toString()));
	}

}
