package library;
//package corba.Server;
//import library.*;

import java.io.FileNotFoundException;
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
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;



import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAPackage.ObjectNotActive;
import org.omg.PortableServer.POAPackage.ServantAlreadyActive;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;









//import animal.BehaviorImpl;
import corba.LibraryObjects.Book;
import corba.LibraryObjects.Student;


public class LibraryImpl extends LibraryPOA implements Runnable {

		private HashMap<Character, ArrayList<Student>> tableStudents = new HashMap<Character, ArrayList<Student>>();
		//private static ArrayList<List<Student>> listStudents = new ArrayList<List<Student>>(); 
		private HashMap<String, Book> tableBooks   = new HashMap<String, Book>();
		private String instituteName;
		private int udpPort;
		//private static ArrayList<LibraryServer> LibraryServers = null;
		private ORB orb= null;
		private Logger logger;

		public void setORB(ORB orbval)
		{
			orb=orbval;
		}
		public LibraryImpl(String instituteName)
		{
			this.instituteName = instituteName;
			this.setLogger(".\\logs\\library\\"+instituteName+".txt");
		}

		public LibraryImpl(){

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
		public LibraryImpl(String strInstitution, int iPortNum) {
			instituteName = strInstitution;
			udpPort = iPortNum;
			this.setLogger("logs/library/"+instituteName+".txt");
		}

//		public static void main(String[] args){
//
//			// TODO Auto-generated method stub
//			int rmiRegistryPort = 1099;
//			//Registry rmiRegistry = LocateRegistry.createRegistry(rmiRegistryPort);
//
//			Thread Server1 = new Thread(new LibraryImpl("Concordia",50001));
//			Thread Server2 = new Thread(new LibraryImpl("Ottawa",50002));
//			Thread Server3 = new Thread(new LibraryImpl("Waterloo",50003));
//
//			//Remote objremote1 = UnicastRemoteObject.exportObject(Server1,rmiRegistryPort);
//			//rmiRegistry.bind("Concordia", objremote1);
//
//			//Remote objremote2 = UnicastRemoteObject.exportObject(Server2,rmiRegistryPort);
//			//rmiRegistry.bind("Ottawa", objremote2);
//
//			//Remote objremote3 = UnicastRemoteObject.exportObject(Server3,rmiRegistryPort);
//			//rmiRegistry.bind("Waterloo", objremote3);
//
//			Server1.start();
//			System.out.println("Concordia server up and running!");
//			Server2.start();
//			System.out.println("Ottawa server up and running!");
//			Server3.start();
//			System.out.println("Waterloo server up and running!");
//
////			addData((LibraryServer)Server1);
////			addData(Server2);
////			addData(Server3);
//
////			LibraryServers = new ArrayList<LibraryServer>();
////			LibraryServers.add(Server1);
////			LibraryServers.add(Server2);
////			LibraryServers.add(Server3);
//
//
//
//			//		try
//			//		{
//			//			(new RMIServer()).exportServer();
//			//			System.out.println("Server is up and running!");
//			//			
//			//		}
//			//		catch(Exception e)
//			//		{
//			//			e.printStackTrace();
//			//		}
//
//
//
//		}

		private static int i=1;
		public void addData()
		{		
			for(int j=1; j<5; j++) { 
				Book book = new Book("Book"+j, "Author"+j, 10);
				this.tableBooks.put(book.getName(), book);
			}
			
			ArrayList<Student> s = new ArrayList<Student>();
			for(char i = 'A'; i <= 'Z' ; i++)
			{
				this.tableStudents.putIfAbsent(i, s);
			}

			this.createAccount("Student"+i, "L"+i, "Student"+i+"@test.com", "1234567890", "Student"+i, "abc123", this.instituteName);
			this.createAccount("yogesh", "rawat","yogesh@gmail.com","5145156743","yogesh","yogesh",this.instituteName);
			this.createAccount("aron", "engineer","aron@gmail.com","5145156743","aron123","aron123",this.instituteName);
			this.createAccount("ashish", "guhe","ashish@gmail.com","5145656743","ashish","ashish",this.instituteName);
			
			this.reserveBook("yogesh", "yogesh", "Book1", "Author1");
			ArrayList<Student> list = this.tableStudents.get('y');
			
			Book Book1 = new Book("Book1","Author1",8);
			for(Student student : list)
			{
				if(student.getUserName().equals("yogesh"))
				{
					student.getReservedBooks().put(Book1, 2);
				}
			}
		}

		public void run()
		{
			String[] args = null;
			//Initialize ORB 
					ORB orb = ORB.init(args,null);
					POA rootPOA = null;
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
					LibraryImpl libraryImpl = new LibraryImpl();
//					libraryImpl.setORB(orb);
//					
//					//get orb reference from the servant
//					Library libraryRef = null;//LibraryHelper reference
//					try {
//						org.omg.CORBA.Object ref = rootPOA.servant_to_reference(libraryImpl);
//						libraryRef = LibraryHelper.narrow(ref);
//						org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
//						//org.omg.CORBA.Object objRef = orb.resolve_initial_references(instituteName);
//						
//						NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
//						
//						String name = instituteName;
//						
//						NameComponent path[]= ncRef.to_name(name);
//						ncRef.rebind(path, libraryRef);
//						
//					} catch (ServantNotActive | WrongPolicy | InvalidName | org.omg.CosNaming.NamingContextPackage.InvalidName | NotFound | CannotProceed e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					System.out.println(instituteName+" Server up and running");
//					orb.run();
					
					byte[] id = null;					
					try {
						id = rootPOA.activate_object(libraryImpl);
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
						file = new PrintWriter(instituteName+".txt");
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					file.println(ior);
					file.close();
					
					try {
						rootPOA.the_POAManager().activate();
					} catch (AdapterInactive e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					orb.run();
					
					
					
//			DatagramSocket socket = null;
//			this.addData();
//			try
//			{
//				socket = new DatagramSocket(this.udpPort);
//				byte[] msg = new byte[10000];
//				//Logger call
	//
//				while(true)
//				{
//					DatagramPacket request = new DatagramPacket(msg, msg.length);
//					socket.receive(request);
//					String data = new String(request.getData());
//					String response = GetNonReturnersByServer(Integer.parseInt(data.trim()));
//					DatagramPacket reply = new DatagramPacket(response.getBytes(),response.length(),request.getAddress(),request.getPort());
//					socket.send(reply);
//				}
//			}
//			catch(Exception ex)
//			{
//				ex.printStackTrace();
//			}
//			finally
//			{
//				socket.close();
//			}
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
					synchronized(tableBooks)
					{
						Book objBook = tableBooks.get(strBookName);
						if(objBook!= null)
						{
							//reserve the book
							if(objBook.getNumOfCopy()>0)
							{
								objBook.setNumOfCopy(objBook.getNumOfCopy()-1);//Decrement available copies
								(objStudent.getReservedBooks()).put(objBook,14);//Add Book to Student's reserved list for 14 days
								success = true;
								logger.info(strUsername+": Reserved the book "+strBookName+"\n. Remaining copies of"+ strBookName+"is/are"+objBook.getNumOfCopy());
								System.out.println(this.instituteName +" Library : "+strUsername+": Reserved the book "+strBookName+"\n. Remaining copies of "+ strBookName+" is/are "+objBook.getNumOfCopy());

							}
							else
							{
								System.out.println("Required book not available currently");
							}
						}
						else
						{
							System.out.println("Required book not found");	
						}
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

		//@Override
		//	public String searchBook(String strBookName) throws RemoteException {
		//		// TODO Auto-generated method stub
		//		
		//		
		//		return null;
		//	}

//		public void exportServer() throws Exception
//		{
//			Remote objremote1 = UnicastRemoteObject.exportObject(this,2020);
//			Registry registry1 = LocateRegistry.createRegistry(2020);
//			registry1.bind("Institution", objremote1);
//		}

		@Override
		public String getNonReturners(String AdminUsername, String AdminPassword,String InstitutionName, int NumDays) 
		{
			String response = null;
//			if(AdminUsername.equalsIgnoreCase("admin")&&AdminPassword.equals("admin"))
//			{
//				response += GetNonReturnersByServer(NumDays);
//				for(LibraryServer Server : LibraryServers)
//				{
//					synchronized(Server)
//					{
//						if(!Server.instituteName.equals(this.instituteName))
//						{
//							DatagramSocket socket = null;
//							try
//							{
//								socket = new DatagramSocket();
//								byte[] msgOut = (""+NumDays).getBytes();
//								InetAddress host = InetAddress.getByName("localhost");
//								int ServerPort = Server.getUDPPort();
//								DatagramPacket request = new DatagramPacket(msgOut, (""+NumDays).length(),host,ServerPort);
//								socket.send(request);
//
//								byte[] msgIn = new byte[10000];
//								DatagramPacket reply = new DatagramPacket(msgIn, msgIn.length);
//								socket.receive(reply);
//								response+=new String(reply.getData());
//							}
//							catch(Exception ex)
//							{
//								ex.printStackTrace();
//							}
//							finally
//							{
//								socket.close();
//							}
//						}
//					}
//				}
//			}
//			else
//				System.out.println("Invalid Login");
			return response;
		}

		private String GetNonReturnersByServer(int NumDays)
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
				String m_bookName, String m_authorName) {
			// TODO Auto-generated method stub
			return false;
		}

	

}