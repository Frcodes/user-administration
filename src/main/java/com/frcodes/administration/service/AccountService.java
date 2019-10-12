package com.frcodes.administration.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.frcodes.administration.model.Account;
import com.frcodes.administration.model.UserAccountDTO;

import javassist.NotFoundException;

@Service
public class AccountService {

	public List<Account> listAccount() {
		// TODO:
		return null;
	}

	public Account createUserAccount(UserAccountDTO userAccount) {
		// TODO:

		return null;
	}

	public Account updateUserAccount(UserAccountDTO userAccount) throws NotFoundException {

		// TODO:

		return null;
	}

	public boolean deleteAccountByIBAN(String iBAN) throws NotFoundException {

		// TODO:

		return null;
	}

	public Account getAccountByIBAN(String iBAN) {

		// TODO:
		return null;
	}

	public void createAccount(Account account) {
		// TODO:
	}

	public void updateAccount(Account account) {
		// TODO:
	}

	public void deleteAccount(Account account) {
		// TODO:
	}
}
