package Exercise9;

public class Part3 {
	private static int x, y, z;

	public static void main(String[] args) {
		long startTime = System.nanoTime();
		Thread t1 = new MyThread();
		Thread t2 = new MyThread();
		t1.start();
		t2.start();
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		printValues();
		long endTime = System.nanoTime();
		System.out.println("Computation took " + ((endTime - startTime) / 1000000) + " milliseconds");
	}

	public synchronized static void f() {
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

class MyThread extends Thread{
	public void run() {
		for (int i = 0; i < 100000000; i++) {
			Part3.f();
		}
	}
}
