package com.medicapital.client.pages.doctor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.medicapital.client.doctor.AboutDoctorForm;
import com.medicapital.client.doctor.ContactDoctorForm;
import com.medicapital.client.evaluation.VisitEvaluationListForm;
import com.medicapital.client.pages.doctor.DoctorHomePage.DoctorHomePageTabs;
import com.medicapital.client.pages.doctor.homepage.DoctorWorkHoursPageTab;

public class DoctorHomePageForm extends Composite {

	private static final String SUB_PAGE_CONTAINER_ID = "subPagePanelContainer";
	private static DoctorHomePageFormUiBinder uiBinder = GWT.create(DoctorHomePageFormUiBinder.class);

	interface DoctorHomePageFormUiBinder extends UiBinder<Widget, DoctorHomePageForm> {
	}

	private DoctorHomePage homePagePresenter;

	@UiField
	Anchor aboutMe;
	@UiField
	Anchor calendar;
	@UiField
	Anchor articles;
	@UiField
	Anchor evaluations;
	@UiField
	Anchor gallery;
	@UiField
	Anchor contact;
	@UiField
	Anchor workHours;
	@UiField
	HTMLPanel subPagePanel;

	public DoctorHomePageForm() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void displayTab(final DoctorHomePageTabs tab) {
		switch (tab) {
		case AboutMe:
			aboutMeClick(null);
			break;

		case Articles:
			articlesClick(null);
			break;

		case Calendar:
			calendarClick(null);
			break;

		case Contact:
			contactClick(null);
			break;

		case Evaluations:
			evaluationsClick(null);
			break;

		case Gallery:
			galleryClick(null);
			break;

		case WorkHours:
			workHoursClick(null);
			break;

		default:
			throw new UnsupportedOperationException("Unsupported page tab: " + tab);
		}
	}

	@UiHandler("contact")
	public void contactClick(ClickEvent event) {
		ContactDoctorForm contactDoctor = new ContactDoctorForm();
		if (homePagePresenter != null) {
			homePagePresenter.displayContact(contactDoctor);
		}
		setSubPage(contactDoctor);
	}

	@UiHandler("gallery")
	public void galleryClick(ClickEvent event) {
		if (homePagePresenter != null) {
			homePagePresenter.displayGallery();
		}
	}

	@UiHandler("evaluations")
	public void evaluationsClick(ClickEvent event) {
		VisitEvaluationListForm evaluationList = new VisitEvaluationListForm();
		if (homePagePresenter != null) {
			homePagePresenter.displayEvaluations(evaluationList);
		}
		setSubPage(evaluationList);
	}

	@UiHandler("articles")
	public void articlesClick(ClickEvent event) {
		if (homePagePresenter != null) {
			homePagePresenter.displayArticles();
		}
	}

	@UiHandler("calendar")
	public void calendarClick(ClickEvent event) {
		if (homePagePresenter != null) {
			homePagePresenter.displayCalendar();
		}
	}

	@UiHandler("aboutMe")
	public void aboutMeClick(ClickEvent event) {
		AboutDoctorForm aboutDoctor = new AboutDoctorForm();
		if (homePagePresenter != null) {
			homePagePresenter.displayAboutMe(aboutDoctor);
		}
		setSubPage(aboutDoctor);
	}

	@UiHandler("workHours")
	public void workHoursClick(ClickEvent event) {
		DoctorWorkHoursPageTab workHoursTab = new DoctorWorkHoursPageTab();
		if (homePagePresenter != null) {
			homePagePresenter.displayWorkHours(workHoursTab);
		}
		setSubPage(workHoursTab);
	}

	private void setSubPage(Widget widget) {
		clear();
		subPagePanel.add(widget, SUB_PAGE_CONTAINER_ID);
	}

	private void clear() {
		subPagePanel.clear();
	}

	public void setHomePagePresenter(DoctorHomePage homePagePresenter) {
		this.homePagePresenter = homePagePresenter;
	}

}
