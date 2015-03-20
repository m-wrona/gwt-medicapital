package com.medicapital.client.ui.table;

import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;

public abstract class TableHeader extends Composite {

	protected CheckBox selectedAll;

	final public void setSelectedAll(CheckBox selectedAll) {
	    this.selectedAll = selectedAll;
    }
	
	final public CheckBox getSelectedAll() {
		return selectedAll;
	}
}
