package ch.hepia.it.cffPathFinder.gui;

import ch.hepia.it.cffPathFinder.backend.*;
import ch.hepia.it.cffPathFinder.data.MapReader;
import ch.hepia.it.cffPathFinder.data.XMLTools;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.util.List;

import static ch.hepia.it.cffPathFinder.backend.PathFinder.ViewType;

public class MapView extends JFrame {

	private static final long serialVersionUID = -3457299074936765792L;
	private DrawPane contentPane;
	private List<Float[]> points;
	private Graph graph;
	private JMenuBar menuBar;
	private Path path;

	public MapView (String file, Graph g) {
		this.graph = g;
		this.path = new Path();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1024, 768);
		contentPane = new DrawPane();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		points = MapReader.readMap(file, g);
		menuBar = new JMenuBar();
		JMenu infoMenu = new JMenu("Informations about the Graph");
		menuBar.add(infoMenu);
		JMenuItem cityListMenu = new JMenuItem("1. List of cities");
		infoMenu.add(cityListMenu);

		cityListMenu.addActionListener(e -> JOptionPane.showMessageDialog(this, graph.verticesToString()));

		JMenuItem weightsMatrix = new JMenuItem("2. Weights matrix");
		infoMenu.add(weightsMatrix);

		weightsMatrix.addActionListener(e -> JOptionPane.showMessageDialog(this, graph.adjacencyMatrixString()));

		JMenuItem weightsList = new JMenuItem("3. Weights list");
		infoMenu.add(weightsList);

		weightsList.addActionListener(e -> JOptionPane.showMessageDialog(this, graph.adjacencyListString()));

		JMenu floydMenu = new JMenu("Floyd algorithm");
		menuBar.add(floydMenu);
		JMenuItem costMatrixFloyd = new JMenuItem("4. Costs matrix");
		floydMenu.add(costMatrixFloyd);

		costMatrixFloyd.addActionListener(e -> {
			String city = JOptionPane.showInputDialog("From which city?");
			Vertex cityVertex = graph.getVertex(city);
			JOptionPane.showMessageDialog(this, cityVertex != null ? Floyd.getInstance().shortestPath(graph, cityVertex, ViewType.COST_VIEW) : "We couldn't find the city " + city);
		});

		JMenuItem precMatrixFloyd = new JMenuItem("5. Precedence matrix");
		floydMenu.add(precMatrixFloyd);

		precMatrixFloyd.addActionListener(e -> {
			String city = JOptionPane.showInputDialog("From which city?");
			Vertex cityVertex = graph.getVertex(city);
			JOptionPane.showMessageDialog(this, cityVertex != null ? Floyd.getInstance().shortestPath(graph, cityVertex, ViewType.PRECEDENCE_VIEW) : "We couldn't find the city " + city);
		});

		JMenuItem twoCitiesFloyd = new JMenuItem("6 and 7. Cost and path between two cities");
		floydMenu.add(twoCitiesFloyd);

		twoCitiesFloyd.addActionListener(e -> {
			String city1Str = JOptionPane.showInputDialog("From which city?");
			Vertex city1 = graph.getVertex(city1Str);
			if (city1 != null) {
				String city2Str = JOptionPane.showInputDialog("To which city?");
				Vertex city2 = graph.getVertex(city2Str);
				if (city2 != null) {
					path = Floyd.getInstance().shortestPath(graph, city1, city2);
					repaint();
					JOptionPane.showMessageDialog(this, "The duration of the trip from " + city1.getName() + " to " + city2.getName() + " is " + path.getCost());
				} else {
					JOptionPane.showMessageDialog(this, "The city " + city2Str + " doesn't exist");
				}
			} else {
				JOptionPane.showMessageDialog(this, "The city " + city1Str + " doesn't exist");
			}
		});

		JMenu dijkstraMenu = new JMenu("Dijkstra algorithm");
		menuBar.add(dijkstraMenu);
		JMenuItem costMatrixDijkstra = new JMenuItem("8. Costs matrix");
		dijkstraMenu.add(costMatrixDijkstra);

