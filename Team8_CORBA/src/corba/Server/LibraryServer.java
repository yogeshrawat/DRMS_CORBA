package corba.Server;
import library.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
//import java.rmi.AlreadyBoundException;
//import java.rmi.Remote;
//import java.rmi.RemoteException;
//import java.rmi.registry.LocateRegistry;
//import java.rmi.registry.Registry;
//import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//import rmi.Interface.AdminInterface;
//import rmi.Interface.StudentInterface;

import java.util.logging.FileHandler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;



import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAPackage.ObjectNotActive;
import org.omg.PortableServer.POAPackage.ServantAlreadyActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;








//import animal.BehaviorImpl;
import corba.LibraryObjects.Book;
import corba.LibraryObjects.Student;


public class LibraryServer extends LibraryPOA implements Runnable {

	private HashMap<Character, ArrayList<Student>> tableStudents = new HashMap<Character, ArrayList<Student>>();
	//private static ArrayList<List<Student>> listStudents = new ArrayList<List<Student>>(); 
	private HashMap<String, Book> tableBooks   = new HashMap<String, Book>();
	private String instituteName;
	private int udpPort;
	private ServerThread socket;

	//private static String[] LibraryServers = {"Concordia","ottawa","Waterloo"};
	private static ArrayList<LibraryServer> LibraryServers = null;

	private Logger logger;

	public LibraryServer(String instituteName)
	{
		this.instituteName = instituteName;
		this.setLogger(".\\logs\\library\\"+instituteName+".txt");
		addData();
	}

	public LibraryServer(){

	}

	private void setLogger(String instituteName) {
		try{
			this.logger = Logger.getLogger(this.instituteName);
			FileHandler fileTxt 	 = new FileHandler(instituteName);
			SimpleFormatter formatterTxt = new SimpleFormatter();
			fileTxt.setFormatter(formatterTxt);
			logger.addHandler(fileTxt);
		}
		catch(Exception err) {
			System.out.println("Couldn't Initiate Logger. Please check file permission");
		}
	}



	public int getUDPPort()
	{
		return this.udpPort;
	}
	public LibraryServer(String strInstitution, int iPortNum) {
		// TODO Auto-generated constructor stub
		instituteName = strInstitution;
		udpPort = iPortNum;
		this.setLogger("logs/library/"+instituteName+".txt");
		addData();
	}

