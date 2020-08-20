package com.anju.lockedme;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class DeleteCredential {
	public static String USERNAME;
	public static String SEPARATOR;
	public static String destnPath;
	
	public DeleteCredential(String Uname, String sep, String destn) {
		USERNAME = Uname;
		SEPARATOR = sep;
		destnPath = destn;
	}
	
	public void deleteCred(String url) throws IOException {
		 File f =  new File(destnPath+USERNAME+".txt");
		 Scanner sc = new Scanner(f);
		 HashMap <String, String> cred = new HashMap <String, String>();
		 while(sc.hasNext()) {
			 String val = sc.next();
			 String eachCred[] = val.split(SEPARATOR);
			 cred.put(eachCred[0], val);			 
		 }
		 /*if the entered tag matches, delete it and update file*/
		 if(cred.containsKey(url)) {
			 cred.remove(url);
			 //update file
			 if(f.canWrite()) {
				 FileWriter fw = new FileWriter(f);
				 fw.write("");
				 Set <String> keys = cred.keySet();
				 for(String key : keys) {
					 fw.append(cred.get(key));
					 fw.write(System.getProperty("line.separator"));
				 }
				 fw.close();
				 System.out.println("Deleted Successfully..");
			 }
		 }else {
			 System.out.println("Sorry. Cannot delete. \""+url+"\" doesn't exist.");
		 }
	}
	
	public void DeleteCredentialMain() throws IOException {
		Scanner sc = new Scanner(System.in);
		System.out.println("");
		System.out.println("-------------------------------------");
		System.out.println("---       Delete Credential       ---");
		System.out.println("-------------------------------------");
		System.out.println("Enter Url/ Tag: ");
		String url = sc.nextLine();
		deleteCred(url);
	}
}
