package com.medicapital.client.core.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import org.junit.Test;
import com.medicapital.client.core.commands.CreateCommandFactory;
import com.medicapital.client.core.commands.DisplayCommandFactory;
import com.medicapital.client.core.entity.CreateEntityPresenter;
import com.medicapital.client.core.entity.CreateEntityView;
import com.medicapital.client.dao.ServiceResponse;
import com.medicapital.client.test.TestEventBus;
import com.medicapital.client.test.TestServiceAccess;
import com.medicapital.common.commands.entity.CreateCommandResp;
import com.medicapital.common.entities.User;

public class CreateEntityPresenterTest extends CreateEntityPresenter<User> {

	@SuppressWarnings("unchecked")
	public CreateEntityPresenterTest() {
		super(User.class, mock(CreateEntityView.class), new TestEventBus());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testCreateElement() {
		final TestEventBus eventBus = (TestEventBus) getEventBus();

		setDisplayCommandFactory(mock(DisplayCommandFactory.class));

		display(getEntityFromView());
		assertEquals(0, eventBus.getReceivedEvents().size());
		final CreateCommandResp<User> commandResp = new CreateCommandResp<User>(User.class, 1);
		final TestServiceAccess testServiceAccess = new TestServiceAccess(commandResp);
		setServiceAccess(testServiceAccess);
		setCreateCommandFactory(mock(CreateCommandFactory.class));
		create();
		assertEquals(1, eventBus.getReceivedEvents().size());
		final ServiceResponse<User> clientEvent = (ServiceResponse<User>) eventBus.getReceivedEvents().get(0);
		final CreateCommandResp<User> createCommandResp = (CreateCommandResp<User>) clientEvent.getCommandResp();
		assertNotNull(createCommandResp);
		assertTrue(createCommandResp.wasCreated());
	}

	@Override
	protected User getEntityFromView() {
		User user = new User();
		user.setId(5);
		return user;
	}

	@Override
	protected void displayEntityOnView(User entity) {
		// ignore

	}

	@Override
	protected void clearView() {
		// ignore

	}

}
