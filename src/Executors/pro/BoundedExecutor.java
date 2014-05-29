package Executors.pro;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class BoundedExecutor {
	private static final int N_THREADS = 0;
	private final Executor exec;
	private final Semaphore semaphore;

	public BoundedExecutor(Executor exec, int bound) {
		this.exec = exec;
		this.semaphore = new Semaphore(bound);
	}

	public void sumbitTask(final Runnable command) throws InterruptedException {
		semaphore.acquire();
		try {
			exec.execute(new Runnable() {
				public void run() {
					try {
						command.run();
					} finally {
						semaphore.release();
					}
				}
			});
		} catch (RejectedExecutionException e) {
			semaphore.release();
		}
	}

	public static void mian(String[] args) {
		ThreadPoolExecutor executor = new ThreadPoolExecutor(N_THREADS,
				N_THREADS, 0L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>());
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
	}
}
