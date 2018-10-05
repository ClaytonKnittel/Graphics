package shapes;

import math.Vector;

public class Camera extends Vector {
	
	private float camAngle;
	private float camYAngle;
	public float camAngleSin;
	public float camAngleCos;
	public float camYAngleSin;
	public float camYAngleCos;

	public Camera(float x, float y, float z, float camAngle, float camYAngle) {
		super(x, y, z);
		this.camAngle = camAngle;
		this.camYAngle = camYAngle;
		update();
	}
	
	public Camera(float x, float y, float z) {
		this(x, y, z, 0, 0);
	}
	
	public void update() {
		camAngleSin = (float) Math.sin(camAngle);
		camAngleCos = (float) Math.cos(camAngle);
		camYAngleSin = (float) Math.sin(camYAngle);
		camYAngleCos = (float) Math.cos(camYAngle);

		if (camAngle < -Math.PI) {
			camAngle += Math.PI * 2;
		}
		if (camAngle >= Math.PI) {
			camAngle -= Math.PI * 2;
		}
		if (camYAngle < -Math.PI) {
			camYAngle += Math.PI * 2;
		}
		if (camYAngle >= Math.PI) {
			camYAngle -= Math.PI * 2;
		}
	}
	
	public float getCamAngle() {
		return camAngle;
	}
	
	public float getCamYAngle() {
		return camYAngle;
	}
	
	public float getSin() {
		return camAngleSin;
	}
	
	public float getCos() {
		return camAngleCos;
	}
	
	public float getYSin() {
		return camYAngleSin;
	}
	
	public float getYCos() {
		return camYAngleCos;
	}
	
	public Vector newVector(Vector v) {
		return v.minus(this).rotate(this);
	}
	
	public Vector newDirection(Vector v) {
		return v.rotate(this);
	}
	
	public void turn(float t) {
		camAngle += t;
	}
	
	public void yTurn(float t) {
		camYAngle += t;
	}
	
	public void move(float x, float y, float z) {
		this.add(x, y, z);
	}
}
