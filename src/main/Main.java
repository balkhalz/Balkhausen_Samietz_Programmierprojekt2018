package main;

import data.Data;
import GUI.Window;

public class Main {

	public static void main(String[] args) {
		Data.initialize();
		Data.readAndWrite();
		Data.testData("stuttgart");
		new Window();
	}

}
