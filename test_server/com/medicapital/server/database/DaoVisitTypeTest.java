package com.medicapital.server.database;

import java.util.List;
import org.junit.Test;
import com.medicapital.common.entities.VisitType;
import com.medicapital.server.database.hibernate.HibernateVisitType;
import com.medicapital.server.database.hibernate.entities.CityEntity;
import com.medicapital.server.database.hibernate.entities.DoctorEntity;
import com.medicapital.server.database.hibernate.entities.HobbyEntity;
import com.medicapital.server.database.hibernate.entities.LocalizationEntity;
import com.medicapital.server.database.hibernate.entities.RegionEntity;
import com.medicapital.server.database.hibernate.entities.SpecializationEntity;
import com.medicapital.server.database.hibernate.entities.UserEntity;
import com.medicapital.server.database.hibernate.entities.VisitTypeEntity;

public class DaoVisitTypeTest extends AbstractDBTestCase {

	private HibernateVisitType daoVisitType;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		addAnnotatedClass(DoctorEntity.class);
		addAnnotatedClass(SpecializationEntity.class);
		addAnnotatedClass(UserEntity.class);
		addAnnotatedClass(LocalizationEntity.class);
		addAnnotatedClass(CityEntity.class);
		addAnnotatedClass(RegionEntity.class);
		addAnnotatedClass(HobbyEntity.class);
		addAnnotatedClass(VisitTypeEntity.class);
		daoVisitType = new HibernateVisitType();
		daoVisitType.setSessionFactory(getHibernateSessionFactory());
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		daoVisitType.close();
	}

	@Test
	public void testGetDoctorVisitTypes() {
		List<VisitType> visitTypes = daoVisitType.getDoctorVisitTypes(1);
		assertNotNull(visitTypes);
		assertEquals(2, visitTypes.size());
		VisitType visitType1 = visitTypes.get(0);
		assertEquals(1, visitType1.getId());
		assertEquals(1, visitType1.getDoctorId());
		assertEquals("visitType1", visitType1.getName());
		assertEquals("desc1", visitType1.getDescription());
		assertEquals(10, visitType1.getDuration());
		VisitType visitType2 = visitTypes.get(1);
		assertEquals(2, visitType2.getId());
		assertEquals(1, visitType2.getDoctorId());
		assertEquals("visitType2", visitType2.getName());
		assertEquals("desc2", visitType2.getDescription());
		assertEquals(15, visitType2.getDuration());
	}

	@Test
	public void testUpdateVisitType() {
		VisitType visitType = daoVisitType.get(3);
		assertEquals(3, visitType.getId());
		assertEquals(2, visitType.getDoctorId());
		assertEquals("visitType3", visitType.getName());
		assertEquals("desc3", visitType.getDescription());
		assertEquals(15, visitType.getDuration());
		visitType.setDuration(20);
		daoVisitType.update(visitType);
		VisitType updatedVisitType = daoVisitType.get(3);
		assertEquals(3, updatedVisitType.getId());
		assertEquals(2, updatedVisitType.getDoctorId());
		assertEquals("visitType3", updatedVisitType.getName());
		assertEquals("desc3", updatedVisitType.getDescription());
		assertEquals(20, updatedVisitType.getDuration());
	}

	@Test
	public void testDeleteVisitType() {
		assertNotNull(daoVisitType.get(4));
		daoVisitType.delete(4);
		assertNull(daoVisitType.get(4));
	}

	@Test
	public void testCreateVisitType() {
		VisitType visitType = new VisitType();
		visitType.setDoctorId(2);
		visitType.setName("newVisitType");
		visitType.setDuration(60);
		visitType.setDescription("newVisitTypeDesc");
		int createdVisitTypeId = daoVisitType.create(visitType);
		assertTrue("New VisitTypeId not known", createdVisitTypeId > 0);
		VisitType createdVisitType = daoVisitType.get(createdVisitTypeId);
		assertEquals(2, createdVisitType.getDoctorId());
		assertEquals("newVisitType", createdVisitType.getName());
		assertEquals("newVisitTypeDesc", createdVisitType.getDescription());
		assertEquals(60, createdVisitType.getDuration());
	}
}
