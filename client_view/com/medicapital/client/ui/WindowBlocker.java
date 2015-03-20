package com.medicapital.client.ui;

import java.util.ArrayList;
import java.util.List;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public final class WindowBlocker extends Composite {

	interface MyUiBinder extends UiBinder<Widget, WindowBlocker> {
	}

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	private static final List<WindowBlocker> instances = new ArrayList<WindowBlocker>();
	private final Composite blockedComposite;
	private DecoratedPopupPanel dialogBox;
	@UiField
	HTMLPanel panel;

	WindowBlocker(Composite composite) {
		instances.add(this);
		initWidget(uiBinder.createAndBindUi(this));
		blockedComposite = composite;
		panel.setStyleName("blockerWindow");
	}

	/**
	 * Set window blocked for user interactions
	 * 
	 * @param blockWindow
	 */
	public void setBlocked(boolean blockWindow) {
		if (blockWindow) {
			if (dialogBox == null) {
				dialogBox = createDialogBox();
				fitBlockingSize();
			}
			dialogBox.show();
		} else {
			if (dialogBox != null) {
				dialogBox.hide();
				dialogBox = null;
			}
		}
	}

	private void fitBlockingSize() {
		dialogBox.setPixelSize(blockedComposite.getOffsetWidth(), blockedComposite.getOffsetHeight());
		panel.setPixelSize(blockedComposite.getOffsetWidth(), blockedComposite.getOffsetHeight());
		dialogBox.setPopupPosition(blockedComposite.getAbsoluteLeft(), blockedComposite.getAbsoluteTop());
	}

	private DecoratedPopupPanel createDialogBox() {
		DecoratedPopupPanel dialogBox = new DecoratedPopupPanel();
		dialogBox.setStyleName("popupWindow");
		dialogBox.add(this);
		dialogBox.setAutoHideEnabled(false);
		return dialogBox;
	}

	@Override
	protected void finalize() throws Throwable {
		setBlocked(false);
		super.finalize();
	}

	/**
	 * Make all blocked windows unblocked again
	 */
	public static void clear() {
		for (WindowBlocker windowBlocker : instances) {
			windowBlocker.setBlocked(false);
		}
		instances.clear();
	}

}
