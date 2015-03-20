package com.medicapital.client.user;

import java.util.Date;
import java.util.Set;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.medicapital.client.ui.UIFactory;
import com.medicapital.client.ui.PopupableComposite;
import com.medicapital.client.ui.generic.ElementCheckBoxList;
import com.medicapital.client.ui.listbox.CityListBox;
import com.medicapital.client.ui.listbox.DateListBox;
import com.medicapital.client.ui.listbox.ElementListBox;
import com.medicapital.client.user.GetterUserDataView;
import com.medicapital.client.user.SetterUserDataView;
import com.medicapital.common.entities.Hobby;
import com.medicapital.common.entities.User.Sex;
import com.medicapital.common.validation.UserValidatorView;

public class EditUserDataForm extends PopupableComposite implements SetterUserDataView, GetterUserDataView, UserValidatorView {

	private static EditUserDataFormUiBinder uiBinder = GWT.create(EditUserDataFormUiBinder.class);

	interface EditUserDataFormUiBinder extends UiBinder<Widget, EditUserDataForm> {
	}

	@UiField
	TextBox login;
	@UiField
	HTMLPanel passwordPanel;
	@UiField
	PasswordTextBox password;
	@UiField
	PasswordTextBox password2;

	@UiField
	TextBox eMail;
	@UiField
	TextBox firstName;
	@UiField
	TextBox lastName;

	@UiField
	ListBox day;
	@UiField
	ListBox month;
	@UiField
	ListBox year;

	@UiField
	ListBox sex;
	@UiField
	TextBox address;
	@UiField
	TextBox postalCode;
	@UiField
	TextBox phoneNumber;
	@UiField
	ListBox cities;
	@UiField
	ListBox regions;
	@UiField
	TextBox mobile;
	@UiField
	TextBox personalId;

	@UiField
	PushButton buttonSave;
	@UiField
	Button buttonDelete;
	@UiField
	Button buttonClose;
	@UiField
	Button buttonCheckLogin;

	@UiField
	Label validationEmail;
	@UiField
	Label validationLogin;
	@UiField
	Label validationFirstName;
	@UiField
	Label validationLastName;
	@UiField
	Label validationSex;
	@UiField
	Label validationPassword;
	@UiField
	Label validationBirth;
	@UiField
	ScrollPanel panelHobbies;
	@UiField
	Label validationAddress;
	@UiField
	Label validationPostalCode;
	@UiField
	Label validationLocalization;
	@UiField
	Label validationCity;

	@UiField
	Label validationLoginFree;
	@UiField
	Label validationLoginExist;

	protected ElementListBox<Sex> sexListBox;
	protected DateListBox birthDateListBox;
	protected CityListBox cityListBox;
	protected ElementCheckBoxList<Hobby> hobbies;

	public EditUserDataForm() {
		this(true);
	}

	public EditUserDataForm(boolean initWidget) {
		if (initWidget) {
			initWidget(uiBinder.createAndBindUi(this));
			initListBoxes();
			initClickHandlers();
		}
	}

	@Override
	public boolean validateOptionalData() {
		validationPassword.setVisible(false);
		validationBirth.setVisible(false);
		boolean valid = true;
		if (!password.getText().equals(password2.getText())) {
			validationPassword.setVisible(true);
			valid = false;
		}
		if (!birthDateListBox.isDateValid()) {
			validationBirth.setVisible(true);
			valid = false;
		}
		return valid;
	}

