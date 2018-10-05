package math;

import director.Director;
import graphics.Graphics;
import shapes.Camera;

public class Vector {
	
	/**
	 * Y and Z axis are swapped from convention
	 * 
	 * +Y
	 *  ^
	 *  |
	 *  +---> +X
	 *  
	 *  Positive Z goes into the page
	 */

	private float x, y, z, magnitude;
	
	private float newX, newY, newZ;
	
	private float screenX, screenY;
	
	/**
	 * true if when finding new Vector with respect to screen,
	 * you only rotate and do not subtract camera.
	 * For normal Vectors, this is true.
	 * For position vectors, this is false.
	 */
	private boolean isDirection = false;

	public Vector(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector(float x, float y, float z, boolean isDirection) {
		this(x, y, z);
		this.isDirection = isDirection;
	}

	public Vector(float x, float y) {
		this(x, y, 0);
	}

	public Vector(Vector v) {
		this(v.x, v.y, v.z);
	}
	
	public Vector(Vector v, boolean isDirection) {
		this(v);
		this.isDirection = isDirection;
	}
	
	public void setMag() {
		this.magnitude = (float) Math.sqrt(MathEngine.square(x)
				+ MathEngine.square(y) + MathEngine.square(z));
	}
	
	public static float magnitude(Vector v) {
		return (float) Math.sqrt(squareMagnitude(v));
	}
	
	public static double squareMagnitude(Vector v) {
		return MathEngine.square(v.getX()) + MathEngine.square(v.getY()) + MathEngine.square(v.getZ());
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getZ() {
		return z;
	}

	public float newX() {
		return newX;
	}

	public float newY() {
		return newY;
	}

	public float newZ() {
		return newZ;
	}

	/**
	 * 
	 * @return screen x-value of this Vector, assuming it has already been transformed
	 */
	public float screenX() {
		return screenX;
	}

	/**
	 * 
	 * @return screen y-value of this Vector, assuming it has already been transformed
	 */
	public float screenY() {
		return screenY;
	}
	
	
	public float setX(float x) {
		float temp = this.x;
		this.x = x;
		return temp;
	}
	
	public float setY(float y) {
		float temp = this.y;
		this.y = y;
		return temp;
	}
	
	public float setZ(float z) {
		float temp = this.z;
		this.z = z;
		return temp;
	}
	
	public Vector set(Vector v) {
		Vector temp = new Vector(this);
		setX(v.getX());
		setY(v.getY());
		setZ(v.getZ());
		return temp;
	}
	
	public boolean isDirection() {
		return isDirection;
	}
	
	public Vector directionalize() {
		this.isDirection = true;
		return this;
	}
	
	/**
	 * 
	 * @return new Vector rotated about z-axis
	 */
	public static Vector getPosFromAngle(Vector v, float f) {
		float cos = (float) Math.cos(f);
		float sin = (float) Math.sin(f);
		return new Vector(cos * v.getX() - sin * v.getY(), sin * v.getX() + cos * v.getY(), v.getZ());
	}

	public float magnitude() {
		return magnitude;
	}

	public void add(Vector v) {
		add(v.getX(), v.getY(), v.getZ());
	}

	public void add(float x, float y, float z) {
		this.x += x;
		this.y += y;
		this.z += z;
	}
	
	public Vector plus(Vector v) {
		return plus(v.getX(), v.getY(), v.getZ());
	}
	
	public Vector plus(float x, float y, float z) {
		return new Vector(this.x + x, this.y + y, this.z + z);
	}

	public void subtract(Vector v) {
		subtract(v.getX(), v.getY(), v.getZ());
	}

	public void subtract(float x, float y, float z) {
		this.x -= x;
		this.y -= y;
		this.z -= z;
	}
	
	public Vector minus(Vector v) {
		return minus(v.getX(), v.getY(), v.getZ());
	}
	
	public Vector minus(float x, float y, float z) {
		return new Vector(this.x - x, this.y - y, this.z - z);
	}

	public void multiply(double scalar) {
		this.x *= scalar;
		this.y *= scalar;
		this.z *= scalar;
	}

	public float angle(Vector v) {
		return (float) Math.acos(this.dot(v)
				/ (this.magnitude() * v.magnitude()));
	}

	public float dot(Vector v) {
		return dot(v.x, v.y, v.z);
	}

	public float dot(float x, float y, float z) {
		return this.x * x + this.y * y + this.z * z;
	}

	public Vector cross(Vector v) {
		return cross(v.getX(), v.getY(), v.getZ());
	}

	public Vector cross(float x, float y, float z) {
		return new Vector(this.y * z - this.z * y, this.z
				* x - this.x * z, this.x * y - this.y * x);
	}
	
	/**
	 * 
	 * @param theta: angle to rotate about y-axis
	 * @param phi: angle to rotate vertically
	 */
	public Vector rotate(float theta, float phi) {
		return rotate((float) Math.sin(theta), (float) Math.cos(theta), (float) Math.sin(phi), (float) Math.cos(phi));
	}
	
	public Vector rotate(Camera camera) {
		return rotate(camera.getSin(), camera.getCos(), camera.getYSin(), camera.getYCos());
	}
	
	/**
	 * 
	 * @param ts = Math.sin(theta)
	 * @param tc = Math.cos(theta)
	 * @param ps = Math.sin(phi)
	 * @param pc = Math.cos(phi)
	 * @return
	 */
	public Vector rotate(float ts, float tc, float ps, float pc) {
		return new Vector(x * tc + z * ts, y * pc - z * tc * ps + x * ts * ps, z * tc * pc - x * pc * ts + y * ps);
	}

	public Vector newVector() {
		return new Vector(newX, newY, newZ);
	}

	public Vector getOldVector() {
		float x = (float) (this.x * Director.screen.getCamera().camAngleCos + this.y * Director.screen.getCamera().camAngleSin
				* Director.screen.getCamera().camYAngleSin - this.z * Director.screen.getCamera().camAngleSin * Director.screen.getCamera().camYAngleCos);
		float y = (float) (this.y * Director.screen.getCamera().camYAngleCos + this.z * Director.screen.getCamera().camYAngleSin);
		float z = (float) (this.x * Director.screen.getCamera().camAngleSin - this.y * Director.screen.getCamera().camAngleCos
				* Director.screen.getCamera().camYAngleSin + this.z * Director.screen.getCamera().camAngleCos * Director.screen.getCamera().camYAngleCos);
		return Director.screen.getCamera().plus(x, y, z);
	}

	public void normalize() {
		if (magnitude == 0)
			setMag();
		this.multiply(1.0 / magnitude);
		setMag();
	}
	
	public String toString() {
		return "" + getX() + " " + getY() + " " + getZ();
	}
	
	public void staticUpdate() {
		setMag();
	}
	
	public void update() {
		staticUpdate();
		Vector newVector;
		if (isDirection)
			newVector = MathEngine.newDirection(this);
		else
			newVector = MathEngine.newVector(this);
		newX = newVector.getX();
		newY = newVector.getY();
		newZ = newVector.getZ();
		screenX = MathEngine.screenX(newX(), newZ());
		screenY = MathEngine.screenY(newY(), newZ());
	}

	public boolean isInFOV() {
		float sdistance = newX * newX + newY * newY + newZ * newZ;
		return !(newZ() < 0 || sdistance > MathEngine.square(Director.screen.getViewDistance()))
				&& (Math.abs(newX() / newZ()) < Graphics.WIDTH / 2d / Director.screen.getDepth()) && (Math
				.abs(newY() / newZ()) < Graphics.HEIGHT / 2d / Director.screen.getDepth()) && newZ() > 0;
	}
}
