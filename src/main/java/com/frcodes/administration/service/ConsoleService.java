package com.frcodes.administration.service;

import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frcodes.administration.model.Account;
import com.frcodes.administration.model.User;
import com.frcodes.administration.model.UserAccountDTO;

@Service
public class ConsoleService {

	public enum Action {
		C, L, U, D, R, E
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

		login();
		String action = chooseAction();
		while (action == null || !action.equals("E")) {
			processAction(action);
			action = chooseAction();
		}

		scanner.close();
		System.out.println("------------- Bye bye ----------------------");
		System.out.println("-------------------------------------------");
	}

	private void login() {

		System.out.println("> Login");
		System.out.print("> Insert user: ");
		scanner = new Scanner(System.in);
		String userSession = scanner.nextLine();

		SessionData.setUserSession(userSession);
		System.out.println("User session: " + userSession);
		System.out.println("");

//		System.out.println("password: ");
//		scanner.useDelimiter("[*]");
//		String pass = scanner.nextLine();

	}

	private String chooseAction() {

		this.printWithUser("Choose action:  create (C), list(L), update(U), delete(D), user register (R), exit(E) ");
		scanner = new Scanner(System.in);
		String line = scanner.nextLine();

		String action = null;

		if (line != null && !line.trim().isEmpty() && line.trim().length() == 1) {
			action = line;
			try {
				Action.valueOf(line.toUpperCase());
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
		scanner = new Scanner(System.in);

		printWithUser("First name:");
		userAccount.setFistName(scanner.nextLine());

		printWithUser("Lastname:");
		userAccount.setLastName(scanner.nextLine());

		printWithUser("IBAN:");
		userAccount.setIBAN(scanner.nextLine());

		return userAccount;
	}

	private String readIBAN() {

		String iBAN = null;

		printWithUser("Insert IBAN:");
		scanner = new Scanner(System.in);

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
		System.out.println("[" + SessionData.getUserSession() + "] " + str);
	}

	private void printWithUserError(String str) {
		System.out.println("[" + SessionData.getUserSession() + "] - ERROR: " + str);
	}

}
