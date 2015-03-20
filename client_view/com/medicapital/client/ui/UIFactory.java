package com.medicapital.client.ui;

import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.medicapital.client.lang.Lang;
import com.medicapital.client.ui.generic.ElementCheckBoxList;
import com.medicapital.client.ui.generic.ElementCheckBoxList.CheckBoxItemDecorator;
import com.medicapital.client.ui.listbox.ElementListBox;
import com.medicapital.client.ui.listbox.ElementListBox.ItemDecorator;
import com.medicapital.common.entities.Hobby;
import com.medicapital.common.entities.User.Sex;

/**
 * GUI regular utils for speed-up the work
 * 
 * @author michal
 * 
 */
final public class UIFactory {

	public static ElementListBox<Sex> createSexListBox(ListBox sexListBox) {
		return new ElementListBox<Sex>(sexListBox, new ItemDecorator<Sex>() {
			@Override
			public String getItemName(Sex element) {
				return Lang.getSex(element);
			}
		}, Sex.values());
	}

	public static ElementCheckBoxList<Hobby> createHobbiesCheckBoxList(VerticalPanel panel) {
		ElementCheckBoxList<Hobby> hobbyCheckBoxList = new ElementCheckBoxList<Hobby>(panel, new CheckBoxItemDecorator<Hobby>() {

			@Override
			public String getItemName(Hobby element) {
				return element.getName();
			}
		});
		HobbyCheckListPresenter.getInstance().getData(hobbyCheckBoxList);
		return hobbyCheckBoxList;
	}

	private UIFactory() {
		// empty
	}
}
