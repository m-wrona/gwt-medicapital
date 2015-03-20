package com.medicapital.client.pages.doctor;

import static com.medicapital.client.log.Tracer.tracer;
import static com.medicapital.common.util.Util.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.medicapital.client.doctor.DoctorFactory;
import com.medicapital.client.doctor.DoctorPresenter;
import com.medicapital.client.doctor.DoctorView;
import com.medicapital.client.doctor.work.WorkHoursFactory;
import com.medicapital.client.doctor.work.WorkHoursListPresenter;
import com.medicapital.client.evaluation.VisitEvaluationFactory;
import com.medicapital.client.evaluation.VisitEvaluationsPresenter;
import com.medicapital.client.evaluation.VisitEvaluationsView;
import com.medicapital.client.pages.DisplayPageEvent;
import com.medicapital.client.pages.UserPage;
import com.medicapital.client.pages.doctor.homepage.DoctorWorkHoursPageTab;
import com.medicapital.common.commands.evaluation.SelectDoctorEvaluationCommand;
import com.medicapital.common.commands.evaluation.SelectDoctorEvaluationCountCommand;
import com.medicapital.common.date.DateFactory;
import com.medicapital.common.date.DateManager;
import com.medicapital.common.util.MapUtils;

/**
 * Doctor home page
 * 
 * @author mwronski
 * 
 */
final public class DoctorHomePage extends UserPage<DoctorHomePageForm> {

	public enum DoctorHomePageTabs {
		AboutMe, Calendar, Articles, Gallery, Evaluations, Contact, WorkHours
	}

	public static final String PARAM_DOCTOR_ID = "DoctorId";
	public static final String PARAM_TAB = "Tab";
	private DoctorHomePageTabs currentTab = DoctorHomePageTabs.AboutMe;
	private int doctorId;
	private DoctorPresenter doctorPresenter;
	private VisitEvaluationsPresenter evaluationsPresenter;

	@Override
	protected DoctorHomePageForm createPageView() {
		return new DoctorHomePageForm();
	}

	@Override
	protected void initPage(DoctorHomePageForm pageView) {
		pageView.setHomePagePresenter(this);
	}

	@Override
	final public void loadState(final Map<String, String> pageStateParameters) {
		doctorId = MapUtils.getInt(pageStateParameters, PARAM_DOCTOR_ID, false);
		final String tab = MapUtils.getString(pageStateParameters, PARAM_TAB, true);
		if (!isEmpty(tab)) {
			currentTab = DoctorHomePageTabs.valueOf(tab);
		}
		getPageView().displayTab(currentTab);
	}

	@Override
	final public Map<String, String> saveState() {
		final Map<String, String> pageState = new HashMap<String, String>();
		pageState.put(PARAM_DOCTOR_ID, "" + doctorId);
		pageState.put(PARAM_TAB, currentTab.toString());
		return pageState;
	}

	/**
	 * Display info about doctor
	 */
	final public void displayAboutMe(DoctorView doctorView) {
		tracer(this).debug("Displaying option: 'about me', doctor: " + doctorId);
		currentTab = DoctorHomePageTabs.AboutMe;
		setDoctorPresenterView(doctorView);
	}

	/**
	 * Display doctor's calendar
	 */
	final public void displayCalendar() {
		tracer(this).debug("Displaying option: 'calendar', doctor: " + doctorId);
		currentTab = DoctorHomePageTabs.Calendar;
		getEventBus().fireEvent(DoctorCalendarPage.createPageEvent(this, doctorId));
	}

	/**
	 * Display doctor's articles
	 */
	final public void displayArticles() {
		tracer(this).debug("Displaying option: 'articles', doctor: " + doctorId);
		currentTab = DoctorHomePageTabs.Articles;
		// TODO
		throw new UnsupportedOperationException();
	}

	/**
	 * Display doctor's gallery
	 */
	final public void displayGallery() {
		tracer(this).debug("Displaying option: 'gallery', doctor: " + doctorId);
		// TODO
		throw new UnsupportedOperationException();
		// currentTab = DoctorHomePageTabs.Gallery;
		// if (doctorGalleryPresenter == null) {
		// DoctorFactory doctorFactory = new DoctorFactory();
		// DoctorPhotoGalleryForm doctorPhotoGalleryForm = new
		// DoctorPhotoGalleryForm();
		// doctorGalleryPresenter =
		// doctorFactory.createGalleryPresenter(doctorPhotoGalleryForm);
		// doctorGalleryPresenter.setDoctorId(doctorId);
		// if (doctorPresenter != null) {
		// doctorGalleryPresenter.setTotalRows(doctorPresenter.getCurrentEntity().getGalleryPhotoCount());
		// }
		// }
		// doctorGalleryPresenter.goToFirstPage();
		// pageView.setSubPage(evaluationsPresenter.getDisplay().asWidget());
	}

	/**
	 * Display contact information
	 */
	final public void displayContact(DoctorView doctorView) {
		tracer(this).debug("Displaying option: 'contact', doctor: " + doctorId);
		currentTab = DoctorHomePageTabs.Contact;
		setDoctorPresenterView(doctorView);
	}

	/**
	 * Display doctor's work hours
	 * 
	 * @param workHoursView
	 */
	final public void displayWorkHours(DoctorWorkHoursPageTab workHoursTab) {
		tracer(this).debug("Displaying option: 'work hours', doctor: " + doctorId);
		currentTab = DoctorHomePageTabs.WorkHours;
		WorkHoursFactory factory = new WorkHoursFactory();
		WorkHoursListPresenter workHoursList = factory.createWorkHoursListPresenter(workHoursTab.getWorkHoursList());
		workHoursTab.init(doctorId, workHoursList, factory);
		Date today = new Date();
		Date month = new Date();
		DateManager dateManager = DateFactory.createDateManager();
		dateManager.addDays(month, 30);
		workHoursTab.filterView(today, month);
	}

	/**
	 * Display doctor's evaluation
	 */
	final public void displayEvaluations(VisitEvaluationsView evaluationsView) {
		tracer(this).debug("Displaying option: 'evaluations', doctor: " + doctorId);
		currentTab = DoctorHomePageTabs.Evaluations;
		if (evaluationsPresenter == null) {
			// lazy init
			VisitEvaluationFactory evaluationFactory = new VisitEvaluationFactory();
			evaluationFactory.getCommandFactory().setSelectCommand(new SelectDoctorEvaluationCommand(doctorId));
			evaluationFactory.getCommandFactory().setSelectCountCommand(new SelectDoctorEvaluationCountCommand(doctorId));
			evaluationsPresenter = evaluationFactory.createEvaluationsPresenter(evaluationsView);
			evaluationsPresenter.refreshDisplay();
			getPresenters().add(evaluationsPresenter);
		} else {
			evaluationsPresenter.refreshDisplay(false);
		}
	}

	/**
	 * Set view for doctor presenter
	 * 
	 * @param doctorView
	 */
	private void setDoctorPresenterView(DoctorView doctorView) {
		DoctorFactory doctorFactory = new DoctorFactory();
		doctorFactory.getCommandFactory().setEntityId(doctorId);
		doctorPresenter = doctorFactory.createDoctorPresenter(doctorView);
		doctorPresenter.display(doctorId);
		getPresenters().add(doctorPresenter);
	}

	public static DisplayPageEvent createPageEvent(final Object sender, final int doctorId) {
		final DisplayPageEvent pageEvent = new DisplayPageEvent(sender, DoctorHomePage.class);
		pageEvent.addRequestParameter(PARAM_DOCTOR_ID, "" + doctorId);
		return pageEvent;
	}

}
