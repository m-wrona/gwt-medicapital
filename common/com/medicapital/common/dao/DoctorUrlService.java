package com.medicapital.common.dao;

import java.util.Set;

public interface DoctorUrlService extends UrlService {

	/**
	 * Context of request
	 */
	String PARAM_CONTEXT = "context";

	/**
	 * Get doctor's profile photo
	 */
	String CONTEXT_DOCTOR_PROFILE = "getDoctorProfile";

	/**
	 * Get doctor's gallery photos
	 */
	String CONTEXT_DOCTOR_GALLERY = "getDoctorGallery";

	/**
	 * Primary ID
	 */
	String PARAM_ID = "id";

	/**
	 * Photo order number
	 */
	String PARAM_PHOTO_NUMBER = "photoNumber";

	/**
	 * Get URL to doctor's profile image
	 * 
	 * @param doctorId
	 * @return photo path
	 * @throws ServerException
	 */
	String getDoctorProfileImageURL(int doctorId);

	/**
	 * Get doctor's gallery photos
	 * 
	 * @param doctorId
	 * @param startPhoto
	 * @param photoCount
	 * @return set of URL paths to the photos
	 * @throws ServerException
	 */
	Set<String> getDoctorGalleryImageURL(int doctorId, int startPhoto, int photoCount);

}
