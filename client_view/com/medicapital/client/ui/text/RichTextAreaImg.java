package com.medicapital.client.ui.text;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

/**
 * Images of rich text area
 * 
 * @author mwronski
 * 
 */
public interface RichTextAreaImg extends ClientBundle {

	@Source("bold.gif")
	ImageResource bold();

	@Source("createLink.gif")
	ImageResource createLink();

	@Source("hr.gif")
	ImageResource hr();

	@Source("indent.gif")
	ImageResource indent();

	@Source("insertImage.gif")
	ImageResource insertImage();

	@Source("italic.gif")
	ImageResource italic();

	@Source("justifyCenter.gif")
	ImageResource justifyCenter();

	@Source("justifyLeft.gif")
	ImageResource justifyLeft();

	@Source("justifyRight.gif")
	ImageResource justifyRight();

	@Source("ol.gif")
	ImageResource ol();

	@Source("outdent.gif")
	ImageResource outdent();

	@Source("removeFormat.gif")
	ImageResource removeFormat();

	@Source("removeLink.gif")
	ImageResource removeLink();

	@Source("strikeThrough.gif")
	ImageResource strikeThrough();

	@Source("subscript.gif")
	ImageResource subscript();

	@Source("superscript.gif")
	ImageResource superscript();

	@Source("ul.gif")
	ImageResource ul();

	@Source("underline.gif")
	ImageResource underline();

}
