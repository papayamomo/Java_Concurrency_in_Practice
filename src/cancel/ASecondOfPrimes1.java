package cancel;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import annotation.DontDoIt;

public class ASecondOfPrimes1 {
	private static final ScheduledExecutorService cancelExec = Executors
			.newScheduledThreadPool(2);

	// Do not know the interrupt strategy
	@DontDoIt
	public static void timedRun(Runnable r, long timeout, TimeUnit unit) {
		final Thread taskThread = Thread.currentThread();
		cancelExec.schedule(new Runnable() {
			public void run() {
				taskThread.interrupt();
			}
		}, timeout, unit);
		r.run();
	}
}
