package com.medicapital.client.doctor.visit;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Widget;
import com.medicapital.client.ui.table.TableHeader;

class EditVisitTypeListHeader extends TableHeader {

	private static EditVisitTypeListHeaderUiBinder uiBinder = GWT.create(EditVisitTypeListHeaderUiBinder.class);

	interface EditVisitTypeListHeaderUiBinder extends UiBinder<Widget, EditVisitTypeListHeader> {
	}

	@UiField
	CheckBox selectAll;

	public EditVisitTypeListHeader() {
		initWidget(uiBinder.createAndBindUi(this));
		setSelectedAll(selectAll);
	}

}
