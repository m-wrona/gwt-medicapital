package com.medicapital.server.logic;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import org.junit.Before;
import org.junit.Test;
import com.medicapital.server.database.DaoVisitType;

public class VisitTypeFacadeTest {

	private VisitTypeFacade visitTypeFacade;
	private DaoVisitType daoVisitType;

	@Before
	public void init() {
		visitTypeFacade = new VisitTypeFacade();
		daoVisitType = mock(DaoVisitType.class);
		visitTypeFacade.setDaoVisitType(daoVisitType);
	}

	@Test
	public void testGetDoctorWorkHours() {
		visitTypeFacade.getDoctorVisitTypes(1);
		verify(daoVisitType).getDoctorVisitTypes(1);
	}
}
