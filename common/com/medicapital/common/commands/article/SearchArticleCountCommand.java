package com.medicapital.common.commands.article;

import java.util.Date;
import com.medicapital.common.commands.entity.SelectCountCommand;
import com.medicapital.common.entities.Article;

public class SearchArticleCountCommand extends SelectCountCommand<Article> {

	private Date createdFrom;
	private Date createdTo;
	private String firstName;
	private String lastName;
	private String title;

	public SearchArticleCountCommand() {
		super(Article.class);
	}

	public Date getCreatedFrom() {
		return createdFrom;
	}

	public void setCreatedFrom(Date createdFrom) {
		this.createdFrom = createdFrom;
	}

	public Date getCreatedTo() {
		return createdTo;
	}

	public void setCreatedTo(Date createdTo) {
		this.createdTo = createdTo;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Override
	public String toString() {
		StringBuilder string = new StringBuilder("[");
		string.append(super.toString()).append(", ");
		string.append("createdFrom: ").append(createdFrom).append(", ");
		string.append("createdTo: ").append(createdTo).append(", ");
		string.append("title: ").append(title).append(", ");
		string.append("firstName: ").append(firstName).append(", ");
		string.append("lastName: ").append(lastName).append(", ");
		string.append("]");
		return string.toString();
	}
}
