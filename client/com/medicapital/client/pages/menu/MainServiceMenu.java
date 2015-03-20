package com.medicapital.client.pages.menu;

import static com.medicapital.client.log.Tracer.tracer;
import com.google.gwt.user.client.ui.Widget;
import com.medicapital.client.core.WidgetView;
import com.medicapital.client.pages.PageNavigator;
import com.medicapital.client.pages.generic.ContactPage;
import com.medicapital.client.pages.generic.HelpPage;
import com.medicapital.client.pages.generic.MainPage;
import com.medicapital.client.pages.generic.OfferPage;
import com.medicapital.client.pages.generic.RegulationsPage;

/**
 * Class represents main service menu which is accessible for visitors.
 * 
 * @author michal
 * 
 */
final public class MainServiceMenu {

	private final WidgetView view;
	
	MainServiceMenu(final WidgetView view) {
		this.view=view;
	}
	
	final public void mainPage() {
		tracer(this).debug("Main service manu clicked: " + MainPage.class);
		PageNavigator.getInstance().goToPage(MainPage.class);
	}

	final public void offer() {
		tracer(this).debug("Main service manu clicked: " + OfferPage.class);
		PageNavigator.getInstance().goToPage(OfferPage.class);
	}

	final public void regulations() {
		tracer(this).debug("Main service manu clicked: " + RegulationsPage.class);
		PageNavigator.getInstance().goToPage(RegulationsPage.class);
	}

	final public void help() {
		tracer(this).debug("Main service manu clicked: " + HelpPage.class);
		PageNavigator.getInstance().goToPage(HelpPage.class);
	}

	final public void contact() {
		tracer(this).debug("Main service manu clicked: " + ContactPage.class);
		PageNavigator.getInstance().goToPage(ContactPage.class);
	}
	
	final public Widget getDisplay() {
		return view.asWidget();
	}

}
