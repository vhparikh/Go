
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

public class Game implements MouseListener {
	
	JFrame f = new JFrame();
	
	int dim = 700;
	
	GamePanel p = new GamePanel(f, dim);
	
	public Game() {
		f.setExtendedState(JFrame.MAXIMIZED_BOTH);
		f.setUndecorated(false);
		
		f.getContentPane().setBackground(Color.DARK_GRAY);
		
		f.add(p);
		p.addMouseListener(this);
		
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}

	public void mousePressed(MouseEvent e) {
		Point pt = p.getPoint(e.getX(), e.getY());
		if (pt != null) {
			if (pt.getState() == p.getBLANK()/* && !pt.isCptd()*/) {
				if (p.getTurn() == p.getBLACK()) {
					pt.setState(p.getBLACK());
					p.setTurn(p.getWHITE());
				} else if (p.getTurn() == p.getWHITE()) {
					pt.setState(p.getWHITE());
					p.setTurn(p.getBLACK());
				}
			}
		}
		
		p.updateBoard();
		
		f.repaint();
	}
	
	public void mouseReleased(MouseEvent e) {}
	
	public void mouseClicked(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}
	
}
