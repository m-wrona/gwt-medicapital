package com.medicapital.common.dao.entity;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.medicapital.common.entities.Doctor;
import com.medicapital.common.entities.search.SearchDoctorCriteria;
import com.medicapital.common.entities.search.SearchResult;

public interface DoctorServiceAsync extends BasicEntityServiceAsync<Doctor> {

	void isLoginFree(String login, AsyncCallback<Boolean> callback);

	void searchDoctors(SearchDoctorCriteria searchDoctorCriteria, AsyncCallback<SearchResult<Doctor>> callback);

}
