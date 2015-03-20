package com.medicapital.client.core.url;

import static com.medicapital.client.log.Tracer.tracer;
import com.medicapital.client.core.entity.EntityPresenter;
import com.medicapital.client.core.upload.FileServerUpload;
import com.medicapital.client.core.upload.FileUploadCallback;
import com.medicapital.client.lang.Lang;
import com.medicapital.client.ui.UIUtil;
import com.medicapital.common.dao.UrlService;
import com.medicapital.common.entities.SerializableEntity;
import com.medicapital.common.entities.UrlResource;

/**
 * Class allows to manage list of URL resources
 * 
 * @author mwronski
 * 
 * @param <E>
 *            entity to which URL resources belong
 * @param <U>
 *            UrlService which manages access to URL resources
 */
abstract public class EditUrlResourceListPresenter<E extends SerializableEntity, U extends UrlService> extends UrlResourceListPresenter<E, U> {

	private final EntityPresenter<E> masterPresenter;
	private final FileServerUpload fileUpload;
	private final EditUrlResourceListPresenterView view;

	public EditUrlResourceListPresenter(EntityPresenter<E> masterPresenter, EditUrlResourceListPresenterView view, U urlService) {
		super(view, masterPresenter.getCurrentEntity(), urlService);
		this.masterPresenter = masterPresenter;
		this.fileUpload = new FileServerUpload(view);
		this.view = view;
	}

	private int getMasterEntityId() {
		return masterPresenter.getCurrentEntity().getId();
	}

	/**
	 * Save URL resource on server
	 * 
	 * @throws IllegalArgumentException
	 *             when presenter isn't fully initialized
	 */
	public void upload() throws IllegalArgumentException {
		tracer(this).debug("Saving URL resource for parent: " + getMasterEntityId());
		masterPresenter.getDisplay().setViewBlocked(true);
		fileUpload.init(getUploadActionName(getMasterEntityId(), fileUpload.getUrlResource()), getUploadFileName());
		fileUpload.upload(new FileUploadCallback() {

			@Override
			public void onSuccess() {
				tracer(this).debug("Saving URL resource - OK - parent entity id: " + masterPresenter.getCurrentEntity().getId());
				masterPresenter.getDisplay().setViewBlocked(false);
				masterPresenter.refreshDisplay();
				UIUtil.alert(Lang.warnings().fileSaved());
			}

			@Override
			public void onError() {
				tracer(this).error("Saving URL resource - ERROR");
				masterPresenter.getDisplay().setViewBlocked(false);
				fileUpload.clear();
				UIUtil.error(Lang.warnings().fileNotSaved());
			}

			@Override
			public void onNoFile() {
				masterPresenter.getDisplay().setViewBlocked(false);
			}

		});
	}

	/**
	 * Get action name for upload operation
	 * 
	 * @param parentId
	 * @param urlResource
	 * @return
	 */
	abstract protected String getUploadActionName(int parentId, UrlResource urlResource);

	/**
	 * Get action name for delete operation
	 * 
	 * @param parentId
	 * @param urlResourceId
	 * @return
	 */
	abstract protected String getDeleteActionName(int parentId, int urlResourceId);

	/**
	 * Get name of file which is uploaded to server
	 * 
	 * @return
	 */
	abstract protected String getUploadFileName();

	/**
	 * Delete URL resource on server
	 * 
	 * @param resourceId
	 * @param deleteFileView
	 *            view which must be passed to make it visible so
	 * @throws IllegalArgumentException
	 *             when presenter isn't fully initialized
	 */
	public void delete(UrlResource urlResource) {
		tracer(this).debug("Deleting URL resource for parent: " + getMasterEntityId() + ", URL resource: " + urlResource);
		view.getFormPanelParent().setViewVisible(true);
		view.getFormPanelParent().setViewBlocked(true);
		masterPresenter.getDisplay().setViewBlocked(true);
		fileUpload.init(getDeleteActionName(getMasterEntityId(), urlResource.getId()), null);
		fileUpload.delete(new FileUploadCallback() {

			@Override
			public void onSuccess() {
				tracer(this).debug("Deleting URL resource - OK");
				view.getFormPanelParent().setViewBlocked(false);
				view.getFormPanelParent().setViewVisible(false);
				masterPresenter.getDisplay().setViewBlocked(false);
				masterPresenter.refreshDisplay();
				UIUtil.alert(Lang.warnings().fileDeleted());
			}

			@Override
			public void onError() {
				tracer(this).error("Deleting URL resource - ERROR");
				view.getFormPanelParent().setViewBlocked(false);
				view.getFormPanelParent().setViewVisible(false);
				masterPresenter.getDisplay().setViewBlocked(false);
				UIUtil.error(Lang.warnings().fileNotDeleted());
			}

			@Override
			public void onNoFile() {
				view.getFormPanelParent().setViewBlocked(false);
				view.getFormPanelParent().setViewVisible(false);
				masterPresenter.getDisplay().setViewBlocked(false);
			}
		});
	}

}
