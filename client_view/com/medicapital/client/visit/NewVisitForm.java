package com.medicapital.client.visit;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.medicapital.client.visit.CreateVisitPresenter;
import com.medicapital.client.visit.CreateVisitPresenterView;

public class NewVisitForm extends EditVisitDataForm implements CreateVisitPresenterView {

	private CreateVisitPresenter createVisitPresenter;

	public NewVisitForm() {
		getButtonDelete().setVisible(false);
		setButtonCloseWindow(getButtonCancel());
		buttonFindPatient.setVisible(true);
		initClickHandlers();
	}

	@Override
	protected void initVisitTypesHandler() {
		getEventType().addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				if (createVisitPresenter != null) {
					createVisitPresenter.lockVisitTypeTerm(getVisitTypes().get(getEventType().getValue(getEventType().getSelectedIndex())));
				}
			}
		});
	}

	private void initClickHandlers() {
		buttonAdd.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (createVisitPresenter != null) {
					createVisitPresenter.create();
				}
			}
		});
		buttonDelete.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (createVisitPresenter != null) {
					createVisitPresenter.create();
				}
			}
		});
		buttonCancel.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				cancelBookVisit();
			}
		});
		buttonFindPatient.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (createVisitPresenter != null) {
					createVisitPresenter.searchPatient();
				}
			}
		});
	}

	private void cancelBookVisit() {
		if (createVisitPresenter != null) {
			createVisitPresenter.cancelBookVisit();
		}
	}

	@Override
	public void setSaveHandlerEnabled(boolean enabled) {
		getButtonAdd().setEnabled(enabled);
	}

	@Override
	public void setCreateVisitPresenter(CreateVisitPresenter createVisitPresenter) {
		this.createVisitPresenter = createVisitPresenter;
	}

}
