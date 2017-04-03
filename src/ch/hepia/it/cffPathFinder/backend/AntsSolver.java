package ch.hepia.it.cffPathFinder.backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AntsSolver implements PathFinder {
	private static AntsSolver instance = new AntsSolver();
	private static final int ANTS_COUNT = 1000;
	private static final int STEP_COUNT = 1000000;
	private static final double ALPHA = 1;
	private static final double BETA = 1;


	private AntsSolver() {

	}

	public static AntsSolver getInstance() {
		return instance;
	}

	@Override
	public Path shortestPath(Graph g, Vertex v1, Vertex v2) {
		Map<Edge, Integer> pheromoneTrail = new HashMap<>();
		Ant[] ants = new Ant[ANTS_COUNT];
		for (int i = 0; i < ants.length; i++) {
			ants[i] = new Ant(v1);
		}

		for (Edge e : g.getEdges()) {
			pheromoneTrail.put(e, 0);
		}

		for (int i = 0; i < STEP_COUNT; i++) {
			for (Ant ant : ants) {
				ant.update(g, pheromoneTrail);
			}
		}
		return null;
	}

	@Override
	public void shortestPath(Graph g, Vertex v1, ViewType viewType) {

	}

	private class Ant {
		private List<Edge> path;
		private Vertex currentVertex;
		private boolean arrived;
		private boolean rest;

		public Ant(Vertex v) {
			this.currentVertex = v;
			this.path = new ArrayList<>();
			this.arrived = false;
			this.rest = false;
		}

		public void update(Graph g, Map<Edge, Integer> ph) {
		}
	}
}
