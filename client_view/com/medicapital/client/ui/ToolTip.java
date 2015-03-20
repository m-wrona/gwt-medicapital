package com.medicapital.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

/**
 * Tool tip message which can be displayed at any place of the screen
 * 
 * @author mwronski
 * 
 */
final class ToolTip extends DialogBox {

	private static ToolTipUiBinder uiBinder = GWT.create(ToolTipUiBinder.class);

	interface ToolTipUiBinder extends UiBinder<Widget, ToolTip> {
	}

	@UiField
	HTML message;

	public ToolTip() {
		add(uiBinder.createAndBindUi(this));
		setStyleName("none"); // transparent window
		setAutoHideEnabled(true);
		setAnimationEnabled(true);
	}

	public void setMessage(String text) {
		this.message.setText(text);
	}

	public void show(boolean autoHide) {
		show();
		if (autoHide) {
			UIUtil.autoHideMessage(this);
		}
	}

	public void showRelativeTo(Widget widget, boolean autoHide) {
		showRelativeTo(widget);
		if (autoHide) {
			UIUtil.autoHideMessage(this);
		}
	}

}
