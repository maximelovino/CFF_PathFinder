package ch.hepia.it.cffPathFinder.tests;

import ch.hepia.it.cffPathFinder.backend.Graph;
import ch.hepia.it.cffPathFinder.data.XMLTools;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

public class TestOutput {
	public static void main (String[] args) throws IOException, SAXException, ParserConfigurationException {
		Graph g = XMLTools.parse("data/villes.xml");
		System.out.println("-----------CITIES-----------");
		System.out.println(g.verticesToString());
		System.out.println("-----------LIST-----------");
		System.out.println(g.adjacencyListString());
		System.out.println("-----------MATRIX-----------");
		System.out.println(g.adjacencyMatrixString());
		System.out.println("-----------XML Output-----------");
		try {
			XMLTools.writeToXML("data/out.xml", g);
		} catch (TransformerException e) {
			e.printStackTrace();
		}

	}
}
