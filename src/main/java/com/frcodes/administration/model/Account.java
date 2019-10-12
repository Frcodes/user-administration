package com.frcodes.administration.model;

import java.util.Date;

public class Account {

	private String accountId;

	private String userId;

	private String iban;

	private String createdBy;

	private Date date;

	private Boolean enabled;

	public Account() {
		this.init();
	}

	public void init() {
		accountId = (String.valueOf((new Date()).getTime()));
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
