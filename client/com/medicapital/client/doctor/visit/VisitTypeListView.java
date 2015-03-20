package com.medicapital.client.doctor.visit;

import com.medicapital.client.core.entities.EntitiesView;
import com.medicapital.common.entities.VisitType;

public interface VisitTypeListView extends EntitiesView<VisitType> {

	void display(int visitTypeId, String name, int duration);
}
