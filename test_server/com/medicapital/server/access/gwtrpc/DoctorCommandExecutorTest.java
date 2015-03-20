package com.medicapital.server.access.gwtrpc;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import com.medicapital.common.commands.entity.SelectCommand;
import com.medicapital.common.commands.entity.SelectCommandResp;
import com.medicapital.common.commands.entity.SelectCountCommand;
import com.medicapital.common.commands.entity.SelectCountCommandResp;
import com.medicapital.common.entities.Doctor;
import com.medicapital.common.entities.User;
import com.medicapital.common.rpc.ServerRequest;
import com.medicapital.common.rpc.ServerResponse;
import com.medicapital.server.access.gwtrpc.DoctorCommandExecutor;
import com.medicapital.server.logic.DoctorFacade;
import com.medicapital.server.security.SecurityManager;
import com.medicapital.server.security.SessionFactory;

public class DoctorCommandExecutorTest {

	private DoctorFacade doctorFacade;
	private DoctorCommandExecutor doctorCommandExecutor;

	@Before
	public void init() {
		doctorFacade = mock(DoctorFacade.class);
		doctorCommandExecutor = new DoctorCommandExecutor();
		doctorCommandExecutor.setDoctorFacade(doctorFacade);
		SessionFactory sessionFactory = new SessionFactory();
		sessionFactory.setSupportLocalSession(true);
		doctorCommandExecutor.setSessionFactory(sessionFactory);
		doctorCommandExecutor.setSecurityManager(new SecurityManager());
	}

	@Test
	public void testGetDoctorByLogin() {
		Doctor mockDoctor = new Doctor();
		User mockUser = new User();
		mockUser.setLogin("doctor");
		mockDoctor.setUser(mockUser);
		when(doctorFacade.get("doctor")).thenReturn(mockDoctor);
		SelectCommand<Doctor> selectCommand = new SelectCommand<Doctor>(Doctor.class, "doctor");
		ServerResponse<Doctor> serverResponse = doctorCommandExecutor.execute(new ServerRequest<Doctor>(selectCommand));
		verify(doctorFacade).get("doctor");
		assertNotNull(serverResponse);
		assertNotNull(serverResponse.getResponse());
		assertEquals(SelectCommandResp.class, serverResponse.getResponse().getClass());
		SelectCommandResp<Doctor> userResp = (SelectCommandResp<Doctor>) serverResponse.getResponse();
		assertEquals(1, userResp.getCount());
		Collection<Doctor> users = userResp.getData();
		assertEquals(1, users.size());
		assertEquals("doctor", users.iterator().next().getUser().getLogin());
	}

	@Test
	public void testGetDoctorById() {
		Doctor mockDoctor = new Doctor();
		mockDoctor.setId(1);
		User mockUser = new User();
		mockUser.setLogin("doctor");
		mockDoctor.setUser(mockUser);
		when(doctorFacade.get(1)).thenReturn(mockDoctor);
		SelectCommand<Doctor> selectCommand = new SelectCommand<Doctor>(Doctor.class, 1);
		ServerResponse<Doctor> serverResponse = doctorCommandExecutor.execute(new ServerRequest<Doctor>(selectCommand));
		verify(doctorFacade).get(1);
		assertNotNull(serverResponse);
		assertNotNull(serverResponse.getResponse());
		assertEquals(SelectCommandResp.class, serverResponse.getResponse().getClass());
		SelectCommandResp<Doctor> userResp = (SelectCommandResp<Doctor>) serverResponse.getResponse();
		assertEquals(1, userResp.getCount());
		Collection<Doctor> users = userResp.getData();
		assertEquals(1, users.size());
		assertEquals(1, users.iterator().next().getId());
	}

}
