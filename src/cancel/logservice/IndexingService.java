package cancel.logservice;

import java.io.File;
import java.util.concurrent.BlockingQueue;

public class IndexingService {

	private static final File POISON = new File("");
	private final IndexerThread consumer = new IndexerThread();
	private final CrawlerThread producer = new CrawlerThread();
	private final BlockingQueue<File> queue;
	private final File root;

	public IndexingService(BlockingQueue<File> queue, File root) {
		this.queue = queue;
		this.root = root;
	}

	class CrawlerThread extends Thread {
		public void run() {
			try {
				crawl(root);
			} finally {
				while (true) {
					try {
						queue.put(POISON);
						break;
					} catch (InterruptedException e1) {
						/* retry */
					}
				}
			}
		}

		private void crawl(File root) {
			// TODO Auto-generated method stub
		}
	}

	class IndexerThread extends Thread {
		public void run() {
			try {
				while (true) {
					File file = queue.take();
					if (file == POISON)
						break;
					else
						indexFile(file);
				}
			} catch (InterruptedException consumed) {

			}
		}

		private void indexFile(File file) {
			// TODO Auto-generated method stub

		}
	}

	public void start() {
		producer.start();
		consumer.start();
	}

	public void stop() {
		producer.interrupt();
	}

	public void awaitTermination() throws InterruptedException {
		consumer.join();
	}
}
