package com.medicapital.client.doctor;

import com.google.gwt.event.shared.EventBus;
import com.medicapital.client.core.Callback;
import com.medicapital.client.core.mvp.SearchEntityPresenter;
import com.medicapital.client.dao.ServerCallback;
import com.medicapital.client.exception.UnsupportedOperationException;
import com.medicapital.common.dao.entity.DoctorServiceAsync;
import com.medicapital.common.entities.Doctor;
import com.medicapital.common.entities.Localization;
import com.medicapital.common.entities.search.SearchDoctorCriteria;
import com.medicapital.common.entities.search.SearchResult;

public final class SearchDoctorsPresenter extends SearchEntityPresenter<Doctor> {

	private final SearchDoctorsView view;
	private final DoctorServiceAsync doctorService;
	private final DoctorPresenter doctorPresenter;

	public SearchDoctorsPresenter(SearchDoctorsView view, EventBus eventBus, DoctorServiceAsync doctorService, DoctorPresenter doctorPresenter) {
		super(Doctor.class, view, eventBus);
		this.view = view;
		this.doctorService = doctorService;
		this.doctorPresenter = doctorPresenter;
		initHandlers();
	}

	private void initHandlers() {
		whenEntityChoosen(new Callback<Doctor>() {
			@Override
			public void onDone(Doctor result) {
				displayDoctorDetails(result.getId());
			}
		});
	}

	@Override
	protected void displayEntityOnView(Doctor doctor) {
		view.addDoctor(doctor.getId(), doctor.getUser().getLastName(), doctor.getUser().getFirstName(), doctor.getAverageEvaluation());
	}

	@Override
	protected void getElements(int startRow, int elementCount) {
		doctorService.searchDoctors(createSearchCriteria(view.getSearchCriteria()), new ServerCallback<SearchResult<Doctor>>() {
			@Override
			public void response(SearchResult<Doctor> result) {
				display(result.getResults());
				setTotalRows(result.getTootalResultsCount());
			}
		});
	}

	private SearchDoctorCriteria createSearchCriteria(SearchDoctorCriteriaView searchView) {
		SearchDoctorCriteria searchDoctorCriteria = new SearchDoctorCriteria();
		searchDoctorCriteria.setFirstName(searchView.getFirstName());
		searchDoctorCriteria.setLastName(searchView.getLastName());
		searchDoctorCriteria.setEvaluationFrom(searchView.getEvaluationFrom());
		searchDoctorCriteria.setEvaluationTo(searchView.getEvaluationTo());
		searchDoctorCriteria.setSex(searchView.getSex());
		if (searchView.getCityId() > 0) {
			Localization localization = new Localization();
			searchDoctorCriteria.setLocalization(localization);
			localization.setCityId(searchView.getCityId());
			localization.setAddress(searchView.getAddress());
		}
		return searchDoctorCriteria;
	}

	/**
	 * Display details of the doctor
	 * 
	 * @param doctorId
	 * @throws IllegalArgumentException
	 *             when doctor presenter is not set or doctor is not displayed
	 *             on this list
	 */
	public void displayDoctorDetails(int doctorId) throws IllegalArgumentException {
		if (!getDisplayedElements().containsKey(doctorId)) {
			throw new IllegalArgumentException("Doctor isn't displayed on list - doctor id: " + doctorId);
		} else if (doctorPresenter == null) {
			throw new IllegalArgumentException("Doctor presenter is not set");
		}
		doctorPresenter.display(getDisplayedElements().get(doctorId));
	}

}
