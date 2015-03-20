package com.medicapital.server.database.hibernate;

import java.util.Date;
import java.util.List;
import java.util.Set;
import org.hibernate.Query;
import com.medicapital.common.entities.Article;
import com.medicapital.common.util.Util;
import com.medicapital.server.database.DaoArticle;
import com.medicapital.server.database.DaoArticleAttachments;
import com.medicapital.server.database.DataAccessException;
import com.medicapital.server.database.hibernate.entities.ArticleEntity;
import com.medicapital.server.database.hibernate.entities.UserEntity;
import com.medicapital.server.database.hibernate.transform.ArticleTransformer;
import com.medicapital.server.database.hibernate.transform.Transformer;

public class HibernateArticle extends HibernateEntityAccess<Article, ArticleEntity> implements DaoArticle {

	private final ArticleTransformer transformer = new ArticleTransformer();
	private DaoArticleAttachments daoArticleAttachments;

	@Override
	public int findArticlesCount(String title, Date dateFrom, Date dateTo, String authorFirstName, String authorLastName) throws DataAccessException {
		try {
			// build query
			StringBuilder select = new StringBuilder("select count(*) from " + ArticleEntity.class.getSimpleName() + " where published=true");
			if (!Util.isEmpty(title)) {
				select.append(" and title like :title");
			}
			if (dateFrom != null) {
				select.append(" and created >= :dateFrom");
			}
			if (dateTo != null) {
				select.append(" and created <= :dateTo");
			}
			if (!Util.isEmpty(authorFirstName) || !Util.isEmpty(authorLastName)) {
				select.append(" and userId in (select userId from " + UserEntity.class.getSimpleName() + " where ");
				if (!Util.isEmpty(authorFirstName)) {
					select.append(" firstName like :authorFirstName ");
				}
				if (!Util.isEmpty(authorLastName)) {
					if (!Util.isEmpty(authorFirstName)) {
						select.append(" and ");
					}
					select.append(" lastName like :authorLastName)");
				}
				select.append(")");
			}
			// append values
			final Query query = getSession().createQuery(select.toString());
			if (!Util.isEmpty(title)) {
				query.setString("title", "%" + title + "%");
			}
			if (dateFrom != null) {
				query.setDate("dateFrom", dateFrom);
			}
			if (dateTo != null) {
				query.setDate("dateTo", dateTo);
			}
			if (!Util.isEmpty(authorFirstName)) {
				query.setString("authorFirstName", "%" + authorFirstName + "%");
			}
			if (!Util.isEmpty(authorLastName)) {
				query.setString("authorLastName", "%" + authorLastName + "%");
			}
			// get results
			final List<?> result = query.list();
			final Long count = result.isEmpty() ? 0 : (Long) result.get(0);
			return count.intValue();
		} catch (Exception e) {
			throw new DataAccessException(e);
		}
	}

	@Override
	public Set<Article> findArticles(String title, Date dateFrom, Date dateTo, String authorFirstName, String authorLastName, int startRow, int rowCount) throws DataAccessException {
		try {
			// build query
			StringBuilder select = new StringBuilder("from " + ArticleEntity.class.getSimpleName() + " where published=true");
			if (!Util.isEmpty(title)) {
				select.append(" and title like :title");
			}
			if (dateFrom != null) {
				select.append(" and created >= :dateFrom");
			}
			if (dateTo != null) {
				select.append(" and created <= :dateTo");
			}
			if (!Util.isEmpty(authorFirstName) || !Util.isEmpty(authorLastName)) {
				select.append(" and userId in (select userId from " + UserEntity.class.getSimpleName() + " where ");
				if (!Util.isEmpty(authorFirstName)) {
					select.append(" firstName like :authorFirstName ");
				}
				if (!Util.isEmpty(authorLastName)) {
					if (!Util.isEmpty(authorFirstName)) {
						select.append(" and ");
					}
					select.append(" lastName like :authorLastName)");
				}
				select.append(")");
			}
			// append values
			final Query query = getSession().createQuery(select.toString());
			if (!Util.isEmpty(title)) {
				query.setString("title", "%" + title + "%");
			}
			if (dateFrom != null) {
				query.setDate("dateFrom", dateFrom);
			}
			if (dateTo != null) {
				query.setDate("dateTo", dateTo);
			}
			if (!Util.isEmpty(authorFirstName)) {
				query.setString("authorFirstName", "%" + authorFirstName + "%");
			}
			if (!Util.isEmpty(authorLastName)) {
				query.setString("authorLastName", "%" + authorLastName + "%");
			}
			// get results
			query.setMaxResults(rowCount);
			query.setFirstResult(startRow);
			@SuppressWarnings("unchecked")
			final List<ArticleEntity> articlesEntities = query.list();
			return articlesEntities.isEmpty() ? null : HibernateDaoUtil.toPojoSet(articlesEntities, transformer);
		} catch (Exception e) {
			throw new DataAccessException(e);
		}
	}

