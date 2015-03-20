package com.medicapital.server.logic;

import static com.medicapital.server.log.Tracer.tracer;

import java.util.List;
import com.medicapital.common.dao.ServerException;
import com.medicapital.common.entities.Doctor;
import com.medicapital.common.entities.UserRole;
import com.medicapital.common.entities.search.SearchDoctorCriteria;
import com.medicapital.server.database.DaoDoctor;
import com.medicapital.server.database.DaoEntityAccess;
import com.medicapital.server.security.Secured;

public class DoctorFacade extends EntityFacade<Doctor> {

	private DaoDoctor daoDoctor;

	@Secured
	public List<Doctor> searchDoctors(SearchDoctorCriteria searchDoctorCriteria) {
		tracer(this).debug("Searching doctors: " + searchDoctorCriteria);
		return daoDoctor.searchDoctors(searchDoctorCriteria);
	}

	@Secured
	public int searchDoctorsCount(SearchDoctorCriteria searchDoctorCriteria) {
		tracer(this).debug("Searching doctors count: " + searchDoctorCriteria);
		return daoDoctor.searchDoctorCount(searchDoctorCriteria);
	}

	@Secured(role = UserRole.Doctor)
	@Override
	public void update(Doctor entity) throws ServerException {
		super.update(entity);
	}

	@Secured(role = UserRole.Doctor)
	@Override
	public void delete(int entityId) throws ServerException {
		throw new ServerException("Unsupported operation");
	}

	@Secured
	public Doctor get(String login) {
		tracer(this).debug("Get doctor by login: " + login);
		return daoDoctor.get(login);
	}

	@Secured
	public byte[] getDoctorGalleryPhoto(int doctorId, int photoNumber) {
		tracer(this).debug("Get doctor's gallery photo - doctorId: " + doctorId + ", photo number: " + photoNumber);
		final byte[] galleryPhoto = daoDoctor.getGalleryPhoto(doctorId, photoNumber);
		return galleryPhoto;
	}

	@Secured
	public byte[] getDoctorProfilePhoto(int doctorId) {
		tracer(this).debug("Get doctor's profile photo - doctorId: " + doctorId);
		final byte[] profilePhoto = daoDoctor.getProfilePhoto(doctorId);
		return profilePhoto;
	}

	public void setDaoDoctor(DaoDoctor daoDoctor) {
		this.daoDoctor = daoDoctor;
	}

	@Override
	protected DaoEntityAccess<Doctor> getDao() {
		return daoDoctor;
	}

}
