package com.medicapital.client.doctor.visit;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.medicapital.client.ui.table.DataTable;
import com.medicapital.common.date.DateFactory;
import com.medicapital.common.entities.VisitType;

public class EditVisitTypeListForm extends DataTable<EditVisitTypeListHeader, EditVisitTypeListRow, VisitType> implements EditableVisitTypeListView {

	private final NumberFormat timeFormat = DateFactory.getTimeFormat();
	private EditableVisitTypeListPresenter visitTypeListPresenter;

	public EditVisitTypeListForm() {
		setDeleteSelectedButtonVisible(true);
		setSelectAllEnabled(true);
		getButtonAdd().setVisible(true);
		initHandlers();
	}
	
	private void initHandlers() {
		getButtonAdd().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (visitTypeListPresenter != null) {
					visitTypeListPresenter.createVisitType();
				}
			}
		});
	}
	
	@Override
	protected EditVisitTypeListHeader createHeader() {
	    return new EditVisitTypeListHeader();
	}

	

	@Override
	public void display(final int visitTypeId, String name, int duration, String description) {
		EditVisitTypeListRow row = new EditVisitTypeListRow();
		row.setRowId(visitTypeId);
		row.getVisitType().setText(name);
		row.getDuration().setText(timeFormat.format(duration));
		row.getDescription().setText(description);
		row.getButtonEdit().setVisible(true);
		getHandlers().add(row.getButtonEdit().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (visitTypeListPresenter != null) {
					visitTypeListPresenter.editVisitType(visitTypeId);
				}

			}
		}));
		addRow(row);
	}

	@Override
	public void setEditablePresenter(EditableVisitTypeListPresenter visitTypeListPresenter) {
		this.visitTypeListPresenter = visitTypeListPresenter;
		setEditableEntitiesPresenter(visitTypeListPresenter);
	}

}
