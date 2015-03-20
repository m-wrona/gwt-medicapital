package com.medicapital.client.core.entity;

import static com.medicapital.client.log.Tracer.tracer;
import com.google.gwt.event.shared.EventBus;
import com.medicapital.client.core.commands.CreateCommandFactory;
import com.medicapital.client.dao.ServiceResponse;
import com.medicapital.common.commands.CommandResp;
import com.medicapital.common.commands.entity.CreateCommand;
import com.medicapital.common.commands.entity.CreateCommandResp;
import com.medicapital.common.dao.ResponseHandler;
import com.medicapital.common.entities.SerializableEntity;
import com.medicapital.common.validation.EntityValidator;

/**
 * Presenter allows to create new elements
 * 
 * @author michal
 * 
 * @param <E>
 *            element type
 */
abstract public class CreateEntityPresenter<E extends SerializableEntity> extends EntityPresenter<E> {

	private final CreateEntityView<E> display;
	private CreateCommandFactory<E> createCommandFactory;
	private EntityValidator<E> entityValidator;

	public CreateEntityPresenter(final Class<E> entityClass, final CreateEntityView<E> display, final EventBus eventBus) {
		super(entityClass, display, eventBus);
		this.display = display;
	}

	/**
	 * Get entity from view
	 * 
	 * @return
	 */
	abstract protected E getEntityFromView();

	/**
	 * Create element in service
	 * 
	 */
	public void create() {
		final E element = getEntityFromView();
		tracer(this).debug("Creating element: " + element);
		validatePresenter();
		if (element == null || !validateElement(element)) {
			tracer(this).debug("Element is not valid - create canceled");
			return;
		}
		display.setSaveHandlerEnabled(false);
		final CreateCommand<E> createCommand = createCommandFactory.createCreateCommand(element);
		getServiceAccess().execute(createCommand, new ResponseHandler<E>() {

			@Override
			public void handle(final CommandResp<E> response) {
				if (response instanceof CreateCommandResp) {
					display.setSaveHandlerEnabled(true);
					final CreateCommandResp<E> createCommandResp = (CreateCommandResp<E>) response;
					if (createCommandResp.wasCreated()) {
						// broadcast change
						element.setId(createCommandResp.getCreatedEntityId());
						createCommandResp.setCreatedEntity(element);
						getEventBus().fireEvent(new ServiceResponse<E>(CreateEntityPresenter.this, createCommandResp));
						afterEntityCreated(element);
					}
				}

			}

			@Override
			public void handleException(final Throwable throwable) {
				display.setSaveHandlerEnabled(true);
			}
		});

	}

	/**
	 * Perform activity after element was created
	 * 
	 * @param createdElement
	 */
	protected void afterEntityCreated(final E createdElement) {
		if (display.isDialogBox()) {
			display.setViewVisible(false);
		} else {
			display(createdElement);
		}
	}

	/**
	 * Check if element is correct
	 * 
	 * @param element
	 * @return
	 */
	protected boolean validateElement(final E element) {
		return entityValidator != null ? entityValidator.validate(element) : true;
	}

	@Override
	protected void validatePresenter() throws IllegalArgumentException {
		super.validatePresenter();
		if (createCommandFactory == null) {
			throw new IllegalArgumentException("Create command factory not set");
		}
	}

	final public void setCreateCommandFactory(final CreateCommandFactory<E> createCommandFactory) {
		this.createCommandFactory = createCommandFactory;
	}

	final public CreateCommandFactory<E> getCreateCommandFactory() {
		return createCommandFactory;
	}

	final public void setEntityValidator(EntityValidator<E> entityValidator) {
		this.entityValidator = entityValidator;
	}

	final public EntityValidator<E> getEntityValidator() {
		return entityValidator;
	}

}
