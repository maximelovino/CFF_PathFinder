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

	public Vertex getV1 () {
		return v1;
	}

	public Vertex getV2 () {
		return v2;
	}

	public int getCost () {
		return cost;
	}

	public boolean hasVertex (Vertex v) {
		return v1.equals(v) || v2.equals(v);
	}

	public Vertex getOtherVertex (Vertex firstV) {
		if (v1.equals(firstV)) {
			return v2;
		} else if (v2.equals(firstV)) {
			return v1;
		} else {
			return null;
		}
	}

	@Override
	public String toString () {
		return "(" + v1.toString() + " --" + String.valueOf(cost) + "--> " + v2.toString() + ")";
	}
}
