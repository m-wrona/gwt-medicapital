package com.medicapital.common.entities;

import java.util.Date;
import java.util.List;

public class Article implements SerializableEntity {

	private int articleId;
	private String title;
	private String body;
	private Date created;
	private Date lastUpdate;
	private int authorId;
	private String authorLogin;
	private String authorLastName;
	private String authorFirstName;
	private List<UrlResource> attachments;
	private boolean published = false;

	public String getTitle() {
		return title;
	}

	public void setTitle(String header) {
		this.title = header;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Date getCreateDate() {
		return created;
	}

	public void setCreateDate(Date date) {
		this.created = date;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public int getId() {
		return articleId;
	}

	public void setId(int articleId) {
		this.articleId = articleId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	public int getAuthorId() {
		return authorId;
	}

	public String getAuthorLogin() {
		return authorLogin;
	}

	public void setAuthorLogin(String authorLogin) {
		this.authorLogin = authorLogin;
	}

	public String getAuthorLastName() {
		return authorLastName;
	}

	public void setAuthorLastName(String authorLastName) {
		this.authorLastName = authorLastName;
	}

	public String getAuthorFirstName() {
		return authorFirstName;
	}

	public void setAuthorFirstName(String authoFirstName) {
		this.authorFirstName = authoFirstName;
	}

	public void setAttachments(List<UrlResource> attachments) {
		this.attachments = attachments;
	}

	public List<UrlResource> getAttachments() {
		return attachments;
	}

	public int getAttachmentsCount() {
		return attachments != null ? attachments.size() : 0;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}

	@Override
	public String toString() {
		StringBuilder data = new StringBuilder("[");
		data.append("articleId: " + articleId);
		data.append(", header: " + title);
		data.append(", body: " + body);
		data.append(", created: " + created);
		data.append(", lastUpdate: " + lastUpdate);
		data.append(", authorId: " + authorId);
		data.append(", authorLogin: " + authorLogin);
		data.append(", authorFirstName: " + authorFirstName);
		data.append(", authorLastName: " + authorLastName);
		data.append(", attachments count: " + getAttachmentsCount());
		data.append(", published: " + published);
		data.append("]");
		return data.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (super.equals(obj)) {
			return true;
		} else if (!(obj instanceof Article)) {
			return false;
		}
		Article other = (Article) obj;
		return articleId == other.articleId;
	}

}
