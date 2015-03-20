package com.medicapital.server.access.gwtrpc;

import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;
import com.medicapital.common.commands.entity.SelectCommand;
import com.medicapital.common.entities.VisitType;
import com.medicapital.common.rpc.ServerRequest;
import com.medicapital.server.logic.VisitTypeFacade;
import com.medicapital.server.security.SecurityManager;
import com.medicapital.server.security.SessionFactory;

public class VisitTypeCommandExecutorTest {

	private VisitTypeFacade visitTypeFacade;
	private VisitTypeCommandExecutor visitTypeCommandExecutor;

	@Before
	public void init() {
		visitTypeFacade = mock(VisitTypeFacade.class);
		visitTypeCommandExecutor = new VisitTypeCommandExecutor();
		visitTypeCommandExecutor.setVisitTypeFacade(visitTypeFacade);
		SessionFactory sessionFactory = new SessionFactory();
		sessionFactory.setSupportLocalSession(true);
		visitTypeCommandExecutor.setSessionFactory(sessionFactory);
		visitTypeCommandExecutor.setSecurityManager(new SecurityManager());
	}

	@Test
	public void testHandleSelectDoctorVisitTypes() {
		SelectCommand<VisitType> selectCommand = new SelectCommand<VisitType>(VisitType.class);
		selectCommand.setUserId(1);
		visitTypeCommandExecutor.execute(new ServerRequest<VisitType>(selectCommand));
		verify(visitTypeFacade).getDoctorVisitTypes(1);
	}
}
