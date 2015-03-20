package com.medicapital.client.doctor.work;

import java.util.Date;
import com.medicapital.client.ui.table.DataTable;
import com.medicapital.common.date.DateFactory;
import com.medicapital.common.date.DateFormatter;
import com.medicapital.common.entities.Day;
import com.medicapital.common.entities.WorkHours;

public class WorkHoursListForm extends DataTable<WorkHoursListHeader, WorkHoursListRow, WorkHours> implements WorkHoursListView {

	private final DateFormatter dateFormatter = DateFactory.createDateFormatter();

	public WorkHoursListForm() {
		setDeleteSelectedButtonVisible(false);
		setSelectAllEnabled(false);
	}

	@Override
	protected WorkHoursListHeader createHeader() {
		return new WorkHoursListHeader();
	}

	@Override
	public void display(final int workHourId, Day day, String workHours, String address, String description) {
		WorkHoursListRow row = new WorkHoursListRow();
		row.setRowId(workHourId);
		row.getDateDay().setText(day.toString());
		row.getWorkHours().setText(workHours);
		row.getAddress().setText(address);
		row.getDescription().setText(description);
		addRow(row);
	}

	@Override
	public void display(final int workHourId, Date dateFrom, Date dateTo, String workHours, String address, String description) {
		String date = dateFormatter.toDateString(dateFrom);
		if (!dateTo.equals(dateFrom)) {
			date += "-" + dateFormatter.toDateString(dateTo);
		}
		WorkHoursListRow row = new WorkHoursListRow();
		row.setRowId(workHourId);
		row.getDateDay().setText(date);
		row.getWorkHours().setText(workHours);
		row.getAddress().setText(address);
		row.getDescription().setText(description);
		addRow(row);
	}

	@Override
	public void setWorkHoursListPresenter(WorkHoursListPresenter workHoursListPresenter) {
		setPageablePresenter(workHoursListPresenter);
	}

}
