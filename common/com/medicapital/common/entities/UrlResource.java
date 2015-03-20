package com.medicapital.common.entities;

import com.medicapital.common.util.Util;

/**
 * Entity represents resource that can be reached using URL address. It can be
 * image or document to down-load from the server.
 * 
 * @author mwronski
 * 
 */
public class UrlResource implements SerializableEntity {

	private int resourceId;
	private String description;
	private String url;
	private UrlResourceType urlResourceType = UrlResourceType.Unknown;

	@Override
	public int getId() {
		return resourceId;
	}

	@Override
	public void setId(int entityId) {
		resourceId = entityId;
	}

	public boolean isUrlEmpty() {
		return Util.isEmpty(url);
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrlResourceType(UrlResourceType urlResourceType) {
		this.urlResourceType = urlResourceType;
	}

	public UrlResourceType getUrlResourceType() {
		return urlResourceType;
	}

	@Override
	public String toString() {
		StringBuilder string = new StringBuilder("[");
		string.append("resourceId: ").append(resourceId).append(", ");
		string.append("url: ").append(url).append(", ");
		string.append("description: ").append(description).append(", ");
		string.append("urlResourceType: ").append(urlResourceType);
		string.append("]");
		return string.toString();
	}
}
