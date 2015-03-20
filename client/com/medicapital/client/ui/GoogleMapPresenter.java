package com.medicapital.client.ui;

import static com.medicapital.client.log.Tracer.tracer;
import java.util.HashMap;
import java.util.Map;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.geocode.Geocoder;
import com.google.gwt.maps.client.geocode.LatLngCallback;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;

/**
 * Class is responsible for marking address on Google map. To perform this
 * action address must be converted to localization first. Conversion is only
 * possible using Google servers.
 * 
 * @author michal
 * 
 */
final public class GoogleMapPresenter {

	private static GoogleMapPresenter instance = new GoogleMapPresenter();
	private final Map<String, LatLng> cache = new HashMap<String, LatLng>();

	public void getLocalization(final MapWidget googleMap, final String cityName, final String address) {
		final String addressText = cityName + "," + address;
		tracer(this).debug("Searching localization of address: " + addressText);
		if (cache.containsKey(addressText)) {
			displayLocalization(googleMap, cache.get(addressText));
			return;
		}
		final Geocoder geocoder = new Geocoder();
		geocoder.getLatLng(addressText, new LatLngCallback() {

			@Override
			public void onSuccess(LatLng point) {
				tracer(this).debug("Localization found: " + addressText + ", point: " + point);
				displayLocalization(googleMap, point);
				cache.put(addressText, point);
			}

			@Override
			public void onFailure() {
				tracer(this).error("Searching localization error for: " + addressText);
			}
		});
	}

	private void displayLocalization(MapWidget googleMap, LatLng point) {
		googleMap.setCenter(point, 15);
		googleMap.addOverlay(new Marker(point));
	}
	
	public static GoogleMapPresenter getInstance() {
		return instance;
	}
	
	private GoogleMapPresenter() {
		//empty
	}
}
