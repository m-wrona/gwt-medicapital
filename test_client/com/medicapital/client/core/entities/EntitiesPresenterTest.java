package com.medicapital.client.core.entities;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import org.junit.Test;
import com.medicapital.client.core.commands.DisplayCommandFactory;
import com.medicapital.client.core.entities.EntitiesPresenter;
import com.medicapital.client.core.entities.EntitiesView;
import com.medicapital.client.dao.ServiceResponse;
import com.medicapital.client.test.TestServiceAccess;
import com.medicapital.common.commands.entity.DeleteCommandResp;
import com.medicapital.common.commands.entity.SelectCommandResp;
import com.medicapital.common.commands.entity.SelectCountCommandResp;
import com.medicapital.common.commands.entity.UpdateCommandResp;
import com.medicapital.common.entities.User;

public class EntitiesPresenterTest extends EntitiesPresenter<User> {

	@SuppressWarnings("unchecked")
	public EntitiesPresenterTest() {
		super(User.class, mock(EntitiesView.class), new SimpleEventBus());
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

	@Test
	public void testDisplayElements() {
		Set<User> data = createTestData();
		display(data);
		assertNotNull(getDisplayedElements());
		assertEquals(data.size(), getDisplayedElements().size());
		refreshDisplay(false);
		assertNotNull(getDisplayedElements());
		assertEquals(data.size(), getDisplayedElements().size());
		assertEquals(User.class, getEntityClass());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testDisplayElementUsingDisplayFactory() {
		setPageSize(1);
		assertEquals(1, getPageSize());
		Set<User> users = createTestData();
		TestServiceAccess serviceAccess = new TestServiceAccess();
		serviceAccess.addResponse(new SelectCommandResp<User>(User.class, users));
		serviceAccess.addResponse(new SelectCountCommandResp<User>(User.class, users.size()));
		setServiceAccess(serviceAccess);
		assertEquals(serviceAccess, getServiceAccess());
		DisplayCommandFactory<User> commandFactory = mock(DisplayCommandFactory.class);
		setDisplayCommandFactory(commandFactory);
		goToFirstPage();
		verify(commandFactory).createSelectCommand(0, getPageSize());
		verify(commandFactory).createCountCommand();
		assertNotNull(getDisplayedElements());
		assertEquals(users.size(), getDisplayedElements().size());
		assertEquals(0, getStartRow());
		assertEquals(users.size(), getTotalRows());
		assertEquals(commandFactory, getDisplayCommandFactory());
		assertEquals(1, getListHandlers().size());
		refreshDisplay();
		assertEquals(0, getDisplayedElements().size());
		clearPresenter();
		assertEquals(0, getListHandlers().size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGoToNextPage() {
		setPageSize(3);
		assertEquals(3, getPageSize());
		Set<User> users = createTestData();
		TestServiceAccess serviceAccess = new TestServiceAccess();
		serviceAccess.addResponse(new SelectCommandResp<User>(User.class, users));
		serviceAccess.addResponse(new SelectCountCommandResp<User>(User.class, users.size()));
		setServiceAccess(serviceAccess);
		DisplayCommandFactory<User> commandFactory = mock(DisplayCommandFactory.class);
		setDisplayCommandFactory(commandFactory);
		goToFirstPage();
		goToNextPage();
		assertEquals(3, getStartRow());
		assertEquals(users.size(), getTotalRows());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGoToLastPage() {
		setPageSize(3);
		assertEquals(3, getPageSize());
		Set<User> users = createTestData();
		TestServiceAccess serviceAccess = new TestServiceAccess();
		serviceAccess.addResponse(new SelectCommandResp<User>(User.class, users));
		serviceAccess.addResponse(new SelectCountCommandResp<User>(User.class, users.size()));
		setServiceAccess(serviceAccess);
		DisplayCommandFactory<User> commandFactory = mock(DisplayCommandFactory.class);
		setDisplayCommandFactory(commandFactory);
		goToFirstPage();
		goToLastPage();
		assertEquals(9, getStartRow());
		assertEquals(users.size(), getTotalRows());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGoToPreviousPage() {
		setPageSize(3);
		assertEquals(3, getPageSize());
		Set<User> users = createTestData();
		TestServiceAccess serviceAccess = new TestServiceAccess();
		serviceAccess.addResponse(new SelectCommandResp<User>(User.class, users));
		serviceAccess.addResponse(new SelectCountCommandResp<User>(User.class, users.size()));
		setServiceAccess(serviceAccess);
		DisplayCommandFactory<User> commandFactory = mock(DisplayCommandFactory.class);
		setDisplayCommandFactory(commandFactory);
		goToFirstPage();
		goToLastPage();
		assertEquals(9, getStartRow());
		assertEquals(users.size(), getTotalRows());
		goToPreviousPage();
		assertEquals(6, getStartRow());
		assertEquals(users.size(), getTotalRows());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGoToFirstPage() {
		setPageSize(3);
		assertEquals(3, getPageSize());
		Set<User> users = createTestData();
		TestServiceAccess serviceAccess = new TestServiceAccess();
		serviceAccess.addResponse(new SelectCommandResp<User>(User.class, users));
		serviceAccess.addResponse(new SelectCountCommandResp<User>(User.class, users.size()));
		setServiceAccess(serviceAccess);
		DisplayCommandFactory<User> commandFactory = mock(DisplayCommandFactory.class);
		setDisplayCommandFactory(commandFactory);
		goToFirstPage();
		assertEquals(0, getStartRow());
		goToLastPage();
		assertEquals(9, getStartRow());
		assertEquals(users.size(), getTotalRows());
		goToFirstPage();
		assertEquals(0, getStartRow());
		assertEquals(users.size(), getTotalRows());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGoToPage() {
		setPageSize(3);
		assertEquals(3, getPageSize());
		Set<User> users = createTestData();
		TestServiceAccess serviceAccess = new TestServiceAccess();
		serviceAccess.addResponse(new SelectCommandResp<User>(User.class, users));
		serviceAccess.addResponse(new SelectCountCommandResp<User>(User.class, users.size()));
		setServiceAccess(serviceAccess);
		DisplayCommandFactory<User> commandFactory = mock(DisplayCommandFactory.class);
		setDisplayCommandFactory(commandFactory);
		goToFirstPage();
		goToPage(2);
		assertEquals(3, getStartRow());
		assertEquals(2, getCurrentPageNumber());
		assertEquals(users.size(), getTotalRows());

	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGoToWrongPageNumber() {
		setPageSize(3);
		assertEquals(3, getPageSize());
		Set<User> users = createTestData();
		TestServiceAccess serviceAccess = new TestServiceAccess();
		serviceAccess.addResponse(new SelectCommandResp<User>(User.class, users));
		serviceAccess.addResponse(new SelectCountCommandResp<User>(User.class, users.size()));
		setServiceAccess(serviceAccess);
		DisplayCommandFactory<User> commandFactory = mock(DisplayCommandFactory.class);
		setDisplayCommandFactory(commandFactory);
		goToFirstPage();
		goToPage(-1);
		assertEquals(1, getCurrentPageNumber());
		goToPage(getPageCount() + 1);
		assertEquals(getPageCount(), getCurrentPageNumber());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testValidatePresenter() {
		try {
			validatePresenter();
			fail("Validation should throw exception");
		} catch (IllegalArgumentException e) {
			// ignore
		}

		TestServiceAccess serviceAccess = new TestServiceAccess();
		setServiceAccess(serviceAccess);

		try {
			validatePresenter();
			fail("Validation should throw exception");
		} catch (IllegalArgumentException e) {
			// ignore
		}

		DisplayCommandFactory<User> commandFactory = mock(DisplayCommandFactory.class);
		setDisplayCommandFactory(commandFactory);
		validatePresenter();

	}

	@SuppressWarnings("unchecked")
	@Test
	public void testHandleUpdateCommand() {
		EventBus eventBus = getEventBus();
		setPageSize(3);
		assertEquals(3, getPageSize());
		Set<User> users = createTestData();
		TestServiceAccess serviceAccess = new TestServiceAccess();
		serviceAccess.addResponse(new SelectCommandResp<User>(User.class, users));
		serviceAccess.addResponse(new SelectCountCommandResp<User>(User.class, users.size()));
		setServiceAccess(serviceAccess);
		DisplayCommandFactory<User> commandFactory = mock(DisplayCommandFactory.class);
		setDisplayCommandFactory(commandFactory);
		goToFirstPage();
		assertEquals(10, getDisplayedElements().size());
		UpdateCommandResp<User> updateCommandResp = new UpdateCommandResp<User>(User.class, 1);
		User user = new User();
		user.setId(1);
		updateCommandResp.setUpdatedEntity(user);
		eventBus.fireEvent(new ServiceResponse<User>(updateCommandResp, updateCommandResp));
		assertEquals(10, getDisplayedElements().size());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testHandleDeleteCommand() {
		EventBus eventBus = getEventBus();
		setPageSize(3);
		assertEquals(3, getPageSize());
		Set<User> users = createTestData();
		TestServiceAccess serviceAccess = new TestServiceAccess();
		serviceAccess.addResponse(new SelectCommandResp<User>(User.class, users));
		serviceAccess.addResponse(new SelectCountCommandResp<User>(User.class, users.size()));
		setServiceAccess(serviceAccess);
		DisplayCommandFactory<User> commandFactory = mock(DisplayCommandFactory.class);
		setDisplayCommandFactory(commandFactory);
		goToFirstPage();
		assertEquals(10, getDisplayedElements().size());
		assertEquals(10, getTotalRows());
		DeleteCommandResp<User> deleteCommandResp = new DeleteCommandResp<User>(User.class, 1);
		Set<Integer> deletedIds = new HashSet<Integer>();
		deletedIds.add(2);
		deletedIds.add(4);
		deleteCommandResp.setDeletedElements(deletedIds);
		eventBus.fireEvent(new ServiceResponse<User>(deleteCommandResp, deleteCommandResp));
		assertEquals(8, getDisplayedElements().size());
		assertEquals(8, getTotalRows());
	}

	@Override
	protected void displayDataOnView(Collection<User> data) {
		// ignore
	}
}
