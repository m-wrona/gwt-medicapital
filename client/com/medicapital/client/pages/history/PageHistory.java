package com.medicapital.client.pages.history;

import static com.medicapital.client.log.Tracer.tracer;
import java.util.Map;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window.Location;
import com.medicapital.client.dao.DaoFactory;
import com.medicapital.client.event.ClientEvent;
import com.medicapital.client.event.SimpleBroadcastHandler;
import com.medicapital.client.pages.Page;
import com.medicapital.client.pages.PageTokenizer;

/**
 * Class observer changes of displayed pages and changes of history. When
 * history is backed/forwarded then class loads state of page if possible.
 * 
 * @author mwronski
 * 
 */
final public class PageHistory implements ValueChangeHandler<String> {

	private static PageHistory instance;
	private final PageTokenizer pageTokenizer = new PageTokenizer();

	public static PageHistory getInstance() {
		if (instance == null) {
			instance = new PageHistory(DaoFactory.getEventBus());
		}
		return instance;
	}

	private PageHistory(final EventBus eventBus) {
		registerInEventBus(eventBus);
		History.addValueChangeHandler(this);
	}

	@Override
	public void onValueChange(final ValueChangeEvent<String> event) {
		String pagePlace = event.getValue();
		tracer(this).debug("History changed: '" + pagePlace + "'");
		int placeTokenIndex = pagePlace.indexOf(PageTokenizer.PLACE_TOKEN);
		final String pageToken = pagePlace.substring(placeTokenIndex + PageTokenizer.PLACE_TOKEN.length());
		loadPageState(pageToken);
	}

	/**
	 * Load page state from history
	 * 
	 * @param pageToken
	 */
	public void loadPageState(String pageToken) {
		try {
			tracer(this).debug("Loading page state - page token: " + pageToken);
			String currentURL = Location.getHref();
			int pageIndex = currentURL.indexOf(PageTokenizer.PAGE_NAME);
			String baseUrl = currentURL.substring(0, pageIndex) + pageToken;
			tracer(this).debug("Loading page state - loaded page URL: " + baseUrl);
			Location.assign(baseUrl);
		} catch (final Exception e) {
			tracer(this).error("History changed: " + pageToken + " - error", e);
		}
	}

	/**
	 * Save state of the page in history
	 * 
	 * @param page
	 */
	private void savePageState(final Page<?> page) {
		tracer(this).debug("Saving page in history: " + page.getPageName());
		final Map<String, String> pageState = page.saveState();
		String pageToken = pageTokenizer.getPageToken(page, pageState);
		tracer(this).debug("Saving page in history - page token: " + pageToken);
	}

	/**
	 * Register handlers in event bus
	 * 
	 * @param eventBus
	 */
	private void registerInEventBus(final EventBus eventBus) {
		tracer(this).debug("Registering event bus handlers");
		eventBus.addHandler(ClientEvent.TYPE, new SimpleBroadcastHandler<PageChangedEvent>(this) {

			@Override
			protected PageChangedEvent castEvent(final ClientEvent event) {
				return event instanceof PageChangedEvent ? (PageChangedEvent) event : null;
			}

			@Override
			protected void handleEvent(final PageChangedEvent event) {
				savePageState(event.getPage());
			}

		});
	}

	/**
	 * Go to previous page from history
	 */
	public static void back() {
		History.back();
	}

	/**
	 * Go to next page in history
	 */
	public static void forward() {
		History.forward();
	}

}
