package com.medicapital.server.logic;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import org.junit.Before;
import org.junit.Test;
import com.medicapital.common.entities.Doctor;
import com.medicapital.server.database.DaoDoctor;

public class DoctorFacadeTest {

	private DaoDoctor daoDoctor;
	private DoctorFacade doctorFacade;

	@Before
	public void init() {
		daoDoctor = mock(DaoDoctor.class);
		doctorFacade = new DoctorFacade();
		doctorFacade.setDaoDoctor(daoDoctor);
	}

	@Test
	public void testGetDoctorByLogin() {
		doctorFacade.get("testDoctor");
		verify(daoDoctor).get("testDoctor");
	}

	@Test
	public void testGetProfilePhoto() {
		doctorFacade.getDoctorProfilePhoto(1);
		verify(daoDoctor).getProfilePhoto(1);
	}

	@Test
	public void testGetGalleryPhoto() {
		doctorFacade.getDoctorGalleryPhoto(1, 2);
		verify(daoDoctor).getGalleryPhoto(1, 2);
	}
}
