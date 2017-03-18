package ch.hepia.it.cffPathFinder.backend;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class Dijkstra implements PathFinder {
	private static Dijkstra instance = new Dijkstra();
	private Map<Vertex, Integer> distances;
	private Map<Vertex, Vertex> precendence;
	
	private Dijkstra () {
	}

	public static Dijkstra getInstance () {
		return instance;
	}

	public Path shortestPath (Graph g, Vertex v1, Vertex v2) {
		distances = new HashMap<>();
		precendence = new HashMap<>();
		Queue<Vertex> queue = new PriorityQueue<>((Vertex o1, Vertex o2)->Integer.compare(distances.get(o1), distances.get(o2)));
		for (Vertex v : g.getVertices()) {
			distances.put(v, Integer.MAX_VALUE);
			precendence.put(v, null);
			queue.add(v);
		}
		//distance from source to source = 0
		distances.put(v1, 0);
		
		while(!queue.isEmpty()) {
			Vertex v = queue.poll();
			if(v == v2) {
				break;
			}
			
			for (Edge e : g.edgesFromVertex(v)) {
				Vertex u = e.getOtherVertex(v);
				int alt = distances.get(v) + e.getCost();
				if(alt < distances.get(u)) {
					distances.put(u, alt);
					precendence.put(u, v);
				}
			}
		}
		
		Path p = new Path();
		while(precendence.get(v2) != null) {
			p.insertAtBeginning(v2);
			v2 = precendence.get(v2);
		}
		p.insertAtBeginning(v2);
		return p;
	}
}
