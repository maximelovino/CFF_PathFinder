package ch.hepia.it.cffPathFinder;

import ch.hepia.it.cffPathFinder.backend.Dijkstra;
import ch.hepia.it.cffPathFinder.backend.Floyd;
import ch.hepia.it.cffPathFinder.backend.Graph;
import ch.hepia.it.cffPathFinder.backend.Path;
import ch.hepia.it.cffPathFinder.data.XMLTools;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

	public static void main (String[] args) throws IOException {
		// permet de prendre les entrées pour le menu
		// soit du clavier, d'un fichier ou de la ligne de commande
		Scanner in;
		switch (args.length) {
			case 0:
				in = new Scanner(System.in);
				break;
			case 1:
				in = new Scanner(new File(args[0]));
				break;
			default:
				String source = args[0];
				for (int i = 1; i < args.length; i++) source += " " + args[i];
				in = new Scanner(source);
		}

		//String filePath = System.getProperty("user.dir") + File.separator + "villes.xml";
		String filePath = "data/villes.xml";
		//lire le fichier villes.xml avec votre code
		Graph graph = null;
		try {
			graph = XMLTools.parse(filePath);
		} catch (Exception e) {
			System.err.println("There was a problem while loading the file...Exiting");
			System.exit(1);
		}
		System.err.println("Le fichier XML " + filePath + " a été chargé\n");
		int choix = 0;
		do {
			// les impressions du menu sont envoyées sur le canal d'erreur
			// pour les différencier des sorties de l'application
			// lesquelles sont envoyées sur la sortie standard
			System.err.println("Choix  0: quitter");
			System.err.println("Choix  1: liste des villes");
			System.err.println("Choix  2: matrice des poids");
			System.err.println("Choix  3: liste des poids");
			System.err.println("Choix  4: matrice des temps de parcours (Floyd)");
			System.err.println("Choix  5: matrice des précédences (Floyd)");
			System.err.println("Choix  6: temps de parcours entre deux villes (Floyd)");
			System.err.println("Choix  7: parcours entre deux villes (Floyd)");
			System.err.println("Choix  8: tableau des temps de parcours (Dijkstra)");
			System.err.println("Choix  9: tableau des précédences (Dijkstra)");
			System.err.println("Choix 10: temps de parcours entre deux villes (Dijkstra)");
			System.err.println("Choix 11: parcours entre deux villes (Dijkstra)");
			System.err.println("Choix 12: ajout d'une ville");
			System.err.println("Choix 13: ajout d'une liaison");
			System.err.println("Choix 14: suppression d'une ville");
			System.err.println("Choix 15: suppression d'une liaison");
			System.err.println("Choix 16: graphe connexe?");
			System.err.println("Choix 17: sauver (format XML)");

			System.err.println("Entrez votre choix: ");
			choix = in.nextInt();
			String str1, str2, str3;
			Path p;
			switch (choix) {
				case 1:
					System.out.println(graph.verticesToString());
					break;
				case 2:
					// Output "inf" instead of Integer.MAX_VALUE
					System.out.println(graph.adjacencyMatrixString());
					break;
				case 3:
					System.out.println(graph.adjacencyListString());
					break;
				case 4:
					// Output "inf" instead of Integer.MAX_VALUE
					Floyd.getInstance().shortestPath(graph, null, Floyd.COST_VIEW);
					break;
				case 5:
					// Print -1 if no predecessor
					Floyd.getInstance().shortestPath(graph, null, Floyd.PRECEDENCE_VIEW);
					break;
				case 6:
					System.err.println("Ville d'origine:");
					str1 = in.next();
					System.err.println("Ville de destination:");
					str2 = in.next();
					System.err.print("Distance: ");
					p = Floyd.getInstance().shortestPath(graph, graph.getVertex(str1), graph.getVertex(str2));
					System.out.println(p.getCost());
					break;
				case 7:
					System.err.println("Ville d'origine:");
					str1 = in.next();
					System.err.println("Ville de destination:");
					str2 = in.next();
					System.err.print("Parcours: ");
					p = Floyd.getInstance().shortestPath(graph, graph.getVertex(str1), graph.getVertex(str2));
					System.out.println(p);
					break;
				case 8:
					System.err.println("Ville d'origine:");
					str1 = in.next();
					Dijkstra.getInstance().shortestPath(graph, graph.getVertex(str1), Dijkstra.COST_VIEW);
					break;
				case 9:
					System.err.println("Ville d'origine:");
					str1 = in.next();
					Dijkstra.getInstance().shortestPath(graph, graph.getVertex(str1), Dijkstra.PRECEDENCE_VIEW);
					break;
				case 10:
					System.err.println("Ville d'origine:");
					str1 = in.next();
					System.err.println("Ville de destination:");
					str2 = in.next();
					System.err.print("Distance: ");
					// Output "inf" instead of Integer.MAX_VALUE
					p = Dijkstra.getInstance().shortestPath(graph, graph.getVertex(str1), graph.getVertex(str2));
					System.out.println(p.getCost());
					break;
				case 11:
					System.err.println("Ville d'origine:");
					str1 = in.next();
					System.err.println("Ville de destination:");
					str2 = in.next();
					System.err.print("Parcours: ");
					p = Dijkstra.getInstance().shortestPath(graph, graph.getVertex(str1), graph.getVertex(str2));
					System.out.println(p);
					break;
				case 12:
					System.err.println("Nom de la ville:");
					str1 = in.next();
					try {
						graph.addVertex(str1);
					} catch (RuntimeException e) {
						System.err.println(e.getMessage());
					}
					break;
				case 13:
					System.err.println("Ville d'origine:");
					str1 = in.next();
					System.err.println("Ville de destination:");
					str2 = in.next();
					System.err.println("Temps de parcours:");
					str3 = in.next();
					try {
						graph.addEdge(str1, str2, Integer.valueOf(str3));
					} catch (NumberFormatException e) {
						System.err.println("The cost you inputted is not an int");
					} catch (RuntimeException e) {
						System.err.println(e.getMessage());
					}
					break;
				case 14:
					System.err.println("Nom de la ville:");
					str1 = in.next();
					try {
						graph.removeVertex(str1);
					} catch (RuntimeException e) {
						System.err.println(e.getMessage());
					}
					break;
				case 15:
					System.err.println("Ville d'origine:");
					str1 = in.next();
					System.err.println("Ville de destination:");
					str2 = in.next();
					try {
						graph.removeEdge(str1, str2);
					} catch (RuntimeException e) {
						System.err.println(e.getMessage());
					}
					break;
				case 16:
					System.out.println(graph.isConnex());
					break;
				case 17:
					System.err.println("Nom du fichier XML:");
					str1 = in.next();
					try {
						XMLTools.writeToXML(str1, graph);
					} catch (Exception e) {
						System.err.println("There was a problem with the output of the xml file");
						e.printStackTrace(System.err);
					}
					break;
			}
		} while (choix != 0);
	}
}











