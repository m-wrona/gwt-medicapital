package com.medicapital.client.core.url;

import static com.medicapital.client.log.Tracer.tracer;
import com.medicapital.client.core.upload.FileServerUpload;
import com.medicapital.client.core.upload.FileUploadCallback;
import com.medicapital.client.lang.Lang;
import com.medicapital.client.ui.UIUtil;
import com.medicapital.common.entities.UrlResource;

/**
 * Class manages URL resource which is attached to a parent
 * 
 * @author mwronski
 * 
 */
abstract public class EditUrlResourcePresenter {

	private final FileServerUpload fileUpload;
	private final EditUrlResourcePresenterView view;
	private int parentId;
	private UrlResource urlResource;

	public EditUrlResourcePresenter(EditUrlResourcePresenterView view) {
		this.fileUpload = new FileServerUpload(view);
		this.view = view;
	}

	/**
	 * Initialize presenter
	 * 
	 * @param parentId
	 * @param urlResource
	 */
	public void init(int parentId, UrlResource urlResource) {
		this.parentId = parentId;
		this.urlResource = urlResource;
	}

	/**
	 * Save URL resource on server
	 * 
	 * @throws IllegalArgumentException
	 *             when presenter isn't fully initialized
	 */
	public void upload() throws IllegalArgumentException {
		tracer(this).debug("Saving URL resource for parent: " + parentId + ", url resource: " + urlResource);
		validatePresenter();
		view.setViewBlocked(true);
		fileUpload.init(getUploadActionName(parentId, view.getUrlResource()), getUploadFileName());
		fileUpload.upload(new FileUploadCallback() {

			@Override
			public void onSuccess() {
				tracer(this).debug("Saving URL resource - OK");
				view.setViewBlocked(false);
				display();
				UIUtil.alert(Lang.warnings().fileSaved());
			}

			@Override
			public void onError() {
				tracer(this).error("Saving URL resource - ERROR");
				view.setViewBlocked(false);
				fileUpload.clear();
				UIUtil.error(Lang.warnings().fileNotSaved());
			}

			@Override
			public void onNoFile() {
				view.setViewBlocked(false);
			}

		});
	}

	/**
	 * Get action name for upload operation
	 * 
	 * @param parentId
	 * @param urlResource
	 *            description of URL resource to be uploaded
	 * @return
	 */
	abstract protected String getUploadActionName(int parentId, UrlResource urlResource);

	/**
	 * Get action name for delete operation
	 * 
	 * @param parentId
	 * @return
	 */
	abstract protected String getDeleteActionName(int parentId);

	/**
	 * Get name of file which is uploaded to server
	 * 
	 * @return
	 */
	abstract protected String getUploadFileName();

	/**
	 * Delete URL resource on server
	 * 
	 * @throws IllegalArgumentException
	 *             when presenter isn't fully initialized
	 */
	public void delete() {
		tracer(this).debug("Deleting URL resource for parent: " + parentId + ", url resource: " + urlResource);
		validatePresenter();
		view.setViewBlocked(true);
		fileUpload.init(getDeleteActionName(parentId), null);
		fileUpload.delete(new FileUploadCallback() {

			@Override
			public void onSuccess() {
				tracer(this).debug("Deleting URL resource - OK");
				view.setViewBlocked(false);
				displayDeletedResource();
				UIUtil.alert(Lang.warnings().fileDeleted());
			}

			@Override
			public void onError() {
				tracer(this).error("Deleting URL resource - ERROR");
				view.setViewBlocked(false);
				UIUtil.error(Lang.warnings().fileNotDeleted());
			}

			@Override
			public void onNoFile() {
				view.setViewBlocked(false);
			}
		});
	}

	/**
	 * Display resource
	 */
	public void display() {
		validatePresenter();
		urlResource.setUrl(getUrlResource(parentId));
		view.display(urlResource);
	}

	/**
	 * Get URL of resource for parent (needed when URL should change with time
	 * to prevent caching)
	 * 
	 * @param parentId
	 * @return
	 */
	abstract protected String getUrlResource(int parentId);

	private void displayDeletedResource() {
		urlResource.setUrl(null);
		view.display(urlResource);
	}

	private void validatePresenter() throws IllegalArgumentException {
		if (parentId <= 0) {
			throw new IllegalArgumentException("Parent of URL resource not defined");
		} else if (urlResource == null) {
			throw new IllegalArgumentException("URL resource not defined");
		}
	}

}
