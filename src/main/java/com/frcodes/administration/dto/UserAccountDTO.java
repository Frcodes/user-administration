package com.frcodes.administration.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Class with the user account information as transfer entity
 * 
 * @author frCodes
 *
 */
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((IBAN == null) ? 0 : IBAN.hashCode());
		result = prime * result + ((fistName == null) ? 0 : fistName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserAccountDTO other = (UserAccountDTO) obj;
		if (IBAN == null) {
			if (other.IBAN != null)
				return false;
		} else if (!IBAN.equals(other.IBAN))
			return false;
		if (fistName == null) {
			if (other.fistName != null)
				return false;
		} else if (!fistName.equals(other.fistName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		return true;
	}

}
