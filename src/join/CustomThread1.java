package join;

public class CustomThread1 extends Thread {
	public CustomThread1() {
		super("[CustomThread1] thread");
	}

	public void run() {
		String threadName = Thread.currentThread().getName();
		System.out.println(threadName + " start.");
		try {
			for (int i = 0; i < 5; i++) {
				System.out.println(threadName + " loop at " + i);
				Thread.sleep(1000);
			}
			System.out.println(threadName + " end.");
		} catch (Exception e) {
			System.out.println("Exception from " + threadName + ".run");
		}
	}
}
