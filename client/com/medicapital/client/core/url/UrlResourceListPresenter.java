package com.medicapital.client.core.url;

import static com.medicapital.client.log.Tracer.tracer;
import java.util.ArrayList;
import java.util.List;
import com.medicapital.client.config.SystemSettings;
import com.medicapital.client.core.PageablePresenter;
import com.medicapital.common.dao.UrlService;
import com.medicapital.common.entities.SerializableEntity;
import com.medicapital.common.entities.UrlResource;

/**
 * Class allows to display URL resources in form of list
 * 
 * @param <E>
 *            entity to which URL resources belong
 * @param <U>
 *            UrlService which manages access to URL resources
 * @author mwronski
 * 
 */
abstract public class UrlResourceListPresenter<E extends SerializableEntity, U extends UrlService> extends PageablePresenter {

	private final BasicUrlResourceListView view;
	private final List<UrlResource> urlResources = new ArrayList<UrlResource>();

	public UrlResourceListPresenter(BasicUrlResourceListView view, E entity, U urlService) {
		super(view);
		this.view = view;
		setPageSize(SystemSettings.getUrlResourceListCount());
		init(entity, urlService, urlResources);
		setTotalRows(urlResources.size());
		goToFirstPage();
	}

	/**
	 * Initialize presenter by setting URL of resources and adding them to URL
	 * resources list.
	 * 
	 * @param entity
	 * @param urlService
	 */
	abstract protected void init(E entity, U urlService, List<UrlResource> urlResources);

	@Override
	final protected void displayCurrentPageData() {
		tracer(this).debug("Displaying url resources - start row: " + getStartRow() + ", page size: " + getPageSize() + ", photos count: " + urlResources.size());
		view.clear();
		for (int i = getStartRow(); (i < (getStartRow() + getPageSize()) && i < urlResources.size()); i++) {
			UrlResource urlImage = urlResources.get(i);
			view.addResources(urlImage);
		}
	}

	protected List<UrlResource> getUrlResources() {
		return urlResources;
	}

}
