package com.medicapital.client.pages.menu;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;
import com.medicapital.client.core.WidgetView;

final public class MainMenuForm extends Composite implements WidgetView {

	interface MyUiBinder extends UiBinder<Widget, MainMenuForm> {
	}

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	private MainServiceMenu mainMenu;

	@UiField
	MenuItem mainPage;
	@UiField
	MenuItem offer;
	@UiField
	MenuItem regulation;
	@UiField
	MenuItem help;
	@UiField
	MenuItem contact;

	public MainMenuForm() {
		initWidget(uiBinder.createAndBindUi(this));
		initMenu();
	}

	private void initMenu() {
		mainPage.setCommand(new Command() {

			@Override
			public void execute() {
				if (mainMenu != null) {
					mainMenu.mainPage();
				}
			}
		});

		offer.setCommand(new Command() {

			@Override
			public void execute() {
				if (mainMenu != null) {
					mainMenu.offer();
				}
			}
		});

		regulation.setCommand(new Command() {

			@Override
			public void execute() {
				if (mainMenu != null) {
					mainMenu.regulations();
				}
			}
		});

		help.setCommand(new Command() {

			@Override
			public void execute() {
				if (mainMenu != null) {
					mainMenu.help();
				}
			}

		});

		contact.setCommand(new Command() {

			@Override
			public void execute() {
				if (mainMenu != null) {
					mainMenu.contact();
				}
			}
		});
	}

	public void setMainMenu(MainServiceMenu mainMenu) {
		this.mainMenu = mainMenu;
	}

	public MainServiceMenu getMainMenu() {
		return mainMenu;
	}

	@Override
	public Widget asWidget() {
		return this;
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
