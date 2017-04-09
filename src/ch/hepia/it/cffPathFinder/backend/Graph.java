package ch.hepia.it.cffPathFinder.backend;

import java.util.*;

public class Graph {
	private final List<Edge> edges;
	private final List<Vertex> vertices;

	public Graph () {
		edges = new ArrayList<>();
		vertices = new ArrayList<>();
	}

	public void addVertex (Vertex v) {
		if (!vertices.contains(v)) {
			vertices.add(v);
		}
	}

	public void addVertex (String name) {
		if (this.getVertex(name) == null) {
			addVertex(new Vertex(name));
		} else {
			throw new RuntimeException("The vertex exists already");
		}
	}

	public void removeVertex (String city) {
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

	public void addEdge (String city1, String city2, int cost) {
		Vertex v1 = this.getVertex(city1);
		Vertex v2 = this.getVertex(city2);

		if (v1 != null && v2 != null) {
			Edge e = new Edge(v1, v2, cost);
			edges.add(e);
		} else {
			throw new RuntimeException((v1 == null ? city1 : "") + (v2 == null && v1 == null ? " and " : "") + (v2 == null ? city2 : "") + " not in the list of vertices");
		}
	}

	public void removeEdge (String city1, String city2) {
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

	private Vertex getNextPointToCheck (Map<Vertex, Boolean[]> map) {
		for (Vertex s : map.keySet()) {
			Boolean[] vals = map.get(s);
			if (vals[0] && !vals[1]) {
				return s;
			}
		}
		return null;
	}

	public List<Edge> edgesFromVertex (Vertex v) {
		List<Edge> result = new ArrayList<>();
		for (Edge e : edges) {
			if (e.hasVertex(v))
				result.add(e);
		}
		return result;
	}

	public Vertex getVertex (String name) {
		for (Vertex v : vertices) {
			if (v.getName().equals(name)) return v;
		}
		return null;
	}

	public List<Edge> getEdges () {
		return edges;
	}

	public List<Vertex> getVertices () {
		return vertices;
	}

	@Override
	public String toString () {
		return edges.toString();
	}

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
