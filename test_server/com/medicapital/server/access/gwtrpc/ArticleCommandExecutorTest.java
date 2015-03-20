package com.medicapital.server.access.gwtrpc;

import static org.mockito.Mockito.*;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import com.medicapital.common.commands.article.SearchArticleCommand;
import com.medicapital.common.commands.article.SearchArticleCountCommand;
import com.medicapital.common.commands.article.SelectArticlesCommand;
import com.medicapital.common.commands.article.SelectArticlesCountCommand;
import com.medicapital.common.commands.entity.SelectCommand;
import com.medicapital.common.entities.Article;
import com.medicapital.common.rpc.ServerRequest;
import com.medicapital.server.logic.ArticleFacade;
import com.medicapital.server.security.SecurityManager;
import com.medicapital.server.security.SessionFactory;

public class ArticleCommandExecutorTest {

	private ArticleCommandExecutor commandExecutor;
	private ArticleFacade articleFacade;

	@Before
	public void init() {
		commandExecutor = new ArticleCommandExecutor();
		articleFacade = mock(ArticleFacade.class);
		commandExecutor.setArticleFacade(articleFacade);
		SessionFactory sessionFactory = new SessionFactory();
		sessionFactory.setSupportLocalSession(true);
		commandExecutor.setSessionFactory(sessionFactory);
		commandExecutor.setSecurityManager(new SecurityManager());
	}

	@Test
	public void testHandleSelectCommandByLogin() {
		SelectCommand<Article> selectCommand = new SelectCommand<Article>(Article.class);
		selectCommand.setLogin("doctor");
		commandExecutor.execute(new ServerRequest<Article>(selectCommand));
		verify(articleFacade).getPublishedArticles("doctor", 0, 10);
	}

	@Test
	public void testHandleSelectCommandById() {
		SelectCommand<Article> selectCommand = new SelectCommand<Article>(Article.class);
		selectCommand.setElementId(1);
		commandExecutor.execute(new ServerRequest<Article>(selectCommand));
		verify(articleFacade).get(1);
	}

	@Test
	public void testHandleSelectDoctorArticlesByLogin() {
		SelectArticlesCommand selectCommand = new SelectArticlesCommand();
		Date dateFrom = new Date();
		Date dateTo = new Date();
		selectCommand.setCreatedFrom(dateFrom);
		selectCommand.setCreatedTo(dateTo);
		selectCommand.setDoctorArticles(true);
		selectCommand.setLogin("doctor");
		commandExecutor.execute(new ServerRequest<Article>(selectCommand));
		verify(articleFacade).getDoctorArticles("doctor", 0, 10);
	}

	@Test
	public void testHandleSelectDoctorArticleByArticleId() {
		SelectArticlesCommand selectCommand = new SelectArticlesCommand();
		selectCommand.setElementId(1);
		selectCommand.setDoctorArticles(true);
		commandExecutor.execute(new ServerRequest<Article>(selectCommand));
		verify(articleFacade).getDoctorArticle(1);
	}

	@Test
	public void testHandleSelectDoctorArticlesCount() {
		SelectArticlesCountCommand selectCommand = new SelectArticlesCountCommand();
		selectCommand.setDoctorArticles(true);
		selectCommand.setLogin("doctor");
		commandExecutor.execute(new ServerRequest<Article>(selectCommand));
		verify(articleFacade).getDoctorArticlesCount("doctor");
	}

	@Test
	public void testHandleSelectPublishedArticlesByLogin() {
		SelectArticlesCommand selectCommand = new SelectArticlesCommand();
		Date dateFrom = new Date();
		Date dateTo = new Date();
		selectCommand.setCreatedFrom(dateFrom);
		selectCommand.setCreatedTo(dateTo);
		selectCommand.setDoctorArticles(false);
		selectCommand.setLogin("doctor");
		commandExecutor.execute(new ServerRequest<Article>(selectCommand));
		verify(articleFacade).getPublishedArticles("doctor", 0, 10);
	}

	@Test
	public void testHandleSelectPublishedArticleByArticleId() {
		SelectArticlesCommand selectCommand = new SelectArticlesCommand();
		selectCommand.setElementId(1);
		selectCommand.setDoctorArticles(false);
		commandExecutor.execute(new ServerRequest<Article>(selectCommand));
		verify(articleFacade).get(1);
	}

	@Test
	public void testHandleSelectPublishedArticlesCount() {
		SelectArticlesCountCommand selectCommand = new SelectArticlesCountCommand();
		selectCommand.setDoctorArticles(false);
		selectCommand.setLogin("doctor");
		commandExecutor.execute(new ServerRequest<Article>(selectCommand));
		verify(articleFacade).getPublishedArticlesCount("doctor");
	}

	@Test
	public void testHandleSearchArticlesCommand() {
		SearchArticleCommand selectCommand = new SearchArticleCommand();
		Date dateFrom = new Date();
		Date dateTo = new Date();
		selectCommand.setCreatedFrom(dateFrom);
		selectCommand.setCreatedTo(dateTo);
		selectCommand.setTitle("title");
		selectCommand.setLogin("doctor");
		commandExecutor.execute(new ServerRequest<Article>(selectCommand));
		verify(articleFacade).findArticles("title", dateFrom, dateTo, null, null, 0, 10);
	}

	@Test
	public void testHandleSearchArticlesCountCommand() {
		SearchArticleCountCommand selectCommand = new SearchArticleCountCommand();
		Date dateFrom = new Date();
		Date dateTo = new Date();
		selectCommand.setCreatedFrom(dateFrom);
		selectCommand.setCreatedTo(dateTo);
		selectCommand.setTitle("title");
		selectCommand.setLogin("doctor");
		commandExecutor.execute(new ServerRequest<Article>(selectCommand));
		verify(articleFacade).findArticlesCount("title", dateFrom, dateTo, null, null);
	}
}
