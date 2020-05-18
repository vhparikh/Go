
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;



public class Game implements MouseListener {
	
	JFrame f = new JFrame();
	Container north = new Container();
	JLabel whiteScore = new JLabel("White Score: ");
	JLabel blackScore = new JLabel("Black Score: ");
	
	int dim = 700;
	
	GamePanel p = new GamePanel(f, dim);
	
	public Game() {
		f.setSize(1000, 1000);
		f.setUndecorated(false);
		f.setLayout(new BorderLayout());
		f.add(p, BorderLayout.CENTER);
		p.addMouseListener(this);
		
		f.getContentPane().setBackground(Color.DARK_GRAY);
		
		north.setLayout(new GridLayout(1,2));
		north.add(whiteScore);
		north.add(blackScore);
		f.add(north, BorderLayout.NORTH);
		
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}

	public void mousePressed(MouseEvent e) {
		Point pt = p.getPoint(e.getX(), e.getY());
		if (pt != null) {
			if (pt.getState() == p.getBLANK()) {
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
		blackScore.setText("Black Score: " + p.updateScore(1));
		whiteScore.setText("White Score: " + p.updateScore(2));
		
		f.repaint();
	}
	
	public void mouseReleased(MouseEvent e) {}
	
	public void mouseClicked(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}
	
}
