package com.medicapital.client.pages.doctor;

import java.util.Date;
import java.util.Map;
import com.medicapital.client.doctor.work.EditableWorkHoursListPresenter;
import com.medicapital.client.doctor.work.WorkHoursFactory;
import com.medicapital.client.pages.DisplayPageEvent;
import com.medicapital.client.pages.DoctorPage;
import com.medicapital.common.commands.doctor.work.SelectWorkHoursCommand;
import com.medicapital.common.commands.doctor.work.SelectWorkHoursCountCommand;
import com.medicapital.common.date.DateFactory;
import com.medicapital.common.date.DateManager;

public class EditDoctorWorkHoursPage extends DoctorPage<EditDoctorWorkHoursPageForm> {

	private final WorkHoursFactory factory = new WorkHoursFactory();
	private EditableWorkHoursListPresenter workHoursListPresenter;

	@Override
	protected EditDoctorWorkHoursPageForm createPageView() {
		return new EditDoctorWorkHoursPageForm();
	}

	@Override
	protected void initPage(EditDoctorWorkHoursPageForm pageView) {
		workHoursListPresenter = factory.createEditWorkHoursList(pageView.getWorkHoursList(), pageView.getCreateWorkHoursView(), pageView.getEditWorkHoursView());
		pageView.setPagePresenter(this);
		getPresenters().add(workHoursListPresenter);
	}

	@Override
	public void loadState(Map<String, String> pageStateParameters) {
		factory.getCommandFactory().setLogin(getLoggedUser());
		factory.getCommandFactory().setUserId(getLoggedUserId());
		workHoursListPresenter.init(getLoggedUserId());
		Date today = new Date();
		Date month = new Date();
		DateManager dateManager = DateFactory.createDateManager();
		dateManager.addDays(month, 30);
		getPageView().setFilterDates(today, month);
		filterView(today, month);
	}

	public void filterView(Date dateFrom, Date dateTo) {
		SelectWorkHoursCommand selectCommand = new SelectWorkHoursCommand();
		selectCommand.setDoctorId(getLoggedUserId());
		selectCommand.setDateFrom(dateFrom);
		selectCommand.setDateTo(dateTo);
		factory.getCommandFactory().setSelectCommand(selectCommand);
		SelectWorkHoursCountCommand selectCountCommand = new SelectWorkHoursCountCommand();
		selectCountCommand.setDoctorId(getLoggedUserId());
		selectCountCommand.setDateFrom(dateFrom);
		selectCountCommand.setDateTo(dateTo);
		factory.getCommandFactory().setSelectCountCommand(selectCountCommand);
		workHoursListPresenter.refreshDisplay();
	}

	public static DisplayPageEvent createPageEvent(final Object sender) {
		final DisplayPageEvent pageEvent = new DisplayPageEvent(sender, EditDoctorWorkHoursPage.class);
		return pageEvent;
	}

}
