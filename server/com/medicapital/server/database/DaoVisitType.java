package com.medicapital.server.database;

import java.util.List;
import com.medicapital.common.entities.VisitType;

public interface DaoVisitType extends DaoEntityAccess<VisitType> {

	List<VisitType> getDoctorVisitTypes(int doctorId) throws DataAccessException;
	
}