	@Override
	public void updateArticleMainPhoto(int articleId, byte[] photo) throws DataAccessException {
		// TODO move to DB trigger
		Article article = get(articleId, null);
		if (article == null) {
			throw new DataAccessException("Article doesn't exist - articleId: " + articleId);
		}
		try {
			final Query query = getSession().createSQLQuery("update article set mainPhoto=:mainPhoto where articleId = :articleId");
			query.setInteger("articleId", articleId);
			query.setBinary("mainPhoto", photo);
			query.executeUpdate();
		} catch (Exception e) {
			throw new DataAccessException(e);
		}
	}

	@Override
	public byte[] getArticleMainPhoto(int articleId) throws DataAccessException {
		try {
			final Query query = getSession().createSQLQuery("select mainPhoto from article where articleId = :articleId");
			query.setInteger("articleId", articleId);
			return (byte[]) query.uniqueResult();
		} catch (final Exception e) {
			throw new DataAccessException(e);
		}
	}

	@Override
	public Article get(int articleId) throws DataAccessException {
		return get(articleId, true);
	}

	@Override
	public Article get(int articleId, Boolean published) throws DataAccessException {
		try {
			String stringQuery = "from " + ArticleEntity.class.getSimpleName() + " where articleId=:articleId";
			if (published != null) {
				stringQuery += " and published=:published";
			}
			final Query query = getSession().createQuery(stringQuery);
			query.setInteger("articleId", articleId);
			if (published != null) {
				query.setBoolean("published", published);
			}
			@SuppressWarnings("unchecked")
			final List<ArticleEntity> results = query.list();
			if (results == null || results.isEmpty()) {
				return null;
			}
			Article article = transformer.createPojo(results.get(0));
			daoArticleAttachments.getArticleAttachments(article);
			return article;
		} catch (final Exception e) {
			throw new DataAccessException(e);
		}
	}

	@Override
	public Set<Article> getArticles(String login, int startRow, int rowCount, Boolean published) throws DataAccessException {
		try {
			String stringQuery = "from " + ArticleEntity.class.getSimpleName() + " where userId=(select userId from " + UserEntity.class.getSimpleName() + " where login=:login)";
			if (published != null) {
				stringQuery += " and published=:published";
			}
			final Query query = getSession().createQuery(stringQuery);
			query.setString("login", login);
			if (published != null) {
				query.setBoolean("published", published);
			}
			query.setFirstResult(startRow);
			query.setMaxResults(rowCount);
			@SuppressWarnings("unchecked")
			final List<ArticleEntity> results = query.list();
			Set<Article> articles = HibernateDaoUtil.toPojoSet(results, transformer);
			daoArticleAttachments.getArticleAttachments(articles);
			return articles;
		} catch (final Exception e) {
			throw new DataAccessException(e);
		}
	}

	@Override
	public Set<Article> getArticles(int userId, int startRow, int rowCount, Boolean published) throws DataAccessException {
		try {
			String stringQuery = "from " + ArticleEntity.class.getSimpleName() + " where userId=:userId";
			if (published != null) {
				stringQuery += " and published=:published";
			}
			final Query query = getSession().createQuery(stringQuery);
			query.setInteger("userId", userId);
			if (published != null) {
				query.setBoolean("published", published);
			}
			query.setFirstResult(startRow);
			query.setMaxResults(rowCount);
			@SuppressWarnings("unchecked")
			final List<ArticleEntity> results = query.list();
			Set<Article> articles = HibernateDaoUtil.toPojoSet(results, transformer);
			daoArticleAttachments.getArticleAttachments(articles);
			return articles;
		} catch (final Exception e) {
			throw new DataAccessException(e);
		}
	}

	@Override
	public int getArticlesCount(int userId, Boolean published) throws DataAccessException {
		try {
			String stringQuery = "select count(*) from " + ArticleEntity.class.getSimpleName() + " where userId=:userId";
			if (published != null) {
				stringQuery += " and published=:published";
			}
			final Query query = getSession().createQuery(stringQuery);
			query.setInteger("userId", userId);
			if (published != null) {
				query.setBoolean("published", published);
			}
			final List<?> result = query.list();
			final Long count = result.isEmpty() ? 0 : (Long) result.get(0);
			return count.intValue();
		} catch (final Exception e) {
			throw new DataAccessException(e);
		}
	}

	@Override
	public int getArticlesCount(String login, Boolean published) throws DataAccessException {
		try {
			String stringQuery = "select count(*) from " + ArticleEntity.class.getSimpleName() + " where userId=(select userId from " + UserEntity.class.getSimpleName() + " where login=:login)";
			if (published != null) {
				stringQuery += " and published=:published";
			}
			final Query query = getSession().createQuery(stringQuery);
			query.setString("login", login);
			if (published != null) {
				query.setBoolean("published", published);
			}
			final List<?> result = query.list();
			final Long count = result.isEmpty() ? 0 : (Long) result.get(0);
			return count.intValue();
		} catch (final Exception e) {
			throw new DataAccessException(e);
		}
	}

	public void setDaoArticleAttachments(DaoArticleAttachments daoArticleAttachments) {
		this.daoArticleAttachments = daoArticleAttachments;
	}

	@Override
	protected Class<ArticleEntity> getPersistenceClass() {
		return ArticleEntity.class;
	}

	@Override
	protected Class<Article> getPojoClass() {
		return Article.class;
	}

	@Override
	protected Transformer<Article, ArticleEntity> getTransformer() {
		return new ArticleTransformer();
	}

}
