package graphics;

import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import director.Director;
import math.Vector;
import shapes.Camera;

public class Screen {

	private int width;
	private int height;
	private int viewDistance = 1200;
	private float viewAngle = 70;
	private int fog = 200;
	private double depth;
	
	private static final int MENU_BAR_HEIGHT = 22;
	
	private Color bgColor;

	//slope of the four planes bounding the view spectrum
	private float slope1;
	private float slope2;

	private BufferedImage image;
	//private int[] pixels;
	private int[][] pixelsStorage;
	private int switcher;
	private float[] zBuffer;
	private float[] zBufferBuffer;
	@SuppressWarnings("unused")
	private boolean[] stencilBuffer;
	
	private Camera camera;
	
	private float lightAngle = (float) Math.PI / 2;
	private Vector lightVector = new Vector(0, -1, .5f);
	private float ambiantLight = 0.07f;
	
	private int maxFrameRate = 12000;
	
	public Screen(int width, int height, float x, float y, float z, float camAngle, float camYAngle, Color bgColor) {
		this(width, height, x, y, z, bgColor);
		camera.turn(camAngle);
		camera.yTurn(camYAngle);
	}
	
	public Screen(int width, int height, Camera cameraPos, Color bgColor) {
		this(width, height, cameraPos.getX(), cameraPos.getY(), cameraPos.getZ(), cameraPos.getCamAngle(), cameraPos.getCamYAngle(), bgColor);
	}
	
	public Screen(int width, int height, float x, float y, float z, Color bgColor) {
		this.width = width;
		this.height = height;
		this.depth = 1.0 / Math.tan(Math.PI * viewAngle / 360.0) * width / 2d;
		this.slope1 = (float) -(2 * depth / width);
		this.slope2 = (float) (2 * depth / height);
		
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		//pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		pixelsStorage = new int[image.getWidth() * image.getHeight()][2];
		switcher = 0;
		this.bgColor = bgColor;
		init();
		initializeImage();
		
		zBuffer = new float[width * height];
		zBufferBuffer = new float[width * height];
		stencilBuffer = new boolean[width * height];
		
		lightVector.normalize();
		
		camera = new Camera(x, y, z);
	}
	
	public void init() {
		for (int x = 0; x < width * height; x++) {
			pixelsStorage[x][switcher] = bgColor.getColor();
		}
	}
	
	public void initializeImage() {
		for (int xx = 0; xx < pixelsStorage.length; xx++) {
			image.setRGB(xx % image.getWidth(), xx / image.getWidth(), bgColor.getColor());
		}
	}
	
	public float getLightAngle() {
		return lightAngle;
	}
	
	public Vector getLightVector() {
		return lightVector;
	}
	
	public void addTime(float f) {
		lightAngle += f;
		lightVector = Vector.getPosFromAngle(lightVector, f);
	}
	
	public float getAmbiantLight() {
		return ambiantLight;
	}
	
	public void update() {
		camera.update();
	}
	
	private void reset() {
		switcher = (switcher == 1) ? 0 : 1;
		init();
		for (int x = 0; x < width * height; x++) {
			zBufferBuffer[x] = zBuffer[x];
			zBuffer[x] = -1;
		}
	}
	
	public static int getMenuBarHeight() {
		return MENU_BAR_HEIGHT;
	}
	
	public void paint(BufferStrategy bs) {
		java.awt.Graphics g = bs.getDrawGraphics();
		reset();
		Director.world.draw();
		render();
		g.drawImage(image, 0, 0, (int) (getWidth() / graphics.Graphics.SCALE), (int) (getHeight() / graphics.Graphics.SCALE), null);
		g.dispose();
		bs.show();
	}
	
	public int getMillisPerFrame() {
		return 1000 / maxFrameRate;
	}
	
	public int getNanosPerFrame() {
		return 1000000000 / maxFrameRate;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getViewDistance() {
		return viewDistance;
	}
	
	public int getFog() {
		return fog;
	}
	
	public float getViewAngle() {
		return viewAngle;
	}
	
	public double getDepth() {
		return depth;
	}
	
	public float getSlope1() {
		return slope1;
	}
	
	public float getSlope2() {
		return slope2;
	}
	
	public Color getBGColor() {
		return bgColor;
	}
	
	public Camera getCamera() {
		return camera;
	}
	
	public float getZBuffer(int x, int y) {
		if (zBuffer[x + y * width] == -1)
			return zBufferBuffer[x + y * width];
		return zBuffer[x + y * width];
	}
	
	public boolean loadZBuffer(int x, int y, float z) {
		if (z == -1)
			return true;
		if ((z >= 0 && z < zBuffer[x + y * width]) || zBuffer[x + y * width] == -1) {
			zBuffer[x + y * width] = z;
			return true;
		}
		return false;
	}
	
	public void setPixels(int x, int y, float z, Color c) {
		if (x >= 0 && x < width && y >= 0 && y < width) {
			if (loadZBuffer(x, y, z))
				pixelsStorage[(int) x + ((int) y) * width][switcher] = c.getColor();
		}
	}
	
	public void render() {
		for (int x = 0; x < pixelsStorage.length; x++) {
			if (pixelsStorage[x][switcher] != pixelsStorage[x][-switcher + 1])
				image.setRGB(x % image.getWidth(), x / image.getWidth(), pixelsStorage[x][switcher]);
		}
	}
}
