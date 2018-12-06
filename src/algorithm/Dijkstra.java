package algorithm;

import data.Data;

public class Dijkstra {
	
	private static int pivotSource;
	
	private static int[] edgeOffset, endNodeID, edgeValue;
	
	private static int amountOfEdges, amountOfNodes;
	
	private static int nodesToVisit;	//	repräsentiert die unbehandelten nodes
	private static int[] priorityQueue;	//	enthält Verweise auf distanceArray
	private static int[] distanceArray;	//	enthält Abstände von sourceNode zu entsprechender Node

	/**
	 * calculates the distance from the source to the target
	 * 
	 * @param source
	 * @param target
	 * @return distance from source to target
	 */
	public static int setSourceAndTarget(int source, int target) {
		
		edgeOffset = Data.getOffsetArray();
		endNodeID = Data.getEndNodeIDArray();
		edgeValue = Data.getedgeValueArray();
		
		amountOfNodes = edgeOffset.length;
		amountOfEdges = endNodeID.length;
		
		nodesToVisit = amountOfNodes;

		priorityQueue = new int[amountOfNodes];
		distanceArray = new int[amountOfEdges];
		
		for (int ctr = 0; ctr < amountOfNodes; ctr++) {
			priorityQueue[ctr] = ctr;
			distanceArray[ctr] = Integer.MAX_VALUE;
		}
		
		priorityQueue[source] = 0;
		priorityQueue[0] = source;
		distanceArray[source] = 0;
		
		
		
		while (true) {
			int tempNode = priorityQueue[0];
			int tempDist = distanceArray[tempNode];
			
			
			// Aus Node zeigende Edges werden mit edgeOffset[Node] und edgeOffset[Node + 1] ermittelt
			int b = edgeOffset[tempNode];
			int n;
			
			// da edgeOffset[Node + 1] eine Exception wefen kann, wird hier nachgefragt
			if (amountOfNodes == tempNode + 1)
				n = amountOfEdges;
			else
				n = edgeOffset[tempNode + 1];
			
			// Für alle neu erreichbaren Nodes y gilt:
			for (int ctr = b; ctr < n; ctr++) {
				/* Falls die Länge des neu entdeckten Pfads zur Node y kürzer ist,
				 * als die vorhandene Distanz zu y, wird der neue Wert übernommen.
				 */
				if (tempDist + edgeValue[ctr] < distanceArray[endNodeID[ctr]])
					distanceArray[endNodeID[ctr]] = tempDist + edgeValue[ctr];
			}
			
			// eine Node wurde abgehandelt, deswegen wird nodesToVisit dekrementiert
			nodesToVisit--;
			
			/* da nur die ersten n = nodesToVisit Nodes der priorityQueue einer Relevanz haben,
			 * wird die abgehandelte Node ersetzt
			 */
			priorityQueue[0] = priorityQueue[nodesToVisit];
			
			// die Node mit der kürzesten Distanz, die noch nicht abgehandelt wurde wird and prioQueue[0] gesetzt
			minHeapSort();
			
			/* falls keine kürzerer Distanz mehr möglich ist, d.h. distanz[target] < distanz[nächster Node],
			 * wir die Distanz zurückgegeben
			 */
			if (distanceArray[target] < distanceArray[priorityQueue[0]])
				return distanceArray[target];
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
		edgeValue = Data.getedgeValueArray();
		
		amountOfNodes = edgeOffset.length;
		amountOfEdges = endNodeID.length;
		
		nodesToVisit = amountOfNodes;

		priorityQueue = new int[amountOfNodes];
		distanceArray = new int[amountOfEdges];
		
		for (int ctr = 0; ctr < amountOfNodes; ctr++) {
			priorityQueue[ctr] = ctr;
			distanceArray[ctr] = Integer.MAX_VALUE;
		}
		
		priorityQueue[pivotSource] = 0;
		priorityQueue[0] = pivotSource;
		distanceArray[pivotSource] = 0;
		
		while (true) {
			int tempNode = priorityQueue[0];
			int tempDist = distanceArray[tempNode];
			int b = edgeOffset[tempNode];
			int n;
			
			if (amountOfNodes == tempNode + 1)
				n = amountOfEdges;
			else
				n = edgeOffset[tempNode + 1];
			
			for (int ctr = b; ctr < n; ctr++) {
				if (distanceArray[endNodeID[ctr]] > tempDist + edgeValue[ctr]) {
					distanceArray[endNodeID[ctr]] = tempDist + edgeValue[ctr];
				}
			}
			
			nodesToVisit--;
			priorityQueue[0] = priorityQueue[nodesToVisit];
			
			minHeapSort();
			
			finished = true;
			
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
	 * sortiert die ersten n = nodesToVisit nodes der PrioQueue anhand der Länge
	 * x = distanceArray[prioQueue[i]] von der Node i
	 * 
	 */
	private static void minHeapSort() {
		
		// bei erstem durchlauf arrayOutOfBounds möglich, deswegen seperater durchlauf mit extra if-Abfrage
		
		int firstParentNode = nodesToVisit / 2;
		int childNode = firstParentNode * 2 + 2;
		int parentPointer = priorityQueue[firstParentNode];

		if (childNode < nodesToVisit && distanceArray[parentPointer] > distanceArray[priorityQueue[childNode]]) {
			priorityQueue[firstParentNode] = priorityQueue[childNode];
			priorityQueue[childNode] = parentPointer;
			parentPointer = priorityQueue[firstParentNode];
		}
		
		childNode--;

		if (childNode < nodesToVisit && distanceArray[parentPointer] > distanceArray[priorityQueue[childNode]]) {
			priorityQueue[firstParentNode] = priorityQueue[childNode];
			priorityQueue[childNode] = parentPointer;
		}
		
		
		
		for (int parentNode = firstParentNode - 1; parentNode >= 0; parentNode--) {
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
