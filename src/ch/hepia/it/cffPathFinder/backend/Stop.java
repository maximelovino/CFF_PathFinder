package ch.hepia.it.cffPathFinder.backend;

public class Stop extends Vertex implements Comparable<Stop> {
	private final int xCoord;
	private final int yCoord;

	public Stop (String name, int xCoord, int yCoord) {
		super(name);
		this.xCoord = xCoord;
		this.yCoord = yCoord;
	}


	public int getxCoord () {
		return xCoord;
	}

	public int getyCoord () {
		return yCoord;
	}


	@Override
	public String toString () {
		return this.getName();
	}

	@Override
	public boolean equals (Object obj) {
		if (obj instanceof Stop) {
			Stop st = (Stop) obj;
			return this.getName().equals(st.getName());
		}
		return false;
	}

	@Override
	public int compareTo (Stop o) {
		return this.getName().compareTo(o.getName());
	}
}
