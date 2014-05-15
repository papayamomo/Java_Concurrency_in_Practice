package concurrency;

public class Task implements Runnable {

	@Override
	public void run() {
		long start = System.nanoTime();
		System.out.println("Thread start::" + start);
	}
}
