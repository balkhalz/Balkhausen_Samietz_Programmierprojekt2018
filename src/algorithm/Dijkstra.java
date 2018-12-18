package algorithm;

import data.Data;
import utility.Utility;

public class Dijkstra {

	private static int pivotSource;

	private static int[] edgeOffset, endNodeID, edgeValue;

	private static int amountOfEdges, amountOfNodes, lastSource = 0;

	private static boolean[] nodeReachable, minimalDistanceFound;
	private static int priorityQueueLength; // repraesentiert die Laenge der priorityQueue
	private static int[] priorityQueue; // enthaelt Verweise auf distanceArray
	private static int[] distanceArray; // enthaelt Abstaende von sourceNode zu entsprechender Node

	/**
	 * calculates the distance from the source to the target
	 * 
	 * @param source
	 * @param target
	 * @return distance from source to target
	 */
	public static int setSourceAndTarget(int source, int target) {

		Utility.startTimer();
		/*
		 * if (source == lastSource) { return distanceArray[target]; }
		 */

		edgeOffset = Data.getOffsetArray();
		endNodeID = Data.getEndNodeIDArray();
		edgeValue = Data.getEdgeValueArray();

		amountOfNodes = edgeOffset.length;
		amountOfEdges = endNodeID.length;

		priorityQueueLength = 1;

		nodeReachable = new boolean[amountOfNodes];
		minimalDistanceFound = new boolean[amountOfNodes];
		priorityQueue = new int[amountOfNodes];
		distanceArray = new int[amountOfEdges];

		for (int ctr = 0; ctr < distanceArray.length; ctr++)
			distanceArray[ctr] = Integer.MAX_VALUE;

		priorityQueue[0] = source;
		distanceArray[source] = 0;

		while (true) {

			int closestNode = priorityQueue[0];
			int tempDist = distanceArray[closestNode];
			int closestNodeTarget;

			minimalDistanceFound[closestNode] = true;

			// Aus Node zeigende Edges werden mit edgeOffset[Node] und edgeOffset[Node + 1]
			// ermittelt
			int b = edgeOffset[closestNode];
			int n;

			// falls b = -1 ist, wurde tempNode eine sackgasse und hat keine Edges, die aus
			// ihr zeigen
			if (b != -1) {

				// da edgeOffset[tempNode + 1] eine Exception wefen kann, wird hier nachgefragt
				if (closestNode + 1 == amountOfNodes)
					n = amountOfEdges;
				else
					n = edgeOffset[closestNode + 1];

				for (int ctr = b; ctr < n; ctr++) {
					closestNodeTarget = endNodeID[ctr];

					// verhindert Schleifen
					if (!minimalDistanceFound[closestNodeTarget])
						if (distanceArray[closestNodeTarget] > tempDist + edgeValue[ctr]) {
							distanceArray[closestNodeTarget] = tempDist + edgeValue[ctr];

							// falls noch nicht in priorityQueue, Aufnahme in diese
							if (!nodeReachable[closestNodeTarget]) {
								priorityQueue[priorityQueueLength] = closestNodeTarget;
								priorityQueueLength++;
								nodeReachable[closestNodeTarget] = true;
							}
						}
				}
			}

			// eine Node wurde abgehandelt, deswegen wird priorityQueue[0] entfernt
			priorityQueueLength--;
			priorityQueue[0] = priorityQueue[priorityQueueLength];
			nodeReachable[closestNode] = false;

			// die Node mit der kuerzesten Distanz, die noch nicht abgehandelt wurde wird an
			// prioQueue[0] gesetzt
			minHeapSort();

			/*
			 * falls keine kuerzerer Distanz mehr moeglich ist, d.h. distanz[target] <
			 * distanz[naechster Node], wird die Distanz zurueckgegeben
			 */
			if (distanceArray[target] < distanceArray[priorityQueue[0]]) {
				lastSource = source;
				return distanceArray[target];
			}
		}

	}

	/**
	 * Sets a pivot source for other methods
	 * 
	 * @param sourceToSet
	 */
	public static void setPivotSource(int sourceToSet) {
		pivotSource = sourceToSet;
	}

	/**
	 * calculates the distance from the pivot source to the given target
	 * 
	 * @param target
	 * @return distance between pivotSource to target
	 */
	public static int fromPivotSourceToTarget(int target) {
		return setSourceAndTarget(pivotSource, target);
	}

