package Executors;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

public class LifecycleWebServer {

	private static final int NTHREADS = 100;
	private final ExecutorService exec = Executors.newFixedThreadPool(NTHREADS);

	public void start() throws IOException {
		ServerSocket socket = new ServerSocket(80);
		while (!exec.isShutdown()) {
			try {
				final Socket connection = socket.accept();
				exec.execute(new Runnable() {
					public void run() {
						handleRequest(connection);
					}
				});
			} catch (RejectedExecutionException e) {
				if (!exec.isShutdown()) {
					System.out.println("task submission rejected" + e);
				}
			}
		}
	}

	public void stop() {
		exec.shutdown();
	}

	private void handleRequest(Socket connection) {
		Request req = readReqeust(connection);
		if (isShutdownRequest(req))
			stop();
		else
			dispatchRequest(req);
	}

	private void dispatchRequest(Request req) {
		// TODO Auto-generated method stub

	}

	private boolean isShutdownRequest(Request req) {
		// TODO Auto-generated method stub
		return false;
	}

	private Request readReqeust(Socket connection) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("!!!!!");
		return null;
	}

}
