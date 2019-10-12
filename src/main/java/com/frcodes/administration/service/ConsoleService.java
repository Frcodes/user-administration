package com.frcodes.administration.service;

import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frcodes.administration.dto.UserAccountDTO;
import com.frcodes.administration.model.Account;
import com.frcodes.administration.model.User;

@Service
public class ConsoleService {

	public enum Action {
		C, L, U, D, R, E
	}

	public enum ActionAdministrator {
		C, L, U, E
	}

	@Autowired
	private AccountService accountService;

	@Autowired
	private UserService userService;

	private Scanner scanner;

	public void start() {
		System.out.println();
		System.out.println("-------------------------------------------");
		System.out.println("-------------- Welcome ---------------------");
		scanner = new Scanner(System.in);

		User user = login();
		if (user != null) {
			SessionData.setUser(user);
			System.out.println("User session: " + user.getName() + ", " + user.getSurname());
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
			System.out.println("> User no registrated ");
			System.out.println("> Do you want to have register ? (Y/N)");
			String answer = scanner.nextLine();
			if (answer != null && answer.trim().toUpperCase().equals("Y")) {

				User userNew = this.readUser();
				user = this.userService.createUser(userNew);
				System.out.println(
						"> User registrated with ID: " + user.getUserId() + " and username: " + user.getName());
			}
		}
		return user;
	}

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
				Action.valueOf(line.toUpperCase());
				if (SessionData.isAdministrator()) {
					Action.valueOf(line.toUpperCase());
				} else {
					ActionAdministrator.valueOf(line.toUpperCase());
				}
			} catch (IllegalArgumentException ex) {
				action = null;
				this.printWithUserError("action no valid");
			}
		} else {
			this.printWithUserError("action no valid");
		}

		return action;

	}

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

	private UserAccountDTO readUserAccount() {

		UserAccountDTO userAccount = new UserAccountDTO();
		printWithUser("Insert account data:");

		printWithUser("First name:");
		userAccount.setFistName(scanner.nextLine());

		printWithUser("Lastname:");
		userAccount.setLastName(scanner.nextLine());

		printWithUser("IBAN:");
		userAccount.setIBAN(scanner.nextLine());

		return userAccount;
	}

	private User readUser() {

		User user = new User();
		System.out.print("Insert user information:");

		System.out.print("First name:");
		user.setName(scanner.nextLine());

		System.out.print("Lastname:");
		user.setSurname(scanner.nextLine());

		return user;
	}

	private String readIBAN() {

		String iBAN = null;

		printWithUser("Insert IBAN:");

		iBAN = scanner.nextLine();

		// TODO: Validate IBAN
		return iBAN;
	}

	private void writeAccount(List<Account> list) {

		printWithUser("Account list:");

		for (Account account : list) {
			printWithUser(account.toString());
		}
	}

	private void writeUser(List<User> list) {

		printWithUser("Users registered:");

		for (User user : list) {
			printWithUser(user.toString());
		}
	}

	private void printWithUser(String str) {
		System.out.println("[" + SessionData.getUser().getName() + "] " + str);
	}

	private void printWithUserError(String str) {
		System.out.println("[" + SessionData.getUser().getName() + "] - ERROR: " + str);
	}

}
