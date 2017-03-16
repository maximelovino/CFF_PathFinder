package ch.hepia.it.cffPathFinder.backend;

public class Edge {
	private final Vertex v1;
	private final Vertex v2;
	private final int cost;

	public Edge (Vertex v1, Vertex v2, int cost) {
		this.v1 = v1;
		this.v2 = v2;
		this.cost = cost;
	}

	public Edge (Stop s1, Stop s2, int cost) {
		this.v1 = s1.getVertex();
		this.v2 = s2.getVertex();
		this.cost = cost;
	}

	public Vertex getV1 () {
		return v1;
	}

	public Vertex getV2 () {
		return v2;
	}

	public int getCost () {
		return cost;
	}

	@Override
	public String toString () {
		return "(" + v1.toString() + " --" + String.valueOf(cost) + "--> " + v2.toString() + ")";
	}
}
