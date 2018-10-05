package graphics;

public class Time {

	private static long time;
	
	public static void set() {
		time = new Long(System.nanoTime());
	}
	
	public static void get() {
		int t = (int) (System.nanoTime() - time);
		if (t > 0)
			System.out.println(t);
		time = new Long(System.nanoTime());
	}
}
