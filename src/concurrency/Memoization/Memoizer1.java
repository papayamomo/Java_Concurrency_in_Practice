package concurrency.Memoization;

import java.util.HashMap;
import java.util.Map;

import annotation.GuardedBy;

public class Memoizer1<A, V> implements Computable<A, V> {
	@GuardedBy("this")
	private final Map<A, V> cache = new HashMap<A, V>();
	private final Computable<A, V> c;

	public Memoizer1(Computable<A, V> c) {
		this.c = c;
	}

	// Only one thread can use this whole method in one time
	// Serially and inefficiently
	public synchronized V compute(A arg) throws InterruptedException {
		V result = cache.get(arg);
		if (result == null) {
			result = c.compute(arg);
			cache.put(arg, result);
		}
		return result;
	}

}
