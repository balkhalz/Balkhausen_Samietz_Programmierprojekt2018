package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import utility.Utility;

public class Data {

	private static int nodeAmount, edgeAmount;
	private static File sourceFile;
	private static BufferedReader reader;

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
	private static void skipLines(int skips) {
		try {
			for (int ctr = 0; ctr < skips; ctr++) {
				reader.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void initialize() {
		// initializing the scanner
		try {
			reader = new BufferedReader(new FileReader(readFilePath()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		skipLines(5);
		try {
			nodeAmount = Integer.parseInt(reader.readLine());
			edgeAmount = Integer.parseInt(reader.readLine());
		} catch (Exception e) {
			e.printStackTrace();
		}

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
		System.out.println("Finished in: " + Utility.endTimer() + " seconds!");
	}

	/**
	 * This method extrapolates all node data needed to run the dijkstra algorithm.
	 */
	private static void readNodes() {
		String[] tempArray = null;
		for (int ctr = 0; ctr < nodeAmount; ctr++) {
			
			try {
				tempArray = reader.readLine().split(" ");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			xLatitude[ctr] = Double.parseDouble(tempArray[2]);
			yLatitude[ctr] = Double.parseDouble(tempArray[3]);
			
			// the offsetArray is inicialized with -1, when a node is a dead end it will be distinguished
			nodeOffset[ctr] = -1;
		}
		// System.out.println("Reading and writing nodes finished");
	}

	/**
	 * This method extrapolates all edge data needed to run the dijkstra algorithm.
	 */
	private static void readEdges() {
		String[] tempArray = null;
		int thisNode = 0;
		int lastNode = -1;
		for (int ctr = 0; ctr < edgeAmount; ctr++) {

			try {
				tempArray = reader.readLine().split(" ");
			} catch (Exception e) {
				e.printStackTrace();
			}

			thisNode = Integer.parseInt(tempArray[0]);

			startNodeID[ctr] = thisNode;
			endNodeID[ctr] = Integer.parseInt(tempArray[1]);
			edgeValue[ctr] = Integer.parseInt(tempArray[2]);

			if (thisNode != lastNode)
				nodeOffset[thisNode] = ctr;

			lastNode = thisNode;
		}
		// System.out.println("Reading and writing edges finished");
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

	public static int[] getEdgeValueArray() {
		return edgeValue;
	}
	
	public static void testData(String map) {
		if (map.equals("stuttgart")) {
			if (xLatitude[0] == 48.667433800000005 && yLatitude[0] == 9.244591100000001 && nodeOffset[0] == 0
					&& xLatitude[nodeAmount - 1] == 48.6420947 && yLatitude[nodeAmount - 1] == 9.0148112
					&& nodeOffset[nodeAmount - 1] == 2292885) {
				System.out.println("Nodes setup correctly!");
			}
			if (startNodeID[0] == 0 && endNodeID[0] == 1104366 && edgeValue[0] == 55 && startNodeID[edgeAmount - 1] == 1132112
					&& endNodeID[edgeAmount - 1] == 1131157 && edgeValue[edgeAmount - 1] == 462) {
				System.out.println("Edges setup correctly!");
			}
		}
	}

}
