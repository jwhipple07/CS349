package Exercise9;

public class Part1 {
	private static int x, y, z;

	public static void main(String[] args) {
		long startTime = System.nanoTime();
		Thread t1 = new Thread("New Thread") {
			public void run() {
				for (int i = 0; i < 200000000; i++) {
					f();
				}
				printValues();
			}
		};

		t1.start();
		try {
			t1.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long endTime = System.nanoTime();
		System.out.println("Computation took " + ((endTime - startTime) / 1000000) + " milliseconds");
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
