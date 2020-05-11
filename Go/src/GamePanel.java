import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Graphics;

public class GamePanel extends JPanel {
	
	JFrame frame = new JFrame();
	
	int dim = 9;
	
	
	
	public GamePanel(JFrame f) {
		super();
		frame = f;
		
		dim++;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.drawLine(100, 100, frame.getHeight() - 100, 100);
		g.drawLine(100, 100, 100, frame.getHeight() - 100);
		g.drawLine(frame.getHeight() - 100, 100, frame.getHeight() - 100, frame.getHeight() - 100);
		g.drawLine(100, frame.getHeight() - 100, frame.getHeight() - 100, frame.getHeight() - 100);
		
		for (int i = frame.getHeight() / dim; i < frame.getHeight() - 100; i += (frame.getWidth() - 100) / dim / 2) {
			g.drawLine(i, 100, i, frame.getHeight() - 100);
		}
		
	}
	
}
