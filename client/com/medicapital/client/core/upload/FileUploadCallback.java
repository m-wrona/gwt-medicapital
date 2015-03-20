package com.medicapital.client.core.upload;

public interface FileUploadCallback {

	/**
	 * File was uploaded successfully
	 */
	void onSuccess();

	/**
	 * No file was selected to upload
	 */
	void onNoFile();

	/**
	 * Error occurred while upload
	 */
	void onError();

}
