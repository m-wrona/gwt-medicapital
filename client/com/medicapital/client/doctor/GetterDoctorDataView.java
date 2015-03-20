package com.medicapital.client.doctor;

import com.medicapital.client.user.GetterUserDataView;

/**
 * Generic interface for getting doctor's data
 * 
 * @author mwronski
 * 
 */
public interface GetterDoctorDataView extends GetterUserDataView {

	String getWorkId();

	float getAverageEvaluation();
	
	String getNotes();

}
