package com.medicapital.common.entities.search;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SearchCriteria implements IsSerializable {

	private int startRow;
	private int rowCount = 10;

	SearchCriteria() {
		// only from this package
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public int getRowCount() {
		return rowCount;
	}
}
