package home.server;

import home.commons.Request;
import home.commons.Response;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class KeyValuesServerThread extends Thread {
	private Socket socket = null;
	private KeyValuesHandler keyValuesHandler = null;
			
	public KeyValuesServerThread(Socket socket, KeyValuesHandler keyValuesHandler) {
		this.socket = socket;
		this.keyValuesHandler = keyValuesHandler;
	}

	public void run() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			
			String rjson = dis.readUTF();
			
			if (rjson != null && rjson.length() > 0) {
				Request request = mapper.readValue(rjson, Request.class);
				Response response = keyValuesHandler.process(request);
				String wjson = mapper.writeValueAsString(response);
				dos.writeUTF(wjson);
			}
			
			dos.flush();
			dos.close();
			dis.close();
			socket.close();
		}
		catch (JsonProcessingException e) {
			System.err.println("couldn't process json. reason: " + e.getMessage() );
		}
		catch (IOException e) {
			System.err.println("get I/O error. reason: " + e.getMessage());
		}
	}
}
