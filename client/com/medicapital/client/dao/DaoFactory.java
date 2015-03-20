package com.medicapital.client.dao;

import static com.medicapital.client.log.Tracer.tracer;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.medicapital.common.dao.DoctorUrlService;
import com.medicapital.common.dao.ServiceAccess;
import com.medicapital.common.dao.entity.DoctorService;
import com.medicapital.common.dao.entity.DoctorServiceAsync;
import com.medicapital.common.dao.entity.UserService;
import com.medicapital.common.dao.entity.UserServiceAsync;

/**
 * Data access factory
 * 
 * @author mwronski
 * 
 */
final public class DaoFactory {

	private static ServiceAccess serviceAccess;
	private static DoctorUrlService doctorUrlService;
	private static ArticleUrlAccess articleUrlAccess;
	private static EventBus eventBus;

	public static DoctorServiceAsync getDoctorService() {
		return GWT.create(DoctorService.class);
	}
	
	public static UserServiceAsync getUserService() {
		return GWT.create(UserService.class);
	}
	
	public static ArticleUrlAccess getArticleUrlService() {
		if (articleUrlAccess == null) {
			tracer(DaoFactory.class).debug("Creating article URL service access...");
			articleUrlAccess = new ArticleUrlAccess();
		}
		return articleUrlAccess;
	}

	public static DoctorUrlService getDoctorUrlService() {
		if (doctorUrlService == null) {
			tracer(DaoFactory.class).debug("Creating doctor URL service access...");
			doctorUrlService = new DoctorUrlAccess();
		}
		return doctorUrlService;
	}

	public static ServiceAccess getServiceAccess() {
		if (serviceAccess == null) {
			tracer(DaoFactory.class).debug("Creating service access...");
			serviceAccess = new ServerAccess();
		}
		return serviceAccess;
	}

	public static EventBus getEventBus() {
		if (eventBus == null) {
			tracer(DaoFactory.class).debug("Creating event bus...");
			eventBus = new SimpleEventBus();
		}
		return eventBus;
	}

	private DaoFactory() {
		// empty
	}
}
