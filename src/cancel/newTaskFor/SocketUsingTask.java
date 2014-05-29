package cancel.newTaskFor;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

import annotation.GuardedBy;

public class SocketUsingTask<T> implements CancellableTask<T> {

	@GuardedBy("this")
	private Socket socket;

	protected synchronized void setSocket(Socket s) {
		socket = s;
	}

	@Override
	public T call() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void cancel() {
		try {
			if (socket != null) {
				socket.close();
			}
		} catch (IOException e) {
		}
	}

	@Override
	public RunnableFuture<T> newTask() {
		return new FutureTask<T>(this) {
			@SuppressWarnings("finally")
			public boolean cancel(boolean mayInterruptIfRunning) {
				try {
					SocketUsingTask.this.cancel();
				} finally {
					return super.cancel(mayInterruptIfRunning);
				}
			}
		};
	}
}
