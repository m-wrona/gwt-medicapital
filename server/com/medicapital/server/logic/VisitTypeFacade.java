package com.medicapital.server.logic;

import java.util.List;
import com.medicapital.common.dao.ServerException;
import com.medicapital.common.entities.UserRole;
import com.medicapital.common.entities.VisitType;
import com.medicapital.server.database.DaoEntityAccess;
import com.medicapital.server.database.DaoVisitType;
import com.medicapital.server.security.Secured;

public class VisitTypeFacade extends EntityFacade<VisitType> {

	private DaoVisitType daoVisitType;
	
	@Secured
	public List<VisitType> getDoctorVisitTypes(int doctorId) {
		return daoVisitType.getDoctorVisitTypes(doctorId);
	}
	
	@Secured(role=UserRole.Doctor)
	@Override
	public int create(VisitType entity) throws ServerException {
	    return super.create(entity);
	}
	
	@Secured(role=UserRole.Doctor)
	@Override
	public void update(VisitType entity) throws ServerException {
	    super.update(entity);
	}
	
	@Secured(role=UserRole.Doctor)
	@Override
	public void delete(int entityId) throws ServerException {
	    super.delete(entityId);
	}
	
	@Override
    protected DaoEntityAccess<VisitType> getDao() {
	    return daoVisitType;
    }
	
	public void setDaoVisitType(DaoVisitType daoVisitType) {
	    this.daoVisitType = daoVisitType;
    }

}
