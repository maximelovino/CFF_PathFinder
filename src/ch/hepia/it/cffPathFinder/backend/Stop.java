package ch.hepia.it.cffPathFinder.backend;

public class Stop extends Vertex implements Comparable<Stop> {
	private float xCoord;
	private float yCoord;

	public Stop (String name, int xCoord, int yCoord) {
		super(name);
		this.xCoord = xCoord;
		this.yCoord = yCoord;
	}


	public float getxCoord () {
		return xCoord;
	}

	public float getyCoord () {
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


	public void setxCoord(float xCoord) {
		this.xCoord = xCoord;
	}


	public void setyCoord(float yCoord) {
		this.yCoord = yCoord;
	}
}
