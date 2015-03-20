package com.medicapital.client.ui.generic;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.medicapital.client.core.PageableView;
import com.medicapital.client.ui.NumberUtils;
import com.medicapital.client.ui.PopupableComposite;

public class PagingForm extends PopupableComposite implements PageableView {

	private static PagingFormUiBinder uiBinder = GWT.create(PagingFormUiBinder.class);

	interface PagingFormUiBinder extends UiBinder<Widget, PagingForm> {
	}

	@UiField
	TextBox currentPageNumber;
	@UiField
	Label pageCount;
	@UiField
	PushButton buttonFirst;
	@UiField
	PushButton buttonNext;
	@UiField
	PushButton buttonPrevious;
	@UiField
	PushButton buttonLast;

	public PagingForm() {
		initWidget(uiBinder.createAndBindUi(this));
		initHandlers();
	}

	private void initHandlers() {
		buttonFirst.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				handleFirstClick(event);

			}
		});
		buttonNext.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				handleNextClick(event);

			}
		});
		buttonPrevious.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				handlePreviousClick(event);

			}
		});
		buttonLast.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				handleLastClick(event);

			}
		});
		currentPageNumber.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				handleGoToPage(event);
			}
		});
	}
	
	public int getCurrentPage() {
		return NumberUtils.getInteger(currentPageNumber.getText(), 1);
	}

	protected void handleGoToPage(ChangeEvent event) {
		// empty
	}

	protected void handleFirstClick(ClickEvent event) {
		// empty
	}

	protected void handleNextClick(ClickEvent event) {
		// empty
	}

	protected void handlePreviousClick(ClickEvent event) {
		// empty
	}

	protected void handleLastClick(ClickEvent event) {
		// empty
	}

	@Override
	public void setCurrentPageNumber(int currentPageNumber) {
		this.currentPageNumber.setText("" + currentPageNumber);
	}

	@Override
	public void setTotalPageNumber(int totalPageNumber) {
		pageCount.setText("" + totalPageNumber);
	}

	@Override
	public void setNextEnable(boolean enabled) {
		buttonNext.setEnabled(enabled);
	}

	@Override
	public void setPreviousEnable(boolean enabled) {
		buttonPrevious.setEnabled(enabled);
	}

	@Override
	public void setFirstEnable(boolean enabled) {
		buttonFirst.setEnabled(enabled);
	}

	@Override
	public void setLastEnable(boolean enabled) {
		buttonLast.setEnabled(enabled);
	}

	public TextBox getCurrentPageNumber() {
		return currentPageNumber;
	}

	public Label getPageCount() {
		return pageCount;
	}

	public PushButton getButtonFirst() {
		return buttonFirst;
	}

	public PushButton getButtonNext() {
		return buttonNext;
	}

	public PushButton getButtonPrevious() {
		return buttonPrevious;
	}

	public PushButton getButtonLast() {
		return buttonLast;
	}

}
