package com.medicapital.client.visit;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import java.util.Date;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.medicapital.client.core.commands.CreateCommandFactory;
import com.medicapital.client.core.commands.DisplayCommandFactory;
import com.medicapital.client.lang.Lang;
import com.medicapital.client.lang.TestLang;
import com.medicapital.client.test.TestEventBus;
import com.medicapital.client.test.TestServiceAccess;
import com.medicapital.client.visit.CreateVisitPresenter;
import com.medicapital.client.visit.CreateVisitPresenterView;
import com.medicapital.common.commands.entity.DeleteCommand;
import com.medicapital.common.commands.entity.DeleteCommandResp;
import com.medicapital.common.commands.entity.SelectCommandResp;
import com.medicapital.common.commands.visit.LockVisitCommand;
import com.medicapital.common.commands.visit.BookVisitCommandResp;
import com.medicapital.common.dao.ServiceAccess;
import com.medicapital.common.entities.Doctor;
import com.medicapital.common.entities.Localization;
import com.medicapital.common.entities.PatientVisit;
import com.medicapital.common.entities.User;
import com.medicapital.common.entities.VisitType;

public class CreateVisitPresenterTest {

	@Before
	public void init() {
		TestLang.init();
	}
	
	@After
	public void clean() {
		TestLang.clear();
	}
	
