package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import director.Director;

public class InputHandle implements KeyListener {
	
	public static final float SPEED = 100;							//movements per second
	public static final float TURNSPEED = (float) (Math.PI / 10);	//radians per second
	
	public InputHandle() {
	}

	public void printStatus() {
		System.out.println("W " + up.pressed + "\nA " + left.pressed + "\nS " + down.pressed + "\nD " + right.pressed + "\nShift " + shift.pressed);
	}

	public class Key {
		private int numTimesPressed = 0;
		private boolean pressed = false;

		public int getNumTimesPressed() {
			return numTimesPressed;
		}

		public boolean isPressed() {
			return pressed;
		}

		public void toggle(boolean isPressed) {
			pressed = isPressed;
			if (pressed) {
				numTimesPressed++;
			}
		}
	}
	
	public synchronized void update() {
		if (shift.isPressed()) {
			if (up.isPressed()) {
				Director.screen.getCamera().add(0, Director.graphics.getTimeBetweenFrames() / 1000f * SPEED, 0);
			}
			if (down.isPressed()) {
				Director.screen.getCamera().add(0, -Director.graphics.getTimeBetweenFrames() / 1000f * SPEED, 0);
			}
			if (left.isPressed())
				Director.screen.getCamera().move((float) (-Director.graphics.getTimeBetweenFrames()
						/ 1000f * SPEED * Math.cos(Director.screen.getCamera().getCamAngle())),
						0, (float) (-Director.graphics.getTimeBetweenFrames()
								/ 1000f * SPEED * Math.sin(Director.screen.getCamera().getCamAngle())));
			if (right.isPressed())
				Director.screen.getCamera().move((float) (Director.graphics.getTimeBetweenFrames()
						/ 1000f * SPEED * Math.cos(Director.screen.getCamera().getCamAngle())), 0,
						(float) (Director.graphics.getTimeBetweenFrames()
								/ 1000f * SPEED * Math.sin(Director.screen.getCamera().getCamAngle())));
		} else {
			if (up.isPressed()) {
				Director.screen.getCamera().move((float) (-Director.graphics.getTimeBetweenFrames()
						/ 1000f * SPEED * Math.sin(Director.screen.getCamera().getCamAngle())), 0,
								(float) (Director.graphics.getTimeBetweenFrames()
										/ 1000f * SPEED * Math.cos(Director.screen.getCamera().getCamAngle())));
			}
			if (down.isPressed()) {
				Director.screen.getCamera().move((float) (Director.graphics.getTimeBetweenFrames()
						/ 1000f * SPEED * Math.sin(Director.screen.getCamera().getCamAngle())),
						0, -(float) (Director.graphics.getTimeBetweenFrames()
								/ 1000f * SPEED * Math.cos(Director.screen.getCamera().getCamAngle())));
			}
			if (left.isPressed())
				Director.screen.getCamera().turn((float) Director.graphics.getTimeBetweenFrames() / 1000f * TURNSPEED);
			if (right.isPressed())
				Director.screen.getCamera().turn((float) -Director.graphics.getTimeBetweenFrames() / 1000f * TURNSPEED);
		}
		if (k.isPressed())
			Director.screen.getCamera().yTurn((float) Director.graphics.getTimeBetweenFrames() / 1000f * TURNSPEED);
		if (m.isPressed())
			Director.screen.getCamera().yTurn((float) -Director.graphics.getTimeBetweenFrames() / 1000f * TURNSPEED);
	}

	public Key up = new Key();
	public Key down = new Key();
	public Key left = new Key();
	public Key right = new Key();
	public Key shift = new Key();
	public Key k = new Key();
	public Key m = new Key();

	public synchronized void keyPressed(KeyEvent e) {
		toggleKey(e.getKeyCode(), true);
	}

	public synchronized void keyReleased(KeyEvent e) {
		toggleKey(e.getKeyCode(), false);
	}

	public synchronized void keyTyped(KeyEvent e) {
	}

	public void toggleKey(int keyCode, boolean isPressed) {
		if (keyCode == KeyEvent.VK_W || keyCode == 'w') {
			up.toggle(isPressed);
		}
		if (keyCode == KeyEvent.VK_S || keyCode == 's') {
			down.toggle(isPressed);
		}
		if (keyCode == KeyEvent.VK_A || keyCode == 'a') {
			left.toggle(isPressed);
		}
		if (keyCode == KeyEvent.VK_D || keyCode == 'd') {
			right.toggle(isPressed);
		}
		if (keyCode == KeyEvent.VK_SHIFT) {
			shift.toggle(isPressed);
		}
		if (keyCode == KeyEvent.VK_K || keyCode == 'k') {
			k.toggle(isPressed);
		}
		if (keyCode == KeyEvent.VK_M || keyCode == 'm') {
			m.toggle(isPressed);
		}
	}
}
