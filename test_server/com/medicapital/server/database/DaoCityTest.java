package com.medicapital.server.database;

import java.util.Iterator;
import java.util.Set;
import org.junit.Test;
import com.medicapital.common.entities.City;
import com.medicapital.server.database.hibernate.HibernateCity;
import com.medicapital.server.database.hibernate.entities.CityEntity;
import com.medicapital.server.database.hibernate.entities.RegionEntity;

public class DaoCityTest extends AbstractDBTestCase {

	private HibernateCity daoCity;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		addAnnotatedClass(CityEntity.class);
		addAnnotatedClass(RegionEntity.class);
		daoCity = new HibernateCity();
		daoCity.setSessionFactory(getHibernateSessionFactory());
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		daoCity.close();
	}

	@Test
	public void testGetCityCount() {
		assertEquals(3, daoCity.getCitiesCount());
	}

	@Test
	public void testGetCities() {
		final Set<City> cities = daoCity.getCities();
		assertNotNull("Cities weren't found in data base", cities);
		assertFalse("Cities weren't found in data base", cities.isEmpty());
		assertEquals(3, cities.size());

		final Iterator<City> cityIt = cities.iterator();
		final City wroclaw = cityIt.next();
		assertEquals(1, wroclaw.getId());
		assertEquals("testCity1", wroclaw.getName());
		assertEquals(1, wroclaw.getRegionId());
		assertEquals("testRegion1", wroclaw.getRegionName());
	}

}
