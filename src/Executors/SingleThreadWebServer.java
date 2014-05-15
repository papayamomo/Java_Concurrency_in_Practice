package Executors;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SingleThreadWebServer {

	public static void main(String[] args) throws IOException {
		ServerSocket socket = new ServerSocket(80);
		while (true) {
			Socket connection = socket.accept(); // BLOCKED until a request
													// arrives
			handleRequest(connection);
		}
	}

	private static void handleRequest(Socket connection) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("!!!!!");
	}
}
