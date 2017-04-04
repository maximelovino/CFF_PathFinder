package ch.hepia.it.cffPathFinder.data;

import ch.hepia.it.cffPathFinder.backend.Edge;
import ch.hepia.it.cffPathFinder.backend.Graph;
import ch.hepia.it.cffPathFinder.backend.Stop;
import ch.hepia.it.cffPathFinder.backend.Vertex;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.font.TransformAttribute;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class with functions for parsing and exporting XML files
 */
public abstract class XMLTools {

	/**
	 * Function to parse the XML data file for the train network
	 *
	 * @param path The path of the file to parse
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 * @return The Graph of the network
	 */
	public static Graph parse (String path) throws ParserConfigurationException, IOException, SAXException {
		Graph g = new Graph();
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
				g.addVertex(new Stop(name, x, y));
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
				try {
					g.addEdge(v1, v2, cost);
				} catch (RuntimeException e) {
					e.printStackTrace();
				}
			}
		}
		return g;
	}

	/**
	 * Function to export an XML data file from our Graph
	 *
	 * @param xmlFilePath The path of the file to export
	 * @param graph       The Graph we want to export
	 * @throws ParserConfigurationException
	 * @throws TransformerException
	 */
	public static void writeToXML (String xmlFilePath, Graph graph) throws ParserConfigurationException, TransformerException {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbFactory.newDocumentBuilder();

		Document doc = db.newDocument();

		//Root element <reseau>...</reseau>
		Element rootElement = doc.createElement("reseau");
		doc.appendChild(rootElement);

		//Title element <titre>CFF</titre>
		Element titleElement = doc.createElement("titre");
		titleElement.appendChild(doc.createTextNode("CFF"));
		rootElement.appendChild(titleElement);

		List<Vertex> vertices = graph.getVertices();

		for (Vertex v : vertices) {
			if (v instanceof Stop) {
				Stop st = (Stop) v;
				Element city = doc.createElement("ville");
				Element name = doc.createElement("nom");
				name.appendChild(doc.createTextNode(st.getName()));
				Element longitude = doc.createElement("longitude");
				longitude.appendChild(doc.createTextNode(String.valueOf(st.getxCoord())));
				Element latitude = doc.createElement("latitude");
				latitude.appendChild(doc.createTextNode(String.valueOf(st.getyCoord())));
				city.appendChild(name);
				city.appendChild(longitude);
				city.appendChild(latitude);
				rootElement.appendChild(city);
			}
		}

		List<Edge> edges = graph.getEdges();

		for (Edge e : edges) {
			Element link = doc.createElement("liaison");
			Element city1 = doc.createElement("vil_1");
			city1.appendChild(doc.createTextNode(e.getV1().getName()));
			Element city2 = doc.createElement("vil_2");
			city2.appendChild(doc.createTextNode(e.getV2().getName()));
			Element cost = doc.createElement("temps");
			cost.appendChild(doc.createTextNode(String.valueOf(e.getCost())));
			link.appendChild(city1);
			link.appendChild(city2);
			link.appendChild(cost);
			rootElement.appendChild(link);
		}

		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(xmlFilePath));
		transformer.transform(source, result);
	}
}
