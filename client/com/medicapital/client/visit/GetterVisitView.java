package com.medicapital.client.visit;

import java.util.Date;

import com.medicapital.client.core.entity.EntityView;

interface GetterVisitView extends EntityView {

	String getVisitTitle();

	String getNotes();

	Date getBeginDate();

	Date getEndDate();

	String getAdress();

	int getCityId();

}
