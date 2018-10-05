package math;

import director.Director;
import graphics.Color;
import graphics.Graphics;
import shapes.Triangle;

public class MathEngine {
	
	private static double unit;
	
	public static void update() {
		unit = Math.atan(1 / Director.screen.getDepth());
	}
	
	public static Vector newVector(Vector v) {
		return Director.screen.getCamera().newVector(v);
	}
	
	public static Vector newDirection(Vector v) {
		return Director.screen.getCamera().newDirection(v);
	}
	
	
	public static float screenX(float x, float z) {
		return (float) (Director.screen.getWidth() / 2f + (x * Director.screen.getDepth() / z));
	}
	
	public static float screenY(float y, float z) {
		return (float) (Director.screen.getHeight() / 2f - (y * Director.screen.getDepth() / z));
	}
	
	/**
	 * @param x with respect to top right of screen
	 * @return x with respect to center
	 */
	public static float cX(float x) {
		return x + (1 / Graphics.SCALE) - Director.screen.getWidth() / 2f;
	}
	
	/**
	 * @param y with respect to top right of screen
	 * @return y with respect to center
	 */
	public static float cY(float y) {
		return Director.screen.getHeight() / 2f - y - (1 / Graphics.SCALE);
	}
	
	
	
	/**
	 * @return a transformation from the original distance between pixels to being 1 to
	 * pixels being scaled by their arctans (makes calculations for real pixel position
	 * significantly easier).
	 */
	public static float getRealX(float x) {
		return (float) (Math.atan(cX(x) / Director.screen.getDepth()) / unit);
	}
	
	/**
	 * @return a transformation from the original distance between pixels to being 1 to
	 * pixels being scaled by their arctans (makes calculations for real pixel position
	 * significantly easier).
	 */
	public static float getRealY(float y) {
		return (float) (Math.atan(cY(y) / Director.screen.getDepth()) / unit);
	}




	public static double square(double d) {
		return d * d;
	}

	public static int square(int d) {
		return d * d;
	}
	
	public static float floor(float n, float f) {
		if (n < f) return f;
		return n;
	}

	public static void drawColor(Triangle t, int x, int y) {
		Vector v = t.getVector(x, y);
		float dis = (float) (square(v.getX())				// fog
				+ square(v.getY()) + square(v.getZ()));
		if (dis <= square(Director.screen.getViewDistance())) {
			int dist = Director.screen.getViewDistance() - Director.screen.getFog();
			Color cc = t.getLightColor(v);
			if (dis <= square(dist)) {
				draw(x, y, v.getZ(), cc);
			} else {
				float perc = (float) ((Math.sqrt(dis) - dist) / (Director.screen.getFog()));
				if (perc != 0)
					draw(x, y, v.getZ(), Director.screen.getBGColor().multiply(perc).add(cc.multiply(1 - perc)));
			}
		}
	}
	
	private static void draw(int x, int y, float z, Color c) {
		if (c != null)
			Director.screen.setPixels(x, y, z, c);
	}
	
	@SuppressWarnings("unused")
	private boolean getInside(Triangle t, int x, int y) {
		Vector v1 = new Vector(screenX(t.getV1().newX(), t.getV1().newZ()), screenY(t.getV1().newY(), t.getV1().newZ()), 0);
		Vector v2 = new Vector(screenX(t.getV2().newX(), t.getV2().newZ()), screenY(t.getV2().newY(), t.getV2().newZ()), 0);
		Vector v3 = new Vector(screenX(t.getV3().newX(), t.getV3().newZ()), screenY(t.getV3().newY(), t.getV3().newZ()), 0);
		boolean n[] = {getIn(v1, v2, v3, x, y), getIn(v2, v3, v1, x, y)};
		return n[0] && n[1];
	}
	
	private static boolean getIn(Vector v1, Vector v2, Vector v3, int x, int y) {
		Vector[] l = {v2.minus(v1), v3.minus(v1)};
		Vector p = (new Vector (x, y, 0)).minus(v1);
		int n = 1;
		if (l[0].getX() * l[1].getY() - l[1].getX() * l[0].getY() < 0)
			n = -1;
		return (n * (p.getX() * l[1].getY() - p.getY() * l[1].getX()) > 0 && n * (p.getY() * l[0].getX() - p.getX() * l[0].getY()) > 0);
	}

	@SuppressWarnings("unused")
	private static float min(float x, float y, float z) {
		if (x < y && x < z)
			return x;
		if (y < x && y < z)
			return y;
		return z;
	}

	/**
	 * 
	 * @param v
	 *            = position Vector of light source in relation to camera
	 * @param p
	 *            = direction from v of the line
	 * 
	 *            y-value of intersection of line of shadow volume that passes
	 *            through plane 1 of the view frustrum (plane on left)
	 * 
	 * @return ret[0] = y-value of intersection in plane 1 or x-value of
	 *         intersection in plane 2 ret[1] = y-value of intersetcion in plane
	 *         3 or x-value of intersection in plane 2 ret[2] = if 0, y-value is
	 *         stored in ret[0], if 1, x-value is stored ret[3] = if 0, y value
	 *         is stored in ret[1], if 1, x-value is stored
	 */
	@SuppressWarnings("unused")
	private float[] getVolumeBoundIntersections(Vector v, Vector p) {
		float[] ret = new float[4];
		float x = Director.screen.getSlope2() * p.getX() + p.getZ();
		float y = p.getY() - Director.screen.getSlope1() * p.getZ();
		if (x != 0) {
			float tx = (float) -((Director.screen.getSlope2() * v.getX() + v.getZ()) / x);
			float txn = (float) -((-Director.screen.getSlope2() * v.getX() + v.getZ())
					/ (-Director.screen.getSlope2() * p.getX() + p.getZ()));
			float x2 = screenX(v.getX() + p.getX() * tx, v.getZ() + p.getZ() * tx);
			float x4 = screenX(v.getX() + p.getX() * txn, v.getZ() + p.getZ() * txn);
			if (Math.abs(x2) <= Director.screen.getWidth() / 2f) {
				ret[0] = x2;
				ret[2] = 1;
			}
			if (Math.abs(x4) <= Director.screen.getWidth() / 2f) {
				ret[1] = x4;
				ret[3] = 1;
			}

		}
		if (y != 0) {
			float ty = (float) (Director.screen.getSlope1() * v.getZ() - v.getY()) / y;
			float tyn = (float) (-Director.screen.getSlope1() * v.getZ() - v.getY())
					/ (Director.screen.getSlope1() * p.getZ() + p.getY());
			float y1 = screenY(v.getY() + p.getY() * ty, v.getZ() + p.getZ() * ty);
			float y3 = screenY(v.getY() + p.getY() * tyn, v.getZ() + p.getZ() * tyn);
			if (Math.abs(y1) <= Director.screen.getHeight()) {
				ret[0] = y1;
				ret[2] = 0;
			}
			if (Math.abs(y3) <= Director.screen.getHeight()) {
				ret[1] = y3;
				ret[3] = 0;
			}
		}
		return ret;
	}

	public static int round(float d) {
		return round((double) d);
	}

	public static int round(double d) {
		if (d % 1 >= 0.5)
			return (int) (d + .5);
		else
			return (int) d;
	}
}
