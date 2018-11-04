package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Data {

	private static boolean readNodes, readEdges;
	private static Scanner scanner;
	private static int nodeAmount, edgeAmount;

	private static int[] edgeOffset;
	private static ArrayList<String[]> nodeDataList, edgeDataList;
	private static String[][] nodeArray;
	private static int[][] edgeArray;

	public static void initialize() {
		try {
			scanner = new Scanner(new BufferedReader(new FileReader(readFilePath().getAbsolutePath())));
			scannerSkipLines(4, scanner);
			nodeAmount = scanner.nextInt();
			edgeAmount = scanner.nextInt();
			scanner.nextLine();
			// System.out.println(nodeAmount + "\n" + edgeAmount);
			edgeOffset = new int[nodeAmount];
			nodeArray = new String[nodeAmount][3];
			edgeArray = new int[edgeAmount][3];

			nodeDataList = new ArrayList<String[]>(nodeAmount);
			edgeDataList = new ArrayList<String[]>(edgeAmount);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static File readFilePath() {
		return new File("src/stgtregbz.fmi");
	}

	public static void readData() {
		readNodes = false;
		for (int ctr = 0; ctr < nodeAmount; ctr++) {
			nodeDataList.add(ctr, scanner.nextLine().split(" "));
		}
		readNodes = true;
		System.out.println("reading nodes finished");

		readEdges = false;
		for (int ctr = 0; ctr < edgeAmount; ctr++) {
			edgeDataList.add(ctr, scanner.nextLine().split(" "));
		}
		readEdges = true;
		System.out.println("reading edges finished");

	}

	/**
	 * This method is used to write the data from the created List to usable Arrays.
	 * It is designed to function multithreaded, so it never skips ahead of the
	 * fileReader.
	 */
	public static void writeData() {
		int nodeCounter = 0;
		edgeOffset[0] = 0;
		while (readNodes = false) {
		}
		for (int ctr = 0; ctr < nodeAmount; ctr++) {
			nodeArray[ctr][0] = nodeDataList.get(ctr)[2];
			nodeArray[ctr][1] = nodeDataList.get(ctr)[3];
		}

		while (readEdges = false) {

		}
		for (int ctr = 0; ctr < edgeAmount; ctr++) {
			edgeArray[ctr][0] = Integer.parseInt(edgeDataList.get(ctr)[0]);
			edgeArray[ctr][1] = Integer.parseInt(edgeDataList.get(ctr)[1]);
			edgeArray[ctr][2] = Integer.parseInt(edgeDataList.get(ctr)[2]);
			if (Integer.parseInt(edgeDataList.get(ctr)[0]) != nodeCounter) {
				nodeCounter++;
				edgeOffset[nodeCounter] = ctr;
			}
		}
		System.out.println("writing arrays finished");
	}

	private static void scannerSkipLines(int skips, Scanner scanner) {
		for (int ctr = 0; ctr < skips; ctr++) {
			scanner.nextLine();
		}
	}

	public static int[] getEdgeOffset() {
		return edgeOffset;
	}

	public static void testData() {
		if (nodeArray[0][0].equals("48.66743380000000485") && nodeArray[0][1].equals("9.24459110000000095")
				&& nodeArray[1132112][0].equals("48.64209470000000124")
				&& nodeArray[1132112][1].equals("9.01481120000000047")) {
			System.out.println("nodeArray setup correctly!");
		}
		if (edgeArray[0][0] == 0 && edgeArray[0][1] == 1104366 && edgeArray[0][2] == 55
				&& edgeArray[2292886][0] == 1132112 && edgeArray[2292886][1] == 1131157
				&& edgeArray[2292886][2] == 462) {
			System.out.println("edgeArray setup correctly!");
		}
		if (edgeOffset[0] == 0 && edgeOffset[1132112] == 2292885) {
			System.out.println("edgeOffsetArray setup correctly!");
		}
	}
}