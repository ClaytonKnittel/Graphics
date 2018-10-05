package shapes;

import director.Director;
import graphics.Color;
import math.MathEngine;
import math.Vector;





/**
 * The <code>Triangle</code> class stores all relevant information for 3D triangles
 * being drawn to the screen.
 * <p>
 * The <code>Vector</code> fields <code>one</code>, <code>two</code>, and <code>three</code>
 * are the positions of the three vertices of the <code>Triangle</code> in 3D space, with
 * respect to the origin of the coordinate system, which is left-handed. 
 * 
 * @author Clayton Knittel
 * @version 1.0
 * @see shapes.shape
 */
public class Triangle extends Shape {
	
	/**
	 * The <code>Vector</code>s corresponding to the positions of the three vertices of the <code>Triangle</code>
	 * 
	 * @serial
	 */
	private Vector one, two, three;
	
	/**
	 * The <code>Vector</code> extending from <code>one</code> to <code>two</code>
	 * with respect to the <code>Camera</code>
	 * in the <code>Screen</code> in the <code>Director</code>.
	 * 
	 * @serial
	 */
	private Vector ab;
	
	/**
	 * The <code>Vector</code> extending from <code>one</code> to <code>three</code>
	 * with respect to the <code>Camera</code> in the <code>Screen</code> in the <code>Director</code>.
	 * 
	 * @serial
	 */
	private Vector ac;
	
	/**
	 * The <code>Vector</code> normal to this <code>Triangle</code>. This is calculated
	 * with respect to the origin.
	 * 
	 * @serial
	 * @see #setNormal
	 */
	private Vector norm;
	
	/**
	 * The <code>Vector</code>s assigned to <code>one</code>, <code>two</code>, and
	 * <code>three</code>, respectively, that are normal to the surface that is being
	 * approximated by the mesh this <code>Triangle</code> is a part of.
	 * <p>
	 * Shared vertices in adjacent <code>Triangle</code>s typically have the same surface
	 * normal <code>Vectors</code>s. This is to create a smooth transition between adjacent
	 * <code>Triangle</code>s and make the object look more rounded.
	 * 
	 * @serial
	 * @see #one
	 * @see #two
	 * @see #three
	 */
	private Vector norm1, norm2, norm3;
	
	/**
	 * The float value assigned to this <code>Triangle</code> in each frame that represents
	 * how bright it is, due to surrounding light sources.
	 * 
	 * @serial
	 * @see #updateLightValue()
	 */
	private float lightVal;
	
	/**
	 * The float values assigned to each vertex of this <code>Triangle</code> (<code>one</code>,
	 * <code>two</code>, and <code>three</code>) that are light values dependent on <code>norm1</code>,
	 * <code>norm2</code>, and <code>norm3</code>. This is used to approximate a smooth surface rather
	 * than just a flat Triangle.
	 * 
	 * @serial
	 * @see #norm1
	 * @see #norm2
	 * @see #norm3
	 * @see #updateSurfaceNormLightValues()
	 */
	private float lightOne, lightTwo, lightThree;
	
	/**
	 * The float value of the area of this <code>Triangle</code> in space. This does not change
	 * unless <code>one</code>, <code>two</code>, or <code>three</code> does.
	 * 
	 * @serial
	 * @see #setArea()
	 */
	private float area;
	
	/**
	 * The value of one.dot(norm). Used to find the location of screen pixels in 3D space.
	 * 
	 * @serial
	 * @see #getLightColor(Vector)
	 */
	private float aNorm;
	
	
	/**
	 * Creates an instance of <code>Triangle</code> with the specified location, color, and surface
	 * normals.
	 * 
	 * @param one <code>Vector</code> for vertex <code>one</code> of this <code>Triangle</code>
	 * @param two <code>Vector</code> for vertex <code>two</code> of this <code>Triangle</code>
	 * @param three <code>Vector</code> for vertex <code>three</code> of this <code>Triangle</code>
	 * @param  c   <code>Color</code> assigned to this <code>Triangle</code>
	 * @param norm1 surface normal <code>Vector</code> to vertex <code>one</code>
	 * @param norm2 surface normal <code>Vector</code> to vertex <code>two</code>
	 * @param norm3 surface normal <code>Vector</code> to vertex <code>three</code>
	 */
	public Triangle(Vector one, Vector two, Vector three, Color c, Vector norm1, Vector norm2, Vector norm3) {
		super(c);
		this.one = new Vector(one);
		this.two = new Vector(two);
		this.three = new Vector(three);
		
		if (norm1 != null) {
			this.norm1 = norm1.directionalize();
			this.norm2 = norm2.directionalize();
			this.norm3 = norm3.directionalize();
		}
		
		super.change();
		staticUpdate();
	}
	