	private void initClickHandlers() {
		buttonSave.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				handleSaveClick(event);
			}
		});
		buttonDelete.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				handleDeleteClick(event);
			}
		});
		buttonCheckLogin.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				handleCheckLoginClick(event);
			}
		});
	}

	/**
	 * Initialize list boxes like cities, birth date etc.
	 */
	private void initListBoxes() {
		sexListBox = UIFactory.createSexListBox(sex);
		birthDateListBox = new DateListBox(year, month, day);
		cityListBox = new CityListBox(cities, regions);

		// initialize hobby panel
		VerticalPanel panelHobbiesList = new VerticalPanel();
		hobbies = UIFactory.createHobbiesCheckBoxList(panelHobbiesList);
		panelHobbies.add(panelHobbiesList);
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	@Override
	public String getLogin() {
		return login.getText();
	}

	public void setLoginEnabled(boolean enabled) {
		login.setEnabled(enabled);
	}

	@Override
	public String getPassword() {
		return password.getText();
	}

	@Override
	public String getEmail() {
		return eMail.getText();
	}

	@Override
	public String getFirstName() {
		return firstName.getText();
	}

	@Override
	public String getLastName() {
		return lastName.getText();
	}

	@Override
	public String getPhoneNumber() {
		return phoneNumber.getText();
	}

	@Override
	public String getMobile() {
		return mobile.getText();
	}

	@Override
	public String getAddress() {
		return address.getText();
	}

	@Override
	public String getPostalCode() {
		return postalCode.getText();
	}

	@Override
	public int getCityId() {
		return cityListBox.getSelectedCityId();
	}

	@Override
	public Sex getSex() {
		return sexListBox.getSelectedElement();
	}

	@Override
	public String getPersonalId() {
		return personalId.getText();
	}

	@Override
	public Set<Integer> getHobbies() {
		return hobbies.getSelectedElements();
	}

	@Override
	public Date getBirthDate() {
		return birthDateListBox.getSelectedDate();
	}

	@Override
	public void setLogin(String text) {
		login.setText(text);
	}

	@Override
	public void setEmail(String text) {
		eMail.setText(text);
	}

	@Override
	public void setFirstName(String text) {
		firstName.setText(text);
	}

	@Override
	public void setLastName(String text) {
		lastName.setText(text);
	}

	@Override
	public void setPhoneNumber(String text) {
		phoneNumber.setText(text);
	}

	@Override
	public void setMobile(String text) {
		mobile.setText(text);
	}

	@Override
	public void setAddress(String text) {
		address.setText(text);

	}

	@Override
	public void setPostalCode(String text) {
		postalCode.setText(text);
	}

	@Override
	public void setCityId(int cityId) {
		cityListBox.selectCity(cityId);
	}

	@Override
	public void setSex(Sex sex) {
		sexListBox.setElement(sex);
	}

	@Override
	public void setPersonalId(String text) {
		personalId.setText(text);
	}

	@Override
	public void setHobbies(Set<Integer> hobbies) {
		this.hobbies.selectElements(hobbies);
	}

	@Override
	public void setBirthDate(Date date) {
		birthDateListBox.setDate(date);

	}

	@Override
	public void setLastLoginDate(Date date) {
		// ignore
	}

	protected void handleSaveClick(ClickEvent event) {
		// empty
	}

	protected void handleDeleteClick(ClickEvent event) {
		// empty
	}

	protected void handleCheckLoginClick(ClickEvent event) {
		// empty
	}

	public PushButton getButtonSave() {
		return buttonSave;
	}

	public Button getButtonDelete() {
		return buttonDelete;
	}

	public Button getButtonClose() {
		return buttonClose;
	}

	public Button getButtonCheckLogin() {
		return buttonCheckLogin;
	}

	public Label getValidationLogin() {
		return validationLogin;
	}

	public Label getValidationLoginExist() {
		return validationLoginExist;
	}

	public Label getValidationLoginFree() {
		return validationLoginFree;
	}

	@Override
	public void setLoginEmptyMsgVisible(boolean visible) {
		validationLogin.setVisible(visible);
	}

	@Override
	public void setWrongEmailMsgVisible(boolean visible) {
		validationEmail.setVisible(visible);
	}

	@Override
	public void setFirstNameEmptyMsgVisible(boolean visible) {
		validationFirstName.setVisible(visible);
	}

	@Override
	public void setLastNameEmptyMsgVisible(boolean visible) {
		validationLastName.setVisible(visible);
	}
	
}
