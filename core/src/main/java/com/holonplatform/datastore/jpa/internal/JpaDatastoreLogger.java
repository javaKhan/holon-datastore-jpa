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
package com.holonplatform.datastore.jpa.internal;

import com.holonplatform.core.internal.Logger;
import com.holonplatform.datastore.jpa.JpaDatastore;

/**
 * JPA module logger provider.
 *
 * @since 5.0.0
 */
public interface JpaDatastoreLogger {

	final static String NAME = JpaDatastore.class.getPackage().getName();

	/**
	 * Get a {@link Logger} bound to {@link #NAME}.
	 * @return Logger
	 */
	static Logger create() {
		return Logger.create(NAME);
	}

}