	/**
	 * Creates an instance of <code>Triangle</code> whose vertices are:
	 * <p>
	 * <code>one = new Vector(x1, y1, z1);</code>
	 * <p>
	 * <code>two = new Vector(x2, y2, z2);</code>
	 * <p>
	 * <code>three = new Vector(x3, y3, z3);</code>
	 * 
	 * @see #Triangle(Vector, Vector, Vector, Color, Vector, Vector, Vector)
	 */
	public Triangle(float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3, Color c, Vector norm1, Vector norm2, Vector norm3) {
		this(new Vector(x1, y1, z1), new Vector(x2, y2, z2), new Vector(x3, y3, z3), c, norm1, norm2, norm3);
	}
	
	/**
	 * Runs as main constructor, with null <code>norm1</code>, <code>norm2</code>, and <code>norm3</code>
	 * <code>Vector</code>s.
	 * 
	 * @see #Triangle(float, float, float, float, float, float, float, float, float, Color, Vector, Vector, Vector)
	 * @see #Triangle(Vector, Vector, Vector, Color, Vector, Vector, Vector)
	 */
	public Triangle(float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3, Color c) {
		this(x1, y1, z1, x2, y2, z2, x3, y3, z3, c, null, null, null);
	}
	
	/**
	 * Runs as main constructor, with null <code>norm1</code>, <code>norm2</code>, and <code>norm3</code>
	 * <code>Vector</code>s.
	 * 
	 * @see #Triangle(Vector, Vector, Vector, Color, Vector, Vector, Vector)
	 */
	public Triangle(Vector one, Vector two, Vector three, Color c1) {
		this(one, two, three, c1, null, null, null);
	}
	
	/**
	 * Creates a new instance of this <code>Triangle</code>
	 * 
	 * @param triangle the <code>Triangle</code> to duplicate
	 */
	public Triangle(Triangle triangle) {
		this(triangle.getV1(), triangle.getV2(), triangle.getV3(), triangle.getColor(),triangle.getNorm1(), triangle.getNorm2(), triangle.getNorm3());
	}
	
	/**
	 * Sets the color of this <code>Triangle</code> to red.
	 */
	public void red() {
		setColor(Color.RED);
	}
	
	/**
	 * Returns the position of the first vertex of this <code>Triangle</code> as a <code>Vector</code>.
	 * 
	 * @return <code>Vector</code> <code>one</code>
	 * 
	 * @see #one
	 */
	public Vector getV1() {
		return one;
	}
	
	/**
	 * Returns the position of the second vertex of this <code>Triangle</code> as a <code>Vector</code>.
	 * 
	 * @return <code>Vector</code> <code>two</code>
	 * 
	 * @see #two
	 */
	public Vector getV2() {
		return two;
	}
	
	/**
	 * Returns the position of the third vertex of this <code>Triangle</code> as a <code>Vector</code>.
	 * 
	 * @return <code>Vector</code> <code>three</code>
	 * 
	 * @see #three
	 */
	public Vector getV3() {
		return three;
	}

	/**
	 * @return the vector from <code>one</code> to <code>two</code>
	 * 
	 * @see #ab
	 */
	public Vector getAB() {
		return ab;
	}

	/**
	 * @return the vector from <code>one</code> to <code>three</code>
	 * 
	 * @see #ac
	 */
	public Vector getAC() {
		return ac;
	}
	
	/**
	 * @return <code>norm1</code>
	 * 
	 * @see #norm1
	 */
	public Vector getNorm1() {
		return norm1;
	}
	
	/**
	 * @return <code>norm2</code>
	 * 
	 * @see #norm2
	 */
	public Vector getNorm2() {
		return norm2;
	}
	
	/**
	 * @return <code>norm3</code>
	 * 
	 * @see #norm3
	 */
	public Vector getNorm3() {
		return norm3;
	}
	
