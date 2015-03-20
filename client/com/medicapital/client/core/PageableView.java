package com.medicapital.client.core;

/**
 * View enables paging of data
 * 
 * @author mwronski
 * 
 */
public interface PageableView extends WidgetView {

	void setNextEnable(boolean enabled);

	void setPreviousEnable(boolean enabled);

	void setFirstEnable(boolean enabled);

	void setLastEnable(boolean enabled);

	void setCurrentPageNumber(int currentPageNumber);

	void setTotalPageNumber(int totalPageNumber);
	
}
