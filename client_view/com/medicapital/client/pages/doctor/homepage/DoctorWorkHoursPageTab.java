package com.medicapital.client.pages.doctor.homepage;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.medicapital.client.doctor.work.WorkHoursFactory;
import com.medicapital.client.doctor.work.WorkHoursFilterForm;
import com.medicapital.client.doctor.work.WorkHoursListForm;
import com.medicapital.client.doctor.work.WorkHoursListPresenter;
import com.medicapital.common.commands.doctor.work.SelectWorkHoursCommand;
import com.medicapital.common.commands.doctor.work.SelectWorkHoursCountCommand;

public class DoctorWorkHoursPageTab extends Composite {

	private static DoctorWorkHoursPageTabUiBinder uiBinder = GWT.create(DoctorWorkHoursPageTabUiBinder.class);

	interface DoctorWorkHoursPageTabUiBinder extends UiBinder<Widget, DoctorWorkHoursPageTab> {
	}

	@UiField
	WorkHoursFilterForm filterWorkHours;
	@UiField
	WorkHoursListForm workHoursList;
	private WorkHoursListPresenter workHoursListPresenter;
	private WorkHoursFactory workHoursFactory;
	private int doctorId;

	public DoctorWorkHoursPageTab() {
		initWidget(uiBinder.createAndBindUi(this));
		initHandlers();
	}

	private void initHandlers() {
		ValueChangeHandler<Date> dateValueHandler = new ValueChangeHandler<Date>() {

			@Override
			public void onValueChange(ValueChangeEvent<Date> event) {
				if (workHoursListPresenter != null) {
					filterView(filterWorkHours.getDateFrom().getValue(), filterWorkHours.getDateTo().getValue());
				}
			}
		};
		filterWorkHours.getDateFrom().addValueChangeHandler(dateValueHandler);
		filterWorkHours.getDateTo().addValueChangeHandler(dateValueHandler);
	}

	public void init(int doctorId, WorkHoursListPresenter workHoursListPresenter, WorkHoursFactory workHoursFactory) {
		this.workHoursListPresenter = workHoursListPresenter;
		this.doctorId = doctorId;
		this.workHoursFactory = workHoursFactory;
	}

	public void filterView(Date dateFrom, Date dateTo) {
		filterWorkHours.getDateFrom().setValue(dateFrom);
		filterWorkHours.getDateTo().setValue(dateTo);
		SelectWorkHoursCommand selectCommand = new SelectWorkHoursCommand();
		selectCommand.setDoctorId(doctorId);
		selectCommand.setDateFrom(dateFrom);
		selectCommand.setDateTo(dateTo);
		workHoursFactory.getCommandFactory().setSelectCommand(selectCommand);
		SelectWorkHoursCountCommand selectCountCommand = new SelectWorkHoursCountCommand();
		selectCountCommand.setDoctorId(doctorId);
		selectCountCommand.setDateFrom(dateFrom);
		selectCountCommand.setDateTo(dateTo);
		workHoursFactory.getCommandFactory().setSelectCountCommand(selectCountCommand);
		workHoursListPresenter.refreshDisplay();
	}

	public WorkHoursListForm getWorkHoursList() {
		return workHoursList;
	}

}
