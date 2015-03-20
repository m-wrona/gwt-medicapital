package com.medicapital.server.database;

import java.util.List;

import com.medicapital.common.entities.Doctor;
import com.medicapital.common.entities.search.SearchDoctorCriteria;

/**
 * Interface enables to perform operations on {@link Doctor}.
 * 
 * @author michal
 * 
 */
public interface DaoDoctor extends DaoEntityAccess<Doctor> {

	/**
	 * Get number of doctors
	 * 
	 * @return
	 * @throws DataAccessException
	 */
	int getCount() throws DataAccessException;

	/**
	 * Get doctor count using search criteria
	 * 
	 * @param searchCriteria
	 * @return
	 * @throws DataAccessException
	 */
	int searchDoctorCount(SearchDoctorCriteria searchCriteria) throws DataAccessException;

	/**
	 * Find doctors using search criteria
	 * 
	 * @param searchCriteria
	 * @param startRow
	 * @param maxRows
	 * @return
	 * @throws DataAccessException
	 */
	List<Doctor> searchDoctors(SearchDoctorCriteria searchCriteria) throws DataAccessException;

	/**
	 * Get doctor
	 * 
	 * @param login
	 * @return
	 * @throws DataAccessException
	 */
	Doctor get(String login) throws DataAccessException;

	/**
	 * Get photo of a doctor
	 * 
	 * @param doctorId
	 * @return
	 * @throws DataAccessException
	 */
	byte[] getProfilePhoto(int doctorId) throws DataAccessException;

	/**
	 * Get doctor's gallery photo
	 * 
	 * @param doctorId
	 * @param photoNumber
	 * @return
	 * @throws DataAccessException
	 */
	byte[] getGalleryPhoto(int doctorId, int photoNumber) throws DataAccessException;

}
