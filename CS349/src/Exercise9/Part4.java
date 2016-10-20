package Exercise9;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Part4 {
	static int totalSum = 0;
	private Integer rows = 3; //default rows
	int[][] matrix;

	public Part4() {
		init();
	}

	public Part4(Integer rows) {
		this.rows = rows;
		init();
	}

	public void init() {
		Random rand = new Random();
		matrix = new int[rows][10000000];
		// Initialize matrix with random numbers
		for (int x = 0; x < matrix.length; x++) {
			for (int y = 0; y < matrix[x].length; y++) {
				int randomNum = rand.nextInt(200); // 0 - 199
				matrix[x][y] = randomNum;
			}
		}
	}

	public static void main(String[] args) {
		Part4 p = new Part4();
		p.nonThread();
		p.thread();
	}

	public void nonThread() {
		long startTime = System.nanoTime();
		int sum = 0;
		for (int x = 0; x < matrix.length; x++) {
			for (int y = 0; y < matrix[x].length; y++) {
				sum = sum + (int) Math.log(matrix[x][y]);
			}
		}
		long endTime = System.nanoTime();
		System.out.println("One thread sum: " + sum);
		System.out.println("Computation took " + ((endTime - startTime) / 1000000) + " milliseconds");
	}

	public void thread() {
		List<Thread> threads = new ArrayList<Thread>();
		long startTime = System.nanoTime();

		for (int[] row : matrix) {
			Thread t = new Thread() {
				public void run() {
					int rowSum = 0;
					for (int y = 0; y < row.length; y++) {
						rowSum = rowSum + (int) Math.log(row[y]);
					}
					addSums(rowSum);
				}
			};
			t.start();
			threads.add(t);
		}
		for (int i = 0; i < threads.size(); i++) {
			try {
				((Thread) threads.get(i)).join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		long endTime = System.nanoTime();
		System.out.println("Multi thread sum: " + totalSum);
		System.out.println("Computation took " + ((endTime - startTime) / 1000000) + " milliseconds");
	}

	public synchronized static void addSums(int sum) {
		totalSum += sum;
	}
}
