package cancel.logservice;

import java.io.PrintWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

import annotation.GuardedBy;

public class LogService2 {
	private static final long TIMEOUT = 10;
	private static final TimeUnit UNIT = TimeUnit.SECONDS;
	private final ExecutorService exec = Executors.newSingleThreadExecutor();
	private final PrintWriter writer;
	@GuardedBy("this")
	private boolean isShutdown;
	@GuardedBy("this")
	private int reservations;

	public void start() {
	}

	public LogService2(PrintWriter writer) {
		this.writer = writer;
	}

	public void stop() throws InterruptedException {
		try {
			exec.shutdown();
			exec.awaitTermination(TIMEOUT, UNIT);
		} finally {
			writer.close();
		}
	}

	public void log(String msg) throws InterruptedException {
		try {
			exec.execute(new WriteTask(msg));
		} catch (RejectedExecutionException ignored) {

		}
	}

}
