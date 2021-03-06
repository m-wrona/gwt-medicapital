package com.medicapital.client.core.entity;

import static com.medicapital.client.log.Tracer.tracer;
import com.google.gwt.event.shared.EventBus;
import com.medicapital.client.core.Presenter;
import com.medicapital.client.core.commands.DisplayCommandFactory;
import com.medicapital.client.core.mvp.AbstractPresenter;
import com.medicapital.client.log.Tracer;
import com.medicapital.common.commands.CommandResp;
import com.medicapital.common.commands.entity.SelectCommand;
import com.medicapital.common.commands.entity.SelectCommandResp;
import com.medicapital.common.dao.ResponseHandler;
import com.medicapital.common.dao.ServiceAccess;
import com.medicapital.common.entities.SerializableEntity;

/**
 * Presenter handles displaying details of element.
 * 
 * @author michal
 * 
 * @param <E>
 *            element type which is displayed
 */
abstract public class EntityPresenter<E extends SerializableEntity> extends AbstractPresenter<E> implements Presenter {

	private ServiceAccess serviceAccess;
	private DisplayCommandFactory<E> displayCommandFactory;

	public EntityPresenter(final Class<E> entityClass, final EntityView entityView, final EventBus eventBus) {
		super(entityClass, entityView, eventBus);
	}

	/**
	 * Display element
	 * 
	 * @param element
	 */
	public void display(final E element) {
		super.display(element);
		if (element != null && getDisplayCommandFactory() != null) {
			getDisplayCommandFactory().setEntityId(element.getId());
		}
	}

	/**
	 * Display element according to command generated by display command factory
	 * 
	 * @param displayCommandFactory
	 */
	final public void display(final DisplayCommandFactory<E> displayCommandFactory) throws IllegalArgumentException {
		tracer(this).debug("Displaying element using display command factory");
		this.displayCommandFactory = displayCommandFactory;
		validatePresenter();
		getEntityFromService(serviceAccess, displayCommandFactory);
	}

	/**
	 * Validate state of presenter
	 * 
	 * @throws IllegalArgumentException
	 */
	protected void validatePresenter() throws IllegalArgumentException {
		if (serviceAccess == null) {
			throw new IllegalArgumentException("Service access not set");
		} else if (displayCommandFactory == null) {
			throw new IllegalArgumentException("Display command factory not set");
		}
	}

	/**
	 * Get entity data from service access
	 * 
	 * @param serviceAccess
	 * @param displayCommandFactory
	 */
	final protected void getEntityFromService(final ServiceAccess serviceAccess, final DisplayCommandFactory<E> displayCommandFactory) {
		final SelectCommand<E> selectCommand = displayCommandFactory.createSelectCommand();
		serviceAccess.execute(selectCommand, new ResponseHandler<E>() {

			@Override
			public void handle(final CommandResp<E> response) {
				final SelectCommandResp<E> selectCommandResp = (SelectCommandResp<E>) response;
				Tracer.tracer(this).debug("Received elements count: " + selectCommandResp.getCount());
				if (selectCommandResp.getCount() > 0) {
					display(selectCommandResp.getData().iterator().next());
				} else {
					clearView();
				}
			}

			@Override
			public void handleException(final Throwable throwable) {
				// ignore
			}

		});
	}

	/**
	 * Refresh display
	 * 
	 * @param redownloadData
	 *            if true data will be redownloaded from server
	 */
	public void refreshDisplay(final boolean redownloadData) {
		tracer(this).debug("RefreshDisplay: redownloadData=" + redownloadData);
		if (redownloadData) {
			display(displayCommandFactory);
		} else if (getCurrentEntity() != null) {
			displayEntityOnView(getCurrentEntity());
		}
	}

	final public void setServiceAccess(final ServiceAccess serviceAccess) {
		this.serviceAccess = serviceAccess;
	}

	final public ServiceAccess getServiceAccess() {
		return serviceAccess;
	}

	final public DisplayCommandFactory<E> getDisplayCommandFactory() {
		return displayCommandFactory;
	}

	final public void setDisplayCommandFactory(final DisplayCommandFactory<E> displayCommandFactory) {
		this.displayCommandFactory = displayCommandFactory;
	}

	@Override
	public EntityView getDisplay() {
	    return super.getDisplay();
	}

}
