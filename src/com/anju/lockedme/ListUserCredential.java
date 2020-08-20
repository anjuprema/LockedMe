package com.anju.lockedme;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class ListUserCredential {
	public static String USERNAME;
	public static String SEPARATOR;
	public static String destnPath;
	
	public ListUserCredential(String Uname, String sep, String destn) {
		USERNAME = Uname;
		SEPARATOR = sep;
		destnPath = destn;
	}


	public void ListUserCredentialMain() throws FileNotFoundException {
		System.out.println("");
		System.out.println("--------------------------------------");
		System.out.println("---     Your Saved Credentials     ---");
		System.out.println("--------------------------------------");
		File UserCred 	= new File(destnPath+USERNAME+".txt");
		Scanner sc 		= new Scanner(UserCred);
		Integer i 		= 1;
		while (sc.hasNextLine()) {
			System.out.println("");
			String data = sc.nextLine();
			String[] cred = data.split(SEPARATOR);
			System.out.println(i+". Tag/Url: "+cred[0]);
			System.out.println("  User Name: "+cred[1]);
			System.out.println("  Password: "+cred[2]);			
			i++;
		}
		if(i == 1) {
			System.out.println("");
			System.out.println("No saved credentials");
			System.out.println("");
		}
		sc.close();
	}
}
