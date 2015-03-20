package com.medicapital.client.doctor.visit;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

public class CreateVisitTypeForm extends EditVisitTypeDataForm implements CreateVisitTypeView {

	private CreateVisitTypePresenter createVisitTypePresenter;

	public CreateVisitTypeForm() {
		initHandlers();
	}

	private void initHandlers() {
		buttonAdd.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (createVisitTypePresenter != null) {
					createVisitTypePresenter.create();
				}
			}
		});
	}

	@Override
	public void setSaveHandlerEnabled(boolean enabled) {
		buttonAdd.setEnabled(enabled);
	}

	@Override
	public void setCreatePresenter(CreateVisitTypePresenter createVisitTypePresenter) {
		this.createVisitTypePresenter = createVisitTypePresenter;
	}

}
