package ch.hepia.it.cffPathFinder.tests;

import ch.hepia.it.cffPathFinder.backend.AntsSolver;
import ch.hepia.it.cffPathFinder.backend.Dijkstra;
import ch.hepia.it.cffPathFinder.backend.Floyd;
import ch.hepia.it.cffPathFinder.backend.Graph;
import ch.hepia.it.cffPathFinder.backend.Path;
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
//		System.out.println("-----------XML Output-----------");
//		try {
//			XMLTools.writeToXML("data/out.xml", g);
//		} catch (TransformerException e) {
//			e.printStackTrace();
//		}
		System.out.println("-----------FLOYD-----------");
		Path floyd = Floyd.getInstance().shortestPath(g, g.getVertex("Sion"), g.getVertex("Zurich"));
		System.out.println(floyd);
		System.out.println("Cost " + floyd.getCost());
		System.out.println("-----------Dijkstra-----------");
		Path dijkstra = Dijkstra.getInstance().shortestPath(g, g.getVertex("Sion"), g.getVertex("Zurich"));
		System.out.println(dijkstra);
		System.out.println("Cost " + dijkstra.getCost());
		
		System.out.println("-----------ACO-----------");
		Path aco = AntsSolver.getInstance().shortestPath(g, g.getVertex("Sion"), g.getVertex("Zurich"));
		System.out.println(aco);
		System.out.println("Cost " + aco.getCost());
	}
}
