package com.medicapital.client.ui.table;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.medicapital.client.core.RegistrationList;
import com.medicapital.client.core.entities.EditableEntitiesPresenter;
import com.medicapital.client.core.entities.EntitiesView;
import com.medicapital.common.entities.SerializableEntity;

public abstract class DataTable<H extends TableHeader, R extends TableRow, T extends SerializableEntity> extends TableNavigationPanel implements EntitiesView<T> {

	private final VerticalPanel table = new VerticalPanel();
	private final RegistrationList handlers = new RegistrationList();
	private final Map<Integer, CheckBox> tableCheckBoxes = new HashMap<Integer, CheckBox>();
	private final Map<Integer, R> rowIdRowMap = new HashMap<Integer, R>();
	private EditableEntitiesPresenter<T> editableEntitiesPresenter;
	private H header;

	public DataTable() {
		getDataPanel().add(table);
		setDeleteSelectedButtonVisible(false);
		initHeader();
		initHandlers();
	}

	private void initHandlers() {
		deleteSelected.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(final ClickEvent event) {
				final Set<Integer> selectedRowIds = getSelectedElements();
				deleteSelectedClickHandler(event, selectedRowIds);
			}
		});
	}

	final protected H getHeader() {
		return header;
	}

	/**
	 * Create table header
	 * 
	 * @return
	 */
	abstract protected H createHeader();

	private void initHeader() {
		header = createHeader();
		if (header.getSelectedAll() != null) {
			handlers.add(header.getSelectedAll().addClickHandler(new ClickHandler() {

				@Override
				public void onClick(final ClickEvent event) {
					setSelectAllEntities(header.getSelectedAll().getValue());
				}
			}));
		}
		table.add(header);
	}

	/**
	 * Add row into table
	 * 
	 * @param row
	 */
	final public void addRow(final R row) {
		addRow(null, row);
	}

	/**
	 * Add row into table
	 * 
	 * @param rowId
	 * @param row
	 */
	final public void addRow(Integer rowId, final R row) {
		table.add(row);
		rowIdRowMap.put(rowId, row);
		if (row.getSelected() != null && row.isEditable()) {
			handlers.add(row.getSelected().addClickHandler(new ClickHandler() {

				@Override
				public void onClick(final ClickEvent event) {
					if (editableEntitiesPresenter != null) {
						editableEntitiesPresenter.setEntitySelected(row.getRowId(), row.getSelected().getValue());
					}
				}
			}));
			tableCheckBoxes.put(row.getRowId(), row.getSelected());
		}
	}

	@Override
	final protected void deleteSelectedClickHandler(final ClickEvent clickEvent, final Set<Integer> selectedRowIds) {
		if (editableEntitiesPresenter != null) {
			editableEntitiesPresenter.deleteSelectedEntities();
		}
	}

	final public void setSelectAllEnabled(final boolean selectAllEnabled) {
		if (header != null && header.getSelectedAll() != null) {
			header.getSelectedAll().setEnabled(selectAllEnabled);
		}
	}

	final public boolean isSelectAllEnabled() {
		if (header != null && header.getSelectedAll() != null) {
			return header.getSelectedAll().isEnabled();
		} else {
			return false;
		}
	}

	final public void setEditableEntitiesPresenter(final EditableEntitiesPresenter<T> editableEntitiesPresenter) {
		this.editableEntitiesPresenter = editableEntitiesPresenter;
		setPageablePresenter(editableEntitiesPresenter);
	}

	@Override
	final public void clear() {
		table.clear();
		handlers.clear();
		tableCheckBoxes.clear();
		table.add(header);
		rowIdRowMap.clear();
	}

	final public void setSelectAllEntities(final boolean select) {
		if (header != null && header.getSelectedAll() != null) {
			header.getSelectedAll().setValue(select);
			for (final Map.Entry<Integer, CheckBox> visitCheckBox : tableCheckBoxes.entrySet()) {
				visitCheckBox.getValue().setValue(select);
				if (editableEntitiesPresenter != null) {
					editableEntitiesPresenter.setEntitySelected(visitCheckBox.getKey(), select);
				}
			}
		}
	}

	protected final Map<Integer, R> getRowIdRowMap() {
		return rowIdRowMap;
	}

	final public Set<Integer> getSelectedElements() {
		final Set<Integer> selectedRowIds = new HashSet<Integer>();
		for (final Map.Entry<Integer, CheckBox> tableCheckBox : tableCheckBoxes.entrySet()) {
			if (tableCheckBox.getValue().getValue()) {
				selectedRowIds.add(tableCheckBox.getKey());
			}
		}
		return selectedRowIds;
	}

	final public RegistrationList getHandlers() {
		return handlers;
	}

}
