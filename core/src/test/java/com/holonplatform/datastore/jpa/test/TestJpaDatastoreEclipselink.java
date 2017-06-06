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
package com.holonplatform.datastore.jpa.test;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.SharedEntityManagerCreator;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.holonplatform.core.datastore.Datastore;
import com.holonplatform.datastore.jpa.JpaDatastore;
import com.holonplatform.datastore.jpa.test.data.KeyIs;
import com.holonplatform.datastore.jpa.test.domain.TestJpaDomain;

@Rollback
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestJpaDatastoreEclipselink.Config.class)
@DirtiesContext
public class TestJpaDatastoreEclipselink extends AbstractJpaDatastoreTest {

	@Configuration
	@EnableTransactionManagement
	protected static class Config {

		@Bean
		public DataSource dataSource() {
			return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).setName("testjpa1elk")
					.addScript("test-db-schema.sql").addScript("test-db-data.sql").build();
		}

		@Bean
		public FactoryBean<EntityManagerFactory> entityManagerFactory() {
			LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
			emf.setDataSource(dataSource());

			EclipseLinkJpaVendorAdapter va = new EclipseLinkJpaVendorAdapter();
			va.setShowSql(true);

			emf.setJpaVendorAdapter(va);
			emf.setPackagesToScan(TestJpaDomain.class.getPackage().getName());
			emf.setPersistenceUnitName("test");
			emf.getJpaPropertyMap().put("eclipselink.weaving", "false");

			return emf;
		}

		@Bean
		public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
			JpaTransactionManager jtm = new JpaTransactionManager(emf);
			jtm.setDataSource(dataSource());
			return jtm;
		}

		@Bean
		public JpaDatastore datastore(EntityManagerFactory emf) throws Exception {
			return JpaDatastore.builder().entityManagerFactory(emf)
					// use spring shared entitymanager to join spring transactions
					.entityManagerInitializer(f -> SharedEntityManagerCreator.createSharedEntityManager(f))
					.autoFlush(true).traceEnabled(true).withExpressionResolver(KeyIs.RESOLVER).build();
		}

	}

	@Autowired
	private Datastore datastore;

	@Override
	protected Datastore getDatastore() {
		return datastore;
	}

	@Override
	public void testAvg() {
	}

}