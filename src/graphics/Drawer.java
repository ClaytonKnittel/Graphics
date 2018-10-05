package graphics;

import director.Director;
import math.MathEngine;
import shapes.Line;
import shapes.Triangle;

public class Drawer {

	public Drawer() {
	}

	public void drawLine(Line l) {
		
		float x1 = l.screenX1();
		float x2 = l.screenX2();
		float y1 = l.screenY1();
		float y2 = l.screenY2();
		this.drawLine(null, x1, y1, x2, y2, l.getColor());
	}
	
	public void drawLine(Triangle tri, float x1, float y1, float x2, float y2, Color c) {
		//Triangle tee = new Triangle(1, 2, 3, 1, 2, 3, 1, 2, 3, null, null, null, null);
		//Triangle tt = new Triangle(1, 1, 1, 1, 1, 1, 1, 1, 1, null);
		double one = x2 - x1;
		double two = y2 - y1;
		double length;
		if (Math.abs(two) > Math.abs(one))
			length = Math.abs(two);
		else
			length = Math.abs(one);
		double dx = one / length;
		double dy = two / length;
		double x = x1;
		double y = y1;
		if (x < 0) {
			y -= dy / dx * x;
			length -= Math.sqrt(Math.pow(y1 - y, 2) + x * x);
			x = 0;
		}
		if (y < 0) {
			x -= dx / dy * y;
			length -= Math.sqrt(Math.pow(x1 - x, 2) + y * y);
			y = 0;
		}
		if (x >= Director.screen.getWidth()) {
			y -= dy / dx * (x - Director.screen.getWidth() + 1);
			length -= Math.sqrt(Math.pow(y1 - y, 2) + (x - Director.screen.getWidth() + 1) * (x - Director.screen.getWidth() + 1));
			x = Director.screen.getWidth() - 1;
		}
		if (y >= Director.screen.getHeight()) {
			x -= dx / dy * (y - Director.screen.getHeight() + 1);
			length -= Math.sqrt(Math.pow(x1 - x, 2) + (y - Director.screen.getHeight() + 1) * (y - Director.screen.getHeight() + 1));
			y = Director.screen.getHeight() - 1;
		}
		for (int t = 0; t <= length && x >= 0 && x < Director.screen.getWidth() && y >= 0 && y < Director.screen.getHeight(); t++) {
			if (tri == null)
				Director.screen.setPixels((int) x, (int) y, -1, c);
			else {
				Director.screen.setPixels((int) x, (int) y, -1, c);
			}
			x += dx;
			y += dy;
		}
	}
	
	public void drawTria(Triangle t) {
		if (!t.isInFOV())
			return;
		float x1 = t.screenX1();
		float x2 = t.screenX2();
		float x3 = t.screenX3();
		float y1 = t.screenY1();
		float y2 = t.screenY2();
		float y3 = t.screenY3();
		
		if (y2 < y1) {
			x1 += x2;
			x2 = x1 - x2;
			x1 -= x2;
			y1 += y2;
			y2 = y1 - y2;
			y1 -= y2;
		}
		if (y3 < y2) {
			x2 += x3;
			x3 = x2 - x3;
			x2 -= x3;
			y2 += y3;
			y3 = y2 - y3;
			y2 -= y3;
		}
		if (y2 < y1) {
			x1 += x2;
			x2 = x1 - x2;
			x1 -= x2;
			y1 += y2;
			y2 = y1 - y2;
			y1 -= y2;
		}
		
		int ry2 = Math.round(y2), ry3 = Math.round(y3);
		
		int y = Math.max(Math.round(y1), 0);
		float s1 = (x2 - x1) / (y2 - y1), s2 = (x3 - x1) / (y3 - y1), s3 = (x3 - x2) / (y3 - y2);
		
		float f1 = Math.min(s1, s2);
		float f2 = s1 + s2 - f1;
		
		while (ry2 > y + .5f && y < Director.screen.getHeight()) {
			for (int x = Math.max(Math.round(f1 * (y + .5f - y1) + x1 + .5f), 0); x < Math.min(Math.round(f2 * (y + .5f - y1) + x1 + .5f), Director.screen.getWidth()); x++)
				MathEngine.drawColor(t, x, y);
			y++;
		}
		f2 = Math.min(s2, s3);
		f1 = s2 + s3 - f2;
		while (ry3 > y + .5f && y < Director.screen.getHeight()) {
			for (int x = Math.max(Math.round(f1 * (y + .5f - y3) + x3 + .5f), 0); x < Math.min(Math.round(f2 * (y + .5f - y3) + x3 + .5f), Director.screen.getWidth()); x++)
				MathEngine.drawColor(t, x, y);
			y++;
		}
	}

	public int getCorr(int x1, int x2, int y1, int y2, int x) {
		if (x == x1)
			return y1;
		if (x == x2)
			return y2;
		return 0;
	}

	public int getCorrX(int x1, int x2, int y1, int y2, int y) {
		if (y == y1)
			return x1;
		if (y == y2)
			return x2;
		return 0;
	}
}
