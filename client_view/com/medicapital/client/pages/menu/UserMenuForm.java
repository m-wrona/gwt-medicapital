package com.medicapital.client.pages.menu;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;
import com.medicapital.client.core.WidgetView;

class UserMenuForm extends Composite implements WidgetView {

	interface UserMenuUiBinder extends UiBinder<Widget, UserMenuForm> {
	}

	private static UserMenuUiBinder uiBinder = GWT.create(UserMenuUiBinder.class);

	private UserMenu userMenuPresenter;

	@UiField
	MenuItem myHomePage;
	@UiField
	MenuItem userProfile;
	@UiField
	MenuItem userVisits;
	@UiField
	MenuItem newVisit;
	@UiField
	MenuItem calendar;
	@UiField
	MenuItem searchArticles;
	@UiField
	MenuItem patientDocumentation;

	public UserMenuForm() {
		this(true);
	}

	UserMenuForm(boolean initWidget) {
		if (initWidget) {
			initWidget(uiBinder.createAndBindUi(this));
			initMenu();
		}
	}

	protected void initMenu() {
		userProfile.setCommand(new Command() {

			@Override
			public void execute() {
				if (userMenuPresenter != null) {
					userMenuPresenter.userProfile();
				}
			}
		});
		initVisitOptions();
		calendar.setCommand(new Command() {

			@Override
			public void execute() {
				if (userMenuPresenter != null) {
					userMenuPresenter.calendar();
				}
			}
		});
		myHomePage.setCommand(new Command() {

			@Override
			public void execute() {
				if (userMenuPresenter != null) {
					userMenuPresenter.homePage();
				}
			}
		});
		initArticlesOptions();
		patientDocumentation.setCommand(new Command() {

			@Override
			public void execute() {
				if (userMenuPresenter != null) {
					userMenuPresenter.medicalHistory();
				}
			}
		});

	}

	protected void initArticlesOptions() {
		searchArticles.setCommand(new Command() {

			@Override
			public void execute() {
				if (userMenuPresenter != null) {
					userMenuPresenter.searchArticles();
				}
			}
		});
	}

	protected void initVisitOptions() {
		userVisits.setCommand(new Command() {

			@Override
			public void execute() {
				if (userMenuPresenter != null) {
					userMenuPresenter.showVisitList();
				}
			}
		});
		newVisit.setCommand(new Command() {

			@Override
			public void execute() {
				if (userMenuPresenter != null) {
					userMenuPresenter.createNewVisit();
				}
			}
		});
	}

	public void setUserMenuPresenter(UserMenu userMenuPresenter) {
		this.userMenuPresenter = userMenuPresenter;
	}

	public UserMenu getUserMenuPresenter() {
		return userMenuPresenter;
	}

	@Override
	public void setViewVisible(boolean visible) {
		// empty
	}

	@Override
	public void setShowAsDialogBox(boolean dialogBox) {
		// empty
	}

	@Override
	public void setViewBlocked(boolean blocked) {
		// empty
	}

	@Override
	public boolean isDialogBox() {
		return false;
	}

}
