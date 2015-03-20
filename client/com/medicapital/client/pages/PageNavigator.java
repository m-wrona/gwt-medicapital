package com.medicapital.client.pages;

import static com.medicapital.client.log.Tracer.tracer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.medicapital.client.dao.DaoFactory;
import com.medicapital.client.event.ClientEvent;
import com.medicapital.client.event.SimpleBroadcastHandler;
import com.medicapital.client.pages.generic.MainPage;
import com.medicapital.client.pages.history.PageHistory;

/**
 * Class is responsible for handling events which allow to navigate between
 * pages. It manages also history so once visited page will be kept in cache and
 * restored when needed.
 * 
 * @author michal
 * 
 */
final public class PageNavigator {

	private static final PageNavigator instance = new PageNavigator();
	private static final List<PageFactory> pageFactories = new ArrayList<PageFactory>();
	private final EventBus eventBus;
	private final PlaceController placeController;

	static {
		tracer(PageNavigator.class).debug("Adding public page factory: " + PublicPageFactory.class);
		pageFactories.add(new PublicPageFactory());
		tracer(PageNavigator.class).debug("Adding user page factory: " + UserPageFactory.class);
		pageFactories.add(new UserPageFactory());
		tracer(PageNavigator.class).debug("Adding doctor page factory: " + UserPageFactory.class);
		pageFactories.add(new DoctorPageFactory());
	}

	private PageNavigator() {
		PageHistory.getInstance();
		eventBus = DaoFactory.getEventBus();
		registerInEventBus(eventBus);
		placeController = createPlaceController(eventBus);
	}

	/**
	 * Create controller for place for pages
	 * 
	 * @param eventBus
	 * @return
	 */
	private PlaceController createPlaceController(final EventBus eventBus) {
		tracer(this).debug("Initializing place controller...");
		PlaceController placeController = new PlaceController(eventBus);
		tracer(this).debug("Initializing history controller...");
		final PageHistoryMapper pageHistoryMapper = GWT.create(PageHistoryMapper.class);
		final PlaceHistoryHandler pageHistoryHandler = new PlaceHistoryHandler(pageHistoryMapper);
		pageHistoryHandler.register(placeController, eventBus, getDefaultPage());
		pageHistoryHandler.handleCurrentHistory();
		return placeController;
	}

	/**
	 * Get default service page
	 * 
	 * @return
	 */
	static Page<?> getDefaultPage() {
		return new MainPage();
	}

	/**
	 * Go to default page of the service
	 */
	final public void goToDefaultPage() {
		tracer(this).debug("Going to default page");
		final Page<?> defaultPage = getDefaultPage();
		placeController.goTo(defaultPage);
		defaultPage.display();
	}

	/**
	 * Go to page of chosen class
	 * 
	 * @param pageClass
	 * @throws IllegalArgumentException
	 *             when page can't be displayed
	 */
	final public <T extends Page<?>> void goToPage(final Class<T> pageClass) throws IllegalArgumentException {
		goToPage(pageClass, null);
	}

	/**
	 * Go to page of chosen class
	 * 
	 * @param pageClass
	 * @param requestParameters
	 * @throws IllegalArgumentException
	 *             when page can't be displayed
	 */
	final public <T extends Page<?>> void goToPage(final Class<T> pageClass, final Map<String, String> requestParameters) throws IllegalArgumentException {
		tracer(this).debug("Going to page: " + pageClass);
		if (pageClass == null) {
			goToDefaultPage();
			return;
		}
		for (final PageFactory pageFactory : pageFactories) {
			final Page<?> page = pageFactory.createPage(pageClass);
			if (page != null) {
				tracer(this).debug("Page created by handler: " + pageFactory.getClass() + ", request parameters: " + requestParameters);
				page.addRequestParameters(requestParameters);
				placeController.goTo(page);
				page.display();
				return;
			}
		}
		throw new IllegalArgumentException("No page factory found for page: " + pageClass);
	}

	/**
	 * Create page
	 * 
	 * @param pageName
	 * @return page instance or null if page wasn't found
	 */
	static Page<?> createPage(final String pageName) {
		if (pageName == null || pageName.isEmpty() || pageFactories == null) {
			return getDefaultPage();
		}
		for (final PageFactory pageFactory : pageFactories) {
			final Page<?> page = pageFactory.createPage(pageName);
			if (page != null) {
				return page;
			}
		}
		return null;
	}

	/**
	 * Go to page of chosen class
	 * 
	 * @param pageName
	 * @throws IllegalArgumentException
	 *             when page can't be displayed
	 */
	final public void goToPage(final String pageName) throws IllegalArgumentException {
		tracer(this).debug("Going to page: " + pageName);
		final Page<?> page = createPage(pageName);
		if (page == null) {
			throw new IllegalArgumentException("No page factory found for page: " + pageName);
		}
		placeController.goTo(page);
		page.display();
	}

	/**
	 * Add handler to event bus to handler display page events
	 * 
	 * @param eventBus
	 */
	private void registerInEventBus(final EventBus eventBus) {
		tracer(this).debug("Registering in event bus for listeing change page requests...");
		eventBus.addHandler(ClientEvent.TYPE, new SimpleBroadcastHandler<DisplayPageEvent>(this) {

			@Override
			protected void handleEvent(final DisplayPageEvent displayPageEvent) {
				tracer(this).debug("Change page request received: " + displayPageEvent.getPageClass() + ", request parameters: " + displayPageEvent.getRequestParameters());
				goToPage(displayPageEvent.getPageClass(), displayPageEvent.getRequestParameters());
			}

			@Override
			protected DisplayPageEvent castEvent(ClientEvent event) {
				if (event instanceof DisplayPageEvent) {
					return (DisplayPageEvent) event;
				}
				return null;
			}
		});
	}

	public static PageNavigator getInstance() {
		return instance;
	}

}
