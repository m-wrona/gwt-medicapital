package com.medicapital.client.visit;

import java.util.Date;

import com.medicapital.client.core.entity.EntityView;

interface SetterVisitView extends EntityView {

	void setVisitTitle(String text);

	void setNotes(String text);

	void setBeginDate(Date beginDate);

	void setEndDate(Date endDate);

	void setDoctor(String firstName, String lastName);

	void setAddress(String text);

	void setCityId(int cityId);
	
	void setPatient(String firstName, String lastName);
	
}
