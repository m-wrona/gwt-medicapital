package com.medicapital.client.ui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.PushButton;
import com.medicapital.client.core.WidgetView;

/**
 * Basic composite which can be displayed as dialog box pop up window.
 * 
 * @author mwronski
 * 
 */
public class PopupableComposite extends Composite implements WidgetView {

	private final ClickHandler closeWindowHandler = new ClickHandler() {

		@Override
		public void onClick(ClickEvent event) {
			closeWindow();
		}
	};
	private final WindowBlocker windowBlocker = new WindowBlocker(this);
	private DialogBox dialogBox;
	private boolean showAsDialogBox = false;
	private PushButton pushButtonCloseWindow;
	private Button buttonCloseWindow;

	@Override
	final public void setViewBlocked(boolean blocked) {
		windowBlocker.setBlocked(blocked);
	};

	/**
	 * Close composite's window
	 */
	public void closeWindow() {
		if (dialogBox != null) {
			dialogBox.hide();
			dialogBox = null;
		}
	}

	@Override
	final public void setViewVisible(boolean visible) {
		if (showAsDialogBox && dialogBox == null) {
			dialogBox = new DialogBox();
			dialogBox.add(this);
		}
		setVisible(visible);
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (dialogBox != null) {
			if (visible) {
				dialogBox.center();
				dialogBox.show();
			} else {
				closeWindow();
			}
		}
	}

	@Override
	final public void setShowAsDialogBox(boolean dialogBox) {
		showAsDialogBox = true;
		if (pushButtonCloseWindow != null) {
			pushButtonCloseWindow.setVisible(dialogBox);
		}
		if (buttonCloseWindow != null) {
			buttonCloseWindow.setVisible(dialogBox);
		}
	}

	@Override
	public boolean isDialogBox() {
		return showAsDialogBox;
	}

	final public void setPushButtonCloseWindow(PushButton pushButtonCloseWindow) {
		this.pushButtonCloseWindow = pushButtonCloseWindow;
		pushButtonCloseWindow.addClickHandler(closeWindowHandler);
	}

	final public void setButtonCloseWindow(Button buttonCloseWindow) {
		this.buttonCloseWindow = buttonCloseWindow;
		buttonCloseWindow.addClickHandler(closeWindowHandler);
	}

}
