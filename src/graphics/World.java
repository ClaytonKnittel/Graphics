package graphics;

import java.util.ArrayList;

import shapes.Shape;

public class World {

	@SuppressWarnings("unused")
	private ArrayList<EmissiveLight> lightSources;
	private ArrayList<Shape> staticObjects;
	
	public World() {
		lightSources = new ArrayList<EmissiveLight>();
		staticObjects = new ArrayList<Shape>();
	}
	
	public synchronized void update() {
		for (int x = 0; x < staticObjects.size(); x++)
			staticObjects.get(x).update();
	}
	
	public synchronized void add(Shape s) {
		staticObjects.add(s);
		staticObjects.get(staticObjects.size() - 1).update();
	}
	
	public synchronized void add(Shape[] s) {
		for (Shape t : s) {
			staticObjects.add(t);
			staticObjects.get(staticObjects.size() - 1).update();
		}
	}
	
	public synchronized void remove(Shape s) {
		staticObjects.remove(s);
	}
	
	public ArrayList<Shape> getObjects() {
		return staticObjects;
	}
	
	public Shape getObject(int pos) {
		return staticObjects.get(pos);
	}
	
	public synchronized void draw() {
		for (int x = 0; x < staticObjects.size(); x++) {
			Shape s = staticObjects.get(x);
			s.draw();
		}
	}
}
