package com.medicapital.client.doctor.work;

import static com.medicapital.client.ui.listbox.ListBoxUtils.*;
import java.util.Date;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.control.LargeMapControl;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.medicapital.client.lang.Lang;
import com.medicapital.client.ui.GoogleMapPresenter;
import com.medicapital.client.ui.NumberUtils;
import com.medicapital.client.ui.PopupableComposite;
import com.medicapital.client.ui.listbox.CityListBox;
import com.medicapital.common.date.DateFactory;
import com.medicapital.common.entities.Day;

class EditWorkHoursDataForm extends PopupableComposite implements SetterWorkHoursView, GetterWorkHoursView {

	private static EditWorkHoursDataFormUiBinder uiBinder = GWT.create(EditWorkHoursDataFormUiBinder.class);

	interface EditWorkHoursDataFormUiBinder extends UiBinder<Widget, EditWorkHoursDataForm> {
	}

	@UiField
	ListBox day;
	@UiField
	DateBox dateFrom;
	@UiField
	DateBox dateTo;
	@UiField
	ListBox startHour;
	@UiField
	ListBox startMinutes;
	@UiField
	ListBox endHour;
	@UiField
	ListBox endMinutes;
	@UiField
	TextBox description;
	@UiField
	ListBox regions;
	@UiField
	ListBox cities;
	@UiField
	TextBox address;
	@UiField
	TextBox postalCode;
	@UiField
	Button buttonAdd;
	@UiField
	Button buttonDelete;
	@UiField
	Button buttonCancel;
	@UiField
	CheckBox freeOfWork;
	@UiField
	MapWidget googleMapWidget;

	private CityListBox cityListBox;

	public EditWorkHoursDataForm() {
		initWidget(uiBinder.createAndBindUi(this));
		initForm();
		initHandlers();
		setButtonCloseWindow(buttonCancel);
		cityListBox = new CityListBox(cities, regions);
		googleMapWidget.addControl(new LargeMapControl());
	}

	private void initForm() {
		NumberFormat timeFormat = DateFactory.getTimeFormat();
		startHour.addItem("", "0");
		endHour.addItem("", "0");
		for (int hour = 0; hour < 24; hour++) {
			startHour.addItem("" + timeFormat.format(hour), "" + hour);
			endHour.addItem("" + timeFormat.format(hour), "" + hour);
		}
		startMinutes.addItem("", "0");
		endMinutes.addItem("", "0");
		for (int minutes = 0; minutes < 60; minutes++) {
			startMinutes.addItem("" + timeFormat.format(minutes), "" + minutes);
			endMinutes.addItem("" + timeFormat.format(minutes), "" + minutes);
		}
		day.addItem("", "-1");
		for (Day day : Day.values()) {
			this.day.addItem(Lang.getDay(day), "" + day.getInt());
		}
	}

	private void initHandlers() {
		day.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				dateFrom.setValue(null);
				dateTo.setValue(null);
			}
		});
		dateFrom.addValueChangeHandler(new ValueChangeHandler<Date>() {

			@Override
			public void onValueChange(ValueChangeEvent<Date> event) {
				selectValueOnListBox(day, "-1");
				if (dateTo.getValue() == null) {
					dateTo.setValue(dateFrom.getValue());
				}

			}
		});
		dateTo.addValueChangeHandler(new ValueChangeHandler<Date>() {

			@Override
			public void onValueChange(ValueChangeEvent<Date> event) {
				selectValueOnListBox(day, "-1");
				if (dateFrom.getValue() == null) {
					dateFrom.setValue(dateTo.getValue());
				}
			}
		});
		freeOfWork.addValueChangeHandler(new ValueChangeHandler<Boolean>() {

			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				startHour.setEnabled(!event.getValue());
				startMinutes.setEnabled(!event.getValue());
				endHour.setEnabled(!event.getValue());
				endMinutes.setEnabled(!event.getValue());
				address.setEnabled(!event.getValue());
				postalCode.setEnabled(!event.getValue());
				cities.setEnabled(!event.getValue());
				regions.setEnabled(!event.getValue());
				selectValueOnListBox(startHour, "-1");
				selectValueOnListBox(startMinutes, "-1");
				selectValueOnListBox(endHour, "-1");
				selectValueOnListBox(endMinutes, "-1");
				address.setText("");
				postalCode.setText("");
				cityListBox.clear();
			}
		});
	}

	@Override
	public void setAddress(String address) {
		this.address.setText(address);
		GoogleMapPresenter.getInstance().getLocalization(googleMapWidget, cityListBox.getSelectedCityName(), address);
	}

	@Override
	public String getAddress() {
		return address.getText();
	}

	@Override
	public void setCityId(int cityId) {
		cityListBox.selectCity(cityId);
	}

	@Override
	public int getCityId() {
		return cityListBox.getSelectedCityId();
	}

	@Override
	public void setPostalCode(String postalCode) {
		this.postalCode.setText(postalCode);
	}

	@Override
	public String getPostalCode() {
		return postalCode.getText();
	}

	@Override
	public Day getDay() {
		int selectedDay = NumberUtils.getInteger(getSelectedValue(day));
		return selectedDay != -1 ? Day.valueOf(selectedDay) : null;
	}

	@Override
	public Date getDateFrom() {
		return dateFrom.getValue();
	}

	@Override
	public Date getDateTo() {
		return dateTo.getValue();
	}

	@Override
	public int getStartHour() {
		return NumberUtils.getInteger(getSelectedValue(startHour));
	}

	@Override
	public int getStartMinutes() {
		return NumberUtils.getInteger(getSelectedValue(startMinutes));
	}

	@Override
	public int getEndHours() {
		return NumberUtils.getInteger(getSelectedValue(endHour));
	}

	@Override
	public int getEndMinutes() {
		return NumberUtils.getInteger(getSelectedValue(endMinutes));
	}

	@Override
	public String getDescription() {
		return description.getText();
	}

	@Override
	public void setDay(Day day) {
		selectValueOnListBox(this.day, day != null ? "" + day.getInt() : "");
	}

	@Override
	public void setDateFrom(Date date) {
		dateFrom.setValue(date);
	}

	@Override
	public void setDateTo(Date date) {
		dateTo.setValue(date);
	}

	@Override
	public void setStartHour(int hour) {
		selectValueOnListBox(this.startHour, "" + hour);
	}

	@Override
	public void setStartMinutes(int minutes) {
		selectValueOnListBox(this.startMinutes, "" + minutes);
	}

	@Override
	public void setEndHour(int hour) {
		selectValueOnListBox(this.endHour, "" + hour);
	}

	@Override
	public void setEndMinutes(int minutes) {
		selectValueOnListBox(this.endMinutes, "" + minutes);
	}

	@Override
	public void setDescription(String description) {
		this.description.setValue(description);
	}

}
