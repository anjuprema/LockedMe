package com.anju.lockedme;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

public class AddNewCredential {
	public static String USERNAME;
	public static String SEPARATOR;
	public static String destnPath;

	public AddNewCredential(String Uname, String sep, String destn) {
		USERNAME = Uname;
		SEPARATOR = sep;
		destnPath = destn;
	}

	public boolean isValidateData(String data) {
		if(data.trim().length() < 3) return false;
		else return true;
	}

	public boolean isExistCredential(String url) throws FileNotFoundException {
		File f = new File(destnPath + USERNAME + ".txt");
		Scanner sc = new Scanner(f);
		HashMap<String, String> cred = new HashMap<String, String>();
		while (sc.hasNext()) {
			String val = sc.next();
			String eachCred[] = val.split(SEPARATOR);
			cred.put(eachCred[0], val);
		}
		/* if the entered tag matches, delete it and update file */
		if (cred.containsKey(url)) {
			return true;
		}else {
			return false;
		}
	}

	public void saveCredential(String url, String Uname, String pswd) throws IOException {
		/* if all ok, register user */
		File f = new File(destnPath + USERNAME + ".txt");
		if (f.canWrite()) {
			FileWriter fw = new FileWriter(f, true);
			fw.append(url + SEPARATOR + Uname + SEPARATOR + pswd);
			fw.write(System.getProperty("line.separator"));
			fw.close();
		}
	}

	public void AddNewCredentialMain() throws IOException {
		Scanner sc = new Scanner(System.in);
		System.out.println("------------------------------------------");
		System.out.println("------       Add New Credential     ------");
		System.out.println("------------------------------------------");
		System.out.println("Enter URL/ Tag: ");
		String Url = sc.nextLine();
		System.out.println("Enter User Name: ");
		String Uname = sc.nextLine();
		System.out.println("Enter Password: ");
		String Pswd = sc.nextLine();
		if(!isValidateData(Url) || !isValidateData(Uname) || !isValidateData(Pswd)) {
			System.out.println("Sorry. Cannot create. Url/ Tag \""+Url+"\" already exists");
			AddNewCredentialMain();
			return;
		}
		if(isExistCredential(Url)) {
			System.out.println("Sorry. Cannot create. Url/ Tag \""+Url+"\" already exists");
			AddNewCredentialMain();
			return;
		}else {
			saveCredential(Url, Uname, Pswd);
			System.out.println("Saved Successfully..");
		}
	}
}
