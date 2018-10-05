package inputs;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

import director.Director;

public class IsKeyPressed {
    private static volatile boolean wPressed = false;
    private static volatile boolean aPressed = false;
    private static volatile boolean sPressed = false;
    private static volatile boolean dPressed = false;
    public static boolean isWPressed() {
        synchronized (IsKeyPressed.class) {
            return wPressed;
        }
    }
    public static boolean isAPressed() {
        synchronized (IsKeyPressed.class) {
            return aPressed;
        }
    }
    public static boolean isSPressed() {
        synchronized (IsKeyPressed.class) {
            return sPressed;
        }
    }
    public static boolean isDPressed() {
        synchronized (IsKeyPressed.class) {
            return dPressed;
        }
    }
    
    public static void update() {
		/*//if (shift.isPressed()) {
			if (IsKeyPressed.isWPressed()) {
				screen.getCamera().add(0, 5, 0);
			}
			if (IsKeyPressed.isSPressed()) {
				screen.getCamera().add(0, -5, 0);
			}
			if (IsKeyPressed.isAPressed())
				screen.getCamera().move((float) (-5 * Math.cos(screen.getCamera().getCamAngle())),
						0, (float) (-5 * Math.sin(screen.getCamera().getCamAngle())));
			if (IsKeyPressed.isDPressed())
				screen.getCamera().move((float) (5 * Math.cos(screen.getCamera().getCamAngle())), 0,
						(float) (5 * Math.sin(screen.getCamera().getCamAngle())));
		//} else {
			if (IsKeyPressed.isWPressed()) {
				screen.getCamera().move((float) (-5 * Math.sin(screen.getCamera().getCamAngle())), 0,
								(float) (5 * Math.cos(screen.getCamera().getCamAngle())));
			}
			if (IsKeyPressed.isSPressed()) {
				screen.getCamera().move((float) (5 * Math.sin(screen.getCamera().getCamAngle())),
						0, -(float) (5 * Math.cos(screen.getCamera().getCamAngle())));
			}
			if (IsKeyPressed.isAPressed())
				screen.getCamera().turn((float) Math.PI / 128);
			if (IsKeyPressed.isDPressed())
				screen.getCamera().turn((float) -Math.PI / 128);
		//}
		if (k.isPressed())
			screen.getCamera().yTurn((float) Math.PI / 64);
		if (m.isPressed())
			screen.getCamera().yTurn((float) -Math.PI / 64);*/
		if (IsKeyPressed.isWPressed()) {
			Director.screen.getCamera().yTurn((float) (-Math.PI / 128));
		}
		if (IsKeyPressed.isSPressed()) {
			Director.screen.getCamera().yTurn((float) (Math.PI / 128));
		}
		if (IsKeyPressed.isAPressed())
			Director.screen.getCamera().turn((float) (-Math.PI / 128));
		if (IsKeyPressed.isDPressed())
			Director.screen.getCamera().turn((float) (Math.PI / 128));
		float distance = 100;
		Director.screen.getCamera().setX((float) (distance * Math.sin(Director.screen.getCamera().getCamAngle()) * Math.cos(Director.screen.getCamera().getCamYAngle())));
		Director.screen.getCamera().setY((float) (distance * -Math.sin(Director.screen.getCamera().getCamYAngle())) + 25);
		Director.screen.getCamera().setZ((float) (distance * -Math.cos(Director.screen.getCamera().getCamAngle()) * Math.cos(Director.screen.getCamera().getCamYAngle())));
    }
	
	public static void init() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
        	
        	
            @Override
            public boolean dispatchKeyEvent(KeyEvent ke) {
                synchronized (IsKeyPressed.class) {
                    switch (ke.getID()) {
                    case KeyEvent.KEY_PRESSED:
                        if (ke.getKeyCode() == KeyEvent.VK_W) {
                            wPressed = true;
                        }
                        if (ke.getKeyCode() == KeyEvent.VK_A) {
                            aPressed = true;
                        }
                        if (ke.getKeyCode() == KeyEvent.VK_S) {
                            sPressed = true;
                        }
                        if (ke.getKeyCode() == KeyEvent.VK_D) {
                            dPressed = true;
                        }
                        break;

                    case KeyEvent.KEY_RELEASED:
                        if (ke.getKeyCode() == KeyEvent.VK_W) {
                            wPressed = false;
                        }
                        if (ke.getKeyCode() == KeyEvent.VK_A) {
                            aPressed = false;
                        }
                        if (ke.getKeyCode() == KeyEvent.VK_S) {
                            sPressed = false;
                        }
                        if (ke.getKeyCode() == KeyEvent.VK_D) {
                            dPressed = false;
                        }
                        break;
                    }
                    return false;
                }
            }
        });
	}
}