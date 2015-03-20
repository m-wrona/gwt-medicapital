package com.medicapital.server.database;

import java.util.List;

import org.junit.Test;

import com.medicapital.common.entities.User;
import com.medicapital.common.entities.User.Sex;
import com.medicapital.server.database.hibernate.HibernateUser;
import com.medicapital.server.database.hibernate.entities.CityEntity;
import com.medicapital.server.database.hibernate.entities.HobbyEntity;
import com.medicapital.server.database.hibernate.entities.LocalizationEntity;
import com.medicapital.server.database.hibernate.entities.RegionEntity;
import com.medicapital.server.database.hibernate.entities.UserEntity;

public class DaoUserTest extends AbstractDBTestCase {

	private HibernateUser daoUser;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		addAnnotatedClass(UserEntity.class);
		addAnnotatedClass(LocalizationEntity.class);
		addAnnotatedClass(CityEntity.class);
		addAnnotatedClass(RegionEntity.class);
		addAnnotatedClass(HobbyEntity.class);
		daoUser = new HibernateUser();
		daoUser.setSessionFactory(getHibernateSessionFactory());
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		daoUser.close();
		daoUser = null;
	}

	static User createTestUser() {
		final User user = new User();
		user.setLogin("testUser");
		user.setPassword("testPassword");
		user.setFirstName("firstName");
		user.setLastName("lastName");
		user.setEmail("testEmail@medicapital.pl");
		user.setBirthDate(null);
		user.setMobile("123456789");
		user.setPhoneNumber("004871123456");
		user.setSex(Sex.Male);
		user.setPersonalId("123-456-789");
		user.setActive(true);
		return user;
	}

	static void assertUsers(final User testUser, final User user) {
		assertTrue("Created user id unknown: " + testUser.getId(), testUser.getId() > 0);
		assertEquals(testUser.getLogin(), user.getLogin());
		// password is never given for safety reasons
		assertNotNull(testUser.getPassword());
		assertEquals(testUser.getFirstName(), user.getFirstName());
		assertEquals(testUser.getLastName(), user.getLastName());
		assertEquals(testUser.getEmail(), user.getEmail());
		assertEquals(testUser.getPhoneNumber(), user.getPhoneNumber());
		assertEquals(testUser.getMobile(), user.getMobile());
		assertEquals(true, user.isActive());
		assertEquals(testUser.getBirthDate(), user.getBirthDate());
		assertEquals(null, user.getLocalization());
		assertEquals(testUser.getSex(), user.getSex());
		assertEquals(testUser.getPersonalId(), user.getPersonalId());
	}

	@Test
	public void testCreateUser() {
		final User user = createTestUser();
		user.setLogin("newUser");
		final int userId = daoUser.create(user);
		assertTrue(userId > 0);
		final User createdUser = daoUser.get(userId);
		assertUsers(createdUser, user);
	}

	@Test
	public void testGetUsersCount() {
		assertEquals(6, daoUser.getCount());
	}

	@Test
	public void testDeleteUserByLogin() {
		daoUser.delete("deleteByLogin");
		assertNull(daoUser.get("deleteByLogin"));
	}

	@Test
	public void testDeleteUserById() {
		daoUser.delete(4);
		assertNull(daoUser.get(4));
	}

	@Test
	public void testGetUserByLogin() {
		final User user = daoUser.get("testUser");
		assertNotNull("User wan't found in data base", user);
		assertEquals(user.getLogin(), "testUser");
	}

	@Test
	public void testGetUserById() {
		final User user = daoUser.get(1);
		assertNotNull("User wan't found in data base", user);
		assertEquals(user.getLogin(), "testUser");
	}

	@Test
	public void testUpdateUser() {
		final User testUser = daoUser.get("updatedUser");
		testUser.setFirstName("firstName1");
		testUser.setLastName("lastName1");
		testUser.setEmail("testEmail1@medicapital.pl");
		testUser.setBirthDate(null);
		testUser.setMobile("1234567891");
		testUser.setPhoneNumber("0048711234561");
		testUser.setSex(Sex.Female);
		testUser.setPersonalId("123-456-7891");
		testUser.setActive(true);
		testUser.setPassword("newPass1");
		daoUser.update(testUser);

		final User user = daoUser.get(testUser.getLogin());
		assertNotNull("User wan't found in data base", user);
		assertUsers(user, testUser);
	}

	@Test
	public void testSearchUserByName() {
		User searchCriteria = new User();
		searchCriteria.setFirstName("john");
		List<User> users = daoUser.searchUsers(searchCriteria, 0, 10);
		assertNotNull(users);
		assertEquals(2, users.size());
		assertEquals(2, daoUser.searchUsersCount(searchCriteria));

		searchCriteria.setFirstName("firstName");
		users = daoUser.searchUsers(searchCriteria, 0, 10);
		assertEquals(4, daoUser.searchUsersCount(searchCriteria));
		assertNotNull(users);
		assertEquals(4, users.size());

		searchCriteria.setFirstName("noName");
		users = daoUser.searchUsers(searchCriteria, 0, 10);
		assertEquals(0, daoUser.searchUsersCount(searchCriteria));
		assertNull(users);
	}

	@Test
	public void testSearchUserByLastName() {
		User searchCriteria = new User();
		searchCriteria.setLastName("parker");
		List<User> users = daoUser.searchUsers(searchCriteria, 0, 10);
		assertEquals(1, daoUser.searchUsersCount(searchCriteria));
		assertNotNull(users);
		assertEquals(1, users.size());

		searchCriteria.setLastName("lastname");
		users = daoUser.searchUsers(searchCriteria, 0, 10);
		assertEquals(4, daoUser.searchUsersCount(searchCriteria));
		assertNotNull(users);
		assertEquals(4, users.size());

		searchCriteria.setLastName("noName");
		users = daoUser.searchUsers(searchCriteria, 0, 10);
		assertEquals(0, daoUser.searchUsersCount(searchCriteria));
		assertNull(users);
	}

	@Test
	public void testSearchUser() {
		User searchCriteria = new User();
		searchCriteria.setFirstName("john");
		searchCriteria.setLastName("parker");
		List<User> users = daoUser.searchUsers(searchCriteria, 0, 10);
		assertEquals(1, daoUser.searchUsersCount(searchCriteria));
		assertNotNull(users);
		assertEquals(1, users.size());

		searchCriteria.setFirstName("firstname");
		searchCriteria.setLastName("lastname");
		users = daoUser.searchUsers(searchCriteria, 0, 10);
		assertEquals(4, daoUser.searchUsersCount(searchCriteria));
		assertNotNull(users);
		assertEquals(4, users.size());

		searchCriteria.setFirstName("noname");
		searchCriteria.setLastName("noName");
		users = daoUser.searchUsers(searchCriteria, 0, 10);
		assertEquals(0, daoUser.searchUsersCount(searchCriteria));
		assertNull(users);
		
		searchCriteria.setFirstName("");
		searchCriteria.setLastName("");
		users = daoUser.searchUsers(searchCriteria, 0, 10);
		assertEquals(6, daoUser.searchUsersCount(searchCriteria));
		assertNotNull(users);
		assertEquals(6, users.size());
	}

}
