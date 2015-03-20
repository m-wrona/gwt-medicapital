package com.medicapital.client.doctor.work;

import java.util.Date;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.medicapital.client.ui.table.DataTable;
import com.medicapital.common.date.DateFactory;
import com.medicapital.common.date.DateFormatter;
import com.medicapital.common.entities.Day;
import com.medicapital.common.entities.WorkHours;

public class EditWorkHoursListForm extends DataTable<WorkHoursListHeader, WorkHoursListRow, WorkHours> implements EditableWorkHoursListView {

	private final DateFormatter dateFormatter = DateFactory.createDateFormatter();
	private EditableWorkHoursListPresenter editPresenter;

	public EditWorkHoursListForm() {
		setDeleteSelectedButtonVisible(true);
		setSelectAllEnabled(true);
		getButtonAdd().setVisible(true);
		initHandlers();
	}

	@Override
	protected WorkHoursListHeader createHeader() {
		return new WorkHoursListHeader();
	}

	private void initHandlers() {
		getButtonAdd().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (editPresenter != null) {
					editPresenter.createWorkHours();
				}
			}
		});
	}

	@Override
	public void display(final int workHourId, Day day, String workHours, String address, String description) {
		WorkHoursListRow row = new WorkHoursListRow();
		row.setRowId(workHourId);
		row.getDateDay().setText(day.toString());
		row.getWorkHours().setText(workHours);
		row.getAddress().setText(address);
		row.getDescription().setText(description);
		row.getButtonEdit().setVisible(true);
		getHandlers().add(row.getButtonEdit().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (editPresenter != null) {
					editPresenter.editWorkHours(workHourId);
				}

			}
		}));
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
		row.getButtonEdit().setVisible(true);
		getHandlers().add(row.getButtonEdit().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (editPresenter != null) {
					editPresenter.editWorkHours(workHourId);
				}

			}
		}));
		addRow(row);
	}

	@Override
	public void setEditWorkHoursPresenter(EditableWorkHoursListPresenter editPresenter) {
		this.editPresenter = editPresenter;
		setEditableEntitiesPresenter(editPresenter);
	}

}
