package com.medicapital.client.ui;

import static com.medicapital.client.log.Tracer.tracer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.medicapital.client.dao.DaoFactory;
import com.medicapital.client.ui.listbox.CityListBox;
import com.medicapital.common.commands.CommandResp;
import com.medicapital.common.commands.entity.SelectCommand;
import com.medicapital.common.commands.entity.SelectCommandResp;
import com.medicapital.common.dao.ResponseHandler;
import com.medicapital.common.entities.City;
import com.medicapital.common.entities.Hobby;

final public class CityListBoxPresenter {

	private static CityListBoxPresenter instance = new CityListBoxPresenter();
	private final Map<String, City> nameCityMap = new LinkedHashMap<String, City>();
	private final Map<Integer, City> idCityMap = new LinkedHashMap<Integer, City>();
	private final List<Hobby> hobbies = new ArrayList<Hobby>();

	public void getData(final CityListBox cityListBox) {
		if (nameCityMap.isEmpty()) {
			tracer(this).debug("Downloading city list...");
			DaoFactory.getServiceAccess().execute(new SelectCommand<City>(City.class, -1), new ResponseHandler<City>() {
				@Override
				public void handle(final CommandResp<City> response) {
					if (response instanceof SelectCommandResp<?>) {
						final SelectCommandResp<City> cityResp = (SelectCommandResp<City>) response;
						for (final City city : cityResp.getData()) {
							nameCityMap.put(city.getName(), city);
							idCityMap.put(city.getId(), city);
						}
						cityListBox.init(getCities());
					}
				}

				@Override
				public void handleException(final Throwable throwable) {
					// ignore
				}
			});
		} else {
			cityListBox.init(getCities());
		}
	}

	public Collection<Hobby> getHobbies() {
		return hobbies;
	}

	public Collection<City> getCities() {
		return nameCityMap.values();
	}

	public City findCity(final String cityName) {
		return nameCityMap.containsKey(cityName) ? nameCityMap.get(cityName) : null;
	}

	public Collection<City> findCitiesByRegion(final String regionName) {
		final List<City> cities = new ArrayList<City>();
		for (final City city : nameCityMap.values()) {
			if (city.getRegionName().equals(regionName)) {
				cities.add(city);
			}
		}
		return cities;
	}

	public City findCity(final int cityId) {
		return idCityMap.get(cityId);
	}

	public static CityListBoxPresenter getInstance() {
		return instance;
	}

	private CityListBoxPresenter() {
		// empty
	}

}
