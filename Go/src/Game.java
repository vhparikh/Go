
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;

public class Game implements MouseListener {
	
	JFrame f = new JFrame();
	
	int dim = 900;
	
	GamePanel p = new GamePanel(f, dim);
	
	int BLANK = 0;
	int BLACK = 1;
	int WHITE = 2;
	int turn = BLACK;
	
	public Game() {
		//f.setSize(dim, dim);
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
			if (pt.getState() == BLANK && !pt.isCptd()) {
				if (turn == BLACK) {
					pt.setState(BLACK);
					turn = WHITE;
				} else if (turn == WHITE) {
					pt.setState(WHITE);
					turn = BLACK;
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
