package corba.Client;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;


import org.omg.CORBA.ORB;

import library.Library;
import library.LibraryHelper;
import Utility.ValidateInput;

public class StudentClient extends Client{

	static Library ConcordiaServer;
	static Library OttawaServer;
	static Library WaterlooServer;
	static final String Concordia ="Concordia", Ottawa="Ottawa", Waterloo="Waterloo";
	protected static String instituteName;
	private String[] args;

	public Library InitializeServer(String[] args, String name) throws Exception 
	{
		ORB orb = ORB.init(args, null);

		BufferedReader br = new BufferedReader(new FileReader(".\\Servers\\"+name+".txt"));
		String ior = br.readLine();
		br.close();
		
		org.omg.CORBA.Object o = orb.string_to_object(ior);
		return LibraryHelper.narrow(o);		
	}

	public Library ServerValidation(String strInstituteName)
	{
		Boolean valid = false;
		Library server = null;
		
		while(!valid)
		{
			try{
				instituteName = strInstituteName;
				server = LocateServer(instituteName);
				if(server != null) {
					valid=true;
				}
				else {
					System.out.println("Invalid Institute Name");
					//keyboard.nextLine();
				}
			}
			catch(Exception e)
			{
				System.out.println("Invalid Institute Name");
				valid=false;
				//keyboard.nextLine();
			}
		}
		//keyboard.nextLine();
		return server;
	}

	//Get Server Connection
	public static Library LocateServer(String instituteName) {
		if(instituteName.equals(Concordia)) {
			return ConcordiaServer;
		}
		else if(instituteName.equals(Ottawa)) {
			return OttawaServer;
		}
		else if(instituteName.equals(Waterloo)) {
			return WaterlooServer;
		}
		return null;
	}

	//Menu to display actions that are need to perform by student
	public static void showMenu()
	{
		System.out.println("DRMS Student Client System \n");
		System.out.println("Please select an option");
		System.out.println("1. Create An Account.");
		System.out.println("2. Reserve a Book");
		System.out.println("3. Reserve a Book InterLibraray");		
		System.out.println("4. Exit");
	}

