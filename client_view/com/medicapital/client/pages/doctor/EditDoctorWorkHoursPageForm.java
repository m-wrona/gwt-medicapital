package com.medicapital.client.pages.doctor;

import java.util.Date;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.medicapital.client.doctor.work.CreateWorkHoursForm;
import com.medicapital.client.doctor.work.CreateWorkHoursView;
import com.medicapital.client.doctor.work.EditWorkHoursForm;
import com.medicapital.client.doctor.work.EditWorkHoursListForm;
import com.medicapital.client.doctor.work.EditWorkHoursView;
import com.medicapital.client.doctor.work.WorkHoursFilterForm;

public class EditDoctorWorkHoursPageForm extends Composite {

	private static DoctorWorkHoursPageFormUiBinder uiBinder = GWT.create(DoctorWorkHoursPageFormUiBinder.class);

	interface DoctorWorkHoursPageFormUiBinder extends UiBinder<Widget, EditDoctorWorkHoursPageForm> {
	}

	@UiField
	WorkHoursFilterForm filterWorkHours;
	@UiField
	EditWorkHoursListForm workHoursList;

	private EditDoctorWorkHoursPage pagePresenter;

	public EditDoctorWorkHoursPageForm() {
		initWidget(uiBinder.createAndBindUi(this));
		initHandlers();
	}

	private void initHandlers() {
		ValueChangeHandler<Date> dateValueHandler = new ValueChangeHandler<Date>() {

			@Override
			public void onValueChange(ValueChangeEvent<Date> event) {
				if (pagePresenter != null) {
					pagePresenter.filterView(filterWorkHours.getDateFrom().getValue(), filterWorkHours.getDateTo().getValue());
				}
			}
		};
		filterWorkHours.getDateFrom().addValueChangeHandler(dateValueHandler);
		filterWorkHours.getDateTo().addValueChangeHandler(dateValueHandler);
	}

	public EditWorkHoursListForm getWorkHoursList() {
		return workHoursList;
	}

	public CreateWorkHoursView getCreateWorkHoursView() {
		CreateWorkHoursView view = new CreateWorkHoursForm();
		view.setShowAsDialogBox(true);
		return view;
	}

	public EditWorkHoursView getEditWorkHoursView() {
		EditWorkHoursView view = new EditWorkHoursForm();
		view.setShowAsDialogBox(true);
		return view;
	}

	public void setFilterDates(Date dateFrom, Date dateTo) {
		filterWorkHours.setDateFrom(dateFrom);
		filterWorkHours.setDateTo(dateTo);
	}

	public void setPagePresenter(EditDoctorWorkHoursPage pagePresenter) {
		this.pagePresenter = pagePresenter;
	}

}
