package com.medicapital.client.ui.listbox;

import java.util.Collection;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.ListBox;
import com.medicapital.client.ui.CityListBoxPresenter;
import com.medicapital.common.entities.City;

public class CityListBox {

	private final CityListBoxPresenter genericData = CityListBoxPresenter.getInstance();
	private final ListBox citiesListBox;
	private final ListBox regionListBox;

	public CityListBox(final ListBox cities, final ListBox regions) {
		citiesListBox = cities;
		regionListBox = regions;
		genericData.getData(this);
	}

	/**
	 * Initialize city list box
	 * 
	 * @param cities
	 */
	public void init(final Collection<City> cities) {
		initStatesListBox(cities);
		initCityListBox(cities);
		initHandlers();
	}

	private void initHandlers() {
		regionListBox.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(final ChangeEvent event) {
				final String regionName = regionListBox.getItemText(regionListBox.getSelectedIndex());
				initCityListBox(regionName.isEmpty() ? genericData.getCities() : genericData.findCitiesByRegion(regionName));
			}
		});

		citiesListBox.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(final ChangeEvent event) {
				final String cityName = citiesListBox.getItemText(citiesListBox.getSelectedIndex());
				final City city = genericData.findCity(cityName);
				if (city != null) {
					ListBoxUtils.selectItemOnListBox(regionListBox, city.getRegionName());
				}
			}
		});
	}

	/**
	 * Initialize list boxes related with states
	 * 
	 * @param cities
	 */
	private void initStatesListBox(final Collection<City> cities) {
		regionListBox.clear();
		regionListBox.addItem("", "");
		for (final City city : cities) {
			regionListBox.addItem(city.getRegionName(), "" + city.getRegionId());
		}
	}

	/**
	 * Initialize city list box
	 * 
	 * @param cityListBox
	 * @param cities
	 */
	private void initCityListBox(final Collection<City> cities) {
		citiesListBox.clear();
		citiesListBox.addItem("", "");
		for (final City city : cities) {
			citiesListBox.addItem(city.getName(), "" + city.getId());
		}
	}

	/**
	 * Select city on list box
	 * 
	 * @param cityId
	 */
	public void selectCity(final int cityId) {
		final City city = genericData.findCity(cityId);
		if (city != null) {
			ListBoxUtils.selectItemOnListBox(citiesListBox, city.getName());
			ListBoxUtils.selectItemOnListBox(regionListBox, city.getRegionName());
		} else {
			clear();
		}
	}

	/**
	 * Get cityId of selected city
	 * 
	 * @return
	 */
	public int getSelectedCityId() {
		if (citiesListBox.getSelectedIndex() == 0) {
			return 0;
		}
		final String cityId = citiesListBox.getValue(citiesListBox.getSelectedIndex());
		return Integer.parseInt(cityId);
	}

	/**
	 * Get name of selected city
	 * 
	 * @return
	 */
	public String getSelectedCityName() {
		return citiesListBox.getValue(citiesListBox.getSelectedIndex());
	}

	/**
	 * Clear list box
	 */
	public void clear() {
		citiesListBox.setSelectedIndex(0);
		regionListBox.setSelectedIndex(0);
	}
}
