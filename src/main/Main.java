package main;

import data.Data;
import utility.Utility;

public class Main {

	public static void main(String[] args) {
		Utility.initializeAll();
		Data.readAndWrite();
		// Data.testData("stuttgart");
		// new Window();
		Utility.benchmark();
	}

}
