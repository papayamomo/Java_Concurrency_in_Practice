package join;

public class CustomThread extends Thread {
	CustomThread1 t1;

	public CustomThread(CustomThread1 t1) {
		super("[CustomThread] thread");
		this.t1 = t1;
	}

	public void run() {
		String threadName = Thread.currentThread().getName();
		System.out.println(threadName + " start.");
		try {
			t1.join();
			System.out.println(threadName + " end.");
		} catch (Exception e) {
			System.out.println("Exception from " + threadName + ".run");
		}
	}
}
