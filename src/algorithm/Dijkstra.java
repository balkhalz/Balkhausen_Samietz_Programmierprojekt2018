package algorithm;

public class Dijkstra {
	private static int [] edgeOffset;
	private static int [][] edgeArray;
	
	private static int prioArrayLength;
	private static int [] prioArray, path;
	private static int [][] nodeArray;
	
	private static int tOffset, mCtr;
	private static int [] tNode, tEdge;

	public static int[] calculatePathBetween(int source, int target) {
		
		//edgeOffset = Data.getEdgeOffset();
		//edgeArray = Data.getEdgeArray();

		prioArrayLength = edgeOffset.length;
		prioArray = new int [prioArrayLength];
		nodeArray = new int [edgeOffset.length][3];

		for (int ctr = 0; ctr < edgeOffset.length; ctr++) {
			prioArray [ctr] = ctr;
			nodeArray [ctr][0] = ctr;
			nodeArray [ctr][1] = Integer.MAX_VALUE;
			nodeArray [ctr][2] = -1;
		}
	
		prioArray[source] = 0;
		prioArray[0] = source;
		nodeArray [source][1] = 0;
	
	while (true) {
		
		tOffset = edgeOffset[prioArray[0]];
		mCtr = 0;
		
		while (tOffset + mCtr < edgeArray.length && edgeArray[tOffset + mCtr][0] == prioArray[0]) {

			tNode = nodeArray[prioArray[0]];
			tEdge = edgeArray[tOffset + mCtr];

			if (nodeArray[tEdge[1]][1] > tNode[1] + tEdge[2]) {
				nodeArray[tEdge[1]][1] = tNode[1] + tEdge[2];
				nodeArray[tEdge[1]][2] = prioArray[0];
			}
			mCtr++;
		}

		prioArray[0] = prioArray[prioArrayLength - 1];
		prioArrayLength--;
		
		if (prioArrayLength == 0)
			break;
		
		sortPrioList();
		
		if (nodeArray[target][1] <= nodeArray[prioArray[0]][1])
			break;
	}
	
	tNode = nodeArray [target];
	mCtr = 0;
	
	while (tNode [2] != -1) {
		tNode = nodeArray [tNode [2]];
		mCtr++;
	}
	
	path = new int [mCtr + 1];
	tNode = nodeArray [target];

	while (mCtr != 0) {
		path [mCtr] = tNode [0];
		tNode = nodeArray [tNode [2]];
		mCtr--;
	}
	
	path [0] = source;
	
	return path;
}

public static void sortPrioList() {
	
	int tempInt;

	for (int ctr = prioArrayLength / 2; ctr >= 0; ctr--) {

		if (ctr * 2 + 1 < prioArrayLength) {
			if (nodeArray[prioArray[ctr]][1] > nodeArray[prioArray[ctr * 2 + 1]][1]) {
				tempInt = prioArray[ctr];
				prioArray[ctr] = prioArray[ctr * 2 + 1];
				prioArray[ctr * 2 + 1] = tempInt;
				ctr = ctr * 2 + 1;
			}
		}

		if (ctr * 2 + 2 < prioArrayLength) {
			if (nodeArray[prioArray[ctr]][1] > nodeArray[prioArray[ctr * 2 + 2]][1]) {
				tempInt = prioArray[ctr];
				prioArray[ctr] = prioArray[ctr * 2 + 2];
				prioArray[ctr * 2 + 2] = tempInt;
				ctr = ctr * 2 + 2;
			}
		}
	}
}

	public static void testData() {
		int[] path = Dijkstra.calculatePathBetween(15, 16);
		
		if (path[0] == 15 && path[1] == 16)
			System.out.println("pathfinder 1 worked correctly!");
		
		path = Dijkstra.calculatePathBetween(51, 969967);
		
		if (path[0] == 51 && path[1] == 52 && path[2] == 969967)
			System.out.println("pathfinder 2 worked correctly!");
	}
}
