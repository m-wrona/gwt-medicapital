package com.medicapital.client.pages.article;

import java.util.HashMap;
import java.util.Map;
import com.medicapital.client.article.ArticleFactory;
import com.medicapital.client.article.EditableArticleListPresenter;
import com.medicapital.client.pages.DisplayPageEvent;
import com.medicapital.client.pages.DoctorPage;
import com.medicapital.common.commands.article.SelectArticlesCommand;
import com.medicapital.common.commands.article.SelectArticlesCountCommand;
import com.medicapital.common.util.MapUtils;

public class DoctorArticlesPage extends DoctorPage<DoctorArticlesPageForm> {

	public static final String PARAM_PAGE_NUMBER = "PageNumber";
	private EditableArticleListPresenter listPresenter;

	@Override
	protected DoctorArticlesPageForm createPageView() {
		return new DoctorArticlesPageForm();
	}

	@Override
	protected void initPage(DoctorArticlesPageForm pageView) {
		ArticleFactory articleFactory = new ArticleFactory();
		articleFactory.getCommandFactory().setLogin(getLoggedUser());
		SelectArticlesCountCommand selectArticlesCountCommand = new SelectArticlesCountCommand();
		selectArticlesCountCommand.setLogin(getLoggedUser());
		selectArticlesCountCommand.setDoctorArticles(true);
		articleFactory.getCommandFactory().setSelectCountCommand(selectArticlesCountCommand);
		SelectArticlesCommand selectArticlesCommand = new SelectArticlesCommand();
		selectArticlesCommand.setLogin(getLoggedUser());
		selectArticlesCommand.setDoctorArticles(true);
		articleFactory.getCommandFactory().setSelectCommand(selectArticlesCommand);
		listPresenter = articleFactory.createEditArticleListPresenter(pageView.getEditArticleListForm());
		getPresenters().add(listPresenter);
	}

	@Override
	public void loadState(Map<String, String> pageStateParameters) {
		int pageNumber = MapUtils.getInt(pageStateParameters, PARAM_PAGE_NUMBER, 1);
		listPresenter.goToPage(pageNumber);
	}

	@Override
	public Map<String, String> saveState() {
		Map<String, String> pageState = new HashMap<String, String>();
		pageState.put(PARAM_PAGE_NUMBER, "" + listPresenter.getCurrentPageNumber());
		return pageState;
	}

	public static DisplayPageEvent createPageEvent(final Object sender) {
		final DisplayPageEvent pageEvent = new DisplayPageEvent(sender, DoctorArticlesPage.class);
		return pageEvent;
	}

}
