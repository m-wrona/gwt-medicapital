package com.medicapital.client.core.entity;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.util.HashSet;
import java.util.Set;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import org.junit.Test;
import com.medicapital.client.core.commands.DisplayCommandFactory;
import com.medicapital.client.core.entity.EntityPresenter;
import com.medicapital.client.core.entity.EntityView;
import com.medicapital.client.dao.ServiceResponse;
import com.medicapital.client.test.TestServiceAccess;
import com.medicapital.common.commands.entity.DeleteCommandResp;
import com.medicapital.common.commands.entity.SelectCommandResp;
import com.medicapital.common.commands.entity.UpdateCommandResp;
import com.medicapital.common.dao.ServiceAccess;
import com.medicapital.common.entities.User;

public class EntityPresenterTest extends EntityPresenter<User> {

	public EntityPresenterTest() {
		super(User.class, mock(EntityView.class), new SimpleEventBus());
	}

	@Test
	public void testDisplayElement() {
		assertFalse(getRegistrationList().isEmpty());
		final User user = new User();
		display(user);
		assertEquals(user, getCurrentEntity());
		assertEquals(User.class, getEntityClass());
		assertEquals(1, getRegistrationList().size());
		refreshDisplay(false);
		display((User) null);
		assertNull(getCurrentEntity());
		clearPresenter();
		assertEquals(0, getRegistrationList().size());
	}

	@Test
	public void testGetDataFromServiceAccess() {
		final User user = new User();
		user.setId(1);
		SelectCommandResp<User> selectResp = new SelectCommandResp<User>(User.class);
		selectResp.add(user);
		ServiceAccess serviceAccess = new TestServiceAccess(selectResp);
		setServiceAccess(serviceAccess);
		assertEquals(serviceAccess, getServiceAccess());
		@SuppressWarnings("unchecked")
		DisplayCommandFactory<User> commandFactory = mock(DisplayCommandFactory.class);
		display(commandFactory);
		assertEquals(user, getCurrentEntity());
		assertEquals(commandFactory, getDisplayCommandFactory());
		refreshDisplay();
	}

	@Test
	public void testNoServiceAccess() {
		@SuppressWarnings("unchecked")
		DisplayCommandFactory<User> commandFactory = mock(DisplayCommandFactory.class);
		setServiceAccess(null);
		try {
			display(commandFactory);
			fail("Should fail because service access is not set");
		} catch (IllegalArgumentException e) {
			assertEquals("Service access not set", e.getMessage());
		}
	}

	@Test
	public void testNoDisplayFactory() {
		setServiceAccess(new TestServiceAccess());
		try {
			display((DisplayCommandFactory<User>) null);
			fail("Should fail because display factory is not set");
		} catch (IllegalArgumentException e) {
			assertEquals("Display command factory not set", e.getMessage());
		}
	}

	@Test
	public void testEmptyResponse() {
		final User user = new User();
		user.setId(1);
		ServiceAccess serviceAccess = new TestServiceAccess(new SelectCommandResp<User>(User.class));
		setServiceAccess(serviceAccess);
		assertEquals(serviceAccess, getServiceAccess());
		@SuppressWarnings("unchecked")
		DisplayCommandFactory<User> commandFactory = mock(DisplayCommandFactory.class);
		display(commandFactory);
		assertNull(getCurrentEntity());
	}

	@Test
	public void testExceptionFromService() {
		final User user = new User();
		user.setId(1);
		ServiceAccess serviceAccess = new TestServiceAccess(new UnsupportedOperationException());
		setServiceAccess(serviceAccess);
		assertEquals(serviceAccess, getServiceAccess());
		@SuppressWarnings("unchecked")
		DisplayCommandFactory<User> commandFactory = mock(DisplayCommandFactory.class);
		display(commandFactory);
	}

	@Test
	public void testHandleEntitysUpdate() {
		final EventBus eventBus = getEventBus();
		final User user = new User();
		user.setId(1);
		display(user);
		assertEquals(user.getLogin(), getCurrentEntity().getLogin());

		final UpdateCommandResp<User> updateResp = new UpdateCommandResp<User>(User.class, 1);
		final User updatedUser = new User();
		updatedUser.setId(user.getId());
		updatedUser.setLogin("login");
		updateResp.setUpdatedEntity(updatedUser);
		eventBus.fireEvent(new ServiceResponse<User>(updatedUser, updateResp));
		assertEquals(updatedUser, getCurrentEntity());

	}

	@Test
	public void testHandleDeleteEntity() {
		final EventBus eventBus = getEventBus();
		final User user = new User();
		user.setId(1);
		display(user);
		assertEquals(user.getLogin(), getCurrentEntity().getLogin());

		final DeleteCommandResp<User> deleteResp = new DeleteCommandResp<User>(User.class, 1);
		final Set<Integer> deletedEntities = new HashSet<Integer>();
		deletedEntities.add(user.getId());
		deleteResp.setDeletedElements(deletedEntities);
		eventBus.fireEvent(new ServiceResponse<User>(deleteResp, deleteResp));
		assertNull(getCurrentEntity());
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
