package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

class LineReaderD2 extends Thread {
	
	private int toSkip, nr, mCtr;
	private int[] sources, targets, weights;

	private Scanner scanner;
	
	public LineReaderD2(int nodeAmount, int edgeAmount, int[] sources, int[] targets, int[] weights, String path, int nr) {
		this.toSkip = nodeAmount + 7 + nr;
		this.nr = nr;
		
		this.sources = sources;
		this.targets = targets;
		this.weights = weights;

		try {
			scanner = new Scanner(new BufferedReader(new FileReader(new File(path).getAbsolutePath())));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		
		mCtr = 0;
		
		for (int ctr = 0; ctr < toSkip; ctr++)
			scanner.nextLine();
		
		while (scanner.hasNext()) {
			mCtr = nr % 3;
			
			if (mCtr == 0) {
				sources[nr] = scanner.nextInt();
				targets[nr] = scanner.nextInt();
				weights[nr] = scanner.nextInt();
			}
				
			nr++;
			scanner.nextLine();
		}
		
		Data2.finished();
	}
}

public class Data2 {
	
	private final static String path = "src/germany.fmi";
	
	private static boolean reading;
	private static int nodeAmount, edgeAmount, thisNode, lastNode, nrsFinished;
	private static int[] edgeOffset, sources, targets, weights;

	private static Scanner scanner;

	public static void read() {
		
		time.Stopwatch.stamp();
		
		try {
			scanner = new Scanner(new BufferedReader(new FileReader(new File(path).getAbsolutePath())));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		for (int ctr = 0; ctr < 4; ctr++)
			scanner.nextLine();

		nodeAmount = scanner.nextInt();
		edgeAmount = scanner.nextInt();

		scanner.nextLine();

		edgeOffset = new int[nodeAmount];
		sources = new int[edgeAmount];
		targets = new int[edgeAmount];
		weights = new int[edgeAmount];
		
		LineReaderD2 nr1 = new LineReaderD2(nodeAmount, edgeAmount, sources, targets, weights, path, 0);
		LineReaderD2 nr2 = new LineReaderD2(nodeAmount, edgeAmount, sources, targets, weights, path, 1);
		LineReaderD2 nr3 = new LineReaderD2(nodeAmount, edgeAmount, sources, targets, weights, path, 2);
		
		nr3.start();
		nr2.start();
		nr1.start();
		
		reading = true;

		for (int ctr = 0; ctr < nodeAmount; ctr++)
			scanner.nextLine();

		lastNode = -1;
		for (int ctr = 0; ctr < edgeAmount; ctr++) {
			thisNode = scanner.nextInt();
			scanner.nextLine();
			
			if (lastNode != thisNode)
				edgeOffset[thisNode] = ctr;
			
			lastNode = thisNode;
		}
		
		try {
			nr1.join();
			nr2.join();
			nr3.join();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		time.Stopwatch.stamp();
	}
	
	public static void finished() {
		nrsFinished++;
		if (nrsFinished == 3)
			reading = false;
	}

	public static int[] getEdgeOffset() {
		return edgeOffset;
	}

	public static int[] getSources() {
		return sources;
	}

	public static int[] getTargets() {
		return targets;
	}

	public static int[] getWeights() {
		return weights;
	}

	public static void testData() {
		if (sources[0] == 0 && targets[0] == 1488520 && weights[0] == 147) {
			System.out.println("edgeArray setup correctly!");
		}
		if (edgeOffset[0] == 0 && edgeOffset[4] == 7) {
			System.out.println("edgeOffsetArray setup correctly!");
		}
	}
}