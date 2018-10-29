package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Data {

	private static File sourceFile;
	private static Scanner scanner;
	private static int nodeAmount, edgeAmount, readIndicator;
	// private static String[] tempStringArray;

	private static int[] edgeOffset;
	private static ArrayList<String[]> nodeDataList, edgeDataList;
	private static String[][] nodeArray;
	private static int[][] edgeArray;

	public static void initialize() {
		try {
			readFilePath();
			scanner = new Scanner(new BufferedReader(new FileReader(sourceFile.getAbsolutePath())));
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

	public static void readFilePath() {
		sourceFile = new File("src/stgtregbz.fmi");
	}

	public static void readData() {
		readIndicator = 0;
		for (int ctr = 0; ctr < nodeAmount; ctr++) {
			nodeDataList.add(ctr, scanner.nextLine().split(" "));
			readIndicator++;
		}
		System.out.println("reading nodes finished");

		for (int ctr = 0; ctr < edgeAmount; ctr++) {
			edgeDataList.add(ctr, scanner.nextLine().split(" "));
			readIndicator++;
		}
		System.out.println("reading edges finished");

	}

	/**
	 * This method is used to write the data from the created List to usable Arrays.
	 * It is designed to function multithreaded, so it never skips ahead of the
	 * fileReader.
	 */
	public static void writeData() {
		String lastNodeWritten = null;
		int nodeCounter = 0;
		for (int ctr = 0; ctr < nodeAmount; ctr++) {
			nodeArray[ctr][0] = nodeDataList.get(ctr)[2];
			nodeArray[ctr][1] = nodeDataList.get(ctr)[3];
		}
		// System.out.println(nodeArray[nodeArray.length - 1][0] + " " +
		// nodeArray[nodeArray.length - 1][1]);

		for (int ctr = 0; ctr < edgeAmount; ctr++) {
			edgeArray[ctr][0] = Integer.parseInt(edgeDataList.get(ctr)[0]);
			edgeArray[ctr][1] = Integer.parseInt(edgeDataList.get(ctr)[1]);
			edgeArray[ctr][2] = Integer.parseInt(edgeDataList.get(ctr)[2]);
			if (edgeDataList.get(ctr)[0].equals(lastNodeWritten) == false) {
				edgeOffset[nodeCounter] = Integer.parseInt(edgeDataList.get(ctr)[0]);
				nodeCounter++;
				lastNodeWritten = edgeDataList.get(ctr)[0];
			}
		}
		// System.out.println(edgeArray[edgeArray.length - 1][0] + " " +
		// edgeArray[edgeArray.length - 1][1] + " "
		// + edgeArray[edgeArray.length - 1][2]);
		System.out.println("writing arrays finished");
	}

	private static void scannerSkipLines(int skips, Scanner scanner) {
		for (int ctr = 0; ctr < skips; ctr++) {
			scanner.nextLine();
		}
	}

	public static void testData() {
		if (nodeArray[0][0].equals("48.66743380000000485") && nodeArray[0][1].equals("9.24459110000000095")
				&& nodeArray[1132112][0].equals("48.64209470000000124")
				&& nodeArray[1132112][1].equals("9.01481120000000047")) {
			System.out.println("nodeArray setup correctly!");
		}
	}
}