		costMatrixDijkstra.addActionListener(e -> {
			String city = JOptionPane.showInputDialog("From which city?");
			Vertex cityVertex = graph.getVertex(city);
			JOptionPane.showMessageDialog(this, cityVertex != null ? Dijkstra.getInstance().shortestPath(graph, cityVertex, ViewType.COST_VIEW) : "We couldn't find the city " + city);
		});

		JMenuItem precMatrixDijkstra = new JMenuItem("9. Precedence matrix");
		dijkstraMenu.add(precMatrixDijkstra);

		precMatrixDijkstra.addActionListener(e -> {
			String city = JOptionPane.showInputDialog("From which city?");
			Vertex cityVertex = graph.getVertex(city);
			JOptionPane.showMessageDialog(this, cityVertex != null ? Dijkstra.getInstance().shortestPath(graph, cityVertex, ViewType.PRECEDENCE_VIEW) : "We couldn't find the city " + city);
		});

		JMenuItem twoCitiesDijkstra = new JMenuItem("10 and 11. Cost and path between two cities");
		dijkstraMenu.add(twoCitiesDijkstra);

		twoCitiesDijkstra.addActionListener(e -> {
			String city1Str = JOptionPane.showInputDialog("From which city?");
			Vertex city1 = graph.getVertex(city1Str);
			if (city1 != null) {
				String city2Str = JOptionPane.showInputDialog("To which city?");
				Vertex city2 = graph.getVertex(city2Str);
				if (city2 != null) {
					path = Dijkstra.getInstance().shortestPath(graph, city1, city2);
					repaint();
					JOptionPane.showMessageDialog(this, "The duration of the trip from " + city1.getName() + " to " + city2.getName() + " is " + path.getCost());
				} else {
					JOptionPane.showMessageDialog(this, "The city " + city2Str + " doesn't exist");
				}
			} else {
				JOptionPane.showMessageDialog(this, "The city " + city1Str + " doesn't exist");
			}
		});

		JMenu graphTools = new JMenu("Graph tools");
		menuBar.add(graphTools);
		JMenuItem addCity = new JMenuItem("12. Add a city");
		graphTools.add(addCity);

		addCity.addActionListener(e -> {
			String name = JOptionPane.showInputDialog("What's the name of the city?");
			String position = JOptionPane.showInputDialog("Whats the position as x,y?");
			String[] positionXY = position.split(",");
			try {
				Stop s = new Stop(name, Integer.valueOf(positionXY[0]), Integer.valueOf(positionXY[1]));
				MapReader.normalizeStop(s);
				graph.addVertex(s);
				repaint();
			} catch (NumberFormatException e1) {
				JOptionPane.showMessageDialog(this, "Invalid position");
			}
		});

		JMenuItem addEdge = new JMenuItem("13. Add a connection");
		graphTools.add(addEdge);

		addEdge.addActionListener(e -> {
			String city1Str = JOptionPane.showInputDialog("From which city?");
			String city2Str = JOptionPane.showInputDialog("To which city?");
			String cost = JOptionPane.showInputDialog("What is the duration of the connection?");
			try {
				graph.addEdge(city1Str, city2Str, Integer.valueOf(cost));
				path = new Path();
				repaint();
			} catch (NumberFormatException e1) {
				JOptionPane.showMessageDialog(this, "The cost must be a number");
			} catch (RuntimeException e2) {
				JOptionPane.showMessageDialog(this, e2.getMessage());
			}
		});

		JMenuItem removeCity = new JMenuItem("14. Remove a city");
		graphTools.add(removeCity);

		removeCity.addActionListener(e -> {
			String city = JOptionPane.showInputDialog("What city do you want to remove?");
			try {
				graph.removeVertex(city);
				path = new Path();
				repaint();
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(this, "The city " + city + " doesn't exist");
			}
		});

		JMenuItem removeEdge = new JMenuItem("15. Remove a connection");
		graphTools.add(removeEdge);

