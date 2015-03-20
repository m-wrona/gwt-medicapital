package com.medicapital.client.article;

import java.util.Date;
import com.medicapital.client.core.WidgetView;
import com.medicapital.common.entities.UrlResource;

interface SetterArticleView extends WidgetView {

	void setArticleTitle(String text);

	void setArticleBody(String text);

	void setAuthorFirstName(String firstName);

	void setAuthorLastName(String lastName);

	void setCreateDate(Date date);

	void setLastUpdateDate(Date date);

	void setPublished(boolean published);

	void display(UrlResource urlResource);

}
