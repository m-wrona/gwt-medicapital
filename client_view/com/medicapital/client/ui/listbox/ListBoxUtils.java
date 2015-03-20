package com.medicapital.client.ui.listbox;

import com.google.gwt.user.client.ui.ListBox;

public class ListBoxUtils {

	/**
	 * Select value on list box
	 * 
	 * @param listBox
	 * @param value
	 */
	public static void selectValueOnListBox(final ListBox listBox, String value) {
		if (value != null) {
			for (int i = 0; i < listBox.getItemCount(); i++) {
				if (listBox.getValue(i).equals(value)) {
					listBox.setSelectedIndex(i);
					return;
				}
			}
		}
	}

	/**
	 * Select item on list box
	 * 
	 * @param listBox
	 * @param item
	 */
	public static void selectItemOnListBox(final ListBox listBox, String item) {
		for (int i = 0; i < listBox.getItemCount(); i++) {
			if (listBox.getItemText(i).equals(item)) {
				listBox.setSelectedIndex(i);
				return;
			}
		}
	}

	public static String getSelectedValue(final ListBox listBox) {
		return listBox.getValue(listBox.getSelectedIndex());
	}

	private ListBoxUtils() {
		// empty
	}
}
