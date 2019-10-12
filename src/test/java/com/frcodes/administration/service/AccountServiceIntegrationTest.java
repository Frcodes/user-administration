package com.frcodes.administration.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.xml.bind.ValidationException;

import org.junit.Before;
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

	@Before
	public void setUp() {
		User user = new User();
		user.setUserId(1L);
		user.setName("Test");
		user.setSurname("Test");
		user.setType("ADMIN");

		SessionData.setUser(user);
	}

	private UserAccountDTO buildUserAccountDTO() {
		UserAccountDTO userAccount = new UserAccountDTO();
		userAccount.setFistName("Paul");
		userAccount.setLastName("Smith");
		userAccount.setIBAN("123456789001122336655440");
		return userAccount;
	}

	@Test
	public void listAccount() throws ValidationException {

		List<?> listEmpty = accountService.listAccount();
		assertEquals(listEmpty.size(), 0L);

		accountService.createUserAccount(buildUserAccountDTO());

		List<?> list = accountService.listAccount();
		assertEquals(list.size(), 1L);
	}

	@Test
	public void createUserAccount() throws ValidationException {

		Account account = accountService.createUserAccount(buildUserAccountDTO());
		assertNotNull(account);
		assertEquals(accountService.listAccount().size(), 1L);
	}

	@Test(expected = ValidationException.class)
	public void createUserAccountEmpty() throws ValidationException {
		UserAccountDTO userAccount = new UserAccountDTO();
		accountService.createUserAccount(userAccount);
	}

	@Test(expected = IllegalArgumentException.class)
	public void updateUserAccountNull() throws NotFoundException, ValidationException {
		accountService.updateUserAccount(null);
	}

	@Test(expected = ValidationException.class)
	public void updateUserAccountEmpty() throws NotFoundException, ValidationException {
		accountService.updateUserAccount(new UserAccountDTO());
	}

	@Test
	public void updateUserAccount() throws NotFoundException, ValidationException {

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

	@Test(expected = NotFoundException.class)
	public void updateUserAccountNotFound() throws NotFoundException, ValidationException {
		UserAccountDTO userAccount = buildUserAccountDTO();
		accountService.updateUserAccount(userAccount);
	}

	@Test(expected = IllegalArgumentException.class)
	public void deleteAccountByIBANNull() throws NotFoundException {
		accountService.deleteAccountByIBAN(null);
	}

	@Test
	public void deleteAccountByIBAN() throws NotFoundException, ValidationException {

		UserAccountDTO userAccount = buildUserAccountDTO();
		accountService.createUserAccount(userAccount);
		assertEquals(accountService.listAccount().size(), 1L);

		accountService.deleteAccountByIBAN(userAccount.getIBAN());

		assertEquals(accountService.listAccount().size(), 0L);
	}

	@Test(expected = NotFoundException.class)
	public void deleteAccountByIBANNotFound() throws NotFoundException {

		accountService.deleteAccountByIBAN("XXX");
	}

	@Test(expected = IllegalArgumentException.class)
	public void getAccountByIBANNull() {
		accountService.getAccountByIBAN(null);
	}

}