	public static void main(String[] args){

		// TODO Auto-generated method stub

		LibraryServer Server1 = new LibraryServer("Concordia",50001);
		//Thread Server1Thread = new Thread(Server1);
		LibraryServer Server2 = new LibraryServer("Ottawa",50002);
		//Thread Server2Thread = new Thread(Server2);
		LibraryServer Server3 = new LibraryServer("Waterloo",50003);
		//Thread Server3Thread = new Thread(Server3);


		//		Server1Thread.start();
		//		System.out.println("Concordia server up and running!");
		//		Server2Thread.start();
		//		System.out.println("Ottawa server up and running!");
		//		Server3Thread.start();
		//		System.out.println("Waterloo server up and running!");

		//		addData(Server1);
		//		addData(Server2);
		//		addData(Server3);

		LibraryServers = new ArrayList<LibraryServer>();
		LibraryServers.add(Server1);
		LibraryServers.add(Server2);
		LibraryServers.add(Server3);

		String[] args1 = null;
		ORB orb = ORB.init(args1,null);
		POA rootPOA = null;
		for(LibraryServer libraryServer : LibraryServers)
		{
			//Thread Server1Thread = new Thread(libraryServer);
			new Thread(libraryServer).start();
			System.out.println(libraryServer.instituteName + " Server is running!");	

			//Server1Thread.start();

			//Initialize ORB 
			try {
				rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));						
			} catch (InvalidName e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				rootPOA.the_POAManager().activate();
			} catch (AdapterInactive e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//Create servant and register it with ORB
			//Obtain a reference
			//LibraryImpl libraryImpl = new LibraryImpl();
			byte[] id = null;					
			try {
				id = rootPOA.activate_object(libraryServer);
			} catch (ServantAlreadyActive | WrongPolicy e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			org.omg.CORBA.Object ref = null;
			try {
				ref = rootPOA.id_to_reference(id);
			} catch (ObjectNotActive | WrongPolicy e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			//Translate to ior and write it to a file
			String ior = orb.object_to_string(ref);
			System.out.println(ior);

			//Initialize the ORB 
			PrintWriter file = null;
			try {
				file = new PrintWriter(".\\Servers\\"+libraryServer.instituteName+".txt");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			file.println(ior);
			file.close();


		}

		try {
			rootPOA.the_POAManager().activate();
		} catch (AdapterInactive e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		orb.run();
		//		try
		//		{
		//			(new RMIServer()).exportServer();
		//			System.out.println("Server is up and running!");
		//			
		//		}
		//		catch(Exception e)
		//		{
		//			e.printStackTrace();
		//		}



	}



	private static int i=1;
	public void addData()
	{	
		if(this.instituteName == "Concordia")
		{
			for(int j=1; j<3; j++) { 

				Book book = new Book("Book"+j, "Author"+j, 10);
				this.tableBooks.put(book.getName(), book);
			}
			Book book = new Book("Book3", "Author3", 10);
			this.tableBooks.put("Book3", book);
		}
		else{
			for(int j=2; j<3; j++) { 
				Book book = new Book("Book"+j, "Author"+j, 0);
				this.tableBooks.put(book.getName(), book);
			}
			Book book = new Book("Book1", "Author1", 10);
			this.tableBooks.put(book.getName(), book);
		}


		ArrayList<Student> s = new ArrayList<Student>();
		for(char i = 'A'; i <= 'Z' ; i++)
		{
			this.tableStudents.putIfAbsent(i, s);
		}

		if(this.instituteName == "Concordia")
		{
			this.createAccount("yogesh", "rawat","yogesh@gmail.com","5145156743","yogesh","yogesh",this.instituteName);
			this.reserveBook("yogesh", "yogesh", "Book1", "Author1");
			ArrayList<Student> list = this.tableStudents.get('y');

			Book Book1 = new Book("Book2","Author2",8);
			for(Student student : list)
			{
				if(student.getUserName().equals("yogesh"))
				{
					student.getReservedBooks().put(Book1, 2);
				}
			}
		}

		if(this.instituteName == "Ottawa"){
			this.createAccount("aron", "engineer","aron@gmail.com","5145156743","aron123","aron123",this.instituteName);
			ArrayList<Student> list = this.tableStudents.get('a');

			Book Book1 = new Book("Book1","Author1",8);
			for(Student student : list)
			{
				if(student.getUserName().equals("aron"))
				{
					student.getReservedBooks().put(Book1, 2);
				}
			}
		}

		if(this.instituteName == "Waterloo"){
			this.createAccount("ashish", "guhe","ashish@gmail.com","5145656743","ashish","ashish",this.instituteName);
			ArrayList<Student> list = this.tableStudents.get('a');

			Book Book1 = new Book("Book1","Author1",8);
			for(Student student : list)
			{
				if(student.getUserName().equals("ashish"))
				{
					student.getReservedBooks().put(Book1, 2);
				}
			}
		}

	}

	public void run()
	{

		this.socket = new ServerThread(this);
		socket.start();	


	}

	@Override
	public boolean createAccount(String strFirstName, String strLastName, String strEmailAddress, String strPhoneNumber, String strUsername,
			String strPassword, String strEducationalInstitution)
	{
		if(isExist(strUsername, strEducationalInstitution )){
			return false;
		}
		else 
		{
			Student objStudent = new Student(strUsername,strPassword,strEducationalInstitution);
			objStudent.setFirstName(strFirstName);
			objStudent.setLastName(strLastName);
			objStudent.setEmailAddress(strEmailAddress);
			objStudent.setPhoneNumber(strPhoneNumber);

			//Add student to HashTable 'tableStudents' with Lock
			if(tableStudents.get(strUsername.charAt(0)) != null){
				synchronized(tableStudents.get(strUsername.charAt(0))) {
					ArrayList<Student> objNewStudent = tableStudents.get(strUsername.charAt(0));
					if(objNewStudent == null) {
						objNewStudent = new ArrayList<Student>();
						tableStudents.put(strUsername.charAt(0), objNewStudent);
					}
					objNewStudent.add(objStudent);

					logger.info("New User added to the library with username as : "+objStudent.getUserName());
				}
			}
			else {
				ArrayList<Student> objNewStudent = tableStudents.get(strUsername.charAt(0));
				if(objNewStudent == null) {
					objNewStudent = new ArrayList<Student>();
					tableStudents.put(strUsername.charAt(0), objNewStudent);
				}
				objNewStudent.add(objStudent);

				logger.info("New User added to the library with username as : "+objStudent.getUserName());

			}


			return true;
		}
	}

	@Override
	public boolean reserveBook(String strUsername, String strPassword, String strBookName, String strAuthor) 
	{
		boolean success =false;
		int iLoginResult = 0;
		Student objStudent = null;
		objStudent = getStudent(strUsername);
		if(objStudent!=null)
		{
			iLoginResult = checkUser(strUsername, strPassword, objStudent.getInst());
			if(iLoginResult==1)
			{
				if(isBookAvailable(strBookName))
				{
					Book objBook = tableBooks.get(strBookName);
					objBook.setNumOfCopy(objBook.getNumOfCopy()-1);//Decrement available copies
					(objStudent.getReservedBooks()).put(objBook,14);//Add Book to Student's reserved list for 14 days
					success = true;
					logger.info(strUsername+": Reserved the book "+strBookName+"\n. Remaining copies of"+ strBookName+"is/are"+objBook.getNumOfCopy());
					System.out.println(this.instituteName +" Library : "+strUsername+": Reserved the book "+strBookName+"\n. Remaining copies of "+ strBookName+" is/are "+objBook.getNumOfCopy());

				}
				else
				{
					System.out.println("Required book not available");	
				}

			}
			else
			{
				if(iLoginResult==2)
					System.out.println("Login is invalid!");
			}
		}
		else
		{
			System.out.println("Student "+strUsername+ " does not exist!");
		}

		return success;
	}

	public boolean grantBookInterServer(String strBookName)
	{
		boolean isAvailable=false;
			System.out.println(strBookName);
			strBookName=strBookName.trim();
		System.out.println("I am "+this.instituteName+". Grant book.");
		Book objBook = (Book)this.tableBooks.get(strBookName);
		if(objBook!= null)
		{
			//reserve the book
			if(objBook.getNumOfCopy()>0)
			{
				objBook.setNumOfCopy(objBook.getNumOfCopy()-1);//Decrement available copies
				isAvailable = true;
			}
		}
		else
		{
			isAvailable = false;
		}
		return isAvailable;
//		if(isBookAvailable(strBookName))
//		{
//			
//			Book objBook = tableBooks.get(strBookName);
//			objBook.setNumOfCopy(objBook.getNumOfCopy()-1);//Decrement available copies
//			return true;
//		}
//		else
//		{
//			return false;
//		}
		//	Book objBook = tableBooks.get(strBookName);
		//	objBook.setNumOfCopy(objBook.getNumOfCopy()-1);//Decrement available copies
		//	(objStudent.getReservedBooks()).put(objBook,14);//Add Book to Student's reserved list for 14 days
		//	success = true;
		//	logger.info(strUsername+": Reserved the book "+strBookName+"\n. Remaining copies of"+ strBookName+"is/are"+objBook.getNumOfCopy());
		//	System.out.println(this.instituteName +" Library : "+strUsername+": Reserved the book "+strBookName+"\n. Remaining copies of "+ strBookName+" is/are "+objBook.getNumOfCopy());

	}

	private boolean isBookAvailable(String strBookName)
	{
		System.out.println("I am "+this.instituteName+". isAvailable.");
		boolean isAvailable=false;
		Book objBook = (Book)tableBooks.get(strBookName.trim());
		if(objBook!= null)
		{
			//reserve the book
			if(objBook.getNumOfCopy()>0)
			{
				isAvailable=true;
			}
		}
		return isAvailable;
	}

	public boolean isExist(String strUsername, String strEducationalInstitution) 
	{
		// TODO Auto-generated method stub
		boolean exist = false;
		ArrayList<Student> listStudent = new ArrayList<Student>();
		listStudent = tableStudents.get(strUsername.charAt(0));
		if(listStudent!=null)
		{
			if(listStudent.size()>0)
			{
				for(Student student : listStudent)
				{
					if(student.getUserName().equals(strUsername))
					{
						exist = true;
					}
				}
			}
		}
		return exist;
	}


	@Override
	public int checkUser(String strUsername, String strPassword,String strEducationalInstitution) 
	{
		// TODO Auto-generated method stub
		int login = 0;
		Student objStudent = null;
		ArrayList<Student> listStudent = new ArrayList<Student>();
		listStudent = tableStudents.get(strUsername.charAt(0));
		if(listStudent!=null)
		{
			if(listStudent.size()>0)
			{
				for(Student student : listStudent)
				{
					if(student.getUserName().equals(strUsername))
					{
						if(student.getPassword().equals(strPassword))
						{
							login =1;
						}
						else
						{
							login = 2;
						}
					}
				}
			}
		}
		return login;
	}



	@Override
	public String getNonReturners(String AdminUsername, String AdminPassword,String InstitutionName, int NumDays) 
	{
		String response = null;
		response += GetNonReturnersByServer(NumDays);
		String[] args = null;

		//for(int i =0;i<LibraryServers.length;i++)
		for(LibraryServer libraryServer : LibraryServers)
		{
			if(this.instituteName!=libraryServer.instituteName)
			{
				try 
				{
					//LibraryServer libraryServer = (LibraryServer) getServerObject(args, LibraryServers[i]);
					DatagramSocket socket = null;
					try
					{
						socket = new DatagramSocket();
						byte[] msgOut = ("Days:"+NumDays).getBytes();
						InetAddress host = InetAddress.getByName("localhost");
						int ServerPort = libraryServer.getUDPPort();
						DatagramPacket request = new DatagramPacket(msgOut, ("Days:"+NumDays).length(),host,ServerPort);
						socket.send(request);

						byte[] msgIn = new byte[10000];
						DatagramPacket reply = new DatagramPacket(msgIn, msgIn.length);
						socket.receive(reply);
						response+=new String(reply.getData());
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
					finally
					{
						socket.close();
					}

					//					response += libraryServer.GetNonReturnersByServer(NumDays);
				} 
				catch (Exception e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}


		return response;
	}



	@Override
	public String GetNonReturnersByServer(int NumDays)
	{
		StringBuilder sbStudentList = new StringBuilder();
		sbStudentList.append(instituteName+": \n");
		// TODO Auto-generated method stub
		Iterator<?> it = tableStudents.entrySet().iterator();
		while(it.hasNext())
		{
			Map.Entry pair = (Map.Entry)it.next();
			ArrayList<Student> listStudent = (ArrayList<Student>) pair.getValue();
			if(!listStudent.isEmpty())
			{					
				for(Student objStudent : listStudent)
				{
					if(!objStudent.getReservedBooks().isEmpty())
					{
						Iterator<?> innerIterator = objStudent.getReservedBooks().entrySet().iterator();
						while(innerIterator.hasNext())
						{
							Map.Entry innerPair = (Map.Entry)innerIterator.next();

							if((int)innerPair.getValue()<=(14-NumDays))
							{
								sbStudentList.append(objStudent.getFirstName() +" "+objStudent.getLastName()+" "+objStudent.getPhoneNumber()+"\n");
							}
						}
					}
				}
			}
		}
		return sbStudentList.toString();
	}


	private Student getStudent(String strUserName)
	{
		Student objStudent = null;
		ArrayList<Student> listStudent = tableStudents.get(strUserName.charAt(0));
		if(tableStudents.get(strUserName.charAt(0)) != null){
			synchronized(tableStudents.get(strUserName.charAt(0)))
			{
				if(listStudent.size()>0)
				{
					for(Student student : listStudent)
					{
						if(student.getUserName().equals(strUserName))
						{
							objStudent = student;
						}
					}
				}
			}
		}
		else {
			if(listStudent.size()>0)
			{
				for(Student student : listStudent)
				{
					if(student.getUserName().equals(strUserName))
					{
						objStudent = student;
					}
				}
			}

		}
		return objStudent;
	}

	@Override
	public boolean reserveInterLibrary(String m_username, String m_password,
			String m_bookName, String m_authorName) 
	{
		String response = "";
		String[] args = null;
		if(isBookAvailable(m_bookName))
		{
			reserveBook(m_username, m_password, m_bookName, m_authorName);
		}
		else
		{
			// TODO Auto-generated method stub
			for(LibraryServer libraryServer : LibraryServers)
			{
				boolean bookAvailabe = false;
				if(this.instituteName!=libraryServer.instituteName)
				{
					try 
					{
						//LibraryServer libraryServer = (LibraryServer) getServerObject(args, LibraryServers[i]);
						DatagramSocket socket = null;
						try
						{
							socket = new DatagramSocket();
							String strInputParameters="ReserveBook:"+m_bookName;
							byte[] msgOut = (strInputParameters).getBytes();
							InetAddress host = InetAddress.getByName("localhost");
							int ServerPort = libraryServer.getUDPPort();
							
							DatagramPacket request = new DatagramPacket(msgOut, (strInputParameters).length(),host,ServerPort);
							socket.send(request);

							byte[] msgIn = new byte[10000];
							DatagramPacket reply = new DatagramPacket(msgIn, msgIn.length);
							socket.receive(reply);
							response=new String(reply.getData());

							if(response.trim().equalsIgnoreCase("true"))//Book granted by other Server
							{
								bookAvailabe=true;
								//Add granted book to Students Reserved book list
								Student objStudent = null;
								objStudent = getStudent(m_username);
								Book objBook = new Book(m_bookName,m_authorName,0);
								(objStudent.getReservedBooks()).put(objBook,14);//Add Book to Student's reserved list for 14 days
								//success = true;
								logger.info(m_username+": Reserved the book "+m_bookName+"\n. Remaining copies of"+ m_bookName+" is/are "+objBook.getNumOfCopy());
								System.out.println(this.instituteName +" Library : "+m_username+": Reserved the book "+m_bookName+"\n. Remaining copies of "+ m_bookName+" is/are "+objBook.getNumOfCopy());	
								break;
							}
							
					
						}
						catch(Exception ex)
						{
							ex.printStackTrace();
						}
						finally
						{
							socket.close();
						}

						//					response += libraryServer.GetNonReturnersByServer(NumDays);
					} 
					catch (Exception e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}

		return false;
	}

	public Library getServerObject(String[] args, String name) throws Exception 
	{
		ORB orb = ORB.init(args, null);
		BufferedReader br = new BufferedReader(new FileReader(".\\Servers\\"+name+".txt"));
		String ior = br.readLine();
		br.close();

		org.omg.CORBA.Object o = orb.string_to_object(ior);
		return LibraryHelper.narrow(o);		
	}	

}
