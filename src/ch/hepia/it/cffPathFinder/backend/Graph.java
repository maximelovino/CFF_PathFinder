package ch.hepia.it.cffPathFinder.backend;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
		return false;
	}

	public List<Edge> edgesFromStop (Stop s) {
		List<Edge> result = new ArrayList<>();
		for (Edge e : edges) {
			if (e.hasStop(s))
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
