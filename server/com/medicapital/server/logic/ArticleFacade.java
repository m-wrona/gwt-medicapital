package com.medicapital.server.logic;

import static com.medicapital.server.log.Tracer.tracer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import com.medicapital.common.dao.ServerException;
import com.medicapital.common.entities.Article;
import com.medicapital.common.entities.UrlResourceType;
import com.medicapital.common.entities.UserRole;
import com.medicapital.server.database.DaoArticle;
import com.medicapital.server.database.DaoArticleAttachments;
import com.medicapital.server.database.DaoEntityAccess;
import com.medicapital.server.security.Secured;

public class ArticleFacade extends EntityFacade<Article> {

	private DaoArticle daoArticle;
	private DaoArticleAttachments daoArticleAttachments;

	@Secured(role = UserRole.Doctor)
	@Override
	public void update(Article entity) throws ServerException {
		entity.setLastUpdate(new Date());
		super.update(entity);
	}

	@Secured(role = UserRole.Doctor)
	@Override
	public int create(Article entity) throws ServerException {
		entity.setCreateDate(new Date());
		return super.create(entity);
	}

	@Secured(role = UserRole.Doctor)
	@Override
	public void delete(int entityId) throws ServerException {
		super.delete(entityId);
	}

	@Secured
	public int findArticlesCount(String title, Date dateFrom, Date dateTo, String authorFirstName, String authorLastName) throws ServerException {
		tracer(this).debug("Searching articles count - title: " + title + ", date from:  " + dateFrom + ", date to: " + dateTo + ", author first name: " + authorFirstName + ", author last name: " + authorLastName);
		return daoArticle.findArticlesCount(title, dateFrom, dateTo, authorFirstName, authorLastName);
	}

	@Secured
	public List<Article> findArticles(String title, Date dateFrom, Date dateTo, String authorFirstName, String authorLastName, int startRow, int rowCount) throws ServerException {
		tracer(this).debug("Searching articles - title: " + title + ", date from:  " + dateFrom + ", date to: " + dateTo + ", author first name: " + authorFirstName + ", author last name: " + authorLastName);
		Set<Article> articles = daoArticle.findArticles(title, dateFrom, dateTo, authorFirstName, authorLastName, startRow, rowCount);
		return articles != null ? new ArrayList<Article>(articles) : null;
	}

	@Secured(role = UserRole.Doctor)
	public void deleteArticleAttachment(int articleId, int attachmentId) throws ServerException {
		tracer(this).debug("Deleting articles attachment - articleId:  " + articleId + ", attachmentId: " + attachmentId);
		daoArticleAttachments.deleteArticleAttachment(articleId, attachmentId);
	}

	@Secured(role = UserRole.Doctor)
	public void createArticleAttachment(int articleId, byte[] value, String description, UrlResourceType urlResourceType) throws ServerException {
		tracer(this).debug("Creating article's attachment - attachmentId: " + articleId);
		daoArticleAttachments.createArticleAttachment(articleId, value, description, urlResourceType);
	}

	@Secured
	public byte[] getArticleAttachment(int articleId, int attachmentId) throws ServerException {
		return daoArticleAttachments.getArticleAttachment(articleId, attachmentId);
	}

	@Secured(role = UserRole.Doctor)
	public void deleteArticleMainPhoto(int articleId) throws ServerException {
		tracer(this).debug("Deleting photo's main picture - " + articleId);
		daoArticle.updateArticleMainPhoto(articleId, null);
	}

	@Secured(role = UserRole.Doctor)
	public void saveArticleMainPhoto(int articleId, byte[] photo) throws ServerException {
		tracer(this).debug("Saving photo's main picture - " + articleId);
		daoArticle.updateArticleMainPhoto(articleId, photo);
	}

	@Secured
	public byte[] getArticleMainPhoto(int articleId) throws ServerException {
		return daoArticle.getArticleMainPhoto(articleId);
	}

	/**
	 * Get doctor's article (published or not)
	 * 
	 * @param articleId
	 * @return
	 * @throws ServerException
	 */
	@Secured(role = UserRole.Doctor)
	public Article getDoctorArticle(int articleId) throws ServerException {
		tracer(this).debug("Getting doctor's article - articleId:  " + articleId);
		Article article = daoArticle.get(articleId, null);
		return article;
	}

	/**
	 * Get doctor's articles (published or not)
	 * 
	 * @param doctorLogin
	 * @param startRow
	 * @param rowCount
	 * @return
	 * @throws ServerException
	 */
	@Secured(role = UserRole.Doctor)
	public List<Article> getDoctorArticles(String doctorLogin, int startRow, int rowCount) throws ServerException {
		Set<Article> articles = daoArticle.getArticles(doctorLogin, startRow, rowCount, null);
		return articles != null ? new ArrayList<Article>(articles) : null;
	}

	/**
	 * Get count of doctor's articles (published or not)
	 * 
	 * @param doctorLogin
	 * @return
	 * @throws ServerException
	 */
	@Secured(role = UserRole.Doctor)
	public int getDoctorArticlesCount(String doctorLogin) throws ServerException {
		return daoArticle.getArticlesCount(doctorLogin, null);
	}

	@Secured
	public List<Article> getPublishedArticles(String login, int startRow, int rowCount) throws ServerException {
		Set<Article> articles = daoArticle.getArticles(login, startRow, rowCount, true);
		return articles != null ? new ArrayList<Article>(articles) : null;
	}

	@Secured
	@Override
	public Article get(int entityId) throws ServerException {
		return super.get(entityId);
	}

	@Secured
	@Override
	public int getCount() throws ServerException {
		return super.getCount();
	}

	@Secured
	public int getPublishedArticlesCount(String login) throws ServerException {
		return daoArticle.getArticlesCount(login, true);
	}

	final public void setDaoArticle(DaoArticle daoArticle) {
		this.daoArticle = daoArticle;
	}

	@Override
	protected DaoEntityAccess<Article> getDao() {
		return daoArticle;
	}

	public void setDaoArticleAttachments(DaoArticleAttachments daoArticleAttachments) {
		this.daoArticleAttachments = daoArticleAttachments;
	}

}
