package com.medicapital.client.pages;

import static com.medicapital.client.log.Tracer.tracer;
import com.google.gwt.user.client.ui.RootPanel;
import com.medicapital.client.login.LoginFactory;
import com.medicapital.client.login.LoginPresenter;
import com.medicapital.client.pages.menu.MenuManager;

/**
 * Class is responsible for creating the constant page frame
 * 
 * @author michal
 * 
 */
final class PageFrame {

	public final static String PAGE_CONTEXT = "pageContext";
	private static PageFrame instance;
	private LoginPresenter loginPresenter;

	/**
	 * Get widget panel
	 * 
	 * @param panelName
	 * @throws IllegalArgumentException
	 *             if panelName doesn't exist
	 * @return
	 */
	private static RootPanel getPanel(String panelName) {
		RootPanel panel = RootPanel.get(panelName);
		if (panel == null) {
			throw new IllegalArgumentException("Root panel not found: " + panelName);
		}
		return panel;
	}

	/**
	 * Get frame of the page
	 * 
	 * @param eventBus
	 * @return
	 */
	static PageFrame getInstance() {
		if (instance == null) {
			tracer(PageFrame.class).debug("Creating page frame");
			instance = new PageFrame();
		}
		return instance;
	}

	private PageFrame() {
		new MenuManager(getPanel("mainMenu"), getPanel("userMenu"));
		initLoginFeatures();
	}

	private void initLoginFeatures() {
		tracer(this).debug("Initializing login features...");
		loginPresenter = new LoginFactory().createLogin();
		final RootPanel loginPanel = getPanel("loginForm");
		loginPanel.add(loginPresenter.getLoginDisplay().asWidget());
		loginPanel.add(loginPresenter.getLoggedUserDisplay().asWidget());
	}

	LoginPresenter getLoginPresenter() {
		return loginPresenter;
	}

	/**
	 * Get panel where context of the page can be displayed
	 * 
	 * @return
	 */
	RootPanel getPageContext() {
		return getPanel(PAGE_CONTEXT);
	}

}
