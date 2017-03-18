package ch.hepia.it.cffPathFinder.tests;

import ch.hepia.it.cffPathFinder.backend.Edge;
import ch.hepia.it.cffPathFinder.backend.Graph;
import ch.hepia.it.cffPathFinder.backend.Stop;
import ch.hepia.it.cffPathFinder.data.XMLTools;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class TestParser {
	public static void main (String[] args) {
		Stop s1 = new Stop("Lausanne", 10, 10);
		Stop s2 = new Stop("Zurich", 10, 10);
		Stop s3 = new Stop("Geneva", 10, 10);
		Stop s4 = new Stop("Bern", 10, 10);

		Edge e1 = new Edge(s1, s2, 30);
		Edge e2 = new Edge(s3, s4, 70);

		Graph g = new Graph(e1, e2);
		System.out.println(g.isConnex()); //false
		System.out.println(g);
		try {
			Graph gPrime = XMLTools.parse("data/villes.xml");
			System.out.println(gPrime.isConnex()); //true
			System.out.println(gPrime);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}

	}
}
