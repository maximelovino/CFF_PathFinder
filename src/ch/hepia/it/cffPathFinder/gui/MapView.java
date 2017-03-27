package ch.hepia.it.cffPathFinder.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ch.hepia.it.cffPathFinder.backend.Edge;
import ch.hepia.it.cffPathFinder.backend.Graph;
import ch.hepia.it.cffPathFinder.backend.Stop;
import ch.hepia.it.cffPathFinder.backend.Vertex;
import ch.hepia.it.cffPathFinder.data.MapReader;
import ch.hepia.it.cffPathFinder.data.XMLTools;

public class MapView extends JFrame {

	private static final long serialVersionUID = -3457299074936765792L;
	private JPanel contentPane;
	private List<Float[]> points;
	private Graph graph;

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
