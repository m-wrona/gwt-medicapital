package com.medicapital.client.core.mvp;

import static com.medicapital.client.log.Tracer.*;
import java.util.HashSet;
import java.util.Set;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Command;
import com.medicapital.client.dao.ServerCallback;
import com.medicapital.client.dao.ServiceResponse;
import com.medicapital.common.commands.entity.DeleteCommandResp;
import com.medicapital.common.commands.entity.UpdateCommandResp;
import com.medicapital.common.dao.entity.BasicEntityServiceAsync;
import com.medicapital.common.entities.SerializableEntity;
import com.medicapital.common.util.AssertUtil;
import com.medicapital.common.validation.EntityValidator;

public class EditEntityPresenter<E extends SerializableEntity> extends EntityPresenter<E> {

	private final EditEntityPresenterView<E> view;
	private EntityValidator<E> entityValidator;
	private Command afterUpdatedCommand;
	private Command afterDeletedCommand;

	public EditEntityPresenter(Class<E> entityClass, EditEntityPresenterView<E> view, EntityViewBinder<E> viewBinder, EventBus eventBus, BasicEntityServiceAsync<E> entityService) {
		super(entityClass, view, viewBinder, eventBus, entityService);
		this.view = view;
		initHandlers();
	}

	private void initHandlers() {
		view.getUpdateClickHandler().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				update();
			}
		});
		view.getDeleteClickHandler().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				delete();
			}
		});
		view.getCancelClickHandler().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				getViewBinder().clear();
			}
		});
	}

	public final void update() {
		AssertUtil.assertNotNull(getCurrentEntity(), "No entity is displayed");
		final E entityToUpdate = getViewBinder().getEntityFromView(getCurrentEntity());
		entityToUpdate.setId(getCurrentEntity().getId());
		tracer(this).debug("Updating entity: " + entityToUpdate);
		if (entityValidator != null && !entityValidator.validate(entityToUpdate)) {
			tracer(this).debug("Entity not valid: " + entityToUpdate);
			return;
		}
		view.setViewBlocked(true);
		view.setEntityUpdatedMsgVisible(false);
		view.setEntityNotUpdatedMsgVisible(false);
		getEntityService().update(entityToUpdate, new ServerCallback<Boolean>(view) {

			@Override
			public boolean error(Throwable caught) {
				view.setEntityNotUpdatedMsgVisible(true);
				return true;
			}

			@Override
			public void response(Boolean result) {
				view.setEntityUpdatedMsgVisible(result);
				view.setEntityNotUpdatedMsgVisible(!result);
				if (result) {
					// broadcast change
					UpdateCommandResp<E> editCommandResp = new UpdateCommandResp<E>(getEntityClass(), 1);
					editCommandResp.setUpdatedEntity(entityToUpdate);
					getEventBus().fireEvent(new ServiceResponse<E>(EditEntityPresenter.this, editCommandResp));
					if (afterUpdatedCommand != null) {
						afterUpdatedCommand.execute();
					}
				}
			}
		});
	}

	public final void delete() {
		tracer(this).debug("Deleting entity: " + getCurrentEntity());
		AssertUtil.assertNotNull(getCurrentEntity(), "No entity is displayed");
		view.setViewBlocked(true);
		view.setEntityNotDeletedMsgVisible(false);
		view.setEntityDeletedMsgVisible(false);
		getEntityService().delete(getCurrentEntity().getId(), new ServerCallback<Boolean>(view) {
			@Override
			public boolean error(Throwable caught) {
				view.setEntityNotDeletedMsgVisible(true);
				return true;
			}

			@Override
			public void response(Boolean result) {
				view.setEntityDeletedMsgVisible(result);
				view.setEntityNotDeletedMsgVisible(!result);
				view.setUpdateButtonEnabled(!result);
				view.setDeleteButtonEnabled(!result);
				if (result) {
					// broadcast change
					DeleteCommandResp<E> deleteCommandResp = new DeleteCommandResp<E>(getEntityClass(), 1);
					Set<Integer> deletedElements = new HashSet<Integer>();
					deletedElements.add(getCurrentEntity().getId());
					deleteCommandResp.setDeletedElements(deletedElements);
					getEventBus().fireEvent(new ServiceResponse<E>(EditEntityPresenter.this, deleteCommandResp));
					if (afterDeletedCommand != null) {
						afterDeletedCommand.execute();
					}
				}
			}
		});
	}

	public final void whenEntityDeleted(Command afterDeletedCommand) {
		this.afterDeletedCommand = afterDeletedCommand;
	}

	public final void whenEntityUpdated(Command afterUpdatedCommand) {
		this.afterUpdatedCommand = afterUpdatedCommand;
	}

	public final void setEntityValidator(EntityValidator<E> entityValidator) {
		this.entityValidator = entityValidator;
	}

}
