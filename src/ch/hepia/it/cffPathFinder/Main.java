package ch.hepia.it.cffPathFinder;

import ch.hepia.it.cffPathFinder.backend.Floyd;
import ch.hepia.it.cffPathFinder.backend.Graph;
import ch.hepia.it.cffPathFinder.backend.Path;
import ch.hepia.it.cffPathFinder.data.XMLTools;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.*;

public class Main {

	public static void main(String[] args) throws IOException {
		// permet de prendre les entrées pour le menu
		// soit du clavier, d'un fichier ou de la ligne de commande
		Scanner in;
		switch(args.length) {
			case 0:
				in = new Scanner(System.in);
				break;
			case 1:
				in = new Scanner(new File(args[0]));
				break;
			default:
				String source = args[0];
				for (int i = 1; i<args.length; i++) source += " " + args[i];
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
			switch(choix) {
				case 1:
					// format de sortie -> à générer avec votre code
					//System.out.println("[0:Geneve] [1:Lausanne] [2:Neuchatel] [3:Delemont] [4:Bale] [5:Berne] [6:Lucerne] [7:Zurich] [8:Schaffouse] [9:St.-Gall] [10:Coire] [11:St.-Moritz] [12:Bellinzone] [13:Andermatt] [14:Sion]");
					System.out.println(graph.verticesToString());
					break;
				case 2:
					// format de sortie -> à générer avec votre code
					// imprimer "inf" à la place Integer.MAX_VALUE
/*					System.out.println("0 34 inf inf inf inf inf inf inf inf inf inf inf inf inf");
					System.out.println("34 0 40 inf inf 67 inf inf inf inf inf inf inf inf 67");
					System.out.println("inf 40 0 49 87 42 inf inf inf inf inf inf inf inf inf");
					System.out.println("inf inf 49 0 34 60 inf inf inf inf inf inf inf inf inf");
					System.out.println("inf inf 87 34 0 66 73 60 inf inf inf inf inf inf inf");
					System.out.println("inf 67 42 60 66 0 83 79 inf inf inf inf inf inf 157");
					System.out.println("inf inf inf inf 73 83 0 46 inf inf inf inf 132 102 inf");
					System.out.println("inf inf inf inf 60 79 46 0 42 66 91 inf inf inf inf");
					System.out.println("inf inf inf inf inf inf inf 42 0 87 inf inf inf inf inf");
					System.out.println("inf inf inf inf inf inf inf 66 87 0 99 inf inf inf inf");
					System.out.println("inf inf inf inf inf inf inf 91 inf 99 0 116 inf 100 inf");
					System.out.println("inf inf inf inf inf inf inf inf inf inf 116 0 inf inf inf");
					System.out.println("inf inf inf inf inf inf 132 inf inf inf inf inf 0 84 inf");
					System.out.println("inf inf inf inf inf inf 102 inf inf inf 100 inf 84 0 162");
					System.out.println("inf 67 inf inf inf 157 inf inf inf inf inf inf inf 162 0");*/
					System.out.println(graph.adjacencyMatrixString());
					break;
				case 3:
					// format de sortie -> à générer avec votre code
/*					System.out.println("Geneve [Lausanne:34]");
					System.out.println("Lausanne [Geneve:34] [Neuchatel:40] [Berne:67] [Sion:67]");
					System.out.println("Neuchatel [Lausanne:40] [Delemont:49] [Bale:87] [Berne:42]");
					System.out.println("Delemont [Neuchatel:49] [Bale:34] [Berne:60]");
					System.out.println("Bale [Neuchatel:87] [Delemont:34] [Berne:66] [Lucerne:73] [Zurich:60]");
					System.out.println("Berne [Lausanne:67] [Neuchatel:42] [Delemont:60] [Bale:66] [Lucerne:83] [Zurich:79] [Sion:157]");
					System.out.println("Lucerne [Bale:73] [Berne:83] [Zurich:46] [Bellinzone:132] [Andermatt:102]");
					System.out.println("Zurich [Bale:60] [Berne:79] [Lucerne:46] [Schaffouse:42] [St.-Gall:66] [Coire:91]");
					System.out.println("Schaffouse [Zurich:42] [St.-Gall:87]");
					System.out.println("St.-Gall [Zurich:66] [Schaffouse:87] [Coire:99]");
					System.out.println("Coire [Zurich:91] [St.-Gall:99] [St.-Moritz:116] [Andermatt:100]");
					System.out.println("St.-Moritz [Coire:116]");
					System.out.println("Bellinzone [Lucerne:132] [Andermatt:84]");
					System.out.println("Andermatt [Lucerne:102] [Coire:100] [Bellinzone:84] [Sion:162]");
					System.out.println("Sion [Lausanne:67] [Berne:157] [Andermatt:162]");*/
					System.out.println(graph.adjacencyListString());
					break;
				case 4:
					// format de sortie -> à générer avec votre code
					// imprimer "inf" à la place Integer.MAX_VALUE
/*					System.out.println("0 34 74 123 157 101 184 180 222 246 271 387 316 263 101");
					System.out.println("34 0 40 89 123 67 150 146 188 212 237 353 282 229 67");
					System.out.println("74 40 0 49 83 42 125 121 163 187 212 328 257 227 107");
					System.out.println("123 89 49 0 34 60 107 94 136 160 185 301 239 209 156");
					System.out.println("157 123 83 34 0 66 73 60 102 126 151 267 205 175 190");
					System.out.println("101 67 42 60 66 0 83 79 121 145 170 286 215 185 134");
					System.out.println("184 150 125 107 73 83 0 46 88 112 137 253 132 102 217");
					System.out.println("180 146 121 94 60 79 46 0 42 66 91 207 178 148 213");
					System.out.println("222 188 163 136 102 121 88 42 0 87 133 249 220 190 255");
					System.out.println("246 212 187 160 126 145 112 66 87 0 99 215 244 199 279");
					System.out.println("271 237 212 185 151 170 137 91 133 99 0 116 184 100 262");
					System.out.println("387 353 328 301 267 286 253 207 249 215 116 0 300 216 378");
					System.out.println("316 282 257 239 205 215 132 178 220 244 184 300 0 84 246");
					System.out.println("263 229 227 209 175 185 102 148 190 199 100 216 84 0 162");
					System.out.println("101 67 107 156 190 134 217 213 255 279 262 378 246 162 0");*/
					Floyd.getInstance().shortestPath(graph, null, Floyd.COST_VIEW);
					break;
				case 5:
					// format de sortie -> à générer avec votre code
					// imprimer -1 si pas de prédécesseur
//					System.out.println("-1 0 1 2 3 1 5 5 7 7 7 10 6 14 1");
//					System.out.println("1 -1 1 2 3 1 5 5 7 7 7 10 6 14 1");
//					System.out.println("1 2 -1 2 3 2 5 5 7 7 7 10 6 6 1");
//					System.out.println("1 2 3 -1 3 3 4 4 7 7 7 10 6 6 1");
//					System.out.println("1 2 3 4 -1 4 4 4 7 7 7 10 6 6 1");
//					System.out.println("1 5 5 5 5 -1 5 5 7 7 7 10 6 6 1");
//					System.out.println("1 5 5 4 6 6 -1 6 7 7 7 10 6 6 1");
//					System.out.println("1 5 5 4 7 7 7 -1 7 7 7 10 6 6 1");
//					System.out.println("1 5 5 4 7 7 7 8 -1 8 7 10 6 6 1");
//					System.out.println("1 5 5 4 7 7 7 9 9 -1 9 10 6 10 1");
//					System.out.println("1 5 5 4 7 7 7 10 7 10 -1 10 13 10 13");
//					System.out.println("1 5 5 4 7 7 7 10 7 10 11 -1 13 10 13");
//					System.out.println("1 5 5 4 6 6 12 6 7 7 13 10 -1 12 13");
//					System.out.println("1 14 5 4 6 6 13 6 7 10 13 10 13 -1 13");
//					System.out.println("1 14 1 2 3 1 5 5 7 7 13 10 13 14 -1");
					Floyd.getInstance().shortestPath(graph, null, Floyd.PRECEDENCE_VIEW);
					break;
				case 6:
					System.err.println("Ville d'origine:");
					str1 = in.next();
					System.err.println("Ville de destination:");
					str2 = in.next();
					System.err.print("Distance: ");
					// format de sortie -> à générer avec votre code
					//System.out.println(123); // valeur pour Geneve à Delemont
					p = Floyd.getInstance().shortestPath(graph, graph.getVertex(str1), graph.getVertex(str2));
					System.out.println(p.getCost());
					break;
				case 7:
					System.err.println("Ville d'origine:");
					str1 = in.next();
					System.err.println("Ville de destination:");
					str2 = in.next();
					System.err.print("Parcours: ");
					// format de sortie -> à générer avec votre code
					//System.out.println("[Geneve:Lausanne:Berne:Zurich:Coire]"); // résultat pour Geneve à Coire
					p = Floyd.getInstance().shortestPath(graph, graph.getVertex(str1), graph.getVertex(str2));
					System.out.println(p);
					break;
				case 8:
					System.err.println("Ville d'origine:");
					str1 = in.next();
					// format de sortie -> à générer avec votre code
					System.out.println("[Geneve:0] [Lausanne:34] [Neuchatel:74] [Delemont:123] [Bale:157] [Berne:101] $[Lucerne:184] [Zurich:180] [Schaffouse:222] [St.-Gall:246] [Coire:271] [St.-Moritz:387] [Bellinzone:316] [Andermatt:263] [Sion:101]"); // résultat pour Geneve
					break;
				case 9:
					System.err.println("Ville d'origine:");
					str1 = in.next();
					// format de sortie -> à générer avec votre code
					System.out.println("[Geneve<-Lausanne] [Lausanne<-Neuchatel] [Neuchatel<-Delemont] [Delemont<-Bale] [Lausanne<-Berne] [Berne<-Lucerne] [Berne<-Zurich] [Zurich<-Schaffouse] [Zurich<-St.-Gall] [Zurich<-Coire] [Coire<-St.-Moritz] [Lucerne<-Bellinzone] [Sion<-Andermatt] [Lausanne<-Sion]"); // résultat pour Geneve
					break;
				case 10:
					System.err.println("Ville d'origine:");
					str1 = in.next();
					System.err.println("Ville de destination:");
					str2 = in.next();
					System.err.print("Distance: ");
					// format de sortie -> à générer avec votre code
					// imprimer "inf" à la place Integer.MAX_VALUE
					System.out.println(267); // résultat pour Bale à St.-Moritz
					break;
				case 11:
					System.err.println("Ville d'origine:");
					str1 = in.next();
					System.err.println("Ville de destination:");
					str2 = in.next();
					System.err.print("Parcours: ");
					// format de sortie -> à générer avec votre code
					System.out.println("[Bale:Zurich:Coire:St.-Moritz]"); // résultat pour Bale à St.-Moritz
					break;
				case 12:
					System.err.println("Nom de la ville:");
					str1 = in.next();
					// mise à jour à faire avec votre code
					break;
				case 13:
					System.err.println("Ville d'origine:");
					str1 = in.next();
					System.err.println("Ville de destination:");
					str2 = in.next();
					System.err.println("Temps de parcours:");
					str3 = in.next();
					// mise à jour à faire avec votre code
					break;
				case 14:
					System.err.println("Nom de la ville:");
					str1 = in.next();
					// mise à jour à faire avec votre code
					break;
				case 15:
					System.err.println("Ville d'origine:");
					str1 = in.next();
					System.err.println("Ville de destination:");
					str2 = in.next();
					// mise à jour à faire avec votre code
					break;
				case 16:
					// format de sortie -> à générer avec votre code
					System.out.println(true); // réponse true ou false
					break;
				case 17:
					System.err.println("Nom du fichier XML:");
					str1 = in.next();
					// sauvegarde à faire avec votre code
					break;
			}
		} while (choix!=0);
	}
}











