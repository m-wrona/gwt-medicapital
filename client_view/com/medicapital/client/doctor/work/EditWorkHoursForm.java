package com.medicapital.client.doctor.work;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

public class EditWorkHoursForm extends EditWorkHoursDataForm implements EditWorkHoursView {

	private EditWorkHoursPresenter editWorkHoursPresenter;

	public EditWorkHoursForm() {
		buttonDelete.setVisible(true);
		initHandlers();
	}

	private void initHandlers() {
		buttonAdd.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (editWorkHoursPresenter != null) {
					editWorkHoursPresenter.update();
				}

			}
		});
		buttonDelete.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (editWorkHoursPresenter != null) {
					editWorkHoursPresenter.delete();
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
	public void setEditPresenter(EditWorkHoursPresenter editWorkHoursPresenter) {
		this.editWorkHoursPresenter = editWorkHoursPresenter;
	}

}
