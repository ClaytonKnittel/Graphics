package graphics;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.awt.Frame;

public class Tester extends Frame implements Runnable {
	private static final long serialVersionUID = 1L;

	private static final int WIDTH = 512;
	private static final int HEIGHT = 512;

	private static BufferedImage image = new BufferedImage(WIDTH, HEIGHT,
			BufferedImage.TYPE_INT_RGB);
	protected static int[] pixels = ((DataBufferInt) image.getRaster()
			.getDataBuffer()).getData();
	//private byte[] iPix;

	public Tester() {
		
	}

	public byte[] extractBytes(String ImageName) throws IOException {
		// open image
		File imgPath = new File(ImageName);
		BufferedImage bufferedImage = ImageIO.read(imgPath);

		// get DataBufferBytes from Raster
		WritableRaster raster = bufferedImage.getRaster();
		DataBufferByte data = (DataBufferByte) raster.getDataBuffer();

		return (data.getData());
	}

	public static void main(String args[]) {
	}

	public void run() {
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000D / 60D;

		int frames = 0;
		int ticks = 0;

		long lastTimer = System.currentTimeMillis();
		double delta = 0;
		while (true) {
			render();
			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			lastTime = now;
	
			boolean shouldRender = true;
	
			while (delta >= 1) {
				ticks++;
				delta--;
				shouldRender = true;
			}
	
			if (shouldRender) {
				frames++;
				render();
			}
	
			if (System.currentTimeMillis() - lastTimer > 1000) {
				lastTimer += 1000;
				System.out.println(ticks + " ticks, " + frames + " frames");
				frames = 0;
				ticks = 0;
			}
		}
	}

	public void render() {
		
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(2);
			return;
		}
		
		//System.out.println(iPix[724 * 200 + 99] + " " + iPix[724 * 200 + 100]);

		Graphics g = bs.getDrawGraphics();

		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);

		g.dispose();
		bs.show();
	}
}
