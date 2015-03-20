package com.medicapital.client.pages.doctor;

import static com.medicapital.common.util.Util.*;
import java.util.HashMap;
import java.util.Map;
import com.medicapital.client.doctor.DoctorFactory;
import com.medicapital.client.doctor.SearchDoctorsPresenter;
import com.medicapital.client.pages.UserPage;
import com.medicapital.common.entities.User.Sex;
import com.medicapital.common.util.MapUtils;

final public class SearchDoctorPage extends UserPage<SearchDoctorPageForm> {

	public static final String PAGE_NAME = "SearchDoctor";
	private static final String PARAM_FIRST_NAME = "FirstName";
	private static final String PARAM_LAST_NAME = "LastName";
	private static final String PARAM_SEX = "Sex";
	private static final String PARAM_ADDRESS = "Address";
	private static final String PARAM_CITY_ID = "CityId";

	private final DoctorFactory doctorFactory = new DoctorFactory();
	private SearchDoctorsPresenter doctorsPresenter;

	@Override
	protected SearchDoctorPageForm createPageView() {
		return new SearchDoctorPageForm();
	}

	@Override
	protected void initPage(SearchDoctorPageForm pageView) {
		doctorsPresenter = doctorFactory.createDoctorListPresenter(pageView.getDoctorList(), pageView.getDoctorsDetailsView());
		doctorsPresenter.goToFirstPage();
	}

	@Override
	final public void loadState(final Map<String, String> pageStateParameters) {
		final String firstName = MapUtils.getString(pageStateParameters, PARAM_FIRST_NAME, true);
		if (!isEmpty(firstName)) {
			getPageView().getSearchDoctor().setFirstName(firstName);
		}
		final String lastName = MapUtils.getString(pageStateParameters, PARAM_LAST_NAME, true);
		if (!isEmpty(lastName)) {
			getPageView().getSearchDoctor().setLastName(lastName);
		}
		final String address = MapUtils.getString(pageStateParameters, PARAM_ADDRESS, true);
		if (!isEmpty(address)) {
			getPageView().getSearchDoctor().setAddress(address);
		}
		final Integer cityId = MapUtils.getInt(pageStateParameters, PARAM_CITY_ID, true);
		if (cityId != null) {
			getPageView().getSearchDoctor().setCityId(cityId);
		}
		final String sex = MapUtils.getString(pageStateParameters, PARAM_SEX, true);
		if (!isEmpty(sex)) {
			getPageView().getSearchDoctor().setSex(Sex.valueOf(sex));
		}
	}

	@Override
	final public Map<String, String> saveState() {
		final Map<String, String> pageState = new HashMap<String, String>();
		if (!isEmpty(getPageView().getSearchDoctor().getFirstName())) {
			pageState.put(PARAM_FIRST_NAME, getPageView().getSearchDoctor().getFirstName());
		}
		if (!isEmpty(getPageView().getSearchDoctor().getLastName())) {
			pageState.put(PARAM_LAST_NAME, getPageView().getSearchDoctor().getLastName());
		}
		if (!isEmpty(getPageView().getSearchDoctor().getAddress())) {
			pageState.put(PARAM_ADDRESS, getPageView().getSearchDoctor().getAddress());
		}
		if (getPageView().getSearchDoctor().getCityId() > 0) {
			pageState.put(PARAM_CITY_ID, "" + getPageView().getSearchDoctor().getCityId());
		}
		if (getPageView().getSearchDoctor().getSex() != null) {
			pageState.put(PARAM_SEX, getPageView().getSearchDoctor().getSex().toString());
		}
		return pageState.isEmpty() ? null : pageState;
	}

}
