package graphics;

import java.awt.Graphics;
/*import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.imageio.ImageIO;*/

public class Image {
	
	//private Color[] c;
	private graphics.Color[] pixels;
	private boolean[] changed;
	private int width;
	
	public Image(int width, int height) {
		this.width = width;
		pixels = new Color[width * height];
		changed = new boolean[width * height];
		for (int x = 0; x < changed.length; x++) {
			changed[x] = true;
		}
	}
	
	public void paint(int x, int y, Color c) {
		if (pixels[y * width + x] == c)
			changed[y * width + x] = true;
		pixels[y * width + x] = c;
	}
	
	public void render(Graphics g) {
		for (int x = 0; x < pixels.length; x++) {
			if (changed[x]) {
				g.setColor(new java.awt.Color(pixels[x].getColor()));
				g.drawRect((x % width) / 2, (x / width) / 2, 0, 0);
				changed[x] = false;
			}
		}
	}
	
	/*public Image(String address) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(address));
		} catch (Exception e) {
			System.err.println("Found it");
		}
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(img, "png", baos);
			baos.flush();
			bite = baos.toByteArray();
			baos.close();
		} catch (Exception e) {
			System.err.println("Person is wrong");
		}
	}
	
	public void print() {
		for (byte b : bite) {
			System.out.println(b);
		}
	}*/
	
}
