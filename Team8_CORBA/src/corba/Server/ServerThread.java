package corba.Server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ServerThread extends Thread{
	
private LibraryServer server;
	
	public ServerThread(LibraryServer server) {
		this.server = server;
		
	}
	@Override
	public void run() {
		DatagramSocket socket = null;
		try
		{
		socket = new DatagramSocket(server.getUDPPort());
		byte [] buffer = new byte[10000];
		//this.logger.info("UPD server for "+this.instituteName+" is running on port: "+udpPort);
		while(true) {
			DatagramPacket request = new DatagramPacket(buffer, buffer.length);
			socket.receive(request);
			String data = new String(request.getData());
			String[] requestParts = data.split(":");
			String response = "";
			if(requestParts.length == 2 ) {
				
				//Call GetNonReturners
				response = server.GetNonReturnersByServer(Integer.parseInt(requestParts[1].trim()));
			}
			else {
				//Call Reserve Book
				response = server.reserveBook("", "", requestParts[1], requestParts[2])?"true":"false";
			}
			
			DatagramPacket reply = new DatagramPacket(response.getBytes(), response.length(), request.getAddress(), request.getPort());
			socket.send(reply);
		}
		}
		catch(Exception err) {
			err.printStackTrace();
		}
		finally{
			socket.close();
		}

	}

}
