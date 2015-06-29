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
		while(true) {
			DatagramPacket request = new DatagramPacket(buffer, buffer.length);
			socket.receive(request);
			String data = new String(request.getData());
			String[] requestParts = data.split(":");
			String response = "";
			if(requestParts[0].equals("Days")) {
				//Call GetNonReturners
				response = server.GetNonReturnersByServer(Integer.parseInt(requestParts[1].trim()));
			}
			else if(requestParts[0].equals("ReserveBook")) {
				//Call Reserve Book
				response = server.grantBookInterServer(requestParts[1])?"true":"false";
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
