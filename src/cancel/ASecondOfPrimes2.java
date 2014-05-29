package cancel;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ASecondOfPrimes2 {

	private static final ScheduledExecutorService cancelExec = Executors
			.newScheduledThreadPool(2);

	public static void timedRun(final Runnable r, long timeout, TimeUnit unit)
			throws InterruptedException {
		class RethrowableTask implements Runnable {
			private volatile Throwable t;

			public void run() {
				try {
					r.run();
				} catch (Throwable t) {
					this.t = t;
				}
			}

			void rethrow() {
				if (t != null)
					try {
						throw launderThrowable(t);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}

		RethrowableTask task = new RethrowableTask();
		final Thread taskThread = new Thread(task);
		taskThread.start();
		cancelExec.schedule(new Runnable() {
			public void run() {
				taskThread.interrupt();
			}
		}, timeout, unit);
		taskThread.join(unit.toMillis(timeout));
		task.rethrow();
	}

	private static Exception launderThrowable(Throwable t) {
		if (t instanceof RuntimeException)
			return (RuntimeException) t;
		else if (t instanceof Error)
			throw (Error) t;
		else
			throw new IllegalStateException("Not unchecked", t);
	}
}
