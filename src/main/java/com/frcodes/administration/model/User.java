package com.frcodes.administration.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USER")
public class User {

	public enum UserType {
		ADMIN, BASIC
	};

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String userId;

	@Column(length = 100, nullable = false)
	private String name;

	@Column(length = 100)
	private String surname;

	@Column(length = 50)
	private String type;

	@Column
	private Date date;

	@Column
	private Boolean enabled;

	public User() {
		this.init();
	}

	public void init() {
		date = new Date();
		enabled = Boolean.TRUE;
		type = UserType.BASIC.name();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", name=" + name + ", surname=" + surname + ", type=" + type + ", date="
				+ date + ", enabled=" + enabled + "]";
	}

	

}
