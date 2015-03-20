package com.medicapital.client.ui.table;

import java.util.Set;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.medicapital.client.core.PageablePresenter;
import com.medicapital.client.core.PageableView;
import com.medicapital.client.ui.PopupableComposite;

/**
 * Basic table navigation panel with next, previous, go to page etc. elements
 * 
 * @author mwronski
 * 
 */
public abstract class TableNavigationPanel extends PopupableComposite implements PageableView {

	private static TableNavigationUiBinder uiBinder = GWT.create(TableNavigationUiBinder.class);

	interface TableNavigationUiBinder extends UiBinder<Widget, TableNavigationPanel> {
	}

	@UiField
	SimplePanel dataPanel;
	@UiField
	PushButton buttonFirst;
	@UiField
	PushButton buttonLast;
	@UiField
	PushButton buttonNext;
	@UiField
	PushButton buttonPrevious;
	@UiField
	PushButton buttonAdd;
	@UiField
	PushButton deleteSelected;
	@UiField
	IntegerBox pageNumber;
	@UiField
	Label pageCount;
	@UiField
	HTMLPanel headerPanel;
	@UiField
	HTMLPanel footerPanel;

	private PageablePresenter pageablePresenter;

	public TableNavigationPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		initHandlers();
	}

	private void initHandlers() {
		buttonFirst.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(final ClickEvent event) {
				firstClickHandler(event);
			}
		});
		buttonNext.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(final ClickEvent event) {
				nextClickHandler(event);
			}
		});
		buttonPrevious.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(final ClickEvent event) {
				previousClickHandler(event);
			}
		});
		buttonLast.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(final ClickEvent event) {
				lastClickHandler(event);
			}
		});
		pageNumber.addValueChangeHandler(new ValueChangeHandler<Integer>() {

			@Override
			public void onValueChange(final ValueChangeEvent<Integer> event) {
				try {
					pageNumberChangedHandler(event, pageNumber.getValue());
				} catch (final Exception e) {
					pageNumber.setValue(1);
					pageNumberChangedHandler(event, 1);
				}

			}
		});
	}

	final protected void firstClickHandler(final ClickEvent clickEvent) {
		if (pageablePresenter != null) {
			pageablePresenter.goToFirstPage();
		}
	}

	final protected void lastClickHandler(final ClickEvent clickEvent) {
		if (pageablePresenter != null) {
			pageablePresenter.goToLastPage();
		}
	}

	final protected void nextClickHandler(final ClickEvent clickEvent) {
		if (pageablePresenter != null) {
			pageablePresenter.goToNextPage();
		}
	}

	final protected void previousClickHandler(final ClickEvent clickEvent) {
		if (pageablePresenter != null) {
			pageablePresenter.goToPreviousPage();
		}
	}

	final protected void pageNumberChangedHandler(final ValueChangeEvent<Integer> event, final int pageNumber) {
		if (pageablePresenter != null) {
			pageablePresenter.goToPage(pageNumber);
		}
	}

	@Override
	final public void setNextEnable(final boolean enabled) {
		buttonNext.setEnabled(enabled);
	}

	@Override
	final public void setPreviousEnable(final boolean enabled) {
		buttonPrevious.setEnabled(enabled);
	}

	@Override
	final public void setFirstEnable(final boolean enabled) {
		buttonFirst.setEnabled(enabled);
	}

	@Override
	final public void setLastEnable(final boolean enabled) {
		buttonLast.setEnabled(enabled);
	}

	@Override
	final public void setCurrentPageNumber(final int currentPageNumber) {
		pageNumber.setValue(currentPageNumber);
	}

	@Override
	final public void setTotalPageNumber(int totalPageNumber) {
		pageCount.setText("" + totalPageNumber);
	}

	final public void setPageablePresenter(PageablePresenter pageablePresenter) {
		this.pageablePresenter = pageablePresenter;
	}

	final public PushButton getButtonAdd() {
		return buttonAdd;
	}

	final public SimplePanel getDataPanel() {
		return dataPanel;
	}

	final public void setDeleteSelectedButtonVisible(final boolean visible) {
		deleteSelected.setVisible(visible);
	}

	final public void setDeleteSelectedHandlerEnabled(boolean enabled) {
		deleteSelected.setEnabled(enabled);
	}

	final public HTMLPanel getHeaderPanel() {
		return headerPanel;
	}

	final public HTMLPanel getFooterPanel() {
		return footerPanel;
	}

	/**
	 * Handle click delete selected element
	 * 
	 * @param clickEvent
	 * @param selectedRowIds
	 */
	protected void deleteSelectedClickHandler(final ClickEvent clickEvent, final Set<Integer> selectedRowIds) {
		// empty
	}

}
