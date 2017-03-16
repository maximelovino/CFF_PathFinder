package ch.hepia.it.cffPathFinder.data;

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

public class XMLTools {

	public static List<Stop> getStops (String path) throws ParserConfigurationException, IOException, SAXException {
		ArrayList<Stop> stops = new ArrayList<>();
		File input = new File(path);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(input);
		doc.getDocumentElement().normalize();
		System.out.println("Root element: " + doc.getDocumentElement().getNodeName());


		NodeList stopsList = doc.getElementsByTagName("ville");

		for (int i = 0; i < stopsList.getLength(); i++) {
			Node n = stopsList.item(i);

			if (n.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) n;
				String name = element.getElementsByTagName("nom").item(0).getTextContent();
				name = name.substring(1, name.length() - 1);
				String xString = element.getElementsByTagName("longitude").item(0).getTextContent();
				xString = xString.substring(1, xString.length() - 1);
				int x = Integer.valueOf(xString);
				String yString = element.getElementsByTagName("latitude").item(0).getTextContent();
				yString = yString.substring(1, yString.length() - 1);
				int y = Integer.valueOf(xString);
				stops.add(new Stop(name, x, y));
			}
		}
		return stops;
	}
}
