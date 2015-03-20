package com.medicapital.common.entities;

import java.util.Date;

/**
 * Entity represents data about access to service by logged user.
 * 
 * @author michal
 * 
 */
public class LoginData implements SerializableEntity {

	private String login;
	private int userId = -1;
	private UserRole role;
	private Date lastLoginDate;

	public LoginData(User user) {
		login = user.getLogin();
		userId = user.getId();
		lastLoginDate = user.getLastLoginDate();
		role = UserRole.User;
	}

	public LoginData(Doctor doctor) {
		this(doctor.getUser());
		role = UserRole.Doctor;
	}

	public LoginData() {

	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getLogin() {
		return login;
	}

	public int getId() {
		return userId;
	}

	public void setId(int userId) {
		this.userId = userId;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public LoginData clone() {
		final LoginData clone = new LoginData();
		clone.setLogin(login);
		clone.setId(userId);
		clone.setRole(role);
		if (lastLoginDate != null) {
			clone.setLastLoginDate(new Date(lastLoginDate.getTime()));
		}
		return clone;
	}

	@Override
	public String toString() {
		final StringBuilder data = new StringBuilder("[");
		data.append("userId: " + userId);
		data.append(", userRole: " + role);
		data.append(", login: " + login);
		data.append(", lastLoginDate: " + lastLoginDate);
		data.append("]");
		return data.toString();
	}

}
