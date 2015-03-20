package com.medicapital.client.doctor;

import com.medicapital.client.user.SetterUserDataView;

/**
 * Generic interface for setting doctor's data
 * 
 * @author mwronski
 * 
 */
public interface SetterDoctorDataView extends SetterUserDataView {

	void setWorkId(String text);

	void setAverageEvaluation(float averageEvaluation);

	void setNotes(String text);

	void setDoctorProfileImage(String imageUrl);

}
