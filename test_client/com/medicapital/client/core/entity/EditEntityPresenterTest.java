package com.medicapital.client.core.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import org.junit.Test;
import com.medicapital.client.core.commands.DisplayCommandFactory;
import com.medicapital.client.core.commands.EditCommandFactory;
import com.medicapital.client.core.entity.EditEntityPresenter;
import com.medicapital.client.core.entity.EditEntityView;
import com.medicapital.client.dao.ServiceResponse;
import com.medicapital.client.test.TestEventBus;
import com.medicapital.client.test.TestServiceAccess;
import com.medicapital.common.commands.entity.DeleteCommandResp;
import com.medicapital.common.commands.entity.UpdateCommandResp;
import com.medicapital.common.entities.User;

public class EditEntityPresenterTest extends EditEntityPresenter<User> {

	@SuppressWarnings("unchecked")
    public EditEntityPresenterTest() {
		super(User.class, mock(EditEntityView.class), new TestEventBus());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testDeleteEntity() {
		final TestEventBus eventBus = (TestEventBus) getEventBus();
		final User user = new User();
		user.setId(5);
		display(user);
		assertEquals(0, eventBus.getReceivedEvents().size());

		final DeleteCommandResp<User> deleteCommandResp = new DeleteCommandResp<User>(User.class, 1);
		final TestServiceAccess serviceAccess = new TestServiceAccess(deleteCommandResp);
		setServiceAccess(serviceAccess);
		setEditCommandFactory(mock(EditCommandFactory.class));
		assertNotNull(getEditCommandFactory());
		setDisplayCommandFactory(mock(DisplayCommandFactory.class));
		delete();
		assertNull(getCurrentEntity());
		assertEquals(1, eventBus.getReceivedEvents().size());
		final ServiceResponse<User> clientEvent = (ServiceResponse<User>)  eventBus.getReceivedEvents().get(0);
		assertEquals(this, clientEvent.getSender());
		final DeleteCommandResp<User> broadcast = (DeleteCommandResp) clientEvent.getCommandResp();
		assertEquals(1, broadcast.getDeletedElements().size());
		assertEquals(user.getId(), broadcast.getDeletedElements().iterator().next().intValue());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testUpdateNullEntity() {
		final TestEventBus eventBus = (TestEventBus) getEventBus();
		setServiceAccess(new TestServiceAccess());
		setEditCommandFactory(mock(EditCommandFactory.class));
		display((User) null);
		assertEquals(0, eventBus.getReceivedEvents().size());
		setDisplayCommandFactory(mock(DisplayCommandFactory.class));
		update();
		assertEquals(0, eventBus.getReceivedEvents().size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testUpdateEntity() {
		final TestEventBus eventBus = (TestEventBus) getEventBus();
		final User user = new User();
		user.setId(5);
		setDisplayCommandFactory(mock(DisplayCommandFactory.class));
		display(user);

		final UpdateCommandResp<User> updateCommandResp = new UpdateCommandResp<User>(User.class, 1);
		final TestServiceAccess serviceAccess = new TestServiceAccess(updateCommandResp);
		setServiceAccess(serviceAccess);
		setEditCommandFactory(mock(EditCommandFactory.class));

		assertEquals(0, eventBus.getReceivedEvents().size());
		update();
		assertEquals(1, eventBus.getReceivedEvents().size());
		final ServiceResponse<User> clientEvent = (ServiceResponse<User>) eventBus.getReceivedEvents().get(0);
		assertEquals(this, clientEvent.getSender());
		final UpdateCommandResp<User> updateCommand = (UpdateCommandResp<User>) clientEvent.getCommandResp();
		assertNotNull(updateCommand);
		assertEquals(1, updateCommand.getCount());
		assertEquals(user, updateCommand.getUpdatedEntity());
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
