package Executors.Renderer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureRenderer {

	private static final int NTHREADS = 4;
	private final ExecutorService exec = Executors.newFixedThreadPool(NTHREADS);

	void renderPage(CharSequence source) {
		final List<ImageInfo> imageInfos = scanForImageInfo(source);
		Callable<List<ImageData>> task = new Callable<List<ImageData>>() {
			public List<ImageData> call() {
				List<ImageData> result = new ArrayList<ImageData>();
				for (ImageInfo imageInfo : imageInfos) {
					result.add(imageInfo.downloadImage());
				}
				return result;
			}
		};

		Future<List<ImageData>> future = exec.submit(task);
		renderText(source);

		try {
			List<ImageData> imageData = future.get();
			for (ImageData data : imageData)
				renderImage(data);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			future.cancel(true);
		} catch (ExecutionException e) {
			try {
				throw launderThrowable(e.getCause());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	private Exception launderThrowable(Throwable cause) {
		// TODO Auto-generated method stub
		return null;
	}

	private void renderImage(ImageData data) {
		// TODO Auto-generated method stub

	}

	private void renderText(CharSequence source) {
		// TODO Auto-generated method stub

	}

	private List<ImageInfo> scanForImageInfo(CharSequence source) {
		// TODO Auto-generated method stub
		return null;
	}
}
