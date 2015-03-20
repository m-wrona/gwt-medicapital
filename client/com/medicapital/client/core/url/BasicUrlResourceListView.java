package com.medicapital.client.core.url;

import java.util.Set;
import com.medicapital.client.core.PageableView;
import com.medicapital.common.entities.UrlResource;

public interface BasicUrlResourceListView extends PageableView {

	void addResource(Set<UrlResource> urlResources);

	void addResources(UrlResource urlResource);

	/**
	 * Clear view
	 */
	void clear();
}
