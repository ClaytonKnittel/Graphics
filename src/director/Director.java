package director;

import graphics.Color;
import graphics.Drawer;
import graphics.Graphics;
import graphics.Raw;
import graphics.Screen;
import graphics.World;
import inputs.InputHandle;
import math.MathEngine;
import shapes.Camera;

public class Director {
	
	public static int width = 800, height = 600;
	
	public static Graphics graphics;
	public static Screen screen;
	public static World world;
	public static InputHandle input;
	public static Drawer drawer;
	
	public static void init(int width, int height, float x, float y, float z, float angle, float yAngle) {
		init(width, height, new Camera(x, y, z, angle, yAngle));
	}
	
	public static void init(int width, int height, Camera cameraPos) {
		screen = new Screen((int) (width * Graphics.SCALE), (int) (height * Graphics.SCALE), cameraPos, Color.SKY_BLUE); // 909 75 720
		screen.init();
		world = new World();
		graphics = new Graphics();
		input = new InputHandle();
		graphics.addKeyListener(Director.input);
		drawer = new Drawer();
		MathEngine.update();
	}
	
	public static void main(String args[]) {
		init(width, height, new Camera(400, 200, -1000, 0.6f, -0.24f));
		world.add(new Raw("Boat").getTri(Color.SOFT_WHITE, 1f, false, true));
	}
	
}
