package com.medicapital.server.database.hibernate.entities;

import java.util.Date;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Table(name = "User")
@Entity
public class UserEntity {

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private int userId;

	@Column(name = "eMail")
	private String email;

	private String firstName;

	private String lastName;

	private String login;

	private String password;

	private String phoneNumber;

	private String mobile;

	private Date birthDate;

	private Date lastLoginDate;

	private String sex;

	private String personalId;

	private boolean active;
	
	private String userRole;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "localizationId")
	private LocalizationEntity localization;

	@ManyToMany(fetch = FetchType.EAGER, targetEntity = HobbyEntity.class)
	@JoinTable(name = "UserHobbies", joinColumns = { @JoinColumn(name = "userId") }, inverseJoinColumns = { @JoinColumn(name = "hobbyId") })
	private Set<HobbyEntity> userHobbies;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public LocalizationEntity getLocalization() {
		return localization;
	}

	public void setLocalization(LocalizationEntity localization) {
		this.localization = localization;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPersonalId() {
		return personalId;
	}

	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Set<HobbyEntity> getHobbies() {
		return userHobbies;
	}

	public void setHobbies(Set<HobbyEntity> hobbies) {
		this.userHobbies = hobbies;
	}
	
	public void setUserRole(String userRole) {
	    this.userRole = userRole;
    }
	
	public String getUserRole() {
	    return userRole;
    }

}
