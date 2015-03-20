package com.medicapital.client.core.upload;

import static com.medicapital.client.log.Tracer.tracer;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.medicapital.client.lang.Lang;
import com.medicapital.client.ui.UIUtil;
import com.medicapital.common.dao.UrlService;
import com.medicapital.common.entities.UrlResource;
import com.medicapital.common.util.Util;

/**
 * Class allows to upload or delete file in server
 * 
 * @author mwronski
 * 
 */
public class FileServerUpload {

	private final FileServerUploadView view;
	private final FormPanel formPanel;
	private final FileUpload fileUpload;
	private FileUploadCallback callback;
	private String previousFileName;

	public FileServerUpload(FileServerUploadView view) {
		this.view = view;
		this.formPanel = view.getFormPanel();
		this.fileUpload = view.getFileUpload();
		initHandlers(formPanel, fileUpload);
	}

	/**
	 * Initialize file upload
	 * 
	 * @param actionName
	 *            name of action which should be executed on server side
	 * @param fileUploadName
	 *            name of file to be sent
	 */
	final public void init(String actionName, String fileUploadName) {
		formPanel.setAction(actionName);
		formPanel.setEncoding(FormPanel.ENCODING_MULTIPART);
		formPanel.setMethod(FormPanel.METHOD_POST);
		fileUpload.setName(fileUploadName);
	}

	/**
	 * Initialize handlers to be able to get response from server
	 * 
	 * @param formPanel
	 * @param fileUpload
	 */
	private void initHandlers(FormPanel formPanel, FileUpload fileUpload) {
		formPanel.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {

			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				tracer(FileServerUpload.this).debug("File upload completed - result: " + event.getResults());
				if (callback != null) {
					if (event.getResults().contains(UrlService.STATUS_ERROR)) {
						callback.onError();
					} else {
						callback.onSuccess();
					}
				}

			}
		});
	}

	/**
	 * Send file to server
	 * 
	 * @param callback
	 */
	final public void upload(FileUploadCallback callback) {
		this.callback = callback;
		if (Util.isEmpty(fileUpload.getFilename())) {
			UIUtil.alert(Lang.warnings().chooseFileToSave());
			callback.onNoFile();
		} else if (fileUpload.getFilename().equals(previousFileName)) {
			tracer(this).debug("File was already uploaded to server: " + fileUpload.getFilename());
			callback.onNoFile();
		} else {
			tracer(this).debug("Uploading file: " + fileUpload.getFilename());
			formPanel.submit();
			previousFileName = fileUpload.getFilename();
		}
	}

	/**
	 * Delete file on server
	 * 
	 * @param callback
	 */
	final public void delete(FileUploadCallback callback) {
		this.callback = callback;
		tracer(this).debug("Deleting file: " + formPanel.getAction());
		formPanel.submit();
		previousFileName = null;
	}

	/**
	 * Clear state of file upload
	 */
	final public void clear() {
		previousFileName = null;
	}

	final protected FileUpload getFileUpload() {
		return fileUpload;
	}

	final protected FormPanel getFormPanel() {
		return formPanel;
	}

	final public UrlResource getUrlResource() {
		return view.getUrlResource();
	}
}
