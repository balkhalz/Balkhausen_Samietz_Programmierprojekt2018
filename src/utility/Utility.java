package utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import algorithm.Dijkstra;
import data.Data;

public class Utility {
	private static int index, differences;
	private static double startTime = 0;
	private static File solSourceFile, logFile, resultsFile;
	private static Scanner scanner;
	private static FileWriter logfileWriter, resultsFileWriter;

	private static ArrayList<Integer> resultsArray, solutionsArray;

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
			solSourceFile = new File("src/stgtregbz.fmi");
		} else if (filename.equals("bw")) {
			solSourceFile = new File("src/bw.fmi");
		} else if (filename.equals("germany")) {
			solSourceFile = new File("src/germany.fmi");
		} else if (filename.equals("questions")) {
			solSourceFile = new File("src/germany.que");
		} else if (filename.equals("solutions")) {
			solSourceFile = new File("src/germany.sol");
		}
		return solSourceFile;
	}

	public static void initializeAll() {

		startTimer();
		solSourceFile = new File("src/germany.que");
		logFile = new File("logfile.txt");
		resultsFile = new File("results.sol");

		try {
			logFile.createNewFile();
			resultsFile.createNewFile();
			logfileWriter = new FileWriter(logFile);
			resultsFileWriter = new FileWriter(resultsFile);
			addLineToFile("This is the logfile for our routeplanner." + System.lineSeparator(), logfileWriter);
			addEmptyLineToFile(logfileWriter);
			logfileWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Data.initialize();
		addLineToFile("Setting up data structures completed in " + endTimer() + " seconds." + System.lineSeparator(),
				logfileWriter);
		try {
			logfileWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void addLineToFile(String nextLine, FileWriter writer) {
		try {
			writer.write(nextLine, 0, nextLine.length());
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void addLineToFile(int nextLine, FileWriter writer) {
		try {
			writer.write(Integer.toString(nextLine), 0, Integer.toString(nextLine).length());
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void addEmptyLineToFile(FileWriter writer) {
		try {
			writer.write(System.lineSeparator());
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static FileWriter getLogFileWriter() {
		return logfileWriter;
	}

	public static void readQuestionFile() {
		startTimer();
		try {
			scanner = new Scanner(new BufferedReader(new FileReader(Utility.readFilePath("questions"))));
			while (scanner.hasNext()) {
				String tempString = scanner.nextLine();
				addLineToFile(
						Dijkstra.setSourceAndTarget(Integer.parseInt(tempString.split(" ")[0]),
								Integer.parseInt(tempString.split(" ")[1])) + System.lineSeparator(),
						resultsFileWriter);
			}
			addLineToFile("Benchmark completed in " + endTimer() + " seconds.", logfileWriter);
			System.out.println("Benchmark completed.");
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		if (solSourceFile != null) {
			System.out.println("Differences: " + compareQuestionToSolution(resultsFile, readFilePath("solutions")));
		}
	}

	public static int compareQuestionToSolution(File resultsFile, File solutionsFile) {
		differences = 0;
		try {
			Scanner resultsFileScanner = new Scanner(new BufferedReader(new FileReader(resultsFile)));
			Scanner solutionsFileScanner = new Scanner(new BufferedReader(new FileReader(resultsFile)));
			while (resultsFileScanner.hasNext()) {
				resultsArray.add(Integer.parseInt(resultsFileScanner.nextLine()));
			}
			while (solutionsFileScanner.hasNext()) {
				solutionsArray.add(Integer.parseInt(solutionsFileScanner.nextLine()));
			}
			if (resultsArray.size() == solutionsArray.size()) {
				for (int i = 0; i < resultsArray.size(); i++) {
					if (resultsArray.get(i) - solutionsArray.get(i) != 0) {
						differences += 1;
					}
				}
			}

			resultsFileScanner.close();
			solutionsFileScanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return differences;
	}
}
