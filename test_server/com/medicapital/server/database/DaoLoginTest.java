package com.medicapital.server.database;

import java.util.Date;
import org.junit.Test;
import com.medicapital.common.entities.LoginData;
import com.medicapital.common.entities.UserRole;
import com.medicapital.server.database.hibernate.HibernateLogin;
import com.medicapital.server.database.hibernate.entities.CityEntity;
import com.medicapital.server.database.hibernate.entities.HobbyEntity;
import com.medicapital.server.database.hibernate.entities.LocalizationEntity;
import com.medicapital.server.database.hibernate.entities.RegionEntity;
import com.medicapital.server.database.hibernate.entities.UserEntity;

public class DaoLoginTest extends AbstractDBTestCase {

	private HibernateLogin daoLogin;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		addAnnotatedClass(UserEntity.class);
		addAnnotatedClass(LocalizationEntity.class);
		addAnnotatedClass(CityEntity.class);
		addAnnotatedClass(RegionEntity.class);
		addAnnotatedClass(HobbyEntity.class);
		daoLogin = new HibernateLogin();
		daoLogin.setSessionFactory(getHibernateSessionFactory());
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		daoLogin.close();
	}

	@Test
	public void testWrongLoginData() {
		assertNull("User was logged with wrong password", daoLogin.loggin("wrongLogin", "wrongPass".toCharArray()));
	}

	@Test
	public void testLoginUser() {
		final LoginData loginData = daoLogin.loggin("testUser", "testPassword".toCharArray());
		assertNotNull("User was not logged with good login data", loginData);
		assertEquals("testUser", loginData.getLogin());
		assertEquals(2, loginData.getId());
		assertNull(loginData.getLastLoginDate());
		assertEquals(UserRole.User, loginData.getRole());
	}

	@Test
	public void testChangePassword() {
		assertTrue(daoLogin.changePassword("testUser", "testUser@medicapital.com", "newPassword".toCharArray()));
	}

	@Test
	public void testChangePasswordWrongEmail() {
		assertFalse(daoLogin.changePassword("testUser", "wrongEmail@medicapital.pl", "newPassword".toCharArray()));
	}

	@Test
	public void testLoginDoctor() {
		final LoginData loginData = daoLogin.loggin("testDoctor", "testPassword".toCharArray());
		assertNotNull("Doctor was not logged with good login data", loginData);
		assertEquals("testDoctor", loginData.getLogin());
		assertEquals(1, loginData.getId());
		assertEquals(UserRole.Doctor, loginData.getRole());
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testUpdateLastLoginDate() {
		final Date newLoginDate = new Date();
		daoLogin.setLastLoginDate("testUser", newLoginDate);
		final LoginData loginData = daoLogin.loggin("testUser", "testPassword".toCharArray());
		assertNotNull("User was not logged with good login data", loginData);
		assertEquals("Login date was not updated", newLoginDate.getDay(), loginData.getLastLoginDate().getDay());
		assertEquals("Login date was not updated", newLoginDate.getHours(), loginData.getLastLoginDate().getHours());
		assertEquals("Login date was not updated", newLoginDate.getMinutes(), loginData.getLastLoginDate().getMinutes());
		assertEquals("Login date was not updated", newLoginDate.getSeconds(), loginData.getLastLoginDate().getSeconds());
	}
}
