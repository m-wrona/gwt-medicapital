package com.medicapital.client.doctor.work;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Widget;
import com.medicapital.client.ui.table.TableHeader;

class WorkHoursListHeader extends TableHeader {

	private static WorkHoursListHeaderUiBinder uiBinder = GWT.create(WorkHoursListHeaderUiBinder.class);

	interface WorkHoursListHeaderUiBinder extends UiBinder<Widget, WorkHoursListHeader> {
	}

	@UiField
	CheckBox selectAll;

	public WorkHoursListHeader() {
		initWidget(uiBinder.createAndBindUi(this));
		setSelectedAll(selectAll);
	}

}
