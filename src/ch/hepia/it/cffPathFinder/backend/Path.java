package ch.hepia.it.cffPathFinder.backend;

import java.util.ArrayList;
import java.util.List;

public class Path {

	private List<Vertex> path;
	
	public Path() {
		this.path = new ArrayList<>();
	}
	
	public void insertAtBeginning(Vertex v) {
		this.path.add(0, v);
	}
	
	public void insertAtEnd(Vertex v) {
		this.path.add(v);
	}

	@Override
	public String toString () {
		return path.toString();
	}
}
