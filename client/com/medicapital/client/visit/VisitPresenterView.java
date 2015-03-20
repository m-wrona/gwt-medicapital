package com.medicapital.client.visit;

public interface VisitPresenterView extends SetterVisitView {

	void setVisitPresenter(VisitPresenter visitPresenter);
	
	void setDisplayEvaluationVisible(boolean visible);
	
	void setCreateEvaluationVisible(boolean visible);
	
	/**
	 * Set whether evaluation button should be visible
	 * 
	 * @param visible
	 */
	void setEvaluationButtonVisible(boolean visible);
	
}
