package ch.hepia.it.cffPathFinder;

import ch.hepia.it.cffPathFinder.data.Parser;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class TestParser {
	public static void main (String[] args) {
		try {
			System.out.println(Parser.getStops("data/villes.xml"));
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
	}
}
