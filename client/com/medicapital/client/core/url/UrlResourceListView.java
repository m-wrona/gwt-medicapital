package com.medicapital.client.core.url;

/**
 * View for list of resources that can be reach only using URL
 * 
 * @author mwronski
 * 
 */
public interface UrlResourceListView extends BasicUrlResourceListView {

	void setUrlListPresenter(UrlResourceListPresenter<?, ?> urlResourceListPresenter);

}
