package com.medicapital.server.database;

import java.util.Date;
import java.util.Set;
import com.medicapital.common.entities.Article;

public interface DaoArticle extends DaoEntityAccess<Article> {

	/**
	 * Find articles count by given criteria
	 * 
	 * @param title
	 * @param dateFrom
	 * @param dateTo
	 * @param authorFirstName
	 * @param authorLastName
	 * @return
	 * @throws DataAccessException
	 */
	int findArticlesCount(String title, Date dateFrom, Date dateTo, String authorFirstName, String authorLastName) throws DataAccessException;

	/**
	 * Find articles by given criteria
	 * 
	 * @param title
	 * @param dateFrom
	 * @param dateTo
	 * @param authorFirstName
	 * @param authorLastName
	 * @param startRow
	 * @param rowCount
	 * @return
	 * @throws DataAccessException
	 */
	Set<Article> findArticles(String title, Date dateFrom, Date dateTo, String authorFirstName, String authorLastName, int startRow, int rowCount) throws DataAccessException;

	/**
	 * Update article's main photo
	 * 
	 * @param articleId
	 * @param photo
	 * @throws DataAccessException
	 */
	void updateArticleMainPhoto(int articleId, byte[] photo) throws DataAccessException;

	/**
	 * Get article's main photo
	 * 
	 * @param articleId
	 * @return
	 * @throws DataAccessException
	 */
	byte[] getArticleMainPhoto(int articleId) throws DataAccessException;

	/**
	 * Get article by ID
	 * 
	 * @param articleId
	 * @param published
	 *            flag, give null if all articles should be taken
	 * @return
	 * @throws DataAccessException
	 */
	Article get(int articleId, Boolean published) throws DataAccessException;

	/**
	 * Get user's articles count
	 * 
	 * @param userId
	 * @param published
	 *            flag, give null if all articles should be taken
	 * @return
	 * @throws DataAccessException
	 */
	int getArticlesCount(int userId, Boolean published) throws DataAccessException;

	/**
	 * Get user's articles count
	 * 
	 * @param login
	 * @param published
	 *            flag, give null if all articles should be taken
	 * @return
	 * @throws DataAccessException
	 */
	int getArticlesCount(String login, Boolean published) throws DataAccessException;

	/**
	 * Get user's articles
	 * 
	 * @param login
	 * @param startRow
	 * @param rowCount
	 * @param published
	 *            flag, give null if all articles should be taken
	 * @return
	 * @throws DataAccessException
	 */
	Set<Article> getArticles(String login, int startRow, int rowCount, Boolean published) throws DataAccessException;

	/**
	 * Get user's articles
	 * 
	 * @param userId
	 * @param startRow
	 * @param rowCount
	 * @param published
	 *            flag, give null if all articles should be taken
	 * @return
	 * @throws DataAccessException
	 */
	Set<Article> getArticles(int userId, int startRow, int rowCount, Boolean published) throws DataAccessException;

}
