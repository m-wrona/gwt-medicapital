package com.medicapital.server.access.gwtrpc;

import java.util.List;
import com.medicapital.common.commands.article.SearchArticleCommand;
import com.medicapital.common.commands.article.SearchArticleCountCommand;
import com.medicapital.common.commands.article.SelectArticlesCommand;
import com.medicapital.common.commands.article.SelectArticlesCountCommand;
import com.medicapital.common.commands.entity.SelectCommand;
import com.medicapital.common.commands.entity.SelectCommandResp;
import com.medicapital.common.commands.entity.SelectCountCommand;
import com.medicapital.common.commands.entity.SelectCountCommandResp;
import com.medicapital.common.dao.ServerException;
import com.medicapital.common.entities.Article;
import com.medicapital.common.util.Util;
import com.medicapital.server.logic.ArticleFacade;

public class ArticleCommandExecutor extends CommandExecutor<Article> {

	private static final long serialVersionUID = 1349939000847783499L;
	private ArticleFacade articleFacade;

	@Override
	protected SelectCommandResp<Article> handleSelectCommand(SelectCommand<Article> selectCommand) throws CommandExecutionException, ServerException {
		if (selectCommand instanceof SelectArticlesCommand) {
			return handleSelectArticlesCommand((SelectArticlesCommand) selectCommand);
		} else if (selectCommand instanceof SearchArticleCommand) {
			return handleSearchArticlesCommand((SearchArticleCommand) selectCommand);
		} else if (!Util.isEmpty(selectCommand.getLogin())) {
			// get articles for user
			List<Article> articles = articleFacade.getPublishedArticles(selectCommand.getLogin(), selectCommand.getStartRow(), selectCommand.getRowCount());
			SelectCommandResp<Article> resp = new SelectCommandResp<Article>(Article.class);
			resp.addAll(articles);
			return resp;
		}
		return super.handleSelectCommand(selectCommand);
	}

	private SelectCommandResp<Article> handleSearchArticlesCommand(SearchArticleCommand selectCommand) {
		List<Article> articles = articleFacade.findArticles(selectCommand.getTitle(), selectCommand.getCreatedFrom(), selectCommand.getCreatedTo(), selectCommand.getFirstName(), selectCommand.getLastName(), selectCommand.getStartRow(), selectCommand.getRowCount());
		SelectCommandResp<Article> resp = new SelectCommandResp<Article>(Article.class);
		resp.addAll(articles);
		return resp;
	}

	private SelectCommandResp<Article> handleSelectArticlesCommand(SelectArticlesCommand selectCommand) {
		SelectCommandResp<Article> resp = new SelectCommandResp<Article>(Article.class);
		if (selectCommand.isDoctorArticles()) {
			if (selectCommand.getElementId() > 0) {
				Article article = articleFacade.getDoctorArticle(selectCommand.getElementId());
				resp.add(article);
			} else {
				List<Article> articles = articleFacade.getDoctorArticles(selectCommand.getLogin(), selectCommand.getStartRow(), selectCommand.getRowCount());
				resp.addAll(articles);
			}
		} else {
			if (selectCommand.getElementId() > 0) {
				Article article = articleFacade.get(selectCommand.getElementId());
				resp.add(article);
			} else {
				List<Article> articles = articleFacade.getPublishedArticles(selectCommand.getLogin(), selectCommand.getStartRow(), selectCommand.getRowCount());
				resp.addAll(articles);
			}
		}
		return resp;
	}

	@Override
	protected SelectCountCommandResp<Article> handleSelectCountCommand(SelectCountCommand<Article> selectCountCommand) throws CommandExecutionException, ServerException {
		if (selectCountCommand instanceof SelectArticlesCountCommand) {
			return handleSelectCountArticlesCommand((SelectArticlesCountCommand) selectCountCommand);
		} else if (selectCountCommand instanceof SearchArticleCountCommand) {
			return handleSearchArticlesCountCommand((SearchArticleCountCommand) selectCountCommand);
		} else if (Util.isEmpty(selectCountCommand.getLogin())) {
			int count = articleFacade.getPublishedArticlesCount(selectCountCommand.getLogin());
			return new SelectCountCommandResp<Article>(Article.class, count);
		}
		return super.handleSelectCountCommand(selectCountCommand);
	}

	private SelectCountCommandResp<Article> handleSearchArticlesCountCommand(SearchArticleCountCommand selectCountCommand) {
		int count = articleFacade.findArticlesCount(selectCountCommand.getTitle(), selectCountCommand.getCreatedFrom(), selectCountCommand.getCreatedTo(), selectCountCommand.getFirstName(), selectCountCommand.getLastName());
		return new SelectCountCommandResp<Article>(Article.class, count);
	}

	private SelectCountCommandResp<Article> handleSelectCountArticlesCommand(SelectArticlesCountCommand selectCountCommand) {
		int count = 0;
		if (selectCountCommand.isDoctorArticles()) {
			count = articleFacade.getDoctorArticlesCount(selectCountCommand.getLogin());
		} else {
			count = articleFacade.getPublishedArticlesCount(selectCountCommand.getLogin());
		}
		return new SelectCountCommandResp<Article>(Article.class, count);
	}

	public void setArticleFacade(ArticleFacade articleFacade) {
		setFacade(articleFacade);
		this.articleFacade = articleFacade;
	}

	@Override
	protected Class<Article> getEntityClass() {
		return Article.class;
	}
}
