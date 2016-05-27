package home.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class KeyValuesClientConnection {

	
	private String host;
	private int port;
	
	public KeyValuesClientConnection(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public Response send(Request request) {
		Response response = null;
		try {
			Socket socket = new Socket(host, port);
			DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
			DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(request);
			
			dataOutputStream.writeUTF(json);
			char c;
			StringBuilder sb = new StringBuilder();
			while ((c = (char)dataInputStream.read()) != -1) {
				sb.append(c);
			}
			
			if (sb.length() > 0) {
				response = mapper.readValue(sb.toString(), Response.class);
			}
			
			dataInputStream.close();
			dataOutputStream.close();
			socket.close();
			
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " + host);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to " + host);
		}
		return response;
	}
	
}
