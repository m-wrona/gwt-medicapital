package com.medicapital.client.pages.history;

import com.medicapital.client.event.ClientEvent;
import com.medicapital.client.pages.Page;

/**
 * Event says that displayed page has changed.
 * 
 * @author mwronski
 * 
 */
final public class PageChangedEvent extends ClientEvent {

	private final Page<?> page;

	public PageChangedEvent(Object sender, Page<?> page) {
		super(sender);
		this.page = page;
	}

	public Page<?> getPage() {
		return page;
	}

}
