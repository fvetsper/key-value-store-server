package home.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class KeyValuesServerThread extends Thread {
	private Socket socket = null;

	public KeyValuesServerThread(Socket socket) {
		this.socket = socket;
	}

	public void run() {

		try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(
						socket.getInputStream()));) {
			String inputLine, outputLine;
			
			
			
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
