package com.frcodes.administration.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import com.frcodes.administration.dto.UserAccountDTO;
import com.frcodes.administration.model.Account;
import com.frcodes.administration.model.User;

import javassist.NotFoundException;

/**
 * Integration test
 * 
 * Use memory database H2
 * 
 * @author frcodes
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class AccountServiceIntegrationTest {

	@Autowired
	private AccountService accountService;

	@Autowired
	private UserService userService;

	private UserAccountDTO buildUserAccountDTO() {
		UserAccountDTO userAccount = new UserAccountDTO();
		userAccount.setFistName("Paul");
		userAccount.setLastName("Smith");
		userAccount.setIBAN("001234AABB00");
		return userAccount;
	}

	@Test
	public void listAccount() {

		List<?> listEmpty = accountService.listAccount();
		assertEquals(listEmpty.size(), 0L);

		accountService.createUserAccount(buildUserAccountDTO());

		List<?> list = accountService.listAccount();
		assertEquals(list.size(), 1L);
	}

	@Test
	public void createUserAccount() {

		Account account = accountService.createUserAccount(buildUserAccountDTO());
		assertNotNull(account);
		assertEquals(accountService.listAccount().size(), 1L);
	}

	@Test(expected = DataIntegrityViolationException.class)
	public void createUserAccountEmpty() {
		UserAccountDTO userAccount = new UserAccountDTO();
		accountService.createUserAccount(userAccount);
	}

	@Test(expected = IllegalArgumentException.class)
	public void updateUserAccountNull() throws NotFoundException {
		accountService.updateUserAccount(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void updateUserAccountEmpty() throws NotFoundException {
		accountService.updateUserAccount(new UserAccountDTO());
	}

	@Test
	public void updateUserAccount() throws NotFoundException {

		UserAccountDTO userAccount = buildUserAccountDTO();
		accountService.createUserAccount(userAccount);
		assertEquals(accountService.listAccount().size(), 1L);

		userAccount.setFistName("Mary");
		userAccount.setLastName("Martins");

		Account accountUpdated = accountService.updateUserAccount(userAccount);

		assertNotNull(accountUpdated);
		assertEquals(accountService.listAccount().size(), 1L);
		assertEquals(accountUpdated.getIban(), userAccount.getIBAN());

		// Check user changes
		User user = userService.getUserByUserId(accountUpdated.getUserId());
		assertEquals(user.getName(), userAccount.getFistName());
		assertEquals(user.getSurname(), userAccount.getLastName());

	}

	@Test(expected = IllegalArgumentException.class)
	public void deleteAccountByIBANNull() throws NotFoundException {
		accountService.deleteAccountByIBAN(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void getAccountByIBANNull() {
		accountService.getAccountByIBAN(null);
	}

}
