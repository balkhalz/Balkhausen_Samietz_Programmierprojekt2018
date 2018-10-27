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
	private static int nodeAmount, edgeAmount, nodeAndEdgeAmount, writeIndicator, iterator;
	// private static String[] tempStringArray;

	private static ArrayList<String[]> dataList;
	private static String[][] nodeArray;
	private static int[][] edgeArray;

	public static void initialize() {
		try {
			readFilePath();
			scanner = new Scanner(new BufferedReader(new FileReader(sourceFile.getAbsolutePath())));
			scannerSkipLines(4, scanner);
			nodeAmount = scanner.nextInt();
			edgeAmount = scanner.nextInt();
			nodeAndEdgeAmount = nodeAmount + edgeAmount;
			scanner.nextLine();
			// System.out.println(nodeAmount + "\n" + edgeAmount);

			nodeArray = new String[nodeAmount][2];
			edgeArray = new int[edgeAmount][3];

			dataList = new ArrayList<String[]>(nodeAndEdgeAmount);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void readFilePath() {
		sourceFile = new File("src/stgtregbz.fmi");
	}

	public static void readData() {
		writeIndicator = -1;
		for (int ctr = 0; ctr < nodeAmount; ctr++) {
			dataList.add(scanner.nextLine().split(" "));
			writeIndicator++;
		}
		System.out.println("reading nodes finished");

		for (int ctr = 0; ctr < edgeAmount; ctr++) {
			dataList.add(writeIndicator, scanner.nextLine().split(" "));
			writeIndicator++;
		}
		System.out.println("reading edges finished");
		System.out.println(writeIndicator);

	}

	/**
	 * This method is used to write the data from the created List to usable Arrays.
	 * It is designed to function multithreaded, so it never goes turns out ahead of
	 * the fileReader.
	 */
	public static void writeData() {
		iterator = 0;
		for (int ctr = 0; ctr < nodeAmount; ctr++) {
			while (iterator < dataList.size() || dataList.size() == nodeAndEdgeAmount) {
				nodeArray[ctr][0] = dataList.get(iterator)[2];
				nodeArray[ctr][1] = dataList.get(iterator)[3];
				iterator++;
				break;
			}
		}
		for (int ctr = 0; ctr < edgeAmount; ctr++) {
			while (iterator < dataList.size() || dataList.size() == nodeAndEdgeAmount) {
				edgeArray[ctr][0] = Integer.parseInt(dataList.get(iterator)[0]);
				edgeArray[ctr][1] = Integer.parseInt(dataList.get(iterator)[1]);
				edgeArray[ctr][2] = Integer.parseInt(dataList.get(iterator)[2]);
				iterator++;
				break;
			}
		}
		System.out.println("writing arrays finished");
	}

	private static void scannerSkipLines(int skips, Scanner scanner) {
		for (int ctr = 0; ctr < skips; ctr++) {
			scanner.nextLine();
		}
	}

	public static void printData() {
		for (int ctr = 0; ctr < nodeAmount; ctr++) {
			System.out.println(nodeArray[ctr][0] + " " + nodeArray[ctr][1]);
		}
	}
}
