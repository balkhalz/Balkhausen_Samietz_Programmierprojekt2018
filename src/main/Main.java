package main;

import data.Data;

class RunReadData extends Thread {
	public void run(String data) {
		if (data.equals("readData")) {
			Data.readData();
		} else if (data.equals("writeData")) {
			Data.writeData();
		} else {
			System.out.println("Wrong Input.");
		}
	}
}

public class Main extends Thread {
	public static void main(String[] args) {
		RunReadData readData = new RunReadData();
		RunReadData writeData = new RunReadData();
		Data.initialize();
		readData.run("readData");
		writeData.run("writeData");
		// Data.readData();
		// Data.writeData();
		Data.testData();
	}
}