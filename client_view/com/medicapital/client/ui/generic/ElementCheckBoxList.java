package com.medicapital.client.ui.generic;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.medicapital.common.entities.SerializableEntity;

public class ElementCheckBoxList<E extends SerializableEntity> {

	public interface CheckBoxItemDecorator<E> {
		/**
		 * Get item name for element
		 * 
		 * @param element
		 * @return
		 */
		String getItemName(E element);

	}

	private final Map<Integer, CheckBox> checkBoxMap = new HashMap<Integer, CheckBox>();
	private final Set<E> checkBoxElements = new LinkedHashSet<E>();
	private final VerticalPanel panel;
	private final CheckBoxItemDecorator<E> decorator;

	public ElementCheckBoxList(VerticalPanel panel, CheckBoxItemDecorator<E> decorator) {
		this.panel = panel;
		this.decorator = decorator;
	}

	/**
	 * Initialize check box list
	 * 
	 * @param checkBoxElements
	 */
	public void init(Collection<E> checkBoxElements) {
		for (final E element : checkBoxElements) {
			final CheckBox checkBox = new CheckBox(decorator.getItemName(element));
			panel.add(checkBox);
			checkBoxMap.put(element.getId(), checkBox);
		}
		this.checkBoxElements.addAll(checkBoxElements);
	}

	public Set<Integer> getSelectedElements() {
		Set<Integer> selectedElements = new HashSet<Integer>();
		for (Map.Entry<Integer, CheckBox> checkBox : checkBoxMap.entrySet()) {
			if (checkBox.getValue().getValue()) {
				selectedElements.add(checkBox.getKey());
			}
		}
		return selectedElements;
	}

	public void selectElements(Set<Integer> elementsIds) {
		setValueForAll(false);
		if (elementsIds != null) {
			for (int elementId : elementsIds) {
				if (checkBoxMap.containsKey(elementId)) {
					checkBoxMap.get(elementId).setValue(true);
				}
			}
		}
	}

	public void setValueForAll(final boolean value) {
		for (CheckBox checkBox : checkBoxMap.values()) {
			checkBox.setValue(value);
		}
	}

	public void clear() {
		setValueForAll(false);
	}
}
