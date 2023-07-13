import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class ThreadClass {
	private static final int THREADS = 5, VALUES = 1000, MIN = 0, MAX = 1000;
	private static final String File = "numbers.txt";

	public static void main(String[] args) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File(File)))) {
			Random random = new Random();
			for (int i = 0; i < VALUES; i++) {
				int value = (int)(random.nextDouble() * ((MAX - MIN) + 1)) + MIN;
				bw.write(Integer.toString(value));
				bw.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		int[] values = new int[VALUES];
		try (Scanner scanner = new Scanner(new File(File))) {
			for (int i = 0; i < VALUES; i++) {
				values[i] = scanner.nextInt();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		long startTime = System.currentTimeMillis();
		int minVal1 = multithreaded(values, THREADS);
		long endTime = System.currentTimeMillis();
		long elapsedTime = endTime - startTime;
		System.out.println("Minimum value using multithreading is " + minVal1+" found in " + elapsedTime + "ms");

		startTime = System.currentTimeMillis();
		int minVal2 = singleThreaded(values);
		endTime = System.currentTimeMillis();
		elapsedTime = endTime - startTime;
		System.out.println("Minimum value without using multithreading is " + minVal2+" found in " + elapsedTime + "ms");
	}

	private static int multithreaded(int[] values, int numThreads) {
		  int minValue = Integer.MAX_VALUE;
		  MinVal[] threads = new MinVal[numThreads];

		  int chunkSize = values.length / numThreads;
		  for (int i = 0; i < numThreads; i++) {
		    int startIndex = i * chunkSize;
		    int endIndex = startIndex + chunkSize;
		    if (i == numThreads - 1) {
		      endIndex = values.length;
		    }
		    threads[i] = new MinVal(values, startIndex, endIndex);
		    threads[i].start();
		  }

		  try {
		    for (MinVal thread : threads) {
		      thread.join();
		      minValue = Math.min(minValue, thread.getMin());
		    }
		  } catch (InterruptedException e) {
		    e.printStackTrace();
		  }
		  return minValue;
		}

	private static int singleThreaded(int[] values) {
		int minValue = Integer.MAX_VALUE;
		for (int value : values) {
			minValue = Math.min(minValue, value);
		}
		return minValue;
	}
}
