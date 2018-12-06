package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import utility.Utility;

public class Data {

	private static int nodeAmount, edgeAmount;
	private static File sourceFile;
	private static Scanner scanner;
	
	private ArrayList<String[]> tempNodeData, tempEdgeData;

	public static int[] nodeOffset; // length of nodeAmount
	public static double[] xLatitude, yLatitude; // length of nodeAmount, sorted by unique nodeID by array index
	public static int[] startNodeID, endNodeID, edgeValue; // length of edgeAmount, sorted by unique edgeID as array
															// index

	/**
	 * This method returns the filepath of the sourcefile.
	 * 
	 * @return
	 */
	private static File readFilePath() {
		sourceFile = new File("src/stgtregbz.fmi");
		return sourceFile;
	}

	/**
	 * This method skips the next line for a given amount of times for a given
	 * scanner.
	 * 
	 * @param skips
	 * @param scanner
	 */
	private static void scannerSkipLines(int skips, Scanner scanner) {
		for (int ctr = 0; ctr < skips; ctr++) {
			scanner.nextLine();
		}
	}

	public static void initialize() {
		// initializing the scanner
		try {
			scanner = new Scanner(new BufferedReader(new FileReader(readFilePath().getAbsolutePath())));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// utilizing the scanner, the amount of nodes + edges are read
		scannerSkipLines(4, scanner);
		nodeAmount = scanner.nextInt();
		edgeAmount = scanner.nextInt();
		scanner.nextLine();

		// initializing the arrays to be used with the amount of nodes and edges
		nodeOffset = new int[nodeAmount];
		xLatitude = new double[nodeAmount];
		yLatitude = new double[nodeAmount];

		startNodeID = new int[edgeAmount];
		endNodeID = new int[edgeAmount];
		edgeValue = new int[edgeAmount];

		System.out.println("Initialized and ready to rumble!");
		// System.out.println(nodeAmount + " " + edgeAmount);
	}

	public static void readAndWrite() {
		Utility.startTimer();
		readNodes();
		readEdges();
		createOffset();
		System.out.println("Finished in: " + Utility.endTimer() + " seconds!");
	}

	/**
	 * This method extrapolates all node data needed to run the dijkstra algorithm.
	 */
	private static void readNodes() {
		String[] tempArray = null;
		for (int ctr = 0; ctr < nodeAmount; ctr++) {
			tempArray = scanner.nextLine().split(" ");
			xLatitude[ctr] = Double.parseDouble(tempArray[2]);
			yLatitude[ctr] = Double.parseDouble(tempArray[3]);
		}
		// System.out.println("Reading and writing nodes finished");
	}

	/**
	 * This method extrapolates all edge data needed to run the dijkstra algorithm.
	 */
	private static void readEdges() {
		String[] tempArray = null;
		for (int ctr = 0; ctr < edgeAmount; ctr++) {
			tempArray = scanner.nextLine().split(" ");
			startNodeID[ctr] = Integer.parseInt(tempArray[0]);
			endNodeID[ctr] = Integer.parseInt(tempArray[1]);
			edgeValue[ctr] = Integer.parseInt(tempArray[2]);
		}
		// System.out.println("Reading and writing edges finished");
	}

	/**
	 * This method creates the offset-table used for faster access to the
	 * startNodeIDs.
	 */
	private static void createOffset() {
		int index = -1;
		for (int i = 0; i < edgeAmount; i++) {
			if (startNodeID[i] != index) {
				index = startNodeID[i];
				nodeOffset[index] = i;
			}
		}
		// System.out.println("Offset created!");
	}

	public static int[] getOffsetArray() {
		return nodeOffset;
	}

	public static int[] getStartNodeIDArray() {
		return startNodeID;
	}

	public static int[] getEndNodeIDArray() {
		return endNodeID;
	}

	public static int[] getedgeValueArray() {
		return edgeValue;
	}
}
