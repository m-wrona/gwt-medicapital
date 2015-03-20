package com.medicapital.client.user;

import java.util.Date;
import java.util.Set;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.medicapital.client.core.mvp.EntityPresenterView;
import com.medicapital.client.lang.Lang;
import com.medicapital.client.ui.PopupableComposite;
import com.medicapital.client.ui.listbox.CityListBox;
import com.medicapital.client.user.UserView;
import com.medicapital.common.date.DateFactory;
import com.medicapital.common.date.DateFormatter;
import com.medicapital.common.entities.User;
import com.medicapital.common.entities.User.Sex;

/**
 * Form display basic info about user
 * 
 * @author mwronski
 * 
 */
public class UserDisplayForm extends PopupableComposite implements UserView, EntityPresenterView<User> {

	interface MyUiBinder extends UiBinder<Widget, UserDisplayForm> {
	}

	private final DateFormatter dateFormatter = DateFactory.createDateFormatter();
	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	@UiField
	Label labelLogin;
	@UiField
	Label labelEmail;
	@UiField
	Label labelFirstName;
	@UiField
	Label labelLastName;
	@UiField
	Label labelBirthDate;
	@UiField
	Label labelSex;
	@UiField
	Label labelAddress;
	@UiField
	Label labelPostalCode;
	@UiField
	Label labelPhoneNumber;
	@UiField
	ListBox cities;
	@UiField
	ListBox regions;

	protected CityListBox cityListBox;

	public UserDisplayForm() {
		this(true);
	}

	public UserDisplayForm(boolean initWidget) {
		if (initWidget) {
			initWidget(uiBinder.createAndBindUi(this));
			initComponents();
		}
	}

	protected void initComponents() {
		cityListBox = new CityListBox(cities, regions);
	}

	@Override
	public void setLogin(String text) {
		labelLogin.setText(text);
	}

	@Override
	public void setEmail(String text) {
		labelEmail.setText(text);
	}

	@Override
	public void setFirstName(String text) {
		labelFirstName.setText(text);
	}

	@Override
	public void setLastName(String text) {
		labelLastName.setText(text);
	}

	@Override
	public void setPhoneNumber(String text) {
		labelPhoneNumber.setText(text);
	}

	@Override
	public void setMobile(String text) {
		// ignore
	}

	@Override
	public void setAddress(String text) {
		labelAddress.setText(text);
	}

	@Override
	public void setPostalCode(String text) {
		labelPostalCode.setText(text);
	}

	@Override
	public void setCityId(int cityId) {
		cityListBox.selectCity(cityId);
	}

	@Override
	public void setSex(Sex sex) {
		labelSex.setText(Lang.getSex(sex));
	}

	@Override
	public void setPersonalId(String text) {
		// ignore
	}

	@Override
	public void setHobbies(Set<Integer> hobbies) {
		// ignore
	}

	@Override
	public void setBirthDate(Date date) {
		labelBirthDate.setText(dateFormatter.toDateString(date));
	}

	@Override
	public void setLastLoginDate(Date date) {
		// ignore
	}

}
