package com.medicapital.client.ui.table;

import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;

abstract public class TableRow extends Composite {

	protected CheckBox selected;
	private int rowId;
	private boolean editable = true;

	final public void setSelected(CheckBox selected) {
		this.selected = selected;
	}

	final public CheckBox getSelected() {
		return selected;
	}

	final public void setRowId(int rowId) {
		this.rowId = rowId;
	}

	final public int getRowId() {
		return rowId;
	}

	final public void setEditable(boolean editable) {
		this.editable = editable;
	}

	final public boolean isEditable() {
		return editable;
	}

}
