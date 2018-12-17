package utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import data.Data;

public class Utility {
	private static int index, distance;
	private static double startTime = 0;
	private static File sourceFile, outputFile;
	private static Scanner scanner;
	private static FileWriter writer;
	private static List<Integer> tempStartQuestionList, tempEndQuestionList, tempAnswerList, differences;
	private static List<int[]> benchmarkList;
	private static int[] tempArray;

	public static void startTimer() {
		startTime = System.nanoTime();
	}

	public static double endTimer() {
		return (System.nanoTime() - startTime) / 1000000000;
	}

	/**
	 * This method returns the filepath of the sourcefile.
	 * 
	 * @return
	 */
	public static File readFilePath(String filename) {
		if (filename.equals("stuttgart")) {
			sourceFile = new File("src/stgtregbz.fmi");
		} else if (filename.equals("bw")) {
			sourceFile = new File("src/bw.fmi");
		} else if (filename.equals("germany")) {
			sourceFile = new File("src/germany.fmi");
		} else if (filename.equals("benchs")) {
			sourceFile = new File("src/Benchs.tar");
		} else {
			return null;
		}
		return sourceFile;
	}

	public static void initializeAll() {

		startTimer();
		tempArray = new int[3];
		outputFile = new File("logfile.txt");
		try {
			outputFile.createNewFile();
			writer = new FileWriter(outputFile);
			addLineToOutputFile("This is the logfile for our routeplanner." + System.lineSeparator());
			addEmptyLineToOutputFile();
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Data.initialize();
		addLineToOutputFile(
				"Setting up data structures completed in " + endTimer() + " seconds." + System.lineSeparator());
		try {
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void addLineToOutputFile(String nextLine) {
		try {
			writer.write(nextLine, 0, nextLine.length());
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void addEmptyLineToOutputFile() {
		try {
			writer.write(System.lineSeparator());
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static int benchmark() {
		try {
			String[] tempString = null;
			scanner = new Scanner(new BufferedReader(new FileReader(Utility.readFilePath("benchs"))));
			scanner.nextLine();
			while (scanner.hasNext()) {
				tempString = scanner.nextLine().split(" ");

				if (tempString.length == 1) {
					tempAnswerList.add(Integer.parseInt(tempString[0]));
				} else if (tempString.length == 1) {
					tempStartQuestionList.add(Integer.parseInt(tempString[0]));
					tempEndQuestionList.add(Integer.parseInt(tempString[1]));
				} else {
					scanner.nextLine();
				}
			}

			for (int i = 0; i < tempStartQuestionList.size(); i++) {
				tempArray[0] = tempStartQuestionList.get(0);
				tempStartQuestionList.remove(0);
				tempArray[1] = tempEndQuestionList.get(0);
				tempStartQuestionList.remove(0);
				tempArray[2] = tempAnswerList.get(0);
				tempStartQuestionList.remove(0);
				benchmarkList.add(tempArray);
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		addEmptyLineToOutputFile();
		addLineToOutputFile("Starting Benchmark.");
		for (int i = 0; i < benchmarkList.size(); i++) {
			int[] temp = benchmarkList.get(0);
			benchmarkList.remove(0);
			distance = algorithm.Dijkstra.setSourceAndTarget(temp[0], temp[1]);
			addLineToOutputFile("Distance from " + temp[0] + " to " + temp[1] + ": " + distance);
			// Checks if the benchmark result is any different from our result.
			if (distance != temp[2]) {
				differences.add(i);
			}
		}

		return differences.size();
	}
}
