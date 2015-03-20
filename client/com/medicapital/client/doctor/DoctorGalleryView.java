package com.medicapital.client.doctor;

import java.util.Set;

import com.medicapital.client.core.PageableView;
import com.medicapital.client.core.WidgetView;

public interface DoctorGalleryView extends PageableView, WidgetView {

	void setGalleryPresenter(DoctorGalleryPresenter doctorGalleryPresenter);
	
	/**
	 * Display gallery photos
	 * 
	 * @param galleryPhotoUrls
	 */
	void displayGalleryPhotos(Set<String> galleryPhotoUrls);
}