		removeEdge.addActionListener(e -> {
			String city1Str = JOptionPane.showInputDialog("From which city?");
			String city2Str = JOptionPane.showInputDialog("To which city?");
			try {
				graph.removeEdge(city1Str, city2Str);
				path = new Path();
				repaint();
			} catch (RuntimeException e2) {
				JOptionPane.showMessageDialog(this, e2.getMessage());
			}
		});

		JMenuItem connexityCheck = new JMenuItem("16. Connexity check");
		graphTools.add(connexityCheck);

		connexityCheck.addActionListener(e -> {
			JOptionPane.showMessageDialog(this, "The graph is " + (graph.isConnex() ? "" : "not ") + "connex");
		});

		JMenuItem exportXML = new JMenuItem("17. Export to XML");
		graphTools.add(exportXML);

		exportXML.addActionListener(e -> {
			JFileChooser choose = new JFileChooser();
			if (choose.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
				File f = choose.getSelectedFile();
				try {
					XMLTools.writeToXML(f, graph);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(this, "There was a problem saving the file");
				}
			}
		});


		JMenuItem clearGraph = new JMenuItem("CLEAR MAP");
		graphTools.add(clearGraph);

		clearGraph.addActionListener(e -> {
			path = new Path();
			repaint();
		});

		setJMenuBar(menuBar);
	}

	class DrawPane extends JPanel {

		private static final long serialVersionUID = -1708275954294990938L;

		@Override
		protected void paintComponent (Graphics g1) {
			super.paintComponent(g1);
			Graphics2D g = (Graphics2D) g1;
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			Float[] p0 = points.get(0);
			g.setColor(Color.black);
			for (int i = 1; i < points.size() + 1; i++) {
				g.drawLine((int) (p0[0] * this.getWidth()), (int) (this.getHeight() - p0[1] * this.getHeight()), (int) (points.get(i % points.size())[0] * this.getWidth()), (int) (this.getHeight() - points.get(i % points.size())[1] * this.getHeight()));
				p0 = points.get(i % points.size());
			}

			for (Vertex v : graph.getVertices()) {
				Stop s = (Stop) v;
				g.setColor(Color.BLUE);
				g.fillOval((int) (s.getxCoord() * this.getWidth()) - 10, (int) (this.getHeight() - s.getyCoord() * this.getHeight()) - 10, 20, 20);
				g.setColor(Color.BLACK);
				g.drawString(s.getName(), (int) (s.getxCoord() * this.getWidth()) - 10, (int) (this.getHeight() - s.getyCoord() * this.getHeight()) - 10);
			}
			g.setColor(Color.BLUE);
			for (Edge e : graph.getEdges()) {
				Stop s1 = (Stop) e.getV1();
				Stop s2 = (Stop) e.getV2();

				g.drawLine((int) (s1.getxCoord() * this.getWidth()), (int) (this.getHeight() - s1.getyCoord() * this.getHeight()), (int) (s2.getxCoord() * this.getWidth()), (int) (this.getHeight() - s2.getyCoord() * this.getHeight()));
			}

			paintPath(g, path);
		}

		public void paintPath (Graphics g, Path p) {
			List<Vertex> pathList = p.getPath();
			for (int i = 0; i < pathList.size() - 1; i++) {
				Stop s1 = (Stop) pathList.get(i);
				Stop s2 = (Stop) pathList.get(i + 1);
				g.setColor(Color.red);
				g.drawLine((int) (s1.getxCoord() * this.getWidth()), (int) (this.getHeight() - s1.getyCoord() * this.getHeight()), (int) (s2.getxCoord() * this.getWidth()), (int) (this.getHeight() - s2.getyCoord() * this.getHeight()));
				g.fillOval((int) (s1.getxCoord() * this.getWidth()) - 10, (int) (this.getHeight() - s1.getyCoord() * this.getHeight()) - 10, 20, 20);
				g.fillOval((int) (s2.getxCoord() * this.getWidth()) - 10, (int) (this.getHeight() - s2.getyCoord() * this.getHeight()) - 10, 20, 20);

			}
		}
	}

}
