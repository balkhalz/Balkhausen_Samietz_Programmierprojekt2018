package algorithm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Scanner;

import data.Data;

public class Benchmark {
	
	private static LinkedList<Integer> questionSources;
	private static LinkedList<int[]> questionTargets;
	private static LinkedList<int[]> solutions;
	
	private static int difference;
	
	public static int calculateDifference(File map, File que, File sol) {
		
		Data.initialize(map);
		Data.readAndWrite();
		readQuestions(que);
		readSolutions(sol);
		
		checkQueSol();
		
		return difference;
	}

	private static void checkQueSol() {
		difference = 0;
		
		int[] tempQue;
		
		for (int ctr = 0; ctr < questionSources.size(); ctr++) {
			Dijkstra.setPivotSource(questionSources.get(ctr));
			System.out.println("calculation from " + questionSources.get(ctr));
			
			tempQue = Dijkstra.fromPivotSourceToTargets(questionTargets.get(ctr));
			
			for (int ctr2 = 0; ctr2 < tempQue.length; ctr2++)
//				if (solutions.get(ctr)[ctr2] != tempQue[ctr2])
//					difference++;
				System.out.println(solutions.get(ctr)[ctr2] + " " + tempQue[ctr2]);
		}
	}

	private static void readQuestions(File que) {
		questionSources = new LinkedList<Integer>();
		questionTargets = new LinkedList<int[]>();
		
		
		
		Scanner scanner = null;
		try {
			scanner = new Scanner(new BufferedReader(new FileReader(que)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		int targetAmount = 1;
		int thisSource = scanner.nextInt();
		int lastSource = thisSource;
		int[] tempIntArray;
		LinkedList<Integer> tempTargets = new LinkedList<Integer>();
		
		tempTargets.add(scanner.nextInt());
		
		
		while (scanner.hasNext()) {
			thisSource = scanner.nextInt();
			
			if (lastSource == thisSource)
				targetAmount++;
			else {
				tempIntArray = new int[targetAmount];
				
				for (int ctr = 0; ctr < targetAmount; ctr++)
					tempIntArray[ctr] = tempTargets.pop();
				
				questionSources.add(lastSource);
				questionTargets.add(tempIntArray);
				
				targetAmount = 1;
			}
			
			lastSource = thisSource;
			tempTargets.add(scanner.nextInt());
		}
		
		scanner.close();
		
		tempIntArray = new int[targetAmount];
		
		for (int ctr = 0; ctr < targetAmount; ctr++)
			tempIntArray[ctr] = tempTargets.pop();
		
		questionSources.add(lastSource);
		questionTargets.add(tempIntArray);
		
	}

	private static void readSolutions(File sol) {
		solutions = new LinkedList<int[]>();
		
		for (int[] iA : questionTargets)
			solutions.add(new int[iA.length]);

		
		Scanner scanner = null;
		try {
			scanner = new Scanner(new BufferedReader(new FileReader(sol)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		
		int arrayCtr = 0, integerCtr = 0;
		
		while (scanner.hasNext()) {
			solutions.get(arrayCtr)[integerCtr] = scanner.nextInt();
			
			integerCtr++;
			if (integerCtr == solutions.get(arrayCtr).length) {
				integerCtr = 0;
				arrayCtr++;
			}
		}
		
		scanner.close();
	}

}
