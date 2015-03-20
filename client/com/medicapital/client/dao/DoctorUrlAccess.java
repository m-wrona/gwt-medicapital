package com.medicapital.client.dao;

import static com.medicapital.client.log.Tracer.tracer;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import com.medicapital.common.dao.DoctorUrlService;
import com.medicapital.common.dao.ServerException;
import com.medicapital.common.util.UrlUtils;

/**
 * Class enables access to URL resources of the server.
 * 
 * @author mwronski
 * 
 */
final public class DoctorUrlAccess implements DoctorUrlService {

	private static final String SERVICE_ENTRY_POINT = ServerAccess.SERVICE_MAIN_ENTRY_POINT + "doctorController";

	public DoctorUrlAccess() {
		tracer(this).debug("Connection to doctor URL access - OK, service entry point: " + SERVICE_ENTRY_POINT);
	}

	@Override
	public String getDoctorProfileImageURL(final int doctorId) throws ServerException {
		final Map<String, String> params = new LinkedHashMap<String, String>();
		params.put(PARAM_CONTEXT, CONTEXT_DOCTOR_PROFILE);
		params.put(PARAM_ID, "" + doctorId);
		params.put(PARAM_TIMESTAMP, "" + new Date().getTime());
		final String url = UrlUtils.buildUrlRequest(SERVICE_ENTRY_POINT, params);
		tracer(this).debug("Doctor id: " + doctorId + ", URL doctor profile picture: " + url);
		return url;
	}

	@Override
	public Set<String> getDoctorGalleryImageURL(final int doctorId, final int startPhoto, final int photoCount) throws ServerException {
		tracer(this).debug("Getting doctor gallery photos - doctor id: " + doctorId + ", startPhoto: " + startPhoto + ", photoCount: " + photoCount);
		final Set<String> galleryPhotos = new LinkedHashSet<String>();
		for (int i = startPhoto; i < photoCount; i++) {
			final Map<String, String> params = new LinkedHashMap<String, String>();
			params.put(PARAM_CONTEXT, CONTEXT_DOCTOR_GALLERY);
			params.put(PARAM_ID, "" + doctorId);
			params.put(PARAM_PHOTO_NUMBER, "" + i);
			params.put(PARAM_TIMESTAMP, "" + new Date().getTime());
			galleryPhotos.add(UrlUtils.buildUrlRequest(SERVICE_ENTRY_POINT, params));
		}
		return galleryPhotos;
	}

}
