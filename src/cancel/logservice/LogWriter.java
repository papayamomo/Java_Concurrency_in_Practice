package cancel.logservice;

import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class LogWriter {

	private final BlockingQueue<String> queue;
	private final LoggerThread logger;
	private boolean shutdonwRequested;

	public LogWriter(PrintWriter writer) {
		this.queue = new LinkedBlockingQueue<String>();
		this.logger = new LoggerThread(writer);
	}

	public void start() {
		logger.start();
	}

	public void log(String msg) throws InterruptedException {
		if (!shutdonwRequested)
			queue.put(msg);
		else
			throw new IllegalStateException("logger is shut down");
	}

	public boolean isShutdonwRequested() {
		return shutdonwRequested;
	}

	public void setShutdonwRequested(boolean shutdonwRequested) {
		this.shutdonwRequested = shutdonwRequested;
	}

	private class LoggerThread extends Thread {
		private final PrintWriter writer;

		public LoggerThread(PrintWriter writer) {
			this.writer = writer;
		}

		public void run() {
			try {
				while (true)
					writer.println(queue.take());
			} catch (InterruptedException ignored) {
			} finally {
				writer.close();
			}
		}
	}

}
