package com.medicapital.client.pages.menu;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;

final class DoctorMenuForm extends UserMenuForm {

	interface DoctorMenuUiBinder extends UiBinder<Widget, DoctorMenuForm> {
	}

	private static DoctorMenuUiBinder uiBinder = GWT.create(DoctorMenuUiBinder.class);

	@UiField
	MenuItem myArticles;
	@UiField
	MenuItem addArticle;
	@UiField
	MenuItem doctorVisits;
	@UiField
	MenuItem patientsData;
	@UiField
	MenuItem workHours;
	@UiField
	MenuItem visitTypes;

	private DoctorMenu doctorMenuPresenter;

	public DoctorMenuForm() {
		super(false);
		initWidget(uiBinder.createAndBindUi(this));
		initMenu();
	}

	@Override
	protected void initMenu() {
		super.initMenu();
		patientsData.setCommand(new Command() {

			@Override
			public void execute() {
				if (doctorMenuPresenter != null) {
					doctorMenuPresenter.patientData();
				}
			}
		});
		workHours.setCommand(new Command() {

			@Override
			public void execute() {
				if (doctorMenuPresenter != null) {
					doctorMenuPresenter.editWorkHours();
				}
			}
		});
		visitTypes.setCommand(new Command() {

			@Override
			public void execute() {
				if (doctorMenuPresenter != null) {
					doctorMenuPresenter.editVisitTypes();
				}
			}
		});
	}

	@Override
	protected void initVisitOptions() {
		super.initVisitOptions();
		doctorVisits.setCommand(new Command() {

			@Override
			public void execute() {
				if (doctorMenuPresenter != null) {
					doctorMenuPresenter.doctorVisits();
				}
			}
		});
	}

	@Override
	protected void initArticlesOptions() {
		super.initArticlesOptions();
		myArticles.setCommand(new Command() {

			@Override
			public void execute() {
				if (doctorMenuPresenter != null) {
					doctorMenuPresenter.doctorArticles();
				}
			}
		});
		addArticle.setCommand(new Command() {

			@Override
			public void execute() {
				if (doctorMenuPresenter != null) {
					doctorMenuPresenter.addArticle();
				}
			}
		});
	}

	public void setDoctorMenuPresenter(DoctorMenu doctorMenuPresenter) {
		this.doctorMenuPresenter = doctorMenuPresenter;
	}

	public DoctorMenu getDoctorMenuPresenter() {
		return doctorMenuPresenter;
	}
}
