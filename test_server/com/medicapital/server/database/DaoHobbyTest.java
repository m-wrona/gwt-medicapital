package com.medicapital.server.database;

import java.util.Iterator;
import java.util.Set;
import org.junit.Test;
import com.medicapital.common.entities.Hobby;
import com.medicapital.server.database.hibernate.HibernateHobby;
import com.medicapital.server.database.hibernate.entities.HobbyEntity;

public class DaoHobbyTest extends AbstractDBTestCase {

	private HibernateHobby daoHobby;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		addAnnotatedClass(HobbyEntity.class);
		daoHobby = new HibernateHobby();
		daoHobby.setSessionFactory(getHibernateSessionFactory());
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		daoHobby.clean();
	}

	@Test
	public void testGetHobbiesCount() {
		assertEquals(3, daoHobby.getHobbiesCount());
	}

	@Test
	public void testGetHobbies() {
		final Set<Hobby> hobbies = daoHobby.getHobbies();
		assertNotNull("Hobbies weren't found in data base", hobbies);
		assertFalse("Hobbies weren't found in data base", hobbies.isEmpty());
		assertEquals(3, hobbies.size());

		final Iterator<Hobby> hobbyIt = hobbies.iterator();
		final Hobby hobby1 = hobbyIt.next();
		assertEquals(1, hobby1.getId());
		assertEquals("hobby1", hobby1.getName());
	}
}
