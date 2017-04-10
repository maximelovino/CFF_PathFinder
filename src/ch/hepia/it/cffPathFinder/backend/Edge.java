package ch.hepia.it.cffPathFinder.backend;

/**
 * Class representing an Edge of the Graph
 */
public class Edge {
	private final Vertex v1;
	private final Vertex v2;
	private final int cost;

	/**
	 * Constructor for Edge
	 *
	 * @param v1   The first Vertex
	 * @param v2   The second Vertex
	 * @param cost The cost
	 */
	public Edge (Vertex v1, Vertex v2, int cost) {
		this.v1 = v1;
		this.v2 = v2;
		this.cost = cost;
	}

	/**
	 * @return The first Vertex
	 */
	public Vertex getV1 () {
		return v1;
	}

	/**
	 * @return The second Vertex
	 */
	public Vertex getV2 () {
		return v2;
	}

	/**
	 * @return The cost
	 */
	public int getCost () {
		return cost;
	}

	/**
	 * @param v The Vertex to check
	 * @return If the Edge has the Vertex v
	 */
	public boolean hasVertex (Vertex v) {
		return v1.equals(v) || v2.equals(v);
	}

	/**
	 * @param firstV The Vertex we already have
	 * @return The other Vertex, or null if the first Vertex is not contained in the Edge
	 */
	public Vertex getOtherVertex (Vertex firstV) {
		if (v1.equals(firstV)) {
			return v2;
		} else if (v2.equals(firstV)) {
			return v1;
		} else {
			return null;
		}
	}

	/**
	 * @return String representation of an Edge
	 */
	@Override
	public String toString () {
		return "(" + v1.toString() + " --" + String.valueOf(cost) + "--> " + v2.toString() + ")";
	}
}
