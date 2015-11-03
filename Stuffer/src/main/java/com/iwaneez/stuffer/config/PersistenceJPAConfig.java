package com.iwaneez.stuffer.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySource({ "classpath:persistence/postgre.properties" }) // @PropertySource({ "classpath:persistence-postgre.properties" })
@ComponentScan({ "com.iwaneez.stuffer.persistence" })
//@ImportResource({ "classpath:hibernate4Config.xml" })
@EnableJpaRepositories("com.iwaneez.stuffer.persistence")
public class PersistenceJPAConfig {
	
    private static final String PROPERTY_NAME_DATABASE_DRIVER = "jdbc.driverClassName";
    private static final String PROPERTY_NAME_DATABASE_URL = "jdbc.url";
    private static final String PROPERTY_NAME_DATABASE_USERNAME = "jdbc.user";
    private static final String PROPERTY_NAME_DATABASE_PASSWORD = "jdbc.pass";

    private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
    private static final String PROPERTY_NAME_HIBERNATE_FORMAT_SQL = "hibernate.format_sql";
    private static final String PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";
    private static final String PROPERTY_NAME_HIBERNATE_GLOBALLY_QUOTED_IDENTIFIERS = "hibernate.globally_quoted_identifiers";
    private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
    
    private static final String PROPERTY_NAME_POPULATING_SCRIPT = "db.populating.script";
   	private static final String PROPERTY_NAME_POPULATING_ENABLED = "db.populating.initdb";
   	
    private static final String PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN = "com.iwaneez.stuffer.persistence";

    @Autowired
    private Environment env;

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf){
       JpaTransactionManager transactionManager = new JpaTransactionManager();
       transactionManager.setEntityManagerFactory(emf);// entityManagerFactory().getObject()
  
       return transactionManager;
    }
    
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
       LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
       em.setDataSource(dataSource());
       em.setPackagesToScan(new String[] { PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN });
  
       JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
       em.setJpaVendorAdapter(vendorAdapter);
       em.setJpaProperties(hibernateProperties());
  
       return em;
    }

    @Bean(name = "dataSource")
    public DataSource dataSource() {
    	DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty(PROPERTY_NAME_DATABASE_DRIVER));
        dataSource.setUrl(env.getProperty(PROPERTY_NAME_DATABASE_URL));
        dataSource.setUsername(env.getProperty(PROPERTY_NAME_DATABASE_USERNAME));
        dataSource.setPassword(env.getProperty(PROPERTY_NAME_DATABASE_PASSWORD));

        return dataSource;
    }

	@Bean
	public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {
		DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
		dataSourceInitializer.setDataSource(dataSource);
		
		ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
		databasePopulator.addScript(new ClassPathResource(env.getProperty(PROPERTY_NAME_POPULATING_SCRIPT)));
		
		dataSourceInitializer.setDatabasePopulator(databasePopulator);
		dataSourceInitializer.setEnabled(Boolean.parseBoolean(env.getProperty(PROPERTY_NAME_POPULATING_ENABLED)));
		
		return dataSourceInitializer;
	}

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
       return new PersistenceExceptionTranslationPostProcessor();
    }

    public Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO, env.getProperty(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO));
        hibernateProperties.setProperty(PROPERTY_NAME_HIBERNATE_DIALECT, env.getProperty(PROPERTY_NAME_HIBERNATE_DIALECT));

        hibernateProperties.setProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL, "true");
        hibernateProperties.setProperty(PROPERTY_NAME_HIBERNATE_FORMAT_SQL, "true");
        hibernateProperties.setProperty(PROPERTY_NAME_HIBERNATE_GLOBALLY_QUOTED_IDENTIFIERS, "true");

        return hibernateProperties;
    }
}
