package com.frcodes.administration.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ACCOUNT")
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String accountId;

	@Column(length = 100,  nullable=false)
	private String userId;

	@Column(unique=true, nullable=false) 
	private String iban;

	@Column(length = 100)
	private String createdBy;

	@Column
	private Date date;

	@Column
	private Boolean enabled;

	public Account() {
		this.init();
	}

	public void init() {
		date = new Date();
		enabled = Boolean.TRUE;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
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

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Override
	public String toString() {
		return "Account [userId=" + userId + ", iban=" + iban + ", createdBy=" + createdBy + ", date=" + date + "]";
	}

}
