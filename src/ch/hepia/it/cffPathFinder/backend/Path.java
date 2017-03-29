package ch.hepia.it.cffPathFinder.backend;

import java.util.ArrayList;
import java.util.List;

public class Path {

	private List<Vertex> path;
	private int cost;
	
	public Path() {
		this.path = new ArrayList<>();
		this.cost = 0;
	}
	
	public void insertAtBeginning(Vertex v) {
		this.path.add(0, v);
	}
	
	public void insertAtEnd(Vertex v) {
		this.path.add(v);
	}

	public void setCost (int cost) {
		this.cost = cost;
	}

	public int getCost () {
		return cost;
	}

	@Override
	public String toString () {
		String out = "[";
		for (int i = 0; i < path.size(); i++) {
			out += path.get(i).toString() + (i != path.size() - 1 ? ":" : "]");
		}
		return out;
	}
}
