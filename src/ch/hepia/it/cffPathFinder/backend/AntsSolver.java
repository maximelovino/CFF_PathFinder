package ch.hepia.it.cffPathFinder.backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AntsSolver implements PathFinder {
	private static AntsSolver instance = new AntsSolver();
	private static final int ANTS_COUNT = 1000;
	private static final int STEP_COUNT = 2000;
	private static final double ALPHA = 1;
	private static final double BETA = 1;
	private static final double Q = 1;

	private AntsSolver() {

	}

	public static AntsSolver getInstance() {
		return instance;
	}

	@Override
	public Path shortestPath(Graph g, Vertex v1, Vertex v2) {
		Map<Edge, Float> pheromoneTrail = new HashMap<>();
		Path p = new Path();
		Ant[] ants = new Ant[ANTS_COUNT];
		for (int i = 0; i < ants.length; i++) {
			ants[i] = new Ant(v1, v2);
		}

		for (Edge e : g.getEdges()) {
			pheromoneTrail.put(e, 1f);
		}

		for (int i = 0; i < STEP_COUNT; i++) {
			for (Ant ant : ants) {
				ant.update(g, pheromoneTrail);
				/*
				for (Edge edge : g.getEdges()) {
					pheromoneTrail.put(edge, Math.max(pheromoneTrail.get(edge) - 1f, 1));
				}
				*/
			}
		}
		Vertex c = v1;
		while (c != v2) {
			p.insertAtEnd(c);
			float maxp = 0;
			Edge m = null;
			List<Edge> edges = g.edgesFromVertex(c);
			for (Edge e : edges) {
				if (pheromoneTrail.get(e) > maxp && !p.contains(e.getOtherVertex(c))) {
					m = e;
					maxp = pheromoneTrail.get(e);
				}
			}
			if (m == null) {
				for (Edge e : edges) {
					if (pheromoneTrail.get(e) > maxp) {
						m = e;
						maxp = pheromoneTrail.get(e);
					}
				}
			}
			p.setCost(p.getCost() + m.getCost());
			c = m.getOtherVertex(c);
		}
		p.insertAtEnd(v2);
		return p;
	}

	@Override
	public String shortestPath(Graph g, Vertex v1, ViewType viewType) {
		return null;
	}

	private class Ant {
		private List<Edge> path;
		private Vertex currentVertex;
		private boolean arrived;
		private final Vertex start, goal;

		private List<Vertex> visited;
		private Set<Edge> pheromoned;

		public Ant(Vertex v1, Vertex goal) {
			this.currentVertex = v1;
			this.path = new ArrayList<>();
			this.arrived = false;
			this.goal = goal;
			this.start = v1;
			this.visited = new ArrayList<>();
			this.visited.add(start);
			this.pheromoned = new HashSet<>();
		}

		public void update(Graph g, Map<Edge, Float> ph) {
			// ant blind progress
			if (!arrived) {
				Edge e = chooseEdge(g, ph);
				path.add(e);
				currentVertex = e.getOtherVertex(currentVertex);
				if (currentVertex == goal) {
					arrived = true;
				}
			} else if (arrived) {
				Edge e = path.remove(path.size() - 1);
				if (!pheromoned.contains(e)) {
					ph.put(e, (float) (ph.get(e) + Q / e.getCost()));
					pheromoned.add(e);
				}
				currentVertex = e.getOtherVertex(currentVertex);
				if (currentVertex == start) {
					arrived = false;
					path.clear();
					pheromoned.clear();
				}
			}
		}

		private Edge chooseEdge(Graph g, Map<Edge, Float> ph) {
			List<Edge> edges = new ArrayList<>(g.edgesFromVertex(currentVertex));
			for (Iterator<Edge> iterator = edges.iterator(); iterator.hasNext();) {
				Edge edge = iterator.next();
				for (Vertex v : visited) {
					if (edge.hasVertex(v)) {
						iterator.remove();
						break;
					}
				}
			}

			if (edges.size() == 0) {
				edges = g.edgesFromVertex(currentVertex);
			}

			float[] prob = new float[edges.size()];
			float sum = 0;
			for (int i = 0; i < prob.length; i++) {
				float etab = (float) Math.pow(1.0f / edges.get(i).getCost(), BETA);
				float taua = (float) Math.pow(ph.get(edges.get(i)), ALPHA);
				sum += etab * taua;
			}
			// compute probability for all edges
			for (int i = 0; i < prob.length; i++) {
				float etab = (float) Math.pow(1.0f / edges.get(i).getCost(), BETA);
				float taua = (float) Math.pow(ph.get(edges.get(i)), ALPHA);
				prob[i] = (etab * taua) / sum;
			}
			// Choose edge from probability
			double p = Math.random();
			double cp = 0;
			Edge r = null;
			for (int i = 0; i < prob.length; i++) {
				cp += prob[i];
				if (p <= cp) {
					r = edges.get(i);
					break;
				}
			}
			return r;
		}
	}
}
