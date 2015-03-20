package com.medicapital.client.ui.table;

import com.medicapital.common.entities.SortOrder;

final public class DataColumn {

	private String columnName;
	private String entityColumnName;
	private SortOrder sortOrder;
	private boolean sortable = false;

	public DataColumn() {
		// empty
	}

	public DataColumn(String columnName) {
		this.columnName = columnName;
	}

	public DataColumn(String columnName, String entityColumnName) {
		this.columnName = columnName;
		this.entityColumnName = entityColumnName;
	}

	public SortOrder getSortOrder() {
		return sortOrder;
	}

	void changeSortOrder() {
		if (!sortable) {
			return;
		} else if (sortOrder == null) {
			sortOrder = SortOrder.Asc;
			return;
		}
		switch (sortOrder) {
		case Asc:
			sortOrder = SortOrder.Desc;
			break;

		case Desc:
			sortOrder = SortOrder.Asc;
			break;

		default:
			throw new RuntimeException("Unknown sort order: " + sortOrder);
		}

	}

	public boolean isSortable() {
		return sortable;
	}

	public void setSortable(boolean sortable) {
		this.sortable = sortable;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getEntityColumnName() {
		return entityColumnName;
	}

	public void setEntityColumnName(String entityColumnName) {
		this.entityColumnName = entityColumnName;
	}

}
