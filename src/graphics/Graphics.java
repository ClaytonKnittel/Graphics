package graphics;

import java.awt.Dimension;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import director.Director;

public class Graphics extends JFrame implements Runnable {
	private static final long serialVersionUID = 1L;
	
	public static final float SCALE = 2f;			// (2880f / 1680) is pixels per number input to JFrame
	

	private int millisSinceLastFrame[];
	
	private boolean debug;
	
	public Graphics(boolean debug) {
		this.debug = debug;
		millisSinceLastFrame = new int[5];

		setMinimumSize(new Dimension((int) (Director.screen.getWidth() / SCALE), (int) (Director.screen.getHeight() / SCALE)));
		setMaximumSize(new Dimension((int) (Director.screen.getWidth() / SCALE), (int) (Director.screen.getHeight() / SCALE)));
		setPreferredSize(new Dimension((int) (Director.screen.getWidth() / SCALE), (int) (Director.screen.getHeight() / SCALE)));
		
		
		this.setTitle("cube");
		setFocusTraversalKeysEnabled(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setFocusable(true);
		setVisible(true);
		
		//update();
		Thread tr = new Thread(this);
		tr.start();
	}

	public Graphics() {
		this(false);
	}
	
	public void update() {
		//screen.addTime(0.1f);
		Director.screen.update();
		Director.world.update();
		Director.input.update();
		//IsKeyPressed.update(screen);
	}

	public void run() {
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000D / 60D;

		int frames = 0;
		int ticks = 0;

		long lastTimer = System.currentTimeMillis();
		int since = 0;
		double delta = 0;
		boolean shouldRender = false;
		
		try { Thread.sleep(100); } catch(Exception e) {}
		//render();
		while (true) {
			update();
			
			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			lastTime = now;

			shouldRender = false;

			while (delta >= 1) {
				ticks++;
				delta--;
				shouldRender = true;
			}
			
			if (shouldRender) {
				frames++;
				render();
			}

			int wait = (int) (lastTimer + since + Director.screen.getMillisPerFrame() - System.currentTimeMillis());
			if (wait > 0) {
				try { Thread.sleep(wait); } catch(Exception e) {}
			}
			add((int) (System.currentTimeMillis() - since - lastTimer));
			if (System.currentTimeMillis() - lastTimer > 1000) {
				lastTimer += 1000;
				if (debug)
					printUpdate(ticks, frames);
				frames = 0;
				ticks = 0;
			}
			since = (int) (System.currentTimeMillis() - lastTimer);
		}
	}
	
	public void add(int n) {
		for (int x = 0; x < millisSinceLastFrame.length - 1; x++) {
			millisSinceLastFrame[x] = millisSinceLastFrame[x + 1];
		}
		millisSinceLastFrame[millisSinceLastFrame.length - 1] = n;
	}
	
	public static void printUpdate(int ticks, int frames) {
		System.out.println(ticks + " ticks, " + frames
				+ " frames camPos: " + (int) Director.screen.getCamera().getX() + " "
				+ (int) Director.screen.getCamera().getY() + " " + (int) Director.screen.getCamera().getZ()
				+ " " + Director.screen.getCamera().getCamAngle() + " " + Director.screen.getCamera().getCamYAngle());
	}
	
	public int getTimeBetweenFrames() {
		int tot = 0;
		for (int n : millisSinceLastFrame)
			tot += n;
		return tot / millisSinceLastFrame.length;
	}
	

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(2);
			return;
		}
		Director.screen.paint(bs);
	}
}