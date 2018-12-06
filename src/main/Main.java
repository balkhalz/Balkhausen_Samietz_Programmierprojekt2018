package main;

import data.Data;

import java.io.IOException;

public class Main {

	public static void main(String[] args)  {
		try {
			Data.initialize();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			Data.readAndWrite();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
