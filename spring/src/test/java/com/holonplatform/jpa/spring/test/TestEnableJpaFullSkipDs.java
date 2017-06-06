/*
 * Copyright 2000-2016 Holon TDCN.
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
package com.holonplatform.jpa.spring.test;

import static org.junit.Assert.assertTrue;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.holonplatform.jdbc.internal.BasicDataSource;
import com.holonplatform.jpa.spring.EnableJpa;
import com.holonplatform.jpa.spring.test.domain1.TestJpaDomain1;

@ContextConfiguration(classes = TestEnableJpaFullSkipDs.Config.class)
public class TestEnableJpaFullSkipDs extends AbstractEnableJpaTest {

	@PropertySource("test.properties")
	@EnableJpa(entityPackageClasses = TestJpaDomain1.class, dataSourceReference = "testds")
	@EnableTransactionManagement
	@Configuration
	protected static class Config {

		@Autowired
		private Environment env;

		@Bean("testds")
		public DataSource dataSource() {
			BasicDataSource ds = new BasicDataSource();
			ds.setUrl(env.getProperty("holon.datasource.url"));
			ds.setUsername(env.getProperty("holon.datasource.username"));
			ds.setPassword(env.getProperty("holon.datasource.password"));
			return ds;
		}

	}

	@Test
	public void testDataSourceSkip() {
		assertTrue(dataSource instanceof BasicDataSource);
	}

}