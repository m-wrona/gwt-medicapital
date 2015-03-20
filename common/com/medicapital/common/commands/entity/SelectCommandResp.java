package com.medicapital.common.commands.entity;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import com.medicapital.common.commands.CommandResp;
import com.medicapital.common.entities.SerializableEntity;

public class SelectCommandResp<E extends SerializableEntity> extends CommandResp<E> {

	private Set<E> data;

	/**
	 * Constructor for RPC communication
	 */
	protected SelectCommandResp() {

	}

	public SelectCommandResp(Class<E> objectClass, Collection<E> data) {
		super(objectClass);
		this.data = new LinkedHashSet<E>();
		this.data.addAll(data);
	}

	public SelectCommandResp(Class<E> objectClass) {
		super(objectClass);
		this.data = new LinkedHashSet<E>();
	}

	public Collection<E> getData() {
		return data;
	}

	public void add(E element) {
		data.add(element);
	}

	public <T extends E> void addAll(Collection<T> elements) {
		if (elements != null) {
			data.addAll(elements);
		}
	}

	public int getCount() {
		return data.size();
	}

	@Override
	public String toString() {
		return "[" + getClass() + ", count=" + data.size() + "]";
	}

}
