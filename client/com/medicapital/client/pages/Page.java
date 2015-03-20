package com.medicapital.client.pages;

import static com.medicapital.client.log.Tracer.tracer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.medicapital.client.config.SystemSettings;
import com.medicapital.client.core.Presenter;
import com.medicapital.client.core.RegistrationList;
import com.medicapital.client.dao.DaoFactory;
import com.medicapital.client.event.ClientEvent;
import com.medicapital.client.event.SimpleBroadcastHandler;
import com.medicapital.client.exception.AccessDeniedException;
import com.medicapital.client.login.LoginEvent;
import com.medicapital.client.login.SessionManager;
import com.medicapital.client.pages.history.PageChangedEvent;
import com.medicapital.client.ui.WindowBlocker;
import com.medicapital.common.entities.LoginData;

/**
 * Class represents page which will be displayed on client side. It handles
 * creation of components which should be displayed and manages layout.
 * 
 * @author michal
 * 
 */
abstract public class Page<T extends Widget> extends Place implements Presenter {

	private static final String SERVICE_NAME = SystemSettings.getServiceName();
	private static Page<?> currentPage;
	private final RegistrationList registrationList = new RegistrationList();
	private final List<Presenter> presenters = new ArrayList<Presenter>();
	private final Map<String, String> requestParameters = new LinkedHashMap<String, String>();
	private final EventBus eventBus;
	private final SimplePanel pageContext = new SimplePanel();
	private T pageView;

	/**
	 * Create page
	 * 
	 */
	public Page() {
		if (currentPage != null) {
			currentPage.clearPresenter();
			WindowBlocker.clear();
		}
		tracer(this).debug("Creating page...");
		eventBus = DaoFactory.getEventBus();
		final RootPanel pageRootPanel = PageFrame.getInstance().getPageContext();
		pageRootPanel.clear();
		pageRootPanel.add(pageContext);
		initEventBusHandlers(eventBus);
		currentPage = this;
	}

	/**
	 * Create page view
	 * 
	 * @return
	 */
	abstract protected T createPageView();

	/**
	 * Init handlers for event from event bus
	 * 
	 * @param eventBus
	 */
	private void initEventBusHandlers(final EventBus eventBus) {
		registrationList.add(eventBus.addHandler(ClientEvent.TYPE, new SimpleBroadcastHandler<LoginEvent>(this) {

			@Override
			protected void handleEvent(final LoginEvent event) {
				validateAccess();
			}

			@Override
			protected LoginEvent castEvent(final ClientEvent event) {
				return event instanceof LoginEvent ? (LoginEvent) event : null;
			}
		}));
	}

	/**
	 * Get name of the page
	 * 
	 * @return
	 */
	final public String getPageName() {
		return PageFactory.getPageName(getClass());
	}

	/**
	 * Check if user can display page
	 * 
	 * @throws AccessDeniedException
	 */
	private final void validateAccess() throws AccessDeniedException {
		try {
			tracer(this).debug("Checking access rigths...");
			checkAccess(SessionManager.getInstacne().getLoginData(), requestParameters);
		} catch (final AccessDeniedException e) {
			tracer(this).error("Checking access rigths - access denied", e);
			tracer(this).error("Checking access rigths - access denied: redirecting into default page");
			PageNavigator.getInstance().goToDefaultPage();
			throw e;
		}
	}

	/**
	 * Check access rights
	 * 
	 * @param loginData
	 * @param requestParameters
	 * @throws AccessDeniedException
	 *             when access rights are not sufficient
	 */
	protected void checkAccess(final LoginData loginData, Map<String, String> requestParameters) throws AccessDeniedException {
		// public page
	}

	/**
	 * Get logged in user data
	 * 
	 * @return
	 */
	final protected LoginData getLoginData() {
		return SessionManager.getInstacne().getLoginData();
	}

	/**
	 * Display page context
	 * 
	 */
	final public void display() {
		try {
			tracer(this).debug("Displaying page...");
			validateAccess();
			clear();
			tracer(this).debug("Creating page layout...");
			pageView = createPageView();
			pageContext.add(pageView);
			tracer(this).debug("Initializing page...");
			initPage(pageView);
			tracer(this).debug("Loading page state");
			loadState(requestParameters);
			Window.setTitle(SERVICE_NAME + " - " + getPageName());
			eventBus.fireEvent(new PageChangedEvent(this, this));
		} catch (final AccessDeniedException e) {
			// this error is handled in validate access so skip it
		} catch (final Exception e) {
			tracer(this).error("Error occured while displaying page", e);
			tracer(this).error("Error occured while displaying page - redirecting into default page");
			PageNavigator.getInstance().goToDefaultPage();
		}
	}

	/**
	 * Initialize page by creating proper elements like presenters, handlers,
	 * etc.
	 * 
	 * @param pageView
	 *            page view which contains all elements needed to initialize
	 *            presenters etc.
	 */
	abstract protected void initPage(T pageView);

	/**
	 * Get view of this page
	 * 
	 * @return
	 */
	final protected T getPageView() {
		return pageView;
	}

	/**
	 * Get event bus
	 * 
	 * @return
	 */
	final protected EventBus getEventBus() {
		return eventBus;
	}

	/**
	 * Clear page context
	 */
	final public void clear() {
		tracer(this).debug("Clearing page...");
		pageContext.clear();
	}

	/**
	 * Clear event bus handlers
	 */
	@Override
	final public void clearPresenter() {
		tracer(this).debug("Clearing page handlers...");
		registrationList.clear();
		for (Presenter presenter : presenters) {
			if (presenter != null) {
				presenter.clearPresenter();
			}
		}
	}

	/**
	 * Get event bus handlers registrations
	 * 
	 * @return
	 */
	final protected RegistrationList getRegistrationList() {
		return registrationList;
	}

	/**
	 * Get page's presenters
	 * 
	 * @return
	 */
	final protected List<Presenter> getPresenters() {
		return presenters;
	}

	/**
	 * Add request parameter for the page
	 * 
	 * @param parameterName
	 * @param parameterValue
	 */
	final public void addRequestParameter(final String parameterName, final String parameterValue) {
		requestParameters.put(parameterName, parameterValue);
	}

	/**
	 * Add request parameters for the page
	 * 
	 * @param requestParameters
	 */
	final public void addRequestParameters(final Map<String, String> requestParameters) {
		if (requestParameters != null) {
			this.requestParameters.putAll(requestParameters);
		}
	}

	/**
	 * Get request parameters for this page
	 * 
	 * @return
	 */
	final protected Map<String, String> getRequestParameters() {
		return requestParameters;
	}

	/**
	 * Save page state
	 * 
	 * @return state of the page or null if page doesn't have any states
	 */
	public Map<String, String> saveState() {
		return null;
	}

	/**
	 * Load state of the page
	 * 
	 * @param pageStateParameters
	 *            parameters related with a state
	 */
	public void loadState(final Map<String, String> pageStateParameters) {
		// empty
	}

	/**
	 * Get logged user name
	 * 
	 * @return login of logged user or null if user is not logged in
	 */
	final protected String getLoggedUser() {
		LoginData loginData = SessionManager.getInstacne().getLoginData();
		if (loginData == null) {
			throw new AccessDeniedException("User is not logged in");
		}
		return loginData.getLogin();
	}

	/**
	 * Get ID of logged user
	 * 
	 * @return
	 */
	final protected int getLoggedUserId() {
		LoginData loginData = SessionManager.getInstacne().getLoginData();
		if (loginData == null) {
			throw new AccessDeniedException("User is not logged in");
		}
		return loginData.getId();
	}

}