	@Test
	public void testDisplayVisit() {
		CreateVisitPresenterView view = mock(CreateVisitPresenterView.class);
		CreateVisitPresenter visitPresenter = new CreateVisitPresenter(view, new TestEventBus());

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
	public void testCreateVisit() {
		CreateVisitPresenterView view = mock(CreateVisitPresenterView.class);
		CreateVisitPresenter visitPresenter = new CreateVisitPresenter(view, new TestEventBus());

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
		visitPresenter.setCreateCommandFactory(mock(CreateCommandFactory.class));
		visitPresenter.create();
		verify(view).getVisitTitle();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testInitPresenterData() {
		CreateVisitPresenterView view = mock(CreateVisitPresenterView.class);
		TestEventBus eventBus = new TestEventBus();
		TestServiceAccess serviceAccess = new TestServiceAccess();
		CreateVisitPresenter visitPresenter = spy(new CreateVisitPresenter(view, eventBus));
		visitPresenter.setServiceAccess(serviceAccess);

		SelectCommandResp<VisitType> selectVisitTypeResp = new SelectCommandResp<VisitType>(VisitType.class);
		VisitType type1 = new VisitType();
		type1.setId(1);
		type1.setName("type1");
		type1.setDuration(15);
		selectVisitTypeResp.add(type1);
		VisitType type2 = new VisitType();
		type2.setId(2);
		type2.setName("type2");
		type2.setDuration(30);
		selectVisitTypeResp.add(type2);
		serviceAccess.addResponse(selectVisitTypeResp);

		PatientVisit bookedVisit = new PatientVisit();
		bookedVisit.setId(1);
		bookedVisit.setPatient(new User());
		Doctor doctor = new Doctor();
		doctor.setUser(new User());
		bookedVisit.setDoctor(doctor);
		bookedVisit.setLocalization(new Localization());
		BookVisitCommandResp bookVisitResp = new BookVisitCommandResp(bookedVisit);
		serviceAccess.addResponse(bookVisitResp);
		Date startTime = new Date();

		visitPresenter.init(3, 2, startTime);
		assertEquals(2, serviceAccess.getReceived().size());
		assertEquals(VisitType.class.getName(), serviceAccess.getReceived().get(0).getEntityClassName());
		verify(view).setVisitTypes(anySet());
		verify(visitPresenter).lockVisitTypeTerm(type1);
		verify(view).setBeginDate(startTime);
		verify(view).setDurationTime(15);
		assertEquals(PatientVisit.class.getName(), serviceAccess.getReceived().get(1).getEntityClassName());
		LockVisitCommand bookVisitCommand = (LockVisitCommand) serviceAccess.getReceived().get(1);
		assertEquals(3, bookVisitCommand.getDoctorId());
		assertEquals(2, bookVisitCommand.getPatientId());
		assertEquals(startTime, bookVisitCommand.getVisitStartTime());
		assertNotNull(bookVisitCommand.getVisitEndTime());
		assertEquals(type1.getName(), bookVisitCommand.getVisitTitle());
		verify(visitPresenter).display(bookedVisit);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testInitPresenterDataSecondTime() {
		CreateVisitPresenterView view = mock(CreateVisitPresenterView.class);
		TestEventBus eventBus = new TestEventBus();
		TestServiceAccess serviceAccess = new TestServiceAccess();
		CreateVisitPresenter visitPresenter = spy(new CreateVisitPresenter(view, eventBus));
		visitPresenter.setServiceAccess(serviceAccess);

		SelectCommandResp<VisitType> selectVisitTypeResp = new SelectCommandResp<VisitType>(VisitType.class);
		VisitType type1 = new VisitType();
		type1.setId(1);
		type1.setName("type1");
		type1.setDuration(15);
		selectVisitTypeResp.add(type1);
		VisitType type2 = new VisitType();
		type2.setId(2);
		type2.setName("type2");
		type2.setDuration(30);
		selectVisitTypeResp.add(type2);
		serviceAccess.addResponse(selectVisitTypeResp);

		PatientVisit bookedVisit = new PatientVisit();
		bookedVisit.setId(1);
		bookedVisit.setPatient(new User());
		Doctor doctor = new Doctor();
		doctor.setUser(new User());
		bookedVisit.setDoctor(doctor);
		bookedVisit.setLocalization(new Localization());
		BookVisitCommandResp bookVisitResp = new BookVisitCommandResp(bookedVisit);
		serviceAccess.addResponse(bookVisitResp);
		Date startTime = new Date();

		visitPresenter.init(3, 2, startTime);
		visitPresenter.init(3, 2, startTime);
		assertEquals(3, serviceAccess.getReceived().size());
		assertEquals(VisitType.class.getName(), serviceAccess.getReceived().get(0).getEntityClassName());
		verify(view).setVisitTypes(anySet());
		verify(visitPresenter, times(2)).lockVisitTypeTerm(type1);
		verify(view, times(2)).setBeginDate(startTime);
		verify(view, times(2)).setDurationTime(15);
		assertEquals(PatientVisit.class.getName(), serviceAccess.getReceived().get(1).getEntityClassName());
		assertEquals(PatientVisit.class.getName(), serviceAccess.getReceived().get(2).getEntityClassName());
		LockVisitCommand bookVisitCommand = (LockVisitCommand) serviceAccess.getReceived().get(2);
		assertEquals(3, bookVisitCommand.getDoctorId());
		assertEquals(2, bookVisitCommand.getPatientId());
		assertEquals(startTime, bookVisitCommand.getVisitStartTime());
		assertNotNull(bookVisitCommand.getVisitEndTime());
		verify(visitPresenter, times(2)).display(bookedVisit);
	}

	@Test
	public void testBookingVisitTerm() {
		CreateVisitPresenterView view = mock(CreateVisitPresenterView.class);
		TestEventBus eventBus = new TestEventBus();
		TestServiceAccess serviceAccess = new TestServiceAccess();
		CreateVisitPresenter visitPresenter = spy(new CreateVisitPresenter(view, eventBus));
		visitPresenter.setServiceAccess(serviceAccess);

		SelectCommandResp<VisitType> selectVisitTypeResp = new SelectCommandResp<VisitType>(VisitType.class);
		VisitType type1 = new VisitType();
		type1.setId(1);
		type1.setName("type1");
		type1.setDuration(15);
		selectVisitTypeResp.add(type1);
		VisitType type2 = new VisitType();
		type2.setId(2);
		type2.setName("type2");
		type2.setDuration(30);
		selectVisitTypeResp.add(type2);
		serviceAccess.addResponse(selectVisitTypeResp);

		PatientVisit bookedVisit = new PatientVisit();
		bookedVisit.setId(1);
		bookedVisit.setPatient(new User());
		Doctor doctor = new Doctor();
		doctor.setUser(new User());
		bookedVisit.setDoctor(doctor);
		bookedVisit.setLocalization(new Localization());
		BookVisitCommandResp bookVisitResp = new BookVisitCommandResp(bookedVisit);
		serviceAccess.addResponse(bookVisitResp);
		Date startTime = new Date();

		visitPresenter.init(3, 2, startTime);
		visitPresenter.lockVisitTypeTerm(type2);
		verify(view, times(2)).setBeginDate(startTime);
		verify(view).setDurationTime(30);

		assertEquals(3, serviceAccess.getReceived().size());
		assertEquals(VisitType.class.getName(), serviceAccess.getReceived().get(0).getEntityClassName());
		assertEquals(PatientVisit.class.getName(), serviceAccess.getReceived().get(1).getEntityClassName());
		assertEquals(PatientVisit.class.getName(), serviceAccess.getReceived().get(2).getEntityClassName());
		LockVisitCommand bookVisitCommand = (LockVisitCommand) serviceAccess.getReceived().get(2);
		assertEquals(3, bookVisitCommand.getDoctorId());
		assertEquals(2, bookVisitCommand.getPatientId());
		assertEquals(startTime, bookVisitCommand.getVisitStartTime());
		assertEquals(type2.getName(), bookVisitCommand.getVisitTitle());
		assertNotNull(bookVisitCommand.getVisitEndTime());
		verify(visitPresenter, times(2)).display(bookedVisit);
	}

	@Test()
	public void testInitPresenterDataNoVisitTypes() {
		CreateVisitPresenterView view = mock(CreateVisitPresenterView.class);
		TestEventBus eventBus = new TestEventBus();
		TestServiceAccess serviceAccess = new TestServiceAccess();
		CreateVisitPresenter visitPresenter = spy(new CreateVisitPresenter(view, eventBus));
		visitPresenter.setServiceAccess(serviceAccess);

		serviceAccess.addResponse(new SelectCommandResp<VisitType>(VisitType.class));

		PatientVisit bookedVisit = new PatientVisit();
		bookedVisit.setId(1);
		bookedVisit.setPatient(new User());
		Doctor doctor = new Doctor();
		doctor.setUser(new User());
		bookedVisit.setDoctor(doctor);
		bookedVisit.setLocalization(new Localization());
		BookVisitCommandResp bookVisitResp = new BookVisitCommandResp(bookedVisit);
		serviceAccess.addResponse(bookVisitResp);
		Date startTime = new Date();

		visitPresenter.init(3, 2, startTime);
		assertEquals(2, serviceAccess.getReceived().size());
		assertEquals(VisitType.class.getName(), serviceAccess.getReceived().get(0).getEntityClassName());
		assertEquals(PatientVisit.class.getName(), serviceAccess.getReceived().get(1).getEntityClassName());
		LockVisitCommand bookVisitCommand = (LockVisitCommand) serviceAccess.getReceived().get(1);
		assertEquals(3, bookVisitCommand.getDoctorId());
		assertEquals(2, bookVisitCommand.getPatientId());
		assertEquals(Lang.generic().patientVisit(), bookVisitCommand.getVisitTitle());
		assertNotNull(bookVisitCommand.getVisitEndTime());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testCancelBookedVisit() {
		CreateVisitPresenterView view = mock(CreateVisitPresenterView.class);
		TestEventBus eventBus = new TestEventBus();
		TestServiceAccess serviceAccess = new TestServiceAccess();
		CreateVisitPresenter visitPresenter = spy(new CreateVisitPresenter(view, eventBus));
		visitPresenter.setServiceAccess(serviceAccess);

		SelectCommandResp<VisitType> selectVisitTypeResp = new SelectCommandResp<VisitType>(VisitType.class);
		VisitType type1 = new VisitType();
		type1.setId(1);
		type1.setName("type1");
		type1.setDuration(15);
		selectVisitTypeResp.add(type1);
		VisitType type2 = new VisitType();
		type2.setId(2);
		type2.setName("type2");
		type2.setDuration(30);
		selectVisitTypeResp.add(type2);
		serviceAccess.addResponse(selectVisitTypeResp);

		PatientVisit bookedVisit = new PatientVisit();
		bookedVisit.setId(8);
		bookedVisit.setPatient(new User());
		Doctor doctor = new Doctor();
		doctor.setUser(new User());
		bookedVisit.setDoctor(doctor);
		bookedVisit.setLocalization(new Localization());
		BookVisitCommandResp bookVisitResp = new BookVisitCommandResp(bookedVisit);
		serviceAccess.addResponse(bookVisitResp);
		Date startTime = new Date();

		visitPresenter.init(3, 2, startTime);
		DeleteCommandResp<PatientVisit> deleteCommandResp = new DeleteCommandResp<PatientVisit>(PatientVisit.class, 1);
		serviceAccess.addResponse(deleteCommandResp);
		visitPresenter.cancelBookVisit();
		assertEquals(3, serviceAccess.getReceived().size());
		assertEquals(VisitType.class.getName(), serviceAccess.getReceived().get(0).getEntityClassName());
		assertEquals(PatientVisit.class.getName(), serviceAccess.getReceived().get(1).getEntityClassName());
		assertEquals(PatientVisit.class.getName(), serviceAccess.getReceived().get(2).getEntityClassName());
		DeleteCommand<PatientVisit> deleteCommand = (DeleteCommand<PatientVisit>) serviceAccess.getReceived().get(2);

		assertEquals(1, deleteCommand.getElementIds().size());
		assertEquals(bookedVisit.getId(), deleteCommand.getElementIds().iterator().next().intValue());
		verify(view).setViewVisible(false);
	}
}
