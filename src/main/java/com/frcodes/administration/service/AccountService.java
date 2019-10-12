package com.frcodes.administration.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frcodes.administration.model.Account;
import com.frcodes.administration.model.User;
import com.frcodes.administration.model.UserAccountDTO;
import com.frcodes.administration.repository.AccountRepository;

import javassist.NotFoundException;

@Service
public class AccountService {

	@Autowired
	private UserService userService;

	@Autowired
	private AccountRepository accountRepository;

	public List<Account> listAccount() {
		return (List<Account>) accountRepository.findAll();
	}

	public Account createUserAccount(UserAccountDTO userAccount) {

		User user = userService.createUser(userAccount);

		Account account = new Account();
		account.setUserId(user.getUserId());
		account.setIban(userAccount.getIBAN());
		account.setCreatedBy(SessionData.getUserSession());
		account = accountRepository.save(account);

		return account;
	}

	public Account updateUserAccount(UserAccountDTO userAccount) throws NotFoundException {

		Account res = null;
		if (userAccount.getIBAN() == null || userAccount.getIBAN().trim().isEmpty()) {
			throw new IllegalArgumentException("IBAN is not correct");
		} else {
			Account accountOld = this.getAccountByIBAN(userAccount.getIBAN());

			if (accountOld != null) {
				User user = userService.createUser(userAccount);
				accountOld.setUserId(user.getUserId());
				res = accountRepository.save(accountOld);
			} else {
				throw new NotFoundException("Account no found by IBAN");
			}
		}

		return res;
	}

	public boolean deleteAccountByIBAN(String iBAN) throws NotFoundException {

		boolean deleted = Boolean.FALSE;

		if (iBAN == null || iBAN.trim().isEmpty()) {
			throw new IllegalArgumentException("IBAN is not correct");
		} else {
			Account accountOld = this.getAccountByIBAN(iBAN);
			if (accountOld != null) {
				// delete accountOld
				deleted = Boolean.TRUE;
			} else {
				throw new NotFoundException("Account no found by IBAN");
			}
		}

		return deleted;
	}

	public Account getAccountByIBAN(String iBAN) {

		if (iBAN == null || iBAN.trim().isEmpty()) {
			throw new IllegalArgumentException("IBAN is not correct");
		}
		return accountRepository.findByIban(iBAN);
	}

	public void createAccount(Account account) {

	}

	public void updateAccount(Account account) {

	}

	public void deleteAccount(Account account) {

	}
}
