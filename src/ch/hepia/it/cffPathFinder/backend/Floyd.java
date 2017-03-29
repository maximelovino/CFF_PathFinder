package ch.hepia.it.cffPathFinder.backend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Floyd implements PathFinder {
	private static Floyd instance = new Floyd();
	public final static int COST_VIEW = 1;
	public final static int PRECEDENCE_VIEW = 2;
	private static int[][] distances;
	private static Vertex[][] precedences;
	private static Map<Vertex, Integer> inverseCorrespondance;

	private Floyd () {

	}

	public static Floyd getInstance () {
		return instance;
	}

	@Override
	public Path shortestPath (Graph g, Vertex v1, Vertex v2) {
		shortestPath(g, v1, NO_VIEW);
		Path p = new Path();
		int cost = distances[inverseCorrespondance.get(v1)][inverseCorrespondance.get(v2)];
		p.setCost(cost);
		while (v2 != null) {
			p.insertAtBeginning(v2);
			v2 = precedences[inverseCorrespondance.get(v2)][inverseCorrespondance.get(v1)];
		}
		return p;
	}

	@Override
	public void shortestPath (Graph g, Vertex v1, int viewType) {
		inverseCorrespondance = new HashMap<>();
		List<Vertex> vertices = g.getVertices();

		distances = new int[vertices.size()][vertices.size()];
		precedences = new Vertex[vertices.size()][vertices.size()];
		for (int i = 0; i < vertices.size(); i++) {
			for (int j = 0; j < vertices.size(); j++) {
				distances[i][j] = Integer.MAX_VALUE;
				precedences[i][j] = null;
			}
		}

		for (int i = 0; i < vertices.size(); i++) {
			inverseCorrespondance.put(vertices.get(i), i);
			distances[i][i] = 0;
		}
		for (Edge e : g.getEdges()) {
			int vTemp1 = inverseCorrespondance.get(e.getV1());
			int vTemp2 = inverseCorrespondance.get(e.getV2());
			distances[vTemp1][vTemp2] = e.getCost();
			distances[vTemp2][vTemp1] = e.getCost();
			precedences[vTemp1][vTemp2] = e.getV2();
			precedences[vTemp2][vTemp1] = e.getV1();
		}

		for (int i = 0; i < vertices.size(); i++) {
			for (int j = 0; j < vertices.size(); j++) {
				for (int k = 0; k < vertices.size(); k++) {
					if (!(distances[i][k] == Integer.MAX_VALUE || distances[k][j] == Integer.MAX_VALUE) && distances[i][j] > distances[i][k] + distances[k][j]) {
						distances[i][j] = distances[i][k] + distances[k][j];
						distances[j][i] = distances[i][k] + distances[k][j];
						precedences[i][j] = precedences[i][k];
						precedences[j][i] = precedences[j][k];
					}
				}
			}
		}

		switch (viewType) {
			case COST_VIEW:
				for (int i = 0; i < distances.length; i++) {
					for (int j = 0; j < distances[i].length; j++) {
						System.out.print(distances[i][j] == Integer.MAX_VALUE ? "inf" : distances[i][j]);
						System.out.print("\t");
					}
					System.out.println();
				}
				break;
			case PRECEDENCE_VIEW:
				for (int i = 0; i < precedences.length; i++) {
					for (int j = 0; j < precedences[i].length; j++) {
						System.out.print(precedences[i][j] == null ? -1 : inverseCorrespondance.get(precedences[i][j]));
						System.out.print("\t");
					}
					System.out.println();
				}
				break;
			default:
				break;
		}

	}
}
