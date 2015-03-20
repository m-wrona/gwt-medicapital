package com.medicapital.client.core.mvp;

import static com.medicapital.client.log.Tracer.tracer;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Command;
import com.medicapital.client.dao.ServerCallback;
import com.medicapital.client.dao.ServiceResponse;
import com.medicapital.common.commands.entity.CreateCommandResp;
import com.medicapital.common.dao.entity.BasicEntityServiceAsync;
import com.medicapital.common.entities.SerializableEntity;
import com.medicapital.common.validation.EntityValidator;

public class CreateEntityPresenter<E extends SerializableEntity> extends EntityPresenter<E> {

	private final CreateEntityPresenterView<E> view;
	private EntityValidator<E> entityValidator;
	private Command afterCreatedCommand;

	public CreateEntityPresenter(Class<E> entityClass, CreateEntityPresenterView<E> view, EntityViewBinder<E> viewBinder, EventBus eventBus, BasicEntityServiceAsync<E> entityService) {
		super(entityClass, view, viewBinder, eventBus, entityService);
		this.view = view;
		initHandlers();
	}

	private void initHandlers() {
		view.getCreateClickHandler().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				create();
			}
		});
		view.getCancelClickHandler().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				getViewBinder().clear();
			}
		});
	}

	public final void create() {
		final E entityToCreate = getViewBinder().getEntityFromView(getCurrentEntity());
		tracer(this).debug("Creating entity: " + entityToCreate);
		if (entityValidator != null && !entityValidator.validate(entityToCreate)) {
			tracer(this).debug("Entity not valid: " + entityToCreate);
			return;
		}
		view.setViewBlocked(true);
		view.setEntityCreatedMsgVisible(false);
		view.setEntityNotCreatedMsgVisible(false);
		getEntityService().create(entityToCreate, new ServerCallback<Integer>(view) {

			@Override
			public boolean error(Throwable caught) {
				view.setEntityNotCreatedMsgVisible(true);
				return true;
			}

			@Override
			public void response(Integer createdEntityId) {
				boolean created = createdEntityId > 0;
				view.setEntityCreatedMsgVisible(created);
				view.setEntityNotCreatedMsgVisible(!created);
				if (created) {
					entityToCreate.setId(createdEntityId);
					display(entityToCreate);
					// broadcast change
					CreateCommandResp<E> createCommandResp = new CreateCommandResp<E>(getEntityClass(), createdEntityId);
					createCommandResp.setCreatedEntity(entityToCreate);
					getEventBus().fireEvent(new ServiceResponse<E>(CreateEntityPresenter.this, createCommandResp));
					if (afterCreatedCommand != null) {
						afterCreatedCommand.execute();
					}
				}
			}
		});
	}

	public final void whenEntityCreated(Command afterCreatedCommand) {
		this.afterCreatedCommand = afterCreatedCommand;
	}

	public final void setEntityValidator(EntityValidator<E> entityValidator) {
		this.entityValidator = entityValidator;
	}

}
