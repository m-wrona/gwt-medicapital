package com.medicapital.server.access.gwtrpc;

import static org.mockito.Mockito.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.HashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import com.medicapital.common.commands.entity.SelectCommand;
import com.medicapital.common.commands.entity.SelectCommandResp;
import com.medicapital.common.commands.entity.SelectCountCommand;
import com.medicapital.common.commands.entity.SelectCountCommandResp;
import com.medicapital.common.entities.Hobby;
import com.medicapital.common.rpc.ServerRequest;
import com.medicapital.common.rpc.ServerResponse;
import com.medicapital.server.access.gwtrpc.HobbyCommandExecutor;
import com.medicapital.server.logic.GenericDataFacade;
import com.medicapital.server.security.SecurityManager;
import com.medicapital.server.security.SessionFactory;

public class HobbyCommandExecutorTest {

	private GenericDataFacade genericDataFacade;
	private HobbyCommandExecutor hobbyCommandExecutor;

	@Before
	public void init() {
		genericDataFacade = mock(GenericDataFacade.class);
		hobbyCommandExecutor = new HobbyCommandExecutor();
		hobbyCommandExecutor.setGenericDataFacade(genericDataFacade);
		SessionFactory sessionFactory = new SessionFactory();
		sessionFactory.setSupportLocalSession(true);
		hobbyCommandExecutor.setSessionFactory(sessionFactory);
		hobbyCommandExecutor.setSecurityManager(new SecurityManager());
	}

	@Test
	public void testGetHobbies() {
		Set<Hobby> hobbies = new HashSet<Hobby>();
		hobbies.add(new Hobby());
		when(genericDataFacade.getHobbies()).thenReturn(hobbies);
		SelectCommand<Hobby> selectCommand = new SelectCommand<Hobby>(Hobby.class);
		ServerResponse<Hobby> serverResponse = hobbyCommandExecutor.execute(new ServerRequest<Hobby>(selectCommand));
		assertNotNull(serverResponse);
		assertNotNull(serverResponse.getResponse());
		assertEquals(SelectCommandResp.class, serverResponse.getResponse().getClass());
		SelectCommandResp<Hobby> hobbyResp = (SelectCommandResp<Hobby>) serverResponse.getResponse();
		assertEquals(1, hobbyResp.getCount());
	}

	@Test
	public void testGetHobbiesCount() {
		when(genericDataFacade.getHobbiesCount()).thenReturn(3);
		SelectCountCommand<Hobby> selectCommand = new SelectCountCommand<Hobby>(Hobby.class);
		ServerResponse<Hobby> serverResponse = hobbyCommandExecutor.execute(new ServerRequest<Hobby>(selectCommand));
		assertNotNull(serverResponse);
		assertNotNull(serverResponse.getResponse());
		assertEquals(SelectCountCommandResp.class, serverResponse.getResponse().getClass());
		SelectCountCommandResp<Hobby> hobbyResp = (SelectCountCommandResp<Hobby>) serverResponse.getResponse();
		assertEquals(3, hobbyResp.getCount());
	}
}
