package Exercise9;

public class Part2 {
	private static int x, y, z;

	public static void main(String[] args) {
		Thread t1 = new MyThreadUnSync();
		Thread t2 = new MyThreadUnSync();
		t1.start();
		t2.start();
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void f() {
		x = x + 1;
		y = y + 1;
		z = z + x - y;
	}

	public static void printValues() {
		System.out.println("x = " + x);
		System.out.println("y = " + y);
		System.out.println("z = " + z);
	}
}
class MyThreadUnSync extends Thread{
	public void run() {
		long startTime = System.nanoTime();
		for (int i = 0; i < 100000000; i++) {
			Part2.f();
		}
		Part2.printValues();
		long endTime = System.nanoTime();
		System.out.println("Computation took " + ((endTime - startTime) / 1000000) + " milliseconds");
	}
}