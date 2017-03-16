package ch.hepia.it.cffPathFinder.backend;

public class Stop implements Comparable<Stop> {
	private final String name;
	private final int xCoord;
	private final int yCoord;
	private final Vertex vertex;

	public Stop (String name, int xCoord, int yCoord) {
		this.name = name;
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		vertex = new Vertex(this);
	}

	public String getName () {
		return name;
	}

	public int getxCoord () {
		return xCoord;
	}

	public int getyCoord () {
		return yCoord;
	}

	public Vertex getVertex () {
		return vertex;
	}

	@Override
	public String toString () {
		return name;
	}

	@Override
	public boolean equals (Object obj) {
		if (obj instanceof Stop) {
			Stop st = (Stop) obj;
			return this.name.equals(st.name);
		}
		return false;
	}

	@Override
	public int compareTo (Stop o) {
		return this.name.compareTo(o.name);
	}
}