	/**
	 * @return <code>lightOne</code>
	 * 
	 * @see #lightOne
	 */
	public float getLightOne() {
		return lightOne;
	}
	
	/**
	 * @return <code>lightTwo</code>
	 * 
	 * @see #lightTwo
	 */
	public float getLightTwo() {
		return lightTwo;
	}
	
	/**
	 * @return <code>lightThree</code>
	 * 
	 * @see #lightThree
	 */
	public float getLightThree() {
		return lightThree;
	}
	
	/**
	 * @return the x-coordinate of <code>Vector</code> <code>one</code> on the screen.
	 * 
	 * @see math.MathEngine#screenX(float, float)
	 */
	public float screenX1() {
		return one.screenX();
	}
	
	/**
	 * @return the y-coordinate of <code>Vector</code> <code>one</code> on the screen.
	 * 
	 * @see math.MathEngine#screenY(float, float)
	 */
	public float screenY1() {
		return one.screenY();
	}
	
	/**
	 * @return the x-coordinate of <code>Vector</code> <code>two</code> on the screen.
	 * 
	 * @see math.MathEngine#screenX(float, float)
	 */
	public float screenX2() {
		return two.screenX();
	}
	
	/**
	 * @return the y-coordinate of <code>Vector</code> <code>two</code> on the screen.
	 * 
	 * @see math.MathEngine#screenY(float, float)
	 */
	public float screenY2() {
		return two.screenY();
	}
	
	/**
	 * @return the x-coordinate of <code>Vector</code> <code>three</code> on the screen.
	 * 
	 * @see math.MathEngine#screenX(float, float)
	 */
	public float screenX3() {
		return three.screenX();
	}
	
	/**
	 * @return the y-coordinate of <code>Vector</code> <code>three</code> on the screen.
	 * 
	 * @see math.MathEngine#screenY(float, float)
	 */
	public float screenY3() {
		return three.screenY();
	}
	
	/**
	 * @return the normal <code>Vector</code> to this <code>Triangle</code>
	 * 
	 * @see #norm
	 */
	public Vector getNormal() {
		return norm;
	}
	
	/**
	 * @return the area of this <code>Triangle</code>
	 * 
	 * @see #area
	 */
	public float getArea() {
		return area;
	}
	
	/**
	 * @return the dot product of one and the normal <code>Vector</code>
	 * 
	 * @see #aNorm
	 * @see #one
	 * @see #norm
	 */
	public float getANorm() {
		return aNorm;
	}
	
	
	/**
	 * sets the surface normal to <code>one</code> to v
	 * 
	 * @param v the new surface normal <code>Vector</code> to <code>one</code>
	 * 
	 * @see #norm1
	 */
	public void setNorm1(Vector v) {
		this.norm1 = new Vector(v, true);
		super.change();
	}
	
	/**
	 * sets the surface normal to <code>two</code> to v
	 * 
	 * @param v the new surface normal <code>Vector</code> to <code>two</code>
	 * 
	 * @see #norm2
	 */
	public void setNorm2(Vector v) {
		this.norm2 = new Vector(v, true);
		super.change();
	}
	
	/**
	 * sets the surface normal to <code>three</code> to v
	 * 
	 * @param v the new surface normal <code>Vector</code> to <code>three</code>
	 * 
	 * @see #norm3
	 */
	public void setNorm3(Vector v) {
		this.norm3 = new Vector(v, true);
		super.change();
	}
	
	
	public Color getLightColor(int x, int y) { //TODO
		return getLightColor(getVector(x, y));
	}
	
	
	/**
	 * Calculates the color of a pixel in this <code>Triangle</code> using a linear interpolation
	 * of the three <code>Color</code>s of each vertex.
	 * <p>
	 * This comes from each vertex being assigned a different surface normal, so calculating the
	 * light value will result in different <code>Color</code>s being assigned to each vertex.
	 * <p>
	 * This is the method used to create a "smooth" looking, curved <code>Triangle</code>.
	 * 
	 * @param v the position in 3D space of the pixel on this <code>Triangle</code> we are finding
	 * the <code>Color</code> of
	 * 
	 * @return the <code>Color</code> of this pixel
	 * 
	 * @see #norm1
	 * @see #lightOne
	 */
	public Color getLightColor(Vector v) {
		if (norm1 == null) {
			return getLightColor();
		}
		//float l = (real1DotNorm - MathEngine.cX(x) * realNorm.getX() - MathEngine.cY(y) * realNorm.getY()) / realNorm.getZ();
		//return getColor().multiply(l);
		Vector x = v.minus(this.one.newVector());
		float A2 = get3DArea(ac, x);
		float A3 = get3DArea(ab, x);
		float A1 = Math.max(area - A2 - A3, 0);
		return getColor().multiply((lightOne * A1 + lightTwo * A2 + lightThree * A3) / (A1 + A2 + A3));
	}
	