	public static void main(String[] args)
	{
		try{
			//System.setProperty("java.security.policy","file:./security.policy");
			StudentClient objClient = new StudentClient();
			objClient.args = args;
			//initialize the connections to registry
			ConcordiaServer = objClient.InitializeServer(args, "Concordia");
			OttawaServer = objClient.InitializeServer(args, "ottawa");
			WaterlooServer = objClient.InitializeServer(args, "Waterloo");

			Library objServer = null;
			Scanner keyboard = new Scanner(System.in);
			
			Integer userInput = 0;

			String userName = null, password = null, institution = null;
			boolean success = false;

			while(true)
			{
				showMenu();

				userInput = Integer.parseInt(objClient.InputStringValidation(keyboard));
				switch(userInput)
				{
				case 1: 
					System.out.println("First Name: ");
					String firstName = objClient.InputStringValidation(keyboard);
					System.out.println("Last Name: ");
					String lastName = objClient.InputStringValidation(keyboard);
					System.out.println("Email: ");
					String emailAddress = objClient.InputStringValidation(keyboard);

					System.out.println("Phone No: ");
					ValidateInput v = new ValidateInput();	
					String phoneNumber = v.validatePhNo(keyboard.nextLine().toString());
					System.out.println("User Name: ");
					userName = v.validateUserName(keyboard.nextLine().toString());
					System.out.println("Password: ");
					password = v.validate(keyboard.nextLine().toString());
					institution= getEducationalInstituteFromUser();
					objServer = objClient.ServerValidation(institution);
					success = objServer.createAccount(firstName, lastName, emailAddress, phoneNumber, userName, password, objClient.instituteName);
					if(success){
						System.out.println("Success");
						File fi = new File(".\\logs\\students\\"+userName+".txt");
						FileWriter fw=new FileWriter(fi);
						objClient.setLogger(userName,".\\logs\\students\\"+userName+".txt");
						objClient.logger.info("Account created successfully for user "+userName);
						fw.close();
					}
					else{
						File fi = new File(".\\logs\\students\\"+userName+".txt");
						FileWriter fw=new FileWriter(fi);
						objClient.setLogger(userName, ".\\logs\\students\\"+userName+".txt");
						objClient.logger.info("Account already exist with username as : "+userName);
						System.out.println(institution+" library : Account already exist with username as : "+userName);
						fw.close();

					}					
					break;
				case 2: 
					boolean flag = true;
					String bookName = "";
					String authorName = "";
					ValidateInput v2 = new ValidateInput();	
					while(flag)
					{
						System.out.println("User Name: ");
						userName = v2.validateUserName(keyboard.nextLine().toString());
						System.out.println("Password: ");
						password = v2.validate(keyboard.nextLine().toString());
						System.out.println("Book Name: ");
						bookName = objClient.InputStringValidation(keyboard);
						System.out.println("Author: ");
						authorName = objClient.InputStringValidation(keyboard);


						institution= getEducationalInstituteFromUser();
					
						objServer = objClient.ServerValidation(institution);
						int loginResult = objServer.checkUser(userName, password, institution);
						switch(loginResult)
						{
						case 1: System.out.println("Redirecting to "+institution+" server");flag = false; break;

						case 0: System.out.println("You are not registered with "+institution);
						System.out.println("Press 1 to continue 0 to Exit.");
						int in1 = objClient.InputIntValidation(keyboard);
						if(in1==1)
						{							
							break;
						}
						else
							System.exit(0);

						case 2: System.out.println("Invalid Password. Please try again");
						System.out.println("Press 1 to continue 0 to Exit.");
						int in2 = objClient.InputIntValidation(keyboard);
						if(in2 == 1)
						{
							keyboard=null;
							break;
						}

						else
							System.exit(0);
						}
					}
					success = objServer.reserveBook(userName, password, bookName, authorName);
					if(success){
						System.out.println(institution+"library : Book reserved successfully for user "+userName);
						objClient.setLogger(userName, ".\\logs\\students\\"+userName+".txt");
						objClient.logger.info("Book reserved successfully for user "+userName);
					}
					else{
						System.out.println("Required book not available.");
						objClient.setLogger(userName, ".\\logs\\students\\"+userName+".txt");
						objClient.logger.info("Book could not be reserved for : "+userName);
						}

									
					break;
				case 3: 
					System.out.println("User Name: ");
					ValidateInput v3 = new ValidateInput();
					userName = v3.validateUserName(keyboard.nextLine().toString());
					System.out.println("Password: ");
					password = v3.validate(keyboard.nextLine().toString());
					System.out.println("Book Name: ");
					bookName = objClient.InputStringValidation(keyboard);
					System.out.println("Author: ");
					authorName = objClient.InputStringValidation(keyboard);
					institution= getEducationalInstituteFromUser();
					
					objServer = objClient.ServerValidation(institution);
					if(objServer.reserveInterLibrary(userName, password, bookName, authorName))	
					{
						objClient.setLogger(userName, ".\\logs\\students\\"+userName+".txt");
						objClient.logger.info("Interlibrary : Book reserved successfully for user "+userName);
						System.out.println("Interlibrary : Book reserved successfully for user "+userName);
					}
					else{
						objClient.setLogger(userName, ".\\logs\\students\\"+userName+".txt");
						objClient.logger.info("Book could not be reserved for : "+userName);
						}
					break;
				case 4: 
					System.out.println("Thank You \n Have a nice day!");
					keyboard.close();
					System.exit(0);
				default:
					System.out.println("Invalid input");
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	private static String getEducationalInstituteFromUser() throws IOException
	{
		Integer ans = 0;
		System.out.println("Institution Name: ");
		while(true)
		{
			System.out.println("Please select a valid option:");
			System.out.println("1 for Concordia");
			System.out.println("2 for Ottawa");
			System.out.println("3 for Waterloo");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			Client objClient = new StudentClient();
			Scanner keyboard = new Scanner(System.in);
			ans = Integer.parseInt(objClient.InputStringValidation(keyboard));
			
			if(ans == 1||ans==2||ans==3)
			{
				break;
			}
			else
			{
				System.out.println("Invalid input!");
			}
		}
		switch(ans)
		{
		case 1: return Concordia;
		case 2: return Ottawa;
		case 3: return Waterloo;					
		}
		return null;
	}
	
}
