package com.medicapital.server.access.url;

import static com.medicapital.server.log.Tracer.tracer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import com.medicapital.common.dao.DoctorUrlService;
import com.medicapital.server.logic.DoctorFacade;

/**
 * Controller is responsible for handling image requests.
 * 
 * @author mwronski
 * 
 */
public class DoctorContoller extends HttpController {

	private DoctorFacade doctorFacade;

	@Override
	protected ModelAndView handleHttpRequest(final HttpServletRequest req, final HttpServletResponse resp) throws Exception {
		final String context = req.getParameter(DoctorUrlService.PARAM_CONTEXT);
		if (context.equals(DoctorUrlService.CONTEXT_DOCTOR_PROFILE)) {
			return getDoctorProfilePhoto(req, resp);
		} else if (context.equals(DoctorUrlService.CONTEXT_DOCTOR_GALLERY)) {
			return getDoctorGalleryPhoto(req, resp);
		}
		return null;
	}

	/**
	 * Get doctor's gallery photos
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	private ModelAndView getDoctorGalleryPhoto(final HttpServletRequest req, final HttpServletResponse resp) throws Exception {
		tracer(this).debug("Get doctor's gallery photo");
		final int doctorId = Integer.valueOf(req.getParameter(DoctorUrlService.PARAM_ID));
		final int photoNumber = Integer.valueOf(req.getParameter(DoctorUrlService.PARAM_PHOTO_NUMBER));
		tracer(this).debug("Get doctor's gallery photo - doctorId: " + doctorId + ", photo number: " + photoNumber);
		final byte[] galleryPhoto = doctorFacade.getDoctorGalleryPhoto(doctorId, photoNumber);
		if (galleryPhoto != null) {
			tracer(this).debug("Attaching doctor gallery image into HTTP response");
			resp.getOutputStream().write(galleryPhoto);
		} else {
			tracer(this).debug("No gallery photo found for doctor: " + doctorId + ", photo number: " + photoNumber);
		}
		resp.setStatus(HttpServletResponse.SC_ACCEPTED);
		return null;
	}

	/**
	 * Get doctor's profile photo
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	private ModelAndView getDoctorProfilePhoto(final HttpServletRequest req, final HttpServletResponse resp) throws Exception {
		tracer(this).debug("Get doctor's profile photo");
		final String stringDoctorId = req.getParameter(DoctorUrlService.PARAM_ID);
		final int doctorId = Integer.valueOf(stringDoctorId);
		tracer(this).debug("Get doctor's profile photo - doctorId: " + doctorId);
		final byte[] profilePhoto = doctorFacade.getDoctorProfilePhoto(doctorId);
		if (profilePhoto != null) {
			tracer(this).debug("Attaching doctor profile image into HTTP response");
			resp.getOutputStream().write(profilePhoto);
		} else {
			tracer(this).debug("No profile photo found for doctor: " + doctorId);
		}
		resp.setStatus(HttpServletResponse.SC_ACCEPTED);
		return null;
	}

	final public void setDoctorFacade(DoctorFacade doctorFacade) {
		this.doctorFacade = doctorFacade;
	}

}
