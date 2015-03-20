package com.medicapital.client.core.mvp;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.medicapital.client.core.Callback;
import com.medicapital.common.entities.SerializableEntity;

public abstract class SearchEntityPresenter<E extends SerializableEntity> extends EntitiesPresenter<E> {

	private final SearchEntityPresenterView<E> searchView;
	private Callback<E> entityChoosenCallback;

	public SearchEntityPresenter(Class<E> entityClass, SearchEntityPresenterView<E> searchView, EventBus eventBus) {
		super(entityClass, searchView, eventBus);
		this.searchView = searchView;
		initHandlers(eventBus);
	}

	private void initHandlers(EventBus eventBus) {
		if (searchView.getSearchClickHandler() != null) {
			searchView.getSearchClickHandler().addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					search();
				}
			});
		}
		if (searchView.getCancelClickHandler() != null) {
			searchView.getCancelClickHandler().addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					clearView();
				}
			});
		}
	}

	public final void search() {
		getElements(getStartRow(), getPageCount());
	}

	protected final void displayDataOnView(final E entity) {
		displayEntityOnView(entity);
		if (entityChoosenCallback != null) {
			getPageHandlers().add(searchView.getDisplayDetailClickHandler(entity.getId()).addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					entityChoosenCallback.onDone(entity);
				}
			}));
		}
	};

	public final void whenEntityChoosen(Callback<E> entityChoosenCallback) {
		this.entityChoosenCallback = entityChoosenCallback;
	}

	protected abstract void displayEntityOnView(E entity);

	protected final SearchEntityPresenterView<E> getSearchView() {
		return searchView;
	}

}
