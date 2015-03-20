package com.medicapital.client.core.upload;

import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.medicapital.client.core.WidgetView;
import com.medicapital.common.entities.UrlResource;

/**
 * View with data needed to send file to server
 * 
 * @author mwronski
 * 
 */
public interface FileServerUploadView extends WidgetView {

	/**
	 * Get form panel needed to send file to server
	 * 
	 * @return
	 */
	FormPanel getFormPanel();

	/**
	 * Get chosen file to be uploaded
	 * 
	 * @return
	 */
	FileUpload getFileUpload();

	/**
	 * Get description of URL resource to be uploaded to server
	 * 
	 * @return
	 */
	UrlResource getUrlResource();

	/**
	 * Get parent of form panel. Parent is needed because it must be visible
	 * while performing operations on files.
	 * 
	 * @return
	 */
	WidgetView getFormPanelParent();

}
