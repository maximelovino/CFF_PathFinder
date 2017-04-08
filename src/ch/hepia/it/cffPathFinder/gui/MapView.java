package ch.hepia.it.cffPathFinder.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import ch.hepia.it.cffPathFinder.backend.*;
import ch.hepia.it.cffPathFinder.data.MapReader;
import ch.hepia.it.cffPathFinder.data.XMLTools;

import static ch.hepia.it.cffPathFinder.backend.PathFinder.*;

public class MapView extends JFrame {

	private static final long serialVersionUID = -3457299074936765792L;
	private JPanel contentPane;
	private List<Float[]> points;
	private Graph graph;
	private JMenuBar menuBar;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MapView frame = new MapView("data/suisse.txt", XMLTools.parse("data/villes.xml"));
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MapView(String file, Graph g) {
		this.graph = g;
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

		cityListMenu.addActionListener(e -> {
			JOptionPane.showMessageDialog(this, graph.verticesToString());
		});

		JMenuItem weightsMatrix = new JMenuItem("2. Weights matrix");
		infoMenu.add(weightsMatrix);

		weightsMatrix.addActionListener(e -> {
			JOptionPane.showMessageDialog(this, graph.adjacencyMatrixString());
		});

		JMenuItem weightsList = new JMenuItem("3. Weights list");
		infoMenu.add(weightsList);

		weightsList.addActionListener(e -> {
			JOptionPane.showMessageDialog(this, graph.adjacencyListString());
		});

		JMenu floydMenu = new JMenu("Floyd algorithm");
		menuBar.add(floydMenu);
		JMenuItem costMatrixFloyd = new JMenuItem("4. Costs matrix");
		floydMenu.add(costMatrixFloyd);

		costMatrixFloyd.addActionListener(e -> {
			String city = JOptionPane.showInputDialog("From what city?");
			Vertex cityVertex = graph.getVertex(city);
			JOptionPane.showMessageDialog(this, cityVertex != null ? Floyd.getInstance().shortestPath(graph, cityVertex, ViewType.COST_VIEW) : "We couldn't find the city " + city);
		});

		JMenuItem precMatrixFloyd = new JMenuItem("5. Precedence matrix");
		floydMenu.add(precMatrixFloyd);

		precMatrixFloyd.addActionListener(e -> {
			String city = JOptionPane.showInputDialog("From what city?");
			Vertex cityVertex = graph.getVertex(city);
			JOptionPane.showMessageDialog(this, cityVertex != null ? Floyd.getInstance().shortestPath(graph, cityVertex, ViewType.PRECEDENCE_VIEW) : "We couldn't find the city " + city);
		});

		JMenuItem twoCitiesCostFloyd = new JMenuItem("6. Cost between two cities");
		floydMenu.add(twoCitiesCostFloyd);

		twoCitiesCostFloyd.addActionListener(e -> {
			//TODO
		});

		JMenuItem twoCitiesPathFloyd = new JMenuItem("7. Path between two cities");
		floydMenu.add(twoCitiesPathFloyd);

		twoCitiesPathFloyd.addActionListener(e -> {
			//TODO
		});

		JMenu dijkstraMenu = new JMenu("Dijkstra algorithm");
		menuBar.add(dijkstraMenu);
		JMenuItem costMatrixDijkstra = new JMenuItem("8. Costs matrix");
		dijkstraMenu.add(costMatrixDijkstra);

		costMatrixDijkstra.addActionListener(e -> {
			String city = JOptionPane.showInputDialog("From what city?");
			Vertex cityVertex = graph.getVertex(city);
			JOptionPane.showMessageDialog(this, cityVertex != null ? Dijkstra.getInstance().shortestPath(graph, cityVertex, ViewType.COST_VIEW) : "We couldn't find the city " + city);
		});

		JMenuItem precMatrixDijkstra = new JMenuItem("9. Precedence matrix");
		dijkstraMenu.add(precMatrixDijkstra);

		precMatrixDijkstra.addActionListener(e -> {
			String city = JOptionPane.showInputDialog("From what city?");
			Vertex cityVertex = graph.getVertex(city);
			JOptionPane.showMessageDialog(this, cityVertex != null ? Dijkstra.getInstance().shortestPath(graph, cityVertex, ViewType.PRECEDENCE_VIEW) : "We couldn't find the city " + city);
		});

		JMenuItem twoCitiesCostDijkstra = new JMenuItem("10. Cost between two cities");
		dijkstraMenu.add(twoCitiesCostDijkstra);

		twoCitiesCostDijkstra.addActionListener(e -> {
			//TODO
		});

		JMenuItem twoCitiesPathDijkstra = new JMenuItem("11. Path between two cities");
		dijkstraMenu.add(twoCitiesPathDijkstra);

		twoCitiesPathDijkstra.addActionListener(e -> {
			//TODO
		});

		JMenu graphTools = new JMenu("Graph tools");
		menuBar.add(graphTools);
		JMenuItem addCity = new JMenuItem("12. Add a city");
		graphTools.add(addCity);
		JMenuItem addEdge = new JMenuItem("13. Add a connection");
		graphTools.add(addEdge);
		JMenuItem removeCity = new JMenuItem("14. Remove a city");
		graphTools.add(removeCity);
		JMenuItem removeEdge = new JMenuItem("15. Remove a connection");
		graphTools.add(removeEdge);
		JMenuItem connexityCheck = new JMenuItem("16. Connexity check");
		graphTools.add(connexityCheck);
		JMenuItem exportXML = new JMenuItem("17. Export to XML");
		graphTools.add(exportXML);


		setJMenuBar(menuBar);
	}
	
	
	class DrawPane extends JPanel {

		private static final long serialVersionUID = -1708275954294990938L;

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Float[] p0 = points.get(0);
			for (int i = 1; i < points.size()+1; i++) {
				g.drawLine((int)(p0[0]*this.getWidth()), (int)(this.getHeight()-p0[1]*this.getHeight()), (int)(points.get(i % points.size())[0]*this.getWidth()), (int)(this.getHeight()-points.get(i % points.size())[1]*this.getHeight()));
				p0 = points.get(i % points.size());
			}
			
			for (Vertex v : graph.getVertices()) {
				Stop s = (Stop)v;
				g.fillOval((int)(s.getxCoord()*this.getWidth())-10, (int)(this.getHeight()-s.getyCoord()*this.getHeight())-10, 20, 20);
			}
			
			for (Edge e : graph.getEdges()) {
				Stop s1 = (Stop)e.getV1();
				Stop s2 = (Stop)e.getV2();

				g.drawLine((int)(s1.getxCoord()*this.getWidth()), (int)(this.getHeight()-s1.getyCoord()*this.getHeight()), (int)(s2.getxCoord()*this.getWidth()), (int)(this.getHeight()-s2.getyCoord()*this.getHeight()));
			}
		}
	}

}
