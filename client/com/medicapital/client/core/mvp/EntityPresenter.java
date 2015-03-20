package com.medicapital.client.core.mvp;

import static com.medicapital.client.log.Tracer.*;
import com.google.gwt.event.shared.EventBus;
import com.medicapital.client.dao.ServerCallback;
import com.medicapital.common.dao.entity.BasicEntityServiceAsync;
import com.medicapital.common.entities.SerializableEntity;

public class EntityPresenter<E extends SerializableEntity> extends AbstractPresenter<E> {

	private final EntityPresenterView<E> view;
	private final BasicEntityServiceAsync<E> entityService;
	private final EntityViewBinder<E> viewBinder;

	public EntityPresenter(Class<E> entityClass, EntityPresenterView<E> view, EntityViewBinder<E> viewBinder, EventBus eventBus, BasicEntityServiceAsync<E> entityService) {
		super(entityClass, view, eventBus);
		this.view = view;
		this.entityService = entityService;
		this.viewBinder = viewBinder;
	}

	public final void display(int entityId) {
		tracer(this).debug("Displaying entity - entityId: " + entityId);
		view.setViewBlocked(true);
		entityService.get(entityId, new ServerCallback<E>(view) {
			public void response(E result) {
				display(result);
			};
		});
	}

	public final EntityPresenterView<E> getEntityPresenterView() {
		return view;
	}

	@Override
	protected void displayEntityOnView(E entity) {
		viewBinder.display(entity);
	}

	@Override
	protected void clearView() {
		viewBinder.clear();
	}

	@Override
	public final void refreshDisplay(boolean redownloadData) {
		if (getCurrentEntity() != null) {
			display(getCurrentEntity().getId());
		}
	}

	protected final BasicEntityServiceAsync<E> getEntityService() {
		return entityService;
	}

	protected final EntityViewBinder<E> getViewBinder() {
		return viewBinder;
	}
}
