package com.medicapital.common.dao.entity;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.medicapital.common.entities.Doctor;
import com.medicapital.common.entities.search.SearchDoctorCriteria;
import com.medicapital.common.entities.search.SearchResult;

@RemoteServiceRelativePath(ServiceURL.BASE_URL + ServiceURL.DOCTOR_SERVICE)
public interface DoctorService extends BasicEntityService<Doctor> {

	SearchResult<Doctor> searchDoctors(SearchDoctorCriteria searchDoctorCriteria);

	/**
	 * Check whether login is not used yet
	 * 
	 * @param login
	 * @return
	 */
	boolean isLoginFree(String login);
}
