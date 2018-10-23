package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Data {

	private static int nodeAmount, edgeAmount;

	private static int[][] nodeArray;
	private static int[][] edgeArray;
	private static File sourceFile = new File("src/stgtregbz.fmi");
	private static FileInputStream inputStream;
	private static Scanner scanner;

	public static void initializeArrays() {

		try {
			inputStream = new FileInputStream(sourceFile.getAbsolutePath());
			scanner = new Scanner(inputStream);
			// System.out.println(sourceFile.getAbsolutePath());

			skipScannerTokens(16);
			nodeAmount = scanner.nextInt();
			edgeAmount = scanner.nextInt();
			// System.out.println(nodeAmount + "\n" + edgeAmount);

			nodeArray = new int[nodeAmount][5];
			edgeArray = new int[edgeAmount][4];

			initializeNodeArray(nodeAmount);
			System.out.println("Nodes Finished!");

			initializeEdgeArray(edgeAmount);
			System.out.println("Edges Finished!");

			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void initializeNodeArray(int nodeAmount) {
		for (int ctr = 0; ctr < nodeAmount; ctr++) {
			nodeArray[ctr][0] = scanner.nextInt();
			skipScannerTokens(1);
			Double tempDouble = scanner.nextDouble();
			nodeArray[ctr][1] = tempDouble.intValue();
			nodeArray[ctr][2] = (int) ((tempDouble - tempDouble.intValue()) * 100000000);
			tempDouble = scanner.nextDouble();
			nodeArray[ctr][3] = tempDouble.intValue();
			nodeArray[ctr][4] = (int) ((tempDouble - tempDouble.intValue()) * 100000000);
			skipScannerTokens(1);

			/*
			 * System.out.println(nodeArray[ctr][0] + " " + nodeArray[ctr][1] + " " +
			 * nodeArray[ctr][2] + " " + nodeArray[ctr][3] + " " + nodeArray[ctr][4]);
			 */
		}
	}

	private static void initializeEdgeArray(int edgeAmount) {
		for (int ctr = 0; ctr < edgeAmount; ctr++) {
			edgeArray[ctr][0] = scanner.nextInt(); // unique startNode ID
			edgeArray[ctr][1] = scanner.nextInt(); // unique endNode ID
			edgeArray[ctr][2] = scanner.nextInt(); // edge weight
			edgeArray[ctr][3] = ctr; // unique edge ID
			skipScannerTokens(2);

			/*
			 * System.out.println( edgeArray[ctr][0] + " " + nodeArray[ctr][1] + " " +
			 * edgeArray[ctr][2] + " " + edgeArray[ctr][3]);
			 */
		}
	}

	private static void skipScannerTokens(int skips) {
		for (int ctr = 0; ctr < skips; ctr++) {
			scanner.next();
		}
	}
}
