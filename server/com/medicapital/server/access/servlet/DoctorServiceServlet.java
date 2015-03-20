package com.medicapital.server.access.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.medicapital.common.dao.entity.DoctorService;
import com.medicapital.common.dao.entity.ServiceURL;
import com.medicapital.common.entities.Doctor;
import com.medicapital.common.entities.search.SearchDoctorCriteria;
import com.medicapital.common.entities.search.SearchResult;
import com.medicapital.server.logic.DoctorFacade;
import com.medicapital.server.logic.EntityFacade;
import com.medicapital.server.logic.UserFacade;

@Service(ServiceURL.DOCTOR_SERVICE)
public final class DoctorServiceServlet extends BasicServiceServlet<Doctor> implements DoctorService {

	private DoctorFacade doctorFacade;
	private UserFacade userFacade;

	@Override
    public SearchResult<Doctor> searchDoctors(SearchDoctorCriteria searchDoctorCriteria) {
	    SearchResult<Doctor> searchResult = new SearchResult<Doctor>();
	    searchResult.setResults(doctorFacade.searchDoctors(searchDoctorCriteria));
	    searchResult.setTootalResultsCount(doctorFacade.searchDoctorsCount(searchDoctorCriteria));
	    return searchResult;
    }
	
	@Override
	public boolean isLoginFree(String login) {
		return userFacade.isLoginFree(login);
	}

	@Override
	protected EntityFacade<Doctor> getEntityFacade() {
		return doctorFacade;
	}

	@Autowired(required = true)
	public void setDoctorFacade(DoctorFacade doctorFacade) {
		this.doctorFacade = doctorFacade;
	}

	@Autowired(required = true)
	public void setUserFacade(UserFacade userFacade) {
		this.userFacade = userFacade;
	}

}
