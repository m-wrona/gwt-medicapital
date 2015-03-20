package com.medicapital.client.core.entities;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import com.google.gwt.event.shared.SimpleEventBus;
import com.medicapital.client.core.commands.DisplayCommandFactory;
import com.medicapital.client.core.commands.EditCommandFactory;
import com.medicapital.client.core.entities.EditableEntitiesPresenter;
import com.medicapital.client.core.entities.EditableEntitiesView;
import com.medicapital.client.test.TestServiceAccess;
import com.medicapital.common.commands.entity.DeleteCommandResp;
import com.medicapital.common.dao.ServiceAccess;
import com.medicapital.common.entities.User;

public class EditableEntitiesPresenterTest extends EditableEntitiesPresenter<User> {

	@SuppressWarnings("unchecked")
    public EditableEntitiesPresenterTest() {
	    super(User.class, mock(EditableEntitiesView.class), new SimpleEventBus());
    }

	private Set<User> createTestData() {
		Set<User> users = new HashSet<User>();
		for (int i = 1; i <= 10; i++) {
			User user = new User();
			user.setId(i);
			users.add(user);
		}
		return users;
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testDeleteSelectedElements() {
		TestServiceAccess serviceAccess = new TestServiceAccess();
		setServiceAccess(serviceAccess);
		setEntitySelected(2, true);
		setEntitySelected(4, true);
		setEntitySelected(6, true);
		setEntitySelected(6, false);
		EditCommandFactory<User> commandFactory = mock(EditCommandFactory.class);
		setEditCommandFactory(commandFactory);
		setDisplayCommandFactory(mock(DisplayCommandFactory.class));
		Set<Integer> entitiesId = new HashSet<Integer>();
		entitiesId.add(2);
		entitiesId.add(4);
		display(createTestData());
		assertEquals(10, getDisplayedElements().size());
		DeleteCommandResp<User> deleteCommandResp = new DeleteCommandResp<User>(User.class, entitiesId.size());
		deleteCommandResp.setDeletedElements(entitiesId);
		serviceAccess.addResponse(deleteCommandResp);
		deleteSelectedEntities();
		assertEquals(1, serviceAccess.getReceived().size());
		assertEquals(8, getDisplayedElements().size());
		refreshDisplay();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testValidatePresenter() {
		setServiceAccess(mock(ServiceAccess.class));
		setDisplayCommandFactory(mock(DisplayCommandFactory.class));
		try {
			validatePresenter();
			fail("Edit command factory is not set");
		} catch (IllegalArgumentException e) {
			// ignore
		}
		setEditCommandFactory(mock(EditCommandFactory.class));
		validatePresenter();
	}

	@Override
    protected void displayDataOnView(Collection<User> data) {
	    // ignore
    }
}
