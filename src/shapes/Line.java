package shapes;

import director.Director;
import graphics.Color;
import math.Vector;

public class Line extends Shape {

	private Vector one, two;

	public Line(float x1, float y1, float z1, float x2, float y2, float z2,
			Color c) {
		super(c);
		this.one = new Vector(x1, y1, z1);
		this.two = new Vector(x2, y2, z2);
		super.change();
	}

	public Line(Vector v1, Vector v2, Color c) {
		this(v1.getX(), v1.getY(), v1.getZ(), v2.getX(), v2.getY(), v2.getZ(), c);
	}

	public float screenX1() {
		return one.screenX();
	}

	public float screenY1() {
		return one.screenY();
	}

	public float screenX2() {
		return two.screenX();
	}

	public float screenY2() {
		return two.screenY();
	}
	
	public void draw() {
		Director.drawer.drawLine(this);
	}
	
	public void update() {
		one.update();
		two.update();
		super.updateChange();
	}

	public boolean isInFOV() {
		if (one.isInFOV() || two.isInFOV()) {
			if (one.newZ() <= 0 && two.newZ() <= 0) {
				return true;
			} else if (one.newZ() >= 0 && two.newZ() >= 0)
				return true;
			else
				return false;
		} else
			return false;
	}
}