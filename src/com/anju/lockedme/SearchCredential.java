package com.anju.lockedme;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class SearchCredential {
	public static String USERNAME;
	public static String SEPARATOR;
	public static String destnPath;

	public SearchCredential(String Uname, String sep, String destn) {
		USERNAME = Uname;
		SEPARATOR = sep;
		destnPath = destn;
	}

	public void searchCredential(String url) throws FileNotFoundException {
		File f = new File(destnPath + USERNAME + ".txt");
		Scanner sc = new Scanner(f);
		HashMap<String, String> cred = new HashMap<String, String>();
		while (sc.hasNext()) {
			String val = sc.next();
			String eachCred[] = val.split(SEPARATOR);
			cred.put(eachCred[0], val);
		}
		/* if the entered tag matches, show the credential */
		if (cred.containsKey(url)) {
			String SearchResult[] = cred.get(url).split(SEPARATOR);
			System.out.println("Url/ Tag: "+SearchResult[0]);
			System.out.println("User Name: "+SearchResult[1]);
			System.out.println("Password: "+SearchResult[2]);
		} else {
			System.out.println("\"" + url + "\" doesn't exist.");
		}
	}

	public void SearchCredentialMain() throws FileNotFoundException {
		Scanner sc = new Scanner(System.in);
		System.out.println("");
		System.out.println("---------------------------------------");
		System.out.println("-------     Search Credential     -----");
		System.out.println("---------------------------------------");
		System.out.println("Enter Url/ Tag: ");
		String url = sc.nextLine();
		searchCredential(url);
	}
}
