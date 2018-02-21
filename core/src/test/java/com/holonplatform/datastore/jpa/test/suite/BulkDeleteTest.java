/*
 * Copyright 2016-2018 Axioma srl.
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
package com.holonplatform.datastore.jpa.test.suite;

import static com.holonplatform.datastore.jpa.test.suite.AbstractJpaDatastoreTestSuite.JPA_TARGET;
import static com.holonplatform.datastore.jpa.test.model.TestDataModel.KEY;
import static com.holonplatform.datastore.jpa.test.suite.AbstractJpaDatastoreTestSuite.PROPERTIES;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import com.holonplatform.core.datastore.Datastore.OperationResult;

public class BulkDeleteTest extends AbstractJpaDatastoreSuiteTest {

	@Test
	public void testBulkDelete() {
		inTransaction(() -> {

			OperationResult result = getDatastore().bulkDelete(JPA_TARGET).filter(KEY.eq(1L)).execute();
			assertEquals(1, result.getAffectedCount());

			assertFalse(getDatastore().query().target(JPA_TARGET).filter(KEY.eq(1L)).findOne(PROPERTIES).isPresent());
		});
	}

	@Test
	public void testBulkDeleteMulti() {
		inTransaction(() -> {

			OperationResult result = getDatastore().bulkDelete(JPA_TARGET).filter(KEY.loe(2L)).execute();
			assertEquals(2, result.getAffectedCount());

			assertEquals(0, getDatastore().query().target(JPA_TARGET).count());
		});
	}
	
	@Test
	public void testBulkDeleteAll() {
		inTransaction(() -> {

			OperationResult result = getDatastore().bulkDelete(JPA_TARGET).execute();
			assertEquals(2, result.getAffectedCount());

			assertEquals(0, getDatastore().query().target(JPA_TARGET).count());
		});
	}

}
