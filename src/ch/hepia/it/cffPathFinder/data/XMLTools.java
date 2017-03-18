package ch.hepia.it.cffPathFinder.data;

import ch.hepia.it.cffPathFinder.backend.Edge;
import ch.hepia.it.cffPathFinder.backend.Graph;
import ch.hepia.it.cffPathFinder.backend.Stop;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class XMLTools {

	public static Graph parse (String path) throws ParserConfigurationException, IOException, SAXException {
		Graph g = new Graph();
		List<Stop> stops = new ArrayList<>();
		File input = new File(path);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(input);
		doc.getDocumentElement().normalize();
		NodeList stopsList = doc.getElementsByTagName("ville");
		NodeList edgesList = doc.getElementsByTagName("liaison");

		for (int i = 0; i < stopsList.getLength(); i++) {
			Node n = stopsList.item(i);

			if (n.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) n;
				String name = element.getElementsByTagName("nom").item(0).getTextContent().trim();
				String xString = element.getElementsByTagName("longitude").item(0).getTextContent().trim();
				int x = Integer.valueOf(xString);
				String yString = element.getElementsByTagName("latitude").item(0).getTextContent().trim();
				int y = Integer.valueOf(yString);
				stops.add(new Stop(name, x, y));
			}
		}

		for (int i = 0; i < edgesList.getLength(); i++) {
			Node n = edgesList.item(i);
			if (n.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) n;
				String v1 = element.getElementsByTagName("vil_1").item(0).getTextContent().trim();
				String v2 = element.getElementsByTagName("vil_2").item(0).getTextContent().trim();
				String costString = element.getElementsByTagName("temps").item(0).getTextContent().trim();
				int cost = Integer.valueOf(costString);
				Stop s1 = findStop(stops, v1);
				Stop s2 = findStop(stops, v2);

				if (s1 != null && s2 != null) {
					Edge e = new Edge(s1, s2, cost);
					g.addToGraph(e);
				} else {
					System.err.println("Couldn't find " + v1 + " or " + v2);
				}
			}
		}
		return g;
	}

	private static Stop findStop (List<Stop> stops, String name) {
		//should use dichotomy if we put a sortedLists
		for (Stop st : stops) {
			if (st.getName().equals(name)) {
				return st;
			}
		}
		return null;
	}
}
