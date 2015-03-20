package com.medicapital.client.core;

import com.google.gwt.user.client.ui.Widget;
import com.medicapital.client.config.SystemSettings;

/**
 * Presenter enable access to date in paging manner
 * 
 * @author mwronski
 * 
 */
abstract public class PageablePresenter {

	private PageableView view;
	private int currentPageNumber = 1;
	private int startRow;
	private int totalRows;
	private int pageSize = SystemSettings.getPageSize();

	public PageablePresenter(PageableView view) {
		this.view = view;
		setNavigationBarEnabled(false);
		view.setCurrentPageNumber(1);
	}

	public boolean isFirstPageEnabled() {
		return currentPageNumber > 1;
	}

	public void goToFirstPage() {
		currentPageNumber = 1;
		startRow = 0;
		updateNavigationBar();
		displayCurrentPageData();
	}

	public boolean isNextPageEnabled() {
		return currentPageNumber < getPageCount();
	}

	public void goToNextPage() {
		if (isNextPageEnabled()) {
			currentPageNumber++;
			startRow += pageSize;
			updateNavigationBar();
			displayCurrentPageData();
		}
	}

	public boolean isPreviousPageEnabled() {
		return currentPageNumber > 1;
	}

	public void goToPreviousPage() {
		if (isPreviousPageEnabled()) {
			currentPageNumber--;
			startRow -= pageSize;
			updateNavigationBar();
			displayCurrentPageData();
		}
	}

	public boolean isLastPageEnabled() {
		return currentPageNumber < getPageCount();
	}

	/**
	 * Go to last page
	 */
	public void goToLastPage() {
		if (isLastPageEnabled()) {
			currentPageNumber = getPageCount();
			startRow = (currentPageNumber - 1) * pageSize;
			updateNavigationBar();
			displayCurrentPageData();
		}
	}

	/**
	 * Display data of current page
	 */
	abstract protected void displayCurrentPageData();

	/**
	 * Get number of pages which can be displayed
	 * 
	 * @return
	 */
	public int getPageCount() {
		int count = totalRows / pageSize;
		if (totalRows % pageSize != 0) {
			count++;
		}
		return count == 0 ? 1 : count;
	}

	/**
	 * Update element visibility on navigation bar
	 */
	public void updateNavigationBar() {
		view.setCurrentPageNumber(currentPageNumber);
		view.setTotalPageNumber(getPageCount());
		view.setNextEnable(isNextPageEnabled());
		view.setPreviousEnable(isPreviousPageEnabled());
		view.setFirstEnable(isFirstPageEnabled());
		view.setLastEnable(isLastPageEnabled());
	}

	/**
	 * Set navigation buttons enabled
	 * 
	 * @param enabled
	 */
	protected void setNavigationBarEnabled(boolean enabled) {
		view.setFirstEnable(enabled);
		view.setPreviousEnable(enabled);
		view.setNextEnable(enabled);
		view.setLastEnable(enabled);
	}

	/**
	 * Go to chosen page number
	 * 
	 * @param pageNumber
	 */
	public void goToPage(int pageNumber) {
		if (pageNumber < 1) {
			pageNumber = 1;
		} else if (pageNumber > getPageCount()) {
			pageNumber = getPageCount();
		}
		this.currentPageNumber = pageNumber;
		this.startRow = (pageNumber - 1) * pageSize;
		updateNavigationBar();
		displayCurrentPageData();
	}

	final public int getCurrentPageNumber() {
		return currentPageNumber;
	}

	/**
	 * Get current start row number
	 * 
	 * @return
	 */
	final protected int getStartRow() {
		return startRow;
	}

	final public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	final public int getPageSize() {
		return pageSize;
	}

	final public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
		updateNavigationBar();
	}

	/**
	 * Get total rows number
	 * 
	 * @return
	 */
	final public int getTotalRows() {
		return totalRows;
	}

	final public Widget getView() {
		return view.asWidget();
	}

}
