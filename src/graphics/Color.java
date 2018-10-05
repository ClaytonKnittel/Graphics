package graphics;

public class Color {

	private int color;
	
	public static final Color RED = new Color(255, 0, 0);
	public static final Color BLUE = new Color(0, 0, 255);
	public static final Color GREEN = new Color(0, 255, 0);
	public static final Color YELLOW = new Color(255, 255, 0);
	
	public static final Color BLACK = new Color(0, 0, 0);
	public static final Color WHITE = new Color(255, 255, 255);
	
	public static final Color DEEP_SKY_BLUE = new Color(0, 191, 255);
	public static final Color SKY_BLUE = new Color(135, 206, 250);
	
	public static final Color FOREST_GREEN = new Color(34, 139, 34);
	public static final Color DARK_GREEN = new Color(0, 100, 0);
	
	public static final Color WOODEN_BROWN = new Color(205, 170, 125);
	public static final Color PIECE_BLACK = new Color(205, 133, 63);
	public static final Color PIECE_WHITE = new Color(255, 228, 196);

	public static final Color SOFT_WHITE = new Color(240, 240, 230);
	public static final Color SOFT_BLACK = new Color(41, 36, 33);
	

	public Color(int r, int g, int b) {
		color = r << 16 | g << 8 | b;
	}

	public Color(int c) {
		color = c;
	}

	public int getColor() {
		return color;
	}

	public int getR() {
		return (int) color >> 16;
	}

	public int getG() {
		return (int) (color >> 8) & 255;
	}

	public int getB() {
		return (int) color & 255;
	}
	
	public void setRGB(Color c) {
	    color = c.getColor();
	}
	
	public void setRGB(int c) {
	    color = c;
	}
	
	public String toString() {
		return "" + getR() + " " + getG() + " " + getB() + "\n";
	}

	public Color add(Color c) {
		return new Color((this.getR() + c.getR()) << 16 | (this.getG() + c.getG()) << 8
				| (this.getB() + c.getB()));
	}

	public int subtract(Color c) {
		return (this.getR() - c.getR()) << 16 | (this.getG() - c.getG()) << 8
				| (this.getB() - c.getB());
	}

	public Color multiply(float s) {
		if (s > 1) s = 1;
		return new Color((int) (this.getR() * s) << 16 | (int) (this.getG() * s) << 8
				| (int) (this.getB() * s));
	}

	public int divide(float s) {
		return (int) (this.getR() / s) << 16 | (int) (this.getG() / s) << 8
				| (int) (this.getB() / s);
	}
	
	public Color inverse() {
		return new Color(255 - this.getR(), 255 - this.getG(), 255 - this.getB());
	}
}
