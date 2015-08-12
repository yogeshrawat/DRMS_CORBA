package _FrontEnd;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;

import Call.BoolResponse;
import library.LibraryPOA;

public class FrontEnd extends LibraryPOA {
	
	private static HashMap<String, InetSocketAddress> replicaManagerData=new HashMap<String, InetSocketAddress>();
	private String systemProperty=null;
	private InetSocketAddress sequencerAddress;
	private String libraryName;
	
	public FrontEnd(String libararyName,InetSocketAddress sequencerAddress){
		libraryName=this.libraryName;
		sequencerAddress=this.sequencerAddress;
	}
	public static void addReplicaManager(String replicaManager, InetAddress IPAddress, int portNumber){
		replicaManagerData.put(replicaManager, new InetSocketAddress(IPAddress,portNumber));
	}
	
	@Override
	public boolean createAccount(String m_firstName, String m_lastName,
			String m_emailAddress, String m_phoneNumber, String m_username,
			String m_password, String m_educationalInstitution) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reserveBook(String m_username, String m_password,
			String m_bookName, String m_author) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int checkUser(String m_username, String m_password,
			String m_educationalInstitution) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean reserveInterLibrary(String m_username, String m_password,
			String m_bookName, String m_authorName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getNonReturners(String AdminUsername, String strPassword,
			String InstitutionName, int NumDays) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String GetNonReturnersByServer(int NumDays) {
		// TODO Auto-generated method stub
		return null;
	}

	public static HashMap<String, InetSocketAddress> getReplicaManagerData() {
		return replicaManagerData;
	}

	public static void setReplicaManagerData(HashMap<String, InetSocketAddress> replicaManagerData) {
		FrontEnd.replicaManagerData = replicaManagerData;
	}

	public String getSystemProperty() {
		return systemProperty;
	}

	public void setSystemProperty(String systemProperty) {
		this.systemProperty = systemProperty;
	}

	public InetSocketAddress getSequencerAddress() {
		return sequencerAddress;
	}

	public void setSequencerAddress(InetSocketAddress sequencerAddress) {
		this.sequencerAddress = sequencerAddress;
	}

	public String getLibraryName() {
		return libraryName;
	}

	public void setLibraryName(String libraryName) {
		this.libraryName = libraryName;
	}
	private boolean calculateMajority(ArrayList<BoolResponse> response){
		System.out.println("Response Size:"+response.size());
		System.out.println("Responses are:");
		for(BoolResponse b:response){
			System.out.println("Replica:"+b.getReplicaName());
			System.out.println("Response:"+b.getResult());
		}
		if(response.size()!=replicaManagerData.size()&&response.size()!=0){
			return response.get(0).getResult();
		}
		boolean majorityResult=false;
		int counter=0;
		
		for(BoolResponse r:response){
			if(counter==0){
				majorityResult=r.getResult();
				counter++;
			}
			else if(majorityResult=r.getResult()){
				counter++;
			}
			else{
				counter--;
			}
		}
		if(counter==replicaManagerData.size()){
			return majorityResult;
		}
		
		for(BoolResponse r:response){
			if(r.getResult()!=majorityResult){
				notifySoftwareFailure(r.getReplicaName(),libraryName);
				return majorityResult;
			}
		}
		return false;
	}
}
