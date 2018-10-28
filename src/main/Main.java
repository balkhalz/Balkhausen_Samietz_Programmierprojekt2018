package main;

import data.Data;

public class Main extends Thread {

	public static void main(String[] args) {
		Data.initialize();
		Data.readData();
		Data.writeData();
	}
}
