package algorithm;

public class Dijkstra {

	private int startNodeID;
	private int pathLength, pathCost;
	private int[] path;

	private int[][] dijkstraArray;
	private int[] heap;

	public Dijkstra(int nodeAmount, int startNodeID) {
		this.startNodeID = startNodeID;
		this.pathLength = 0;
		this.pathCost = 0;
		this.path = new int[nodeAmount];

		this.dijkstraArray = new int[nodeAmount][4];
		this.heap = new int[nodeAmount];
	}
}
