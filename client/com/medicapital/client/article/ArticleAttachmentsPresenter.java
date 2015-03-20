package com.medicapital.client.article;

import java.util.List;
import com.medicapital.client.core.url.UrlResourceListPresenter;
import com.medicapital.client.core.url.UrlResourceListView;
import com.medicapital.common.dao.ArticleUrlService;
import com.medicapital.common.entities.Article;
import com.medicapital.common.entities.UrlResource;

/**
 * Class allows to display article's attachments
 * 
 * @author mwronski
 * 
 */
final class ArticleAttachmentsPresenter extends UrlResourceListPresenter<Article, ArticleUrlService> {

	ArticleAttachmentsPresenter(UrlResourceListView view, ArticleUrlService imageAccess, Article article) {
		super(view, article, imageAccess);
	}

	@Override
	protected void init(Article article, ArticleUrlService imageAccess, List<UrlResource> urlResources) {
		if (article.getAttachmentsCount() > 0) {
			for (UrlResource image : article.getAttachments()) {
				image.setUrl(imageAccess.getArticleAttachment(article.getId(), image.getId()));
				urlResources.add(image);
			}
		}
	}

}
