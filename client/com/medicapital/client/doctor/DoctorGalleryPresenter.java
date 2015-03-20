package com.medicapital.client.doctor;

import static com.medicapital.client.log.Tracer.*;

import com.medicapital.client.core.PageablePresenter;
import com.medicapital.common.dao.DoctorUrlService;

public class DoctorGalleryPresenter extends PageablePresenter {

	private final DoctorGalleryView view;
	private final DoctorUrlService imageService;
	private int doctorId = -1;

	public DoctorGalleryPresenter(DoctorGalleryView view, DoctorUrlService imageService) {
		super(view);
		setPageSize(3);
		this.view = view;
		this.imageService = imageService;
	}
	
	@Override
	protected void displayCurrentPageData() {
		tracer(this).debug("Dislaying gallery photos - doctorId: " + doctorId + ", startPhoto: " + getStartRow() + ", photoCount: " + getPageSize());
		if (doctorId == -1) {
			throw new IllegalArgumentException("DoctorId not set");
		}
		view.displayGalleryPhotos(imageService.getDoctorGalleryImageURL(doctorId, getStartRow(), getPageSize()));
	}

	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}

	public int getDoctorId() {
		return doctorId;
	}

}
