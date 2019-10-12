package com.frcodes.administration.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserAccountDTO {

	@NotNull(message = "Name cannot be null")
	private String fistName;

	private String lastName;

	@Size(min = 24, max = 24, message = "IBAN must be 24 characters")
	private String IBAN;

	public String getFistName() {
		return fistName;
	}

	public void setFistName(String fistName) {
		this.fistName = fistName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getIBAN() {
		return IBAN;
	}

	public void setIBAN(String iBAN) {
		IBAN = iBAN;
	}



}
