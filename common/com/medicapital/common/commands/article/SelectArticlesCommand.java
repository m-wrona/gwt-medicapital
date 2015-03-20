package com.medicapital.common.commands.article;

import java.util.Date;
import com.medicapital.common.commands.entity.SelectCommand;
import com.medicapital.common.entities.Article;

public class SelectArticlesCommand extends SelectCommand<Article> {

	private boolean doctorArticles;
	private Date createdFrom;
	private Date createdTo;

	public SelectArticlesCommand() {
		super(Article.class);
	}

	public boolean isDoctorArticles() {
		return doctorArticles;
	}

	public void setDoctorArticles(boolean doctorArticles) {
		this.doctorArticles = doctorArticles;
	}

	public void setCreatedFrom(Date createdFrom) {
		this.createdFrom = createdFrom;
	}

	public void setCreatedTo(Date createdTo) {
		this.createdTo = createdTo;
	}

	public Date getCreatedTo() {
		return createdTo;
	}

	public Date getCreatedFrom() {
		return createdFrom;
	}

	@Override
	public String toString() {
		StringBuilder string = new StringBuilder("[");
		string.append(super.toString()).append(", ");
		string.append("doctorArticles: ").append(doctorArticles).append(", ");
		string.append("createdFrom: ").append(createdFrom).append(", ");
		string.append("createdTo: ").append(createdTo).append(", ");
		string.append("]");
		return string.toString();
	}
}
