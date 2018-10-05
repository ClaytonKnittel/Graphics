package shapes;

import graphics.Color;
import graphics.Drawable;

public abstract class Shape implements Drawable{

	private Color color;
	private boolean hasChanged;

	public Shape(Color c) {
		color = c;
		hasChanged = false;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color c) {
		color = c;
	}
	
	public boolean hasChanged() {
		return hasChanged;
	}
	
	public void change() {
		hasChanged = true;
	}
	
	public void updateChange() {
		hasChanged = false;
	}
	
	public abstract void update();
	public abstract boolean isInFOV();
}