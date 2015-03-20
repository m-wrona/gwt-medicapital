package com.medicapital.client.doctor.work;

import java.util.Date;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public class WorkHoursFilterForm extends Composite {

	private static WorkHoursFilterFormUiBinder uiBinder = GWT.create(WorkHoursFilterFormUiBinder.class);

	interface WorkHoursFilterFormUiBinder extends UiBinder<Widget, WorkHoursFilterForm> {
	}

	@UiField
	DateBox dateFrom;
	@UiField
	DateBox dateTo;

	public WorkHoursFilterForm() {
		initWidget(uiBinder.createAndBindUi(this));
		initHandlers();
	}

	private void initHandlers() {
		ValueChangeHandler<Date> dateValueHandler = new ValueChangeHandler<Date>() {

			@Override
			public void onValueChange(ValueChangeEvent<Date> event) {
				Date from = dateFrom.getValue();
				Date to = dateTo.getValue();
				if (from != null && to != null) {
					if (from.after(to)) {
						dateFrom.setValue(to);
						dateTo.setValue(from);
					}
				}
			}
		};
		dateFrom.addValueChangeHandler(dateValueHandler);
		dateTo.addValueChangeHandler(dateValueHandler);
	}

	public DateBox getDateFrom() {
		return dateFrom;
	}

	public DateBox getDateTo() {
		return dateTo;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom.setValue(dateFrom);
	}

	public void setDateTo(Date dateTo) {
		this.dateTo.setValue(dateTo);
	}

}
