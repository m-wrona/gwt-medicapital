package com.medicapital.server.database;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.apache.commons.dbcp.BasicDataSource;
import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;

abstract class AbstractDBTestCase extends DBTestCase {

	private static final String DB_DATA_PATH = System.getProperty("user.dir") + "/test_server/com/medicapital/server/database/data/";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "wrona";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/medicapital?characterEncoding=utf8";
	private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	private final List<Class<?>> annotatedClasses = new ArrayList<Class<?>>();
	private BasicDataSource basicDataSource;
	private AnnotationSessionFactoryBean sessionFactoryBean;
	private SessionFactory sessionFactory;

	private BasicDataSource getBasicDataSource() {
		if (basicDataSource == null) {
			basicDataSource = new BasicDataSource();
			basicDataSource.setDriverClassName(DB_DRIVER);
			basicDataSource.setUrl(DB_URL);
			basicDataSource.setUsername(DB_USER);
			basicDataSource.setPassword(DB_PASSWORD);
			basicDataSource.setMaxIdle(10);
			basicDataSource.setMaxActive(10);
			basicDataSource.setMaxWait(10);
		}
		return basicDataSource;
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		annotatedClasses.clear();
	}

	protected void addAnnotatedClass(Class<?> clazz) {
		annotatedClasses.add(clazz);
	}

	final protected SessionFactory getHibernateSessionFactory() {
		try {
			if (sessionFactoryBean == null) {
				sessionFactoryBean = new AnnotationSessionFactoryBean();
				sessionFactoryBean.setDataSource(getBasicDataSource());
				Properties hibernateProperties = new Properties();
				hibernateProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
				hibernateProperties.put("show_sql", "true");
				hibernateProperties.put("hibernate.connection.release", "after_transaction");
				hibernateProperties.put("hibernate.dbcp.maxWait", "10");
				hibernateProperties.put("hibernate.connection.autocommit", "false");
				hibernateProperties.put("cache.provider_class", "org.hibernate.cache.NoCacheProvider");
				sessionFactoryBean.setHibernateProperties(hibernateProperties);
				sessionFactoryBean.setAnnotatedClasses(annotatedClasses.toArray(new Class[annotatedClasses.size()]));
				sessionFactoryBean.afterPropertiesSet();
				sessionFactory = sessionFactoryBean.getObject();
			}
			return sessionFactory;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public AbstractDBTestCase() {
		loadDrivers();
		setDBParams();

	}

	@Override
	final protected DatabaseOperation getTearDownOperation() throws Exception {
		return DatabaseOperation.DELETE_ALL;
	}

	private void loadDrivers() {
		try {
			Class.forName(DB_DRIVER);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load DB drivers:  " + DB_DRIVER, e);
		}
	}

	private void setDBParams() {
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, DB_DRIVER);
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, DB_URL);
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, DB_USER);
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, DB_PASSWORD);
	}

	@Override
	final protected IDataSet getDataSet() throws Exception {
		String dataFileName = getClass().getSimpleName() + ".xml";
		return new FlatXmlDataSetBuilder().build(new FileInputStream(DB_DATA_PATH + dataFileName));
	}

}
