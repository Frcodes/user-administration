package com.frcodes.administration.service;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.xml.bind.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frcodes.administration.dto.UserAccountDTO;
import com.frcodes.administration.model.Account;
import com.frcodes.administration.model.User;
import com.frcodes.administration.repository.AccountRepository;

import javassist.NotFoundException;

/**
 * Class with the methods about accounts management.
 * 
 * @author frCodes
 *
 */
@Service
public class AccountService {

	@Autowired
	private UserService userService;

	@Autowired
	private AccountRepository accountRepository;

	/**
	 * Returns account list of application
	 * 
	 * @return List<Account> List of accounts
	 */
	public List<Account> listAccount() {
		List<Account> list = null;
		if (SessionData.isAdministrator()) {
			list = (List<Account>) accountRepository.findAll();
		} else {
			list = (List<Account>) accountRepository.findByCreatedBy(SessionData.getUser().getUserId());
		}
		return list;
	}

	/**
	 * Creates a new account in application. Also a new user is created associated
	 * to new account
	 * 
	 * @param userAccount Transfer object with acoount and user information
	 * @return New account created
	 * @throws ValidationException
	 */
	public Account createUserAccount(UserAccountDTO userAccount) throws ValidationException {

		validateUserAccount(userAccount);
		User user = userService.createUser(userAccount);
		Account account = new Account();
		account.setUserId(user.getUserId());
		account.setIban(userAccount.getIBAN());
		account.setCreatedBy(SessionData.getUser().getUserId());
		account = accountRepository.save(account);

		return account;
	}

	/**
	 * Updates new account. If IBAN is null or empty, throw
	 * IllegalArgumentException.
	 * 
	 * Also a new user is created associated to new account. This point is improve,
	 * because it is possible to create duplicated users. The solutions is send the
	 * username or userId of user by input for check the user.
	 * 
	 * @param userAccount New information of account
	 * @return Account updated
	 * @throws ValidationException
	 * @throws                     NotFoundException. Exception when not found
	 *                             account by IBAN.
	 */
	public Account updateUserAccount(UserAccountDTO userAccount) throws NotFoundException, ValidationException {

		Account res = null;
		validateUserAccount(userAccount);
		Account accountOld = this.getAccountByIBAN(userAccount.getIBAN());

		if (accountOld != null) {

			if (accountOld.getCreatedBy() != null
					&& SessionData.getUser().getUserId().equals(accountOld.getCreatedBy())) {
				User user = userService.createUser(userAccount);
				accountOld.setUserId(user.getUserId());
				res = accountRepository.save(accountOld);
			} else {
				throw new SecurityException("No privilege for modify a account created by another user");
			}

		} else {
			throw new NotFoundException("Account no found by IBAN");
		}

		return res;
	}

	/***
	 * Deletes account by IBAN. If IBAN is null or empty, throw
	 * IllegalArgumentException.
	 * 
	 * @param iBAN IBAN of account
	 * @return <TRUE> if the account had been deleted, <FALSE> in another case.
	 * @throws NotFoundException. Exception when not found account by IBAN.
	 */
	public boolean deleteAccountByIBAN(String iBAN) throws NotFoundException {

		boolean deleted = Boolean.FALSE;

		if (iBAN == null || iBAN.trim().isEmpty()) {
			throw new IllegalArgumentException("IBAN is not correct");
		}
		Account accountOld = this.getAccountByIBAN(iBAN);
		if (accountOld != null) {

			if (accountOld.getCreatedBy() != null
					&& SessionData.getUser().getUserId().equals(accountOld.getCreatedBy())) {
				accountRepository.delete(accountOld);
				deleted = Boolean.TRUE;
			} else {
				throw new SecurityException("No privilege for delete a account created by another user");
			}

		} else {
			throw new NotFoundException("Account no found by IBAN");
		}

		return deleted;
	}

	/**
	 * Finds account by IBAN. If IBAN is null or empty, throw
	 * IllegalArgumentException. If account is not found, return null.
	 * 
	 * @param iBAN IBAN of account
	 * @return Account with IBAN found.
	 */
	public Account getAccountByIBAN(String iBAN) {

		if (iBAN == null || iBAN.trim().isEmpty()) {
			throw new IllegalArgumentException("IBAN is not correct");
		}
		return accountRepository.findByIban(iBAN);
	}

	/**
	 * Validates entity UserAccountDTO.
	 * 
	 * @param userAccount Entity UserAccountDTO
	 * @throws ValidationException
	 */
	private void validateUserAccount(UserAccountDTO userAccount) throws ValidationException {

		if (userAccount == null) {
			throw new IllegalArgumentException("Information is null");
		}

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<UserAccountDTO>> violations = validator.validate(userAccount);

		if (!violations.isEmpty()) {
			StringBuilder str = new StringBuilder();
			for (ConstraintViolation<UserAccountDTO> violation : violations) {
				str.append(violation.getMessage()).append("\n");
			}
			throw new ValidationException(str.toString());
		}
	}

}
