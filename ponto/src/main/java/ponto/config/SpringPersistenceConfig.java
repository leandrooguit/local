package ponto.config;

import java.beans.PropertyVetoException;
import java.util.Properties;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jersey.JerseyAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@ComponentScan(basePackages = { "ponto.model" })
@Configuration
@EnableTransactionManagement
// @PropertySource("classpath:persistence-${env.target}.properties")
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class,
		JerseyAutoConfiguration.class })
@EnableCaching
public class SpringPersistenceConfig {
	
	@Autowired
	private Environment env;

	@Bean(destroyMethod = "close")
	public ComboPooledDataSource dataSource() {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		try {
			dataSource.setDriverClass("org.hsqldb.jdbc.JDBCDriver");
			dataSource.setJdbcUrl("jdbc:hsqldb:hsql://localhost/");
			dataSource.setUser("SA");
			dataSource.setPassword("");
			// dataSource.setDriverClass(env.getProperty("driverClass"));
			// dataSource.setJdbcUrl(env.getProperty("url"));
			// dataSource.setUser(env.getProperty("usuario"));
			// dataSource.setPassword(env.getProperty("senha"));
			dataSource.setAcquireIncrement(10);
			dataSource.setInitialPoolSize(300);
			dataSource.setMinPoolSize(300);
			dataSource.setMaxPoolSize(5000);
			dataSource.setMaxStatements(5000);
			dataSource.setIdleConnectionTestPeriod(3000);
			dataSource.setAutoCommitOnClose(true);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		return dataSource;
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		Properties hibernateProperties = new Properties();
		hibernateProperties.put("hibernate.hbm2ddl.auto", "create-drop");
		hibernateProperties.put("hibernate.dialect",
				"org.hibernate.dialect.HSQLDialect");
		hibernateProperties.put("hibernate.show_sql", "true");
		hibernateProperties
				.put("jadira.usertype.autoRegisterUserTypes", "true");
		hibernateProperties.put("jadira.usertype.databaseZone",
				"America/Sao_Paulo");
		hibernateProperties
				.put("jadira.usertype.javaZone", "America/Sao_Paulo");
		// hibernateProperties.put("hibernate.hbm2ddl.auto",
		// env.getProperty("hbm2ddl.auto"));
		// hibernateProperties
		// .put("hibernate.dialect", env.getProperty("dialect"));
		// hibernateProperties.put("hibernate.show_sql",
		// env.getProperty("show_sql"));
		sessionFactory.setHibernateProperties(hibernateProperties);
		sessionFactory.setPackagesToScan("ponto.model.domain");
		return sessionFactory;
	}

	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(
			SessionFactory sessionFactory) {
		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(sessionFactory);
		return txManager;
	}

	@Bean
	public CacheManager cacheManager() {
		return new EhCacheCacheManager(ehCacheCacheManager().getObject());
	}

	@Bean
	public EhCacheManagerFactoryBean ehCacheCacheManager() {
		EhCacheManagerFactoryBean cmfb = new EhCacheManagerFactoryBean();
		cmfb.setConfigLocation(new ClassPathResource("ehCache.xml"));
		cmfb.setShared(true);
		return cmfb;
	}

}