	/**
	 * This method is used when no surface normals are speficied (<code>norm1</code>, <code>norm2</code>,
	 * and <code>norm3</code> are null).
	 * 
	 * @return the color of this <code>Triangle</code>, calculated using its lightValue.
	 * 
	 * @see #lightVal
	 */
	private Color getLightColor() {
		return getColor().multiply(lightVal);
	}
	
	public static float get3DArea(Vector a, Vector b) {
		return Vector.magnitude(b.cross(a)) / 2f;
	}

	public Vector getVector(int x, int y) {
		float xv = MathEngine.cX(x);
		float yv = MathEngine.cY(y);
		float z = (float) (Director.screen.getDepth() * aNorm
				/ norm.newVector().dot(xv, yv, (float) Director.screen.getDepth()));
		return new Vector((float) (z * (xv) / Director.screen.getDepth()),
				(float) (z * (yv) / Director.screen.getDepth()), z);
	}
	
	
	
	
	public void setNormal() {
		this.norm = new Vector(two.minus(one).cross(three.minus(one)), true);
		this.norm.multiply(1 / Vector.magnitude(norm));
	}
	
	public void setArea() {
		this.area = get3DArea(two.minus(one), three.minus(one));
	}
	
	
	

	public void subtract(Vector v) {
		subtract(v.getX(), v.getY(), v.getZ());
	}

	public void subtract(float x, float y, float z) {
		one.subtract(x, y, z);
		two.subtract(x, y, z);
		three.subtract(x, y, z);
	}
	
	public Triangle plus(Vector v) {
		return minus(-v.getX(), -v.getY(), -v.getZ());
	}

	public Triangle minus(Vector v) {
		return minus(v.getX(), v.getY(), v.getZ());
	}

	public Triangle minus(float x, float y, float z) {
		return new Triangle(one.minus(x, y, z), two.minus(x, y, z), three.minus(x, y, z), this.getColor(), this.getNorm1(), this.getNorm2(), this.getNorm3());
	}
	
	public void draw() {
		Director.drawer.drawTria(this);
	}
	
	public void staticUpdate() {
		setNormal();
		setArea();
	}

	public void update() {
		updateVectors();
		updateDirections();
		updateLightValue();
		updateSurfaceNormLightValues();
		super.updateChange();
	}
	
	private void updateVectors() {
		one.update();
		two.update();
		three.update();
		norm.update();
	}
	
	private void updateDirections() {
		this.ab = two.newVector().minus(one.newVector());
		this.ac = three.newVector().minus(one.newVector());
		this.aNorm = one.newVector().dot(norm.newVector());
	}
	
	private void updateLightValue() {
		lightVal = -norm.dot(Director.screen.getLightVector());
		if (lightVal < Director.screen.getAmbiantLight())
			lightVal = Director.screen.getAmbiantLight();
	}
	
	private void updateSurfaceNormLightValues() {
		if (norm1 != null) {
			this.lightOne = MathEngine.floor(-norm1.dot(Director.screen.getLightVector()), Director.screen.getAmbiantLight());
			this.lightTwo = MathEngine.floor(-norm2.dot(Director.screen.getLightVector()), Director.screen.getAmbiantLight());
			this.lightThree = MathEngine.floor(-norm3.dot(Director.screen.getLightVector()), Director.screen.getAmbiantLight());
		}
	}
	
	public String toString() {
		return getV1() + "\n" + getV2() + "\n" + getV3();
	}

	/**
	 * Draw triangles clockwise
	 */
	public boolean isInFOV() {
		if (norm == null)
			update();
		if (norm.newVector().dot(one.newVector()) >= 0) // back face culling
			return false;
		return one.newZ() >= 0 && two.newZ() >= 0 && three.newZ() >= 0;
	}
}
