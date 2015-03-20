package com.medicapital.common.commands.article;

import com.medicapital.common.entities.Article;
import com.medicapital.common.commands.entity.SelectCountCommand;

public class SelectArticlesCountCommand extends SelectCountCommand<Article> {

	private boolean doctorArticles;

	public SelectArticlesCountCommand() {
		super(Article.class);
	}

	public boolean isDoctorArticles() {
		return doctorArticles;
	}

	public void setDoctorArticles(boolean doctorArticles) {
		this.doctorArticles = doctorArticles;
	}
	
	@Override
	public String toString() {
		StringBuilder string = new StringBuilder("[");
		string.append(super.toString()).append(", ");
		string.append("doctorArticles: ").append(doctorArticles).append(", ");
		string.append("]");
		return string.toString();
	}
}
