package ch.hepia.it.cffPathFinder.backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Graph {
	private final List<Edge> edges;
	private final List<Vertex> vertices;

	public Graph () {
		edges = new ArrayList<>();
		vertices = new ArrayList<>();
	}

	public Graph (Edge... edges) {
		this();
		for (Edge edge : edges) {
			addToGraph(edge);
		}
	}

	public void addToGraph (Edge edge) {
		if (this.edges.contains(edge)) {
			return;
		}
		this.edges.add(edge);
		if (!this.vertices.contains(edge.getV1())) {
			this.vertices.add(edge.getV1());
		}
		if (!this.vertices.contains(edge.getV2())) {
			this.vertices.add(edge.getV2());
		}
	}

	public boolean isConnex () {
		//{Passed, tested}
		Map<Stop, Boolean[]> checks = new HashMap<>();

		for (Vertex v : vertices) {
			checks.put(v.getStop(), new Boolean[]{false, false});
		}

		Stop s;
		while ((s = getNextPointToCheck(checks)) != null) {
			checks.get(s)[0] = true;
			checks.get(s)[1] = true;
			List<Edge> edges = edgesFromStop(s);
			for (Edge e : edges) {
				checks.get(e.getOtherStop(s))[0] = true;
			}
		}

		return false;
	}

	private Stop getNextPointToCheck (Map<Stop, Boolean[]> map) {
		for (Stop s : map.keySet()) {
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
}
