import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Graphics;

public class GamePanel extends JPanel {
	
	JFrame frame = new JFrame();

	int dim = 9;
	int w = 800;
	int h = 800;
	int d = 800;
	
	
	
	public GamePanel(JFrame f) {
		super();
		frame = f;
		
		dim++;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.drawLine(100, 100, d - 100, 100);
		g.drawLine(100, 100, 100, d - 100);
		g.drawLine(d - 100, 100, d - 100, d - 100);
		g.drawLine(100, d - 100, d - 100, d - 100);
		
		for (int i = 100; i < d - 100; i += (d - 100) / dim) {
			g.drawLine(i, 100, i, d - 100);
		}
		
	}
	
}
