package com.medicapital.client.visit;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Widget;
import com.medicapital.client.ui.table.TableHeader;

class VisitListFormHeader extends TableHeader {

	private static VisitListFormHeaderUiBinder uiBinder = GWT.create(VisitListFormHeaderUiBinder.class);

	interface VisitListFormHeaderUiBinder extends UiBinder<Widget, VisitListFormHeader> {
	}

	@UiField
	CheckBox selectAll;

	public VisitListFormHeader() {
		initWidget(uiBinder.createAndBindUi(this));
		setSelectedAll(selectAll);
	}

}
