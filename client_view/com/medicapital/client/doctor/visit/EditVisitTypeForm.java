package com.medicapital.client.doctor.visit;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

public class EditVisitTypeForm extends EditVisitTypeDataForm implements EditVisitTypeView {

	private EditVisitTypePresenter editVisitTypePresenter;

	public EditVisitTypeForm() {
		buttonDelete.setVisible(true);
		initHandlers();
	}

	private void initHandlers() {
		buttonAdd.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (editVisitTypePresenter != null) {
					editVisitTypePresenter.update();
				}
			}
		});
		buttonDelete.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (editVisitTypePresenter != null) {
					editVisitTypePresenter.delete();
				}
			}
		});
	}

	@Override
	public void setDeleteHandlerEnabled(boolean enabled) {
		buttonDelete.setEnabled(enabled);
	}

	@Override
	public void setSaveHandlerEnabled(boolean enabled) {
		buttonAdd.setEnabled(enabled);
	}

	@Override
	public void setEditPresenter(EditVisitTypePresenter editVisitTypePresenter) {
		this.editVisitTypePresenter = editVisitTypePresenter;
	}

}
