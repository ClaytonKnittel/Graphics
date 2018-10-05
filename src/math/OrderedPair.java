package math;

public class OrderedPair {

	public float x, y;
	
	public OrderedPair(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public OrderedPair(OrderedPair o) {
		this.x = o.x;
		this.y = o.y;
	}
	
	public OrderedPair(int x) {
		this(x % 8, x / 8);
	}
	
	public int x() {
		return (int) x;
	}
	
	public int y() {
		return (int) y;
	}
	
	public float setX(float x) {
		float p = this.x;
		this.x = x;
		return p;
	}

	public float setY(float y) {
		float p = this.y;
		this.y = y;
		return p;
	}
	
	public void add(OrderedPair o) {
		add(o.x, o.y);
	}
	
	public void add(float x, float y) {
		this.x += x;
		this.y += y;
	}
	
	public OrderedPair plus(OrderedPair o) {
		return plus(o.x, o.y);
	}
	
	public OrderedPair plus(float x, float y) {
		return new OrderedPair(this.x + x, this.y + y);
	}
	
	public void swap(OrderedPair o) {
		setX(o.setX(x));
		setY(o.setY(y));
	}
	
	public boolean insideBoard() {
		return (int) x >= 0 && (int) x < 8 && (int) y >= 0 && (int) y < 8;
	}
	
	public String toString() {
		return x + " " + y;
	}
	
	public static OrderedPair toPos(int i) {
		return new OrderedPair(i % 8, i / 8);
	}
	
	public boolean equals(OrderedPair o) {
		return this.x == o.x && this.y == o.y;
	}
	
	/**
	 * 
	 * @param o: list of OrderedPairs to be ordered
	 * 
	 * Orders list in ascending order with preference to y
	 */
	public static void order(OrderedPair[] o) {
		if (o[0].y == o[1].y) {
			if (o[0].x > o[1].x) {
				o[0].swap(o[1]);
			}
		} else if (o[0].y > o[1].y) {
			o[0].swap(o[1]);
		}
		if (o[1].y == o[2].y) {
			if (o[1].x > o[2].x) {
				o[1].swap(o[2]);
			}
		} else if (o[1].y > o[2].y) {
			o[1].swap(o[2]);
		}
		if (o[0].y == o[1].y) {
			if (o[0].x > o[1].x) {
				o[0].swap(o[1]);
			}
		} else if (o[0].y > o[1].y) {
			o[0].swap(o[1]);
		}
	}
}
