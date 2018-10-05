package graphics;

import java.util.ArrayList;

import director.Director;
import math.Vector;
import shapes.Shape;
import shapes.Triangle;

public class EmissiveLight {

	private Vector pos;
	@SuppressWarnings("unused")
	private float brightness;
	private ArrayList<Triangle> obj = new ArrayList<Triangle>();
	private ArrayList<Vector[]> objNorm = new ArrayList<Vector[]>();

	public EmissiveLight(Vector pos, float brightness) {
		this.pos = pos;
		this.brightness = brightness;
		set();
	}

	public Vector getPos() {
		return pos;
	}

	public float getLightValue(Vector v, Vector normal) {
		v.subtract(pos);
		if (this.isInShadow(v)) {
			return Director.screen.getAmbiantLight();
		} else {
			float cons = (float) Math.abs(v.dot(normal));
			// float mag = v.minus(pos).magnitude();
			// if ((brightness - mag) * cons / brightness < Rasterer.ambiantLight)
			// return Rasterer.ambiantLight;
			// else
			// return ((brightness - mag) * cons / brightness);
			return cons;
			/// return 1;
		}
	}

	private boolean isInShadow(Vector v) {
		for (int x = 0; x < objNorm.size(); x++) {
			Vector[] a = objNorm.get(x);
			if (v.dot(a[0]) >= 0) {
				if (v.dot(a[1]) >= 0) {
					if (v.dot(a[2]) >= 0) {
						if (v.minus(obj.get(x).getV1()).dot(a[3]) < -0.1)
							return false;
					}
				}
			}
		}
		return false;
	}

	public void set() {
		for (Shape s : Director.world.getObjects()) {
			if (s instanceof Triangle) {
				/*obj.add(((Triangle) s).minus(pos));
				Triangle t = obj.get(obj.size() - 1);
				Vector norm1 = t.getNorm1();
				Vector norm2 = t.getNorm2();
				Vector norm3 = t.getNorm3();
				Vector norm = t.getNormal();
				if (norm.dot(t.getV1()) > 0)
					norm.multiply(-1);
				objNorm.add(new Vector[] { norm1, norm2, norm3, norm });*/
			}
		}
	}
}