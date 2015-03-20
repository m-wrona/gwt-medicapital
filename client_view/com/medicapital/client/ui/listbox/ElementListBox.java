package com.medicapital.client.ui.listbox;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import com.google.gwt.user.client.ui.ListBox;

public class ElementListBox<E> {

	public interface ItemDecorator<E> {
		/**
		 * Get item name for element
		 * 
		 * @param element
		 * @return
		 */
		String getItemName(E element);

	}

	protected final ListBox listBox;
	protected final ItemDecorator<E> itemDecorator;
	protected Map<Integer, E> dataMap = new HashMap<Integer, E>();

	public ElementListBox(ListBox listBox, ItemDecorator<E> itemDecorator, Collection<E> data) {
		this.listBox = listBox;
		this.itemDecorator = itemDecorator;
		addItem((E) null);
		for (E element : data) {
			addItem(element);
		}
	}

	public ElementListBox(ListBox listBox, ItemDecorator<E> itemDecorator, E element, E... elements) {
		this.listBox = listBox;
		this.itemDecorator = itemDecorator;
		addItem((E) null);
		addItem(element);
		for (E otherElement : elements) {
			addItem(otherElement);
		}
	}

	public ElementListBox(ListBox listBox, ItemDecorator<E> itemDecorator, E[] elements) {
		this.listBox = listBox;
		this.itemDecorator = itemDecorator;
		addItem((E) null);
		for (E otherElement : elements) {
			addItem(otherElement);
		}
	}

	private void addItem(E element) {
		String itemName = getItemName(element);
		listBox.addItem(itemName);
		dataMap.put(dataMap.size(), element);
	}

	private String getItemName(E element) {
		if (element != null) {
			String itemName = itemDecorator.getItemName(element);
			return itemName != null ? itemName : "";
		}
		return "";
	}

	public E getSelectedElement() {
		return dataMap.get(listBox.getSelectedIndex());
	}

	public void setElement(E element) {
		ListBoxUtils.selectItemOnListBox(listBox, getItemName(element));
	}

	public void clear() {
		listBox.setSelectedIndex(0);
	}

}
