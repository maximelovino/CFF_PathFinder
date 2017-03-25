package ch.hepia.it.cffPathFinder.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ch.hepia.it.cffPathFinder.data.MapReader;

public class MapView extends JFrame {

	private static final long serialVersionUID = -3457299074936765792L;
	private JPanel contentPane;
	private List<Float[]> points;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MapView frame = new MapView("data/suisse.txt");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MapView(String file) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1024, 768);
		contentPane = new DrawPane();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		points = MapReader.readMap(file);
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
		}
	}

}
