package com.medicapital.client;

import static com.medicapital.client.log.Tracer.tracer;
import com.google.gwt.core.client.EntryPoint;
import com.medicapital.client.dao.DaoFactory;
import com.medicapital.client.pages.PageNavigator;
import com.medicapital.client.pages.generic.RegisterPage;

/**
 * Class is loaded by GWT at runtime. It starts whole service.
 * 
 * @author michal
 * 
 */
public class ServiceEntryPoint implements EntryPoint {

	@Override
	public void onModuleLoad() {
		tracer(this).info("Loading service modules...");
		DaoFactory.getServiceAccess();
		DaoFactory.getEventBus();

		tracer(this).debug("Creating page navigation...");
//		PageNavigator.getInstance().goToDefaultPage();
		PageNavigator.getInstance().goToPage(RegisterPage.class);
	}
}
