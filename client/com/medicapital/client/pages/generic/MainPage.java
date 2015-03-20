package com.medicapital.client.pages.generic;

import com.medicapital.client.pages.Page;

/**
 * Main page where news and articles will be presented
 * 
 * @author michal
 * 
 */
final public class MainPage extends Page<MainPageForm> {

	public static final String PAGE_NAME = "MainPage";

	@Override
    protected MainPageForm createPageView() {
	    return new MainPageForm();
    }

	@Override
    protected void initPage(MainPageForm pageView) {
	    // empty
    }

}
