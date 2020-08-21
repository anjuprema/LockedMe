package com.anju.lockedme;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class LockedMeMain {
	public static String rootPath = System.getProperty("user.home");
	public static String destnPath = rootPath + "/LockedMeCred/";
	public static String userDB = destnPath + "userDB.txt";
	public static String SEPARATOR = "%%%";
	public static String USERNAME = null;

	public void greetUser() {
		System.out.println("**************************************");
		System.out.println("*     Hello! Welcome to LockedMe     *");
		System.out.println("* Credential Management Application  *");
		System.out.println("*                                    *");
		System.out.println("*     Created by: Anju Prema         *");
		System.out.println("**************************************");
	}

	public Integer showMainMenu() throws InvalidOptionException {
		Scanner sc = new Scanner(System.in);
		Integer opt;
		System.out.println("1. Register");
		System.out.println("2. Login");
		System.out.println("3. Exit");
		System.out.println("Enter the option: ");
		opt = sc.nextInt();
		if (opt > 0 && opt <= 3) {
			return opt;
		} else
			throw new InvalidOptionException("Sorry.. Cannot process.You entered an Invalid option..");
	}

	public boolean validateUnamePswd(String s) {
		return Pattern.compile("[a-zA-Z0-9]{5,25}+").matcher(s).matches();
	}

	public boolean isUserAlreadyExist(String userName) throws FileNotFoundException {
		File UsersFile = new File(userDB);
		Scanner sc = new Scanner(UsersFile);
		HashMap<String, String> useraccess = new HashMap<String, String>();

		while (sc.hasNextLine()) {
			String data = sc.nextLine();
			String[] accessSplit = data.split(SEPARATOR);
			useraccess.put(accessSplit[0], accessSplit[1]);
		}
		sc.close();
		if (useraccess.containsKey(userName))
			return true;
		else
			return false;
	}

	public void registerUser(Integer mainOpt) throws InvalidOptionException, IOException {
		Scanner sc = new Scanner(System.in);
		System.out.println("");
		System.out.println("--------       Register     ----------");
		System.out.println(" Only Alpha Numeric characters allowed ");
		System.out.println(" & Requires more than 4 characters ");
		System.out.println("--------------------------------------");
		System.out.println("Enter User Name: ");
		String Uname = sc.nextLine();
		System.out.println("Enter Password: ");
		String Upswd = sc.nextLine();
		if (!validateUnamePswd(Uname)) {
			System.err.println("Cannot register. Not a valid User Name.");
			registerUser(mainOpt);
			return;
		} else {
			if (!validateUnamePswd(Upswd)) {
				System.err.println("Cannot register. Not a valid Password");
				registerUser(mainOpt);
				return;
			}
		}
		/* check if user name already exists */
		if (isUserAlreadyExist(Uname)) {
			System.err.println("Sorry! Cannot Register. User Name Already exists. ");
			registerUser(mainOpt);
			return;
		}
		/* if all ok, register user */
		File f = new File(userDB);
		if (f.canWrite()) {
			FileWriter fw = new FileWriter(f, true);
			fw.append(Uname + SEPARATOR + Upswd);
			fw.write(System.getProperty("line.separator"));
			fw.close();
		}
		// create file for each user to save credentials
		f = new File(destnPath + Uname + ".txt");
		if (!f.exists()) {
			f.createNewFile();
		}
		System.out.println("Registration Successful..");
		System.out.println("");
	}

	public boolean verifyLogin(String userName, String password) throws FileNotFoundException {
		File UsersFile = new File(userDB);
		Scanner sc = new Scanner(UsersFile);
		HashMap<String, String> useraccess = new HashMap<String, String>();

		while (sc.hasNextLine()) {
			String data = sc.nextLine();
			String[] accessSplit = data.split(SEPARATOR);
			useraccess.put(accessSplit[0], accessSplit[1]);
		}
		sc.close();
		String passwordFromHash = useraccess.get(userName);
		if (password.equals(passwordFromHash))
			return true;
		else
			return false;
	}

	public void loginUser() throws FileNotFoundException {
		Scanner sc = new Scanner(System.in);
		System.out.println("");
		System.out.println("---------------------------------------");
		System.out.println("--------         Login       ----------");
		System.out.println("---------------------------------------");
		System.out.println("Enter User Name: ");
		String Uname = sc.nextLine();
		System.out.println("Enter Password: ");
		String Upswd = sc.nextLine();
		Boolean status = verifyLogin(Uname, Upswd);
		if (status) {
			USERNAME = Uname;
			System.out.println("");
			System.out.println("-------     Welcome " + Uname + "      ------");
		} else {
			System.out.println("Cannot Login.. User Name, Password doesnot match.");
			loginUser();
			return;
		}
	}

	public Integer showSubMenu() throws InvalidOptionException {
		Scanner sc = new Scanner(System.in);
		System.out.println("");
		System.out.println("--------------------------------------");
		System.out.println("---    Manage Your Credentials    ----");
		System.out.println("--------------------------------------");
		System.out.println("1. Add New");
		System.out.println("2. List All");
		System.out.println("3. Search");
		System.out.println("4. Delete");
		System.out.println("5. Logout");
		Integer subMenuOpt = sc.nextInt();
		if (subMenuOpt > 0 && subMenuOpt <= 5) {
			return subMenuOpt;
		} else
			throw new InvalidOptionException("Sorry.. Cannot process.You entered an Invalid option..");

	}
	
	public void logoutUser() {
		USERNAME = null;
		System.out.println("Logout Successful..");
		System.out.println("-------------------------------------");
	}

	public void processSubMenuOption(Integer subMenuOpt) throws IOException, InvalidOptionException {
		if (USERNAME != null) {
			switch (subMenuOpt) {
			case 1:
				/* save new credential */
				AddNewCredential addNew = new AddNewCredential(USERNAME, SEPARATOR, destnPath);
				addNew.AddNewCredentialMain();
				subMenuOpt = showSubMenu();
				processSubMenuOption(subMenuOpt);
				break;
			case 2:
				/* list all user credentials */
				ListUserCredential listCred = new ListUserCredential(USERNAME, SEPARATOR, destnPath);
				listCred.ListUserCredentialMain();
				subMenuOpt = showSubMenu();
				processSubMenuOption(subMenuOpt);
				break;
			case 3:
				/* search for a credential */
				SearchCredential searchCred = new SearchCredential(USERNAME, SEPARATOR, destnPath);
				searchCred.SearchCredentialMain();
				subMenuOpt = showSubMenu();
				processSubMenuOption(subMenuOpt);
				break;
			case 4:
				/* delete credential */
				DeleteCredential deleteCred = new DeleteCredential(USERNAME, SEPARATOR, destnPath);
				deleteCred.DeleteCredentialMain();
				subMenuOpt = showSubMenu();
				processSubMenuOption(subMenuOpt);
				break;
			case 5:
				/* logout */
				logoutUser();
				Integer mainOpt = showMainMenu();
				processMainMenuOption(mainOpt);
				break;
			}
		}
	}

	public void processMainMenuOption(Integer mainOpt) throws InvalidOptionException, IOException {

		switch (mainOpt) {
		case 1:
			/* Register into Application */
			registerUser(mainOpt);
			mainOpt = showMainMenu();
			processMainMenuOption(mainOpt);
			break;
		case 2:
			/* Login to Application */
			loginUser();
			Integer subMenuOpt = showSubMenu();
			processSubMenuOption(subMenuOpt);
			break;
		case 3:
			/* Exit */
			sayGoodBye();
			break;
		}
	}

	public void sayGoodBye() {
		System.out.println("------- Exiting Successfully ---------");
		System.out.println("-----------   Thank you  -------------");
	}

	public LockedMeMain() throws IOException {
		/* make folder ready to store user data */
		File f = new File(destnPath);
		if (!f.exists()) {
			f.mkdir();
		}
		/* make file ready user login credentials */
		f = new File(userDB);
		if (!f.exists()) {
			f.createNewFile();
		}
	}

	public static void main(String[] args) {
		try {
			LockedMeMain lm = new LockedMeMain();
			lm.greetUser();
			Integer mainOpt = lm.showMainMenu();
			lm.processMainMenuOption(mainOpt);
		} catch (InvalidOptionException e) {
			System.out.println(e.getMessage());
		} catch (InputMismatchException e) {
			System.out.println("Sorry.. Cannot process.You entered an Invalid option..");
		} catch (IOException e) {
			e.getMessage();
		}
	}

}
