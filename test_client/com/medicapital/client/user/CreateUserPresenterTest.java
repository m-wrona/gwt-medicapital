package com.medicapital.client.user;

import static org.mockito.Mockito.*;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.medicapital.client.core.mvp.EntityViewBinder;
import com.medicapital.client.test.TestEventBus;
import com.medicapital.client.user.CreateUserPresenter;
import com.medicapital.client.user.CreateUserView;
import com.medicapital.common.dao.entity.UserServiceAsync;
import com.medicapital.common.entities.User;

public class CreateUserPresenterTest {

	private CreateUserView mockView() {
		HasClickHandlers mockClickHandlers = mock(HasClickHandlers.class);
		CreateUserView view = mock(CreateUserView.class);
		when(view.getCancelClickHandler()).thenReturn(mockClickHandlers);
		when(view.getCreateClickHandler()).thenReturn(mockClickHandlers);
		when(view.getIsLoginFreeClickHandler()).thenReturn(mockClickHandlers);
		return view;
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testCheckIsLoginFree_LoginFree() {
		CreateUserView view = mockView();
		EntityViewBinder<User> viewBinder = mock(EntityViewBinder.class);
		UserServiceAsync entityService = mock(UserServiceAsync.class);
		doAnswer(new Answer<Object>() {

			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				AsyncCallback<Boolean> asyncCallback = (AsyncCallback<Boolean>) invocation.getArguments()[1];
				asyncCallback.onSuccess(true);
				return null;
			}
		}).when(entityService).isLoginFree(anyString(), any(AsyncCallback.class));
		CreateUserPresenter userPresenter = new CreateUserPresenter(view, viewBinder, new TestEventBus(), entityService);
		when(view.getLogin()).thenReturn("login");
		userPresenter.checkIsLoginFree();
		verify(entityService).isLoginFree(anyString(), any(AsyncCallback.class));
		verify(view).setLoginFreeMessageVisible(true);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testCheckIsLoginFree_LoginNotFree() {
		CreateUserView view = mockView();
		EntityViewBinder<User> viewBinder = mock(EntityViewBinder.class);
		UserServiceAsync entityService = mock(UserServiceAsync.class);
		doAnswer(new Answer<Object>() {

			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				AsyncCallback<Boolean> asyncCallback = (AsyncCallback<Boolean>) invocation.getArguments()[1];
				asyncCallback.onSuccess(false);
				return null;
			}
		}).when(entityService).isLoginFree(anyString(), any(AsyncCallback.class));
		CreateUserPresenter userPresenter = new CreateUserPresenter(view, viewBinder, new TestEventBus(), entityService);
		when(view.getLogin()).thenReturn("login");
		userPresenter.checkIsLoginFree();
		verify(entityService).isLoginFree(anyString(), any(AsyncCallback.class));
		verify(view).setLoginExistMessageVisible(true);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testCheckIsLoginFree_EmptyLogin() {
		CreateUserView view = mockView();
		EntityViewBinder<User> viewBinder = mock(EntityViewBinder.class);
		UserServiceAsync entityService = mock(UserServiceAsync.class);
		doAnswer(new Answer<Object>() {

			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				AsyncCallback<Boolean> asyncCallback = (AsyncCallback<Boolean>) invocation.getArguments()[1];
				asyncCallback.onSuccess(false);
				return null;
			}
		}).when(entityService).isLoginFree(anyString(), any(AsyncCallback.class));
		CreateUserPresenter userPresenter = new CreateUserPresenter(view, viewBinder, new TestEventBus(), entityService);
		userPresenter.checkIsLoginFree();
		verify(view).setLoginEmptyMessageVisible(true);
	}
}
