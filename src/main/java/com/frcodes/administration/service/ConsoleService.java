package com.frcodes.administration.service;

import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frcodes.administration.dto.UserAccountDTO;
import com.frcodes.administration.model.Account;
import com.frcodes.administration.model.User;

/**
 * Service with input and output in console. This class use other services with
 * functionality about user administration.
 * 
 * @author frCodes
 *
 */
@Service
public class ConsoleService {

	/**
	 * Actions: Create, List, Update, Delete, Users registers, Exit
	 *
	 */
	public enum ActionAdministrator {
		C, L, U, D, R, E
	}

	/**
	 * Actions: Create, List, Exit
	 *
	 */
	public enum ActionBasic {
		C, L, E
	}

	@Autowired
	private AccountService accountService;

	@Autowired
	private UserService userService;

	private Scanner scanner;

	/**
	 * Main process with input and output of applications
	 */
	public void start() {
		System.out.println();
		System.out.println("-------------------------------------------");
		System.out.println("-------------- Welcome ---------------------");
		scanner = new Scanner(System.in);

		User user = login();
		if (user != null) {
			SessionData.setUser(user);
			System.out
					.println("User session. ID: " + user.getUserId() + ", " + user.getName() + " " + user.getSurname());
			System.out.println("");

			String action = chooseAction();
			while (action == null || !action.equals("E")) {
				processAction(action);
				action = chooseAction();
			}
		}

		scanner.close();
		System.out.println("------------- Bye bye ----------------------");
		System.out.println("-------------------------------------------");
	}

	/**
	 * Login of user with name and userId input. If user is not registered, if
	 * possible to create user.
	 * 
	 * @return User login. User registered in applications.
	 */
	private User login() {

		User user = null;
		System.out.println("> Login");
		System.out.print("> Insert user name: ");
		String username = scanner.nextLine();

		try {
			System.out.print("> Insert user ID: ");
			String id = scanner.nextLine();
			Long userId = Long.valueOf(id);
			user = userService.validUser(userId, username);
		} catch (NumberFormatException e) {
			System.out.println("> User id format no valid");
		}

		if (user == null) {
			user = this.registerUser();
		}
		return user;
	}

	/**
	 * Registers user in application.
	 * 
	 * @return User registered
	 */
	private User registerUser() {
		User user = null;
		System.out.println("> User no registrated ");
		System.out.println("> Do you want to have register ? (Y/N)");
		String answer = scanner.nextLine();
		if (answer != null && answer.trim().toUpperCase().equals("Y")) {

			User userNew = this.readUser();
			user = this.userService.createUser(userNew);
			System.out.println("> User registrated with ID: " + user.getUserId() + " and username: " + user.getName());
		} else if (answer != null && !answer.trim().toUpperCase().equals("N")) {
			System.out.println("> Answer no valid");
		}

		return user;
	}

	/**
	 * 
	 * @return
	 */
	private String chooseAction() {

		String action = null;

		if (SessionData.isAdministrator()) {
			this.printWithUser("Choose action:  create (C), list(L), update(U), delete(D), user register(R), exit(E) ");
		} else {
			this.printWithUser("Choose action:  create (C), list(L), update(U), exit(E) ");
		}
		String line = scanner.nextLine();
		if (line != null && !line.trim().isEmpty() && line.trim().length() == 1) {
			action = line;
			try {
				if (SessionData.isAdministrator()) {
					ActionAdministrator.valueOf(line.toUpperCase());
				} else {
					ActionBasic.valueOf(line.toUpperCase());
				}
			} catch (IllegalArgumentException ex) {
				action = null;
				this.printWithUserError("action no permitted");
			}
		} else {
			this.printWithUserError("action no valid");
		}

		return action;

	}

	/**
	 * Perfom the action choose for user.
	 * 
	 * @param action Type of action. See Actions enums.
	 */
	private void processAction(String action) {

		if (action != null) {

			try {
				switch (action) {
				case "C":
					UserAccountDTO userAccount = readUserAccount();
					Account account = accountService.createUserAccount(userAccount);
					printWithUser("Account created with ID: " + account.getAccountId());
					break;
				case "L":
					List<Account> list = accountService.listAccount();
					writeAccount(list);
					break;
				case "U":
					UserAccountDTO userAccountUpdate = readUserAccount();
					Account accountUpdated = accountService.updateUserAccount(userAccountUpdate);
					printWithUser("Account updated with ID: " + accountUpdated.getAccountId());
					break;
				case "D":
					String iBAN = readIBAN();
					accountService.deleteAccountByIBAN(iBAN);
					printWithUser("Account deleted");
					break;
				case "R":
					List<User> listUser = userService.listUser();
					writeUser(listUser);
					break;
				default:
					printWithUserError("Operation no implemented");
				}
			} catch (Exception ex) {
				printWithUserError(ex.getMessage());
			}
		}
	}

	/**
	 * Read of console information about user account.
	 * 
	 * @return User account introduced by user
	 */
	private UserAccountDTO readUserAccount() {

		UserAccountDTO userAccount = new UserAccountDTO();
		printWithUser("Insert account data:");

		printWithUserLine("First name:");
		userAccount.setFistName(scanner.nextLine());

		printWithUserLine("Lastname:");
		userAccount.setLastName(scanner.nextLine());

		printWithUserLine("IBAN:");
		userAccount.setIBAN(scanner.nextLine());

		return userAccount;
	}

	/**
	 * Read of console information about user.
	 * 
	 * @return User data introduced by user
	 */
	private User readUser() {

		User user = new User();
		System.out.println("Insert user information,");

		System.out.print("First name:");
		user.setName(scanner.nextLine());

		System.out.print("Lastname:");
		user.setSurname(scanner.nextLine());

		return user;
	}

	/**
	 * Read of console IBAN information
	 * 
	 * @return IBAN introduced by user
	 */
	private String readIBAN() {

		String iBAN = null;

		printWithUserLine("Insert IBAN:");

		iBAN = scanner.nextLine();

		return iBAN;
	}

	/**
	 * Writes in console list of account
	 * 
	 * @param list List of account information
	 */
	private void writeAccount(List<Account> list) {

		printWithUser("Account list:");

		for (Account account : list) {
			printWithUser(account.toString());
		}
	}

	/**
	 * Writes in console list of users
	 * 
	 * @param list List of users
	 */
	private void writeUser(List<User> list) {

		printWithUser("Users registered:");

		for (User user : list) {
			printWithUser(user.toString());
		}
	}

	/**
	 * Writes in console string with prefix user session and enter
	 * 
	 * @param str String
	 */
	private void printWithUser(String str) {
		System.out.println("[" + SessionData.getUser().getName() + "] " + str);
	}

	/**
	 * Writes in console string with prefix user session
	 * 
	 * @param str String
	 */
	private void printWithUserLine(String str) {
		System.out.print("[" + SessionData.getUser().getName() + "] " + str);
	}

	/**
	 * Writes in console string with prefix user session and error identify
	 * 
	 * @param str String
	 */
	private void printWithUserError(String str) {
		System.out.println("[" + SessionData.getUser().getName() + "] - ERROR: " + str);
	}

}
