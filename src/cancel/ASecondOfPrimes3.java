package cancel;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ASecondOfPrimes3 {

	private static final ScheduledExecutorService taskExec = Executors
			.newScheduledThreadPool(2);

	public static void timedRun(final Runnable r, long timeout, TimeUnit unit)
			throws InterruptedException {
		Future<?> task = taskExec.submit(r);
		try {
			task.get(timeout, unit);
		} catch (TimeoutException e) {
			// 接下来任务将被取消
		} catch (ExecutionException e) {
			// 如果在任务中抛出异常，那么操心抛出该异常
			try {
				throw launderThrowable(e.getCause());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			// 如果任务已经结束，那么只需取消操作也不会有影响
			task.cancel(true); // 如果任务正在进行，那么将中断
		}
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
