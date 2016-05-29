package home.server;

import java.io.IOException;
import java.net.ServerSocket;

public class KeyValuesServer {

	public static void main(String[] args) throws IOException {

		KeyValuesHandler keyValuesHandler = new KeyValuesHandler();
		
		if (args.length != 1) {
			System.err.println("Usage: java -jar server-jar-with-dependencies.jar <port number>");
			System.exit(1);
		}

		int portNumber = Integer.parseInt(args[0]);
		boolean listening = true;

		try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
			while (listening) {
				new KeyValuesServerThread(serverSocket.accept(), keyValuesHandler).start();
			}
		} catch (IOException e) {
			System.err.println("Could not listen on port " + portNumber);
			System.exit(-1);
		}
	}
}
