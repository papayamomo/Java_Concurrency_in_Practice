package Executors.Renderer;

import java.util.ArrayList;
import java.util.List;

public class SingleThreadRenderer {
	void renderPage(CharSequence source) {
		renderText(source);
		List<ImageData> imageData = new ArrayList<ImageData>();
		for (ImageInfo imageInfo : scanForImageInfo(source))
			imageData.add(imageInfo.downloadImage());
		for (ImageData data : imageData)
			renderImage(data);
	}

	private void renderImage(ImageData data) {
		// TODO Auto-generated method stub

	}

	private List<ImageInfo> scanForImageInfo(CharSequence source) {
		// TODO Auto-generated method stub
		return null;
	}

	private void renderText(CharSequence source) {
		// TODO Auto-generated method stub

	}
}
