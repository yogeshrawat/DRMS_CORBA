package _FrontEnd;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.CosNaming.*;

import DRMSServices.*;

import java.util.*;
import java.net.*;
import java.io.*;


public class FE_Main {

	public static void main(String[] args) {
		// Get the system properties
		Properties orbProp = System.getProperties();

		// Initialize ORB
		ORB orb = ORB.init(args, orbProp);

		POA rootPOA = null;
		org.omg.CORBA.Object obj = null; // generic CORBA object
		NamingContext n = null;
		try {
			// Get the reference to RootPOA and NameService
			obj = orb.resolve_initial_references("RootPOA");
			rootPOA = POAHelper.narrow(obj);
			obj = orb.resolve_initial_references("NameService");
			n = NamingContextHelper.narrow(obj);
		} catch (org.omg.CORBA.ORBPackage.InvalidName e) {
			System.out.println(e.getMessage());
		}

		while (true) {
			try {
				InputStreamReader in = new InputStreamReader(System.in);
				BufferedReader r = new BufferedReader(in);
				System.out
						.println("Enter the specified number:");
				System.out.println("1. Fault Tolerance");
				System.out.println("2. High Availibility");
				int choice = Integer.parseInt(r.readLine());
				if (choice == 1) {
					System.out
							.println("The Fault Tolerant System is now ready to accept client requests");
					FrontEnd.setSystemProperty("Fault Tolerance");
					break;
				} else if (choice == 2) {
					System.out
							.println("The Highly Available System is now ready to accept client requests");
					FrontEnd.setSystemProperty("High Availability");
					break;
				} else {
					System.out.println("You have entered a wrong choice");
					System.out.println("Press any key to continue: ");
					r.readLine();
				}
			} catch (IOException e) {
				System.out.println(e.getMessage());
			} catch (NumberFormatException e) {
				System.out
						.println("You have entered a wrong choice. Please try again");
				continue;
			}
		}

		FrontEnd[] libraries = new FrontEnd[3];
		InetSocketAddress sequencerAddress = new InetSocketAddress(9988);

		libraries[0] = new FrontEnd("concordia", sequencerAddress);
		libraries[1] = new FrontEnd("ottawa", sequencerAddress);
		libraries[2] = new FrontEnd("waterloo", sequencerAddress);

		try {
			FrontEnd.addReplicaManager("RM1", InetAddress.getLocalHost(), 10101);
			FrontEnd.addReplicaManager("RM2", InetAddress.getLocalHost(), 10102);
			FrontEnd.addReplicaManager("RM3", InetAddress.getLocalHost(), 10103);
		} catch (UnknownHostException e) {
			System.out.println(e.getMessage());
		}

		NameComponent[] name = new NameComponent[1];
		name[0] = new NameComponent();
		LibraryInterface[] libInterface = new LibraryInterface[3];

		try {

			for (int i = 0; i != libraries.length; i++) {

				// Activate the library object and associate it with rootPOA
				byte[] id = rootPOA.activate_object(libraries[i]);

				// Get a reference to the Library object in the form of CORBA
				// reference
				org.omg.CORBA.Object tempOBJ = rootPOA.id_to_reference(id);
				libInterface[i] = LibraryInterfaceHelper.narrow(tempOBJ);

				// bind the CORBA reference of library object to NameService
				name[0].id = libraries[i].getName();
				name[0].kind = "library";
				n.rebind(name, libInterface[i]);

			}
		} catch (org.omg.CORBA.UserException e) {
			System.out.println(e.getMessage());
		}

		try {
			// Activate POA manager
			rootPOA.the_POAManager().activate();
		} catch (AdapterInactive e) {
			System.out.println(e.getMessage());
		}
		// run the underlying CORBA ORB. It is now ready to receive requests
		// from clients
		orb.run();

		System.out.println("The following " + libraries.length
				+ " Libraries are currently part of the DRMS");
		for (FrontEnd l : libraries) {
			System.out.println(l.getName());
		}
	}
}