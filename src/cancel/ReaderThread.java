package cancel;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

// 封装非标准的取消操作到 Thread 里面
public class ReaderThread extends Thread {
	private static final int BUFSZ = 10;
	private final Socket socket;
	private final InputStream in;

	public ReaderThread(Socket socket) throws IOException {
		this.socket = socket;
		this.in = socket.getInputStream();
	}

	public void interrupt() {
		try {
			socket.close();
		} catch (IOException ignored) {
		} finally {
			super.interrupt();
		}
	}

	public void run() {
		try {
			byte[] buf = new byte[BUFSZ];
			while (true) {
				int count = in.read(buf);
				if (count < 0) {
					break;
				} else if (count > 0) {
					processBuffer(buf, count);
				}
			}
		} catch (IOException e) {

		}
	}

	private void processBuffer(byte[] buf, int count) {
		// TODO Auto-generated method stub

	}
}
