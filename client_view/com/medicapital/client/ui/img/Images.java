package com.medicapital.client.ui.img;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface Images extends ClientBundle {

	Images instance = (Images) GWT.create(Images.class);

	@Source("imgNotSet.jpg")
	ImageResource imageNotSet();
}
