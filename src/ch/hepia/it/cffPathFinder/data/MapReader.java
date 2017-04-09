package ch.hepia.it.cffPathFinder.data;

import ch.hepia.it.cffPathFinder.backend.Graph;
import ch.hepia.it.cffPathFinder.backend.Stop;
import ch.hepia.it.cffPathFinder.backend.Vertex;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class MapReader {
	private static float maxx = 1, maxy = 1;
	private static float minx = Float.POSITIVE_INFINITY, miny = Float.POSITIVE_INFINITY;

	public static List<Float[]> readMap (String file, Graph g) {
		List<Float[]> points = new ArrayList<>();

		try {
			for (String s : Files.readAllLines(new File(file).toPath())) {
				String[] p = s.split(" ");
				Float[] ps = new Float[2];
				ps[0] = Float.parseFloat(p[0]);
				ps[1] = Float.parseFloat(p[1]);
				if (ps[0] > maxx) maxx = ps[0];
				if (ps[1] > maxy) maxy = ps[1];
				if (ps[0] < minx) minx = ps[0];
				if (ps[1] < miny) miny = ps[1];
				points.add(ps);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		//normalize points (align at 0,0 and scale in [0,1])
		for (Float[] p : points) {
			p[0] -= minx;
			p[1] -= miny;
			p[0] /= (maxx - minx);
			p[1] /= (maxy - miny);
		}

		for (Vertex v : g.getVertices()) {
			if (v instanceof Stop) {
				Stop s = (Stop) v;
				normalizeStop(s);
			}
		}
		return points;
	}

	public static void normalizeStop (Stop s) {
		s.setxCoord(s.getxCoord() - minx);
		s.setyCoord(s.getyCoord() - miny);

		s.setxCoord(s.getxCoord() / (maxx - minx));
		s.setyCoord(s.getyCoord() / (maxy - miny));
	}

}
