package com.medicapital.client.doctor.work;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

public class CreateWorkHoursForm extends EditWorkHoursDataForm implements CreateWorkHoursView {

	private CreateWorkHoursPresenter createWorkHoursPresenter;

	public CreateWorkHoursForm() {
		initHandlers();
	}

	private void initHandlers() {
		buttonAdd.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (createWorkHoursPresenter != null) {
					createWorkHoursPresenter.create();
				}

			}
		});
	}

	@Override
	public void setSaveHandlerEnabled(boolean enabled) {
		buttonAdd.setEnabled(enabled);
	}

	@Override
	public void setCreatePresenter(CreateWorkHoursPresenter createWorkHoursPresenter) {
		this.createWorkHoursPresenter = createWorkHoursPresenter;
	}

}
