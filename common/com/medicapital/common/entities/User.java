package com.medicapital.common.entities;

import java.util.Date;
import java.util.Set;
import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * The persistent class for the User database table.
 * 
 */
public class User implements SerializableEntity {

	public enum Sex implements IsSerializable {
		Male, Female
	}

	private int userId;

	private String email;

	private String firstName;

	private String lastName;

	private String login;

	private String password;

	private String phoneNumber;

	private String mobile;

	private Localization localization;

	private Date birthDate;

	private Date lastLoginDate;

	private Sex sex;

	private String personalId;

	private boolean active;

	private Set<Integer> hobbies;

	private UserRole userRole = UserRole.User;

	public int getId() {
		return this.userId;
	}

	public void setId(int userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Localization getLocalization() {
		return localization;
	}

	public void setLocalization(Localization localization) {
		this.localization = localization;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMobile() {
		return mobile;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}

	public String getPersonalId() {
		return personalId;
	}

	public void setHobbies(Set<Integer> hobbies) {
		this.hobbies = hobbies;
	}

	public Set<Integer> getHobbies() {
		return hobbies;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	@Override
	public String toString() {
		StringBuilder data = new StringBuilder("[");
		data.append("id: " + userId);
		data.append(", login: " + login);
		data.append(", password: " + password);
		data.append(", eMail: " + email);
		data.append(", firstName: " + firstName);
		data.append(", lastName: " + lastName);
		data.append(", birthDate: " + birthDate);
		data.append(", sex: " + sex);
		data.append(", phoneNumber: " + phoneNumber);
		data.append(", mobile: " + mobile);
		data.append(", active: " + active);
		data.append(", lastLoginDate: " + lastLoginDate);
		data.append(", userRole: " + userRole);
		data.append(", Localization: " + localization);
		data.append(", PersonalId: " + personalId);
		data.append(", Hobbies: " + hobbies);
		data.append("]");
		return data.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (super.equals(obj)) {
			return true;
		} else if (!(obj instanceof User)) {
			return false;
		}
		User other = (User) obj;
		return userId == other.userId;
	}

}