	/**
	 * calculates the distance from the pivot source to multiple targets
	 * 
	 * @param targets
	 * @return distance between pivotSource to multiple targets
	 */
	public static int[] fromPivotSourceToTargets(int[] targets) {

		boolean finished = false;

		edgeOffset = Data.getOffsetArray();
		endNodeID = Data.getEndNodeIDArray();
		edgeValue = Data.getEdgeValueArray();

		amountOfNodes = edgeOffset.length;
		amountOfEdges = endNodeID.length;

		priorityQueueLength = 1;

		nodeReachable = new boolean[amountOfNodes];
		minimalDistanceFound = new boolean[amountOfNodes];
		priorityQueue = new int[amountOfNodes];
		distanceArray = new int[amountOfEdges];

		for (int ctr = 0; ctr < distanceArray.length; ctr++)
			distanceArray[ctr] = Integer.MAX_VALUE;

		priorityQueue[0] = pivotSource;
		distanceArray[pivotSource] = 0;

		while (true) {

			int closestNode = priorityQueue[0];
			int tempDist = distanceArray[closestNode];
			int closestNodeTarget;

			minimalDistanceFound[closestNode] = true;

			// Aus Node zeigende Edges werden mit edgeOffset[Node] und edgeOffset[Node + 1]
			// ermittelt
			int b = edgeOffset[closestNode];
			int n;

			// falls b = -1 ist, wurde tempNode eine sackgasse und hat keine Edges, die aus
			// ihr zeigen
			if (b != -1) {

				// da edgeOffset[tempNode + 1] eine Exception wefen kann, wird hier nachgefragt
				if (closestNode + 1 == amountOfNodes)
					n = amountOfEdges;
				else
					n = edgeOffset[closestNode + 1];

				for (int ctr = b; ctr < n; ctr++) {
					closestNodeTarget = endNodeID[ctr];

					// verhindert Schleifen
					if (!minimalDistanceFound[closestNodeTarget])
						if (distanceArray[closestNodeTarget] > tempDist + edgeValue[ctr]) {
							distanceArray[closestNodeTarget] = tempDist + edgeValue[ctr];

							// falls noch nicht in priorityQueue, Aufnahme in diese
							if (!nodeReachable[closestNodeTarget]) {
								priorityQueue[priorityQueueLength] = closestNodeTarget;
								priorityQueueLength++;
								nodeReachable[closestNodeTarget] = true;
							}
						}
				}
			}
			priorityQueueLength--;
			priorityQueue[0] = priorityQueue[priorityQueueLength];
			nodeReachable[closestNode] = false;

			minHeapSort();

			finished = true;

			// falls f�r nur 1 target kein ergebniss gefunden wurde, ist die Suche NICHT
			// beendet
			for (int target : targets) {
				if (distanceArray[target] < distanceArray[priorityQueue[0]]) {
					finished = false;
					break;
				}
			}
			if (finished) {
				for (int ctr = 0; ctr < targets.length; ctr++)
					targets[ctr] = distanceArray[targets[ctr]];
				return targets;
			}
		}
	}

	/**
	 * minHeap sortierer
	 * 
	 * sortiert die ersten n = nodesToVisit nodes der PrioQueue anhand der L舅ge x =
	 * distanceArray[prioQueue[i]] von der Node i
	 * 
	 */
	private static void minHeapSort() {

		if (priorityQueueLength == 0)
			return;

		int firstParentNode = (priorityQueueLength - 2) / 2;
		int childNode = firstParentNode * 2 + 2;

		int parentPointer = priorityQueue[firstParentNode];

		// bei erstem durchlauf ArrayOutOfBounds mlich, deswegen seperater durchlauf
		// mit extra if-Abfrage
		if (childNode < priorityQueueLength && distanceArray[parentPointer] > distanceArray[priorityQueue[childNode]]) {
			priorityQueue[firstParentNode] = priorityQueue[childNode];
			priorityQueue[childNode] = parentPointer;

			parentPointer = priorityQueue[firstParentNode];
		}

		childNode--;

		if (childNode < priorityQueueLength && distanceArray[parentPointer] > distanceArray[priorityQueue[childNode]]) {
			priorityQueue[firstParentNode] = priorityQueue[childNode];
			priorityQueue[childNode] = parentPointer;
		}

		for (int parentNode = firstParentNode - 1; parentNode != -1; parentNode--) {
			childNode--;
			parentPointer = priorityQueue[parentNode];

			if (distanceArray[parentPointer] > distanceArray[priorityQueue[childNode]]) {
				priorityQueue[parentNode] = priorityQueue[childNode];
				priorityQueue[childNode] = parentPointer;

				parentPointer = priorityQueue[parentNode];
			}

			childNode--;

			if (distanceArray[parentPointer] > distanceArray[priorityQueue[childNode]]) {
				priorityQueue[parentNode] = priorityQueue[childNode];
				priorityQueue[childNode] = parentPointer;
			}
		}
	}
}
