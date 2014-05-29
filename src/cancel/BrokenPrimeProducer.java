package cancel;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

// 用boolean来判断是否被取消
// ERROR: 调用cancel()之后，将不会再调用take。那么queue里面的任务，将不会被cancel。
public class BrokenPrimeProducer extends Thread {

	private final BlockingQueue<BigInteger> queue;
	private volatile boolean cancelled = false;

	BrokenPrimeProducer(BlockingQueue<BigInteger> queue) {
		this.queue = queue;
	}

	public void run() {
		try {
			BigInteger p = BigInteger.ONE;
			while (!cancelled)
				queue.put(p = p.nextProbablePrime());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void cancel() {
		cancelled = true;
	}

	void consumePrimes() throws InterruptedException {
		BlockingQueue<BigInteger> primes = new LinkedBlockingQueue<BigInteger>(
				10);
		BrokenPrimeProducer producer = new BrokenPrimeProducer(primes);
		producer.start();
		try {
			while (needMorePrimes())
				consume(primes.take());
		} finally {
			producer.cancel();
		}
	}

	private void consume(BigInteger take) {
		// TODO Auto-generated method stub

	}

	private boolean needMorePrimes() {
		// TODO Auto-generated method stub
		return false;
	}
}
