package com.medicapital.client.visit;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.*;
import org.junit.Test;

import com.medicapital.client.core.commands.DisplayCommandFactory;
import com.medicapital.client.core.commands.EditCommandFactory;
import com.medicapital.client.test.TestEventBus;
import com.medicapital.client.visit.EditVisitPresenter;
import com.medicapital.client.visit.EditVisitView;
import com.medicapital.common.dao.ServiceAccess;
import com.medicapital.common.entities.Doctor;
import com.medicapital.common.entities.Localization;
import com.medicapital.common.entities.PatientVisit;
import com.medicapital.common.entities.User;

public class EditVisitPresenterTest {

	@Test
	public void testDisplayVisit() {
		EditVisitView view = mock(EditVisitView.class);
		EditVisitPresenter visitPresenter = new EditVisitPresenter(view, new TestEventBus());

		PatientVisit visit = new PatientVisit();
		visit.setId(1);
		visit.setPatient(new User());
		Doctor doctor = new Doctor();
		doctor.setUser(new User());
		visit.setDoctor(doctor);
		visit.setLocalization(new Localization());
		visitPresenter.display(visit);
		verify(view).setVisitTitle(null);
		verify(view).setViewVisible(true);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testEditVisit() {
		EditVisitView view = mock(EditVisitView.class);
		EditVisitPresenter visitPresenter = new EditVisitPresenter(view, new TestEventBus());

		PatientVisit visit = new PatientVisit();
		visit.setId(1);
		visit.setPatient(new User());
		Doctor doctor = new Doctor();
		doctor.setUser(new User());
		visit.setDoctor(doctor);
		visit.setLocalization(new Localization());
		visitPresenter.display(visit);
		visitPresenter.setServiceAccess(mock(ServiceAccess.class));
		visitPresenter.setDisplayCommandFactory(mock(DisplayCommandFactory.class));
		visitPresenter.setEditCommandFactory(mock(EditCommandFactory.class));
		visitPresenter.update();
		verify(view).getVisitTitle();
	}
}
