package utility;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import data.Data;

public class Utility {
	private static double startTime = 0;
	private static File logFile, resultsFile, sourceFile;
	private static FileWriter logfileWriter;

	public static void startTimer() {
		startTime = System.nanoTime();
	}

	public static double endTimer() {
		return (System.nanoTime() - startTime) / 1000000000;
	}

	public static void initializeAll() {

		startTimer();
		logFile = new File("logfile.txt");
		resultsFile = new File("results.sol");
		sourceFile = new File("src/germany.fmi");
		new File("src/germany.que");
		new File("src/germany.sol");
		try {
			logFile.createNewFile();
			resultsFile.createNewFile();
			logfileWriter = new FileWriter(logFile);
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

	public static File getSourceFile() {
		return sourceFile;
	}
}