package ch.hepia.it.cffPathFinder.backend;

import java.util.*;

/**
 * Class representing the Graph
 */
public class Graph {
	private final List<Edge> edges;
	private final List<Vertex> vertices;

	/**
	 * Constructor that initializes an empty Graph
	 */
	public Graph () {
		edges = new ArrayList<>();
		vertices = new ArrayList<>();
	}

	/**
	 * Method to add a Vertex to the Graph (if not already done)
	 *
	 * @param v The Vertex to add
	 * @throws RuntimeException If the Vertex already exists
	 */
	public void addVertex (Vertex v) throws RuntimeException {
		if (!vertices.contains(v)) {
			vertices.add(v);
		} else {
			throw new RuntimeException("The vertex exists already");
		}
	}

	/**
	 * Method to add a Vertex by name (if there isn't one already)
	 *
	 * @param name The name of the Vertex to add
	 * @throws RuntimeException If the Vertex already exists
	 */
	public void addVertex (String name) throws RuntimeException {
		if (this.getVertex(name) == null) {
			addVertex(new Vertex(name));
		} else {
			throw new RuntimeException("The vertex exists already");
		}
	}

	/**
	 * Method to remove a Vertex by name
	 *
	 * @param city The name of the Vertex to remove
	 * @throws RuntimeException If the Vertex doesn't exist
	 */
	public void removeVertex (String city) throws RuntimeException {
		Vertex v = this.getVertex(city);

		if (v != null) {
			for (Edge e : this.edgesFromVertex(v)) {
				this.edges.remove(e);
			}
			vertices.remove(v);
		} else {
			throw new RuntimeException("Vertex " + city + " doesn't exist");
		}
	}

	/**
	 * Method to add an Edge between two Vertices by names
	 *
	 * @param city1 The name of the first Vertex
	 * @param city2 The name of the second Vertex
	 * @param cost  The cost of the Edge
	 * @throws RuntimeException If one or both of the Vertices don't exist
	 */
	public void addEdge (String city1, String city2, int cost) throws RuntimeException {
		Vertex v1 = this.getVertex(city1);
		Vertex v2 = this.getVertex(city2);

		if (v1 != null && v2 != null) {
			Edge e = new Edge(v1, v2, cost);
			edges.add(e);
		} else {
			throw new RuntimeException((v1 == null ? city1 : "") + (v2 == null && v1 == null ? " and " : "") + (v2 == null ? city2 : "") + " not in the list of vertices");
		}
	}

	/**
	 * Method to remove an Edge between two Vertices by names
	 *
	 * @param city1 The name of the first Vertex
	 * @param city2 The name of the second Vertex
	 * @throws RuntimeException If one or both of the Vertices don't exist, or the Edge doesn't exist
	 */
	public void removeEdge (String city1, String city2) throws RuntimeException {
		Vertex v1 = this.getVertex(city1);
		Vertex v2 = this.getVertex(city2);
		boolean ok = false;

		if (v1 != null && v2 != null) {
			for (Edge e : this.edgesFromVertex(v1)) {
				if (e.getOtherVertex(v1).equals(v2)) {
					this.edges.remove(e);
					ok = true;
					break;
				}
			}
		} else {
			throw new RuntimeException((v1 == null ? city1 : "") + (v2 == null && v1 == null ? " and " : "") + (v2 == null ? city2 : "") + " not in the list of vertices");
		}
		if (!ok) {
			throw new RuntimeException("No edge was found");
		}
	}

	/**
	 * @return If the graph is connex or not
	 */
	public boolean isConnex () {
		//{Reachable, tested}
		Map<Vertex, Boolean[]> checks = new HashMap<>();

		for (Vertex v : vertices) {
			checks.put(v, new Boolean[]{false, false});
		}

		Vertex v;
		//we set the first one as reachable, so we can continue
		checks.get(vertices.get(0))[0] = true;

		while ((v = getNextPointToCheck(checks)) != null) {
			checks.get(v)[1] = true;
			List<Edge> edges = edgesFromVertex(v);
			for (Edge e : edges) {
				checks.get(e.getOtherVertex(v))[0] = true;
			}
		}

		for (Boolean[] vals : checks.values()) {
			if (!vals[0]) return false;
		}

		return true;
	}

	/**
	 * Method to get the next point to check in connexity check
	 *
	 * @param map The map of the test
	 * @return The Vertex to check
	 */
	private Vertex getNextPointToCheck (Map<Vertex, Boolean[]> map) {
		for (Vertex s : map.keySet()) {
			Boolean[] vals = map.get(s);
			if (vals[0] && !vals[1]) {
				return s;
			}
		}
		return null;
	}

	/**
	 * Method to get a List of the Edges from a Vertex
	 *
	 * @param v The Vertex to get the Edges from
	 * @return The List of the Edges from that Vertex
	 */
	public List<Edge> edgesFromVertex (Vertex v) {
		List<Edge> result = new ArrayList<>();
		for (Edge e : edges) {
			if (e.hasVertex(v))
				result.add(e);
		}
		return result;
	}

	/**
	 * Method to get a Vertex by name
	 *
	 * @param name The name of the Vertex to get
	 * @return The Vertex, or null if doesn't exist
	 */
	public Vertex getVertex (String name) {
		for (Vertex v : vertices) {
			if (v.getName().equals(name)) return v;
		}
		return null;
	}

	/**
	 * @return The list of Edges
	 */
	public List<Edge> getEdges () {
		return edges;
	}

	/**
	 * @return The list of Vertices
	 */
	public List<Vertex> getVertices () {
		return vertices;
	}

	/**
	 * @return String representation of the Graph
	 */
	@Override
	public String toString () {
		return edges.toString();
	}

	/**
	 * @return A string representation of the Graph as an adjacency list
	 */
	public String adjacencyListString () {
		String out = "";
		for (Vertex v : vertices) {
			out += v.getName();
			List<Edge> edges = edgesFromVertex(v);
			for (Edge e : edges) {
				out += " [" + e.getOtherVertex(v).getName() + ":" + String.valueOf(e.getCost()) + "]";
			}
			out += "\n";
		}
		return out;
	}

	/**
	 * @return A string representation of all the Vertices in the Graph
	 */
	public String verticesToString () {
		String out = "";
		for (int i = 0; i < vertices.size(); i++) {
			out += "[" + String.valueOf(i) + ":" + vertices.get(i).getName() + "]";
			if (i != vertices.size() - 1) {
				out += " ";
			}
		}
		return out;
	}

	/**
	 * @return A string representation of the Graph as an adjacency matrix
	 */
	public String adjacencyMatrixString () {
		Map<Vertex, Integer> inverseCorrespondance = new HashMap<>();

		for (int i = 0; i < vertices.size(); i++) {
			inverseCorrespondance.put(vertices.get(i), i);
		}

		String out = "";
		for (Vertex v : vertices) {
			int[] values = new int[vertices.size()];
			Arrays.fill(values, Integer.MAX_VALUE);
			values[inverseCorrespondance.get(v)] = 0;
			for (Edge e : edgesFromVertex(v)) {
				values[inverseCorrespondance.get(e.getOtherVertex(v))] = e.getCost();
			}
			for (int i = 0; i < values.length; i++) {
				out += values[i] == Integer.MAX_VALUE ? "inf" : String.valueOf(values[i]);
				out += i != values.length - 1 ? "\t" : "\n";
			}
		}
		return out;
	}
}
