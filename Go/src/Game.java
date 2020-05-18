
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Game implements MouseListener, KeyListener {

	JFrame f = new JFrame();
	Container south = new Container();
	JLabel whiteScore = new JLabel("White Score: ");
	JLabel blackScore = new JLabel("Black Score: ");

	Dimension dim = new Dimension(800, 600);

	// GamePanel p = new GamePanel(f, dim);
	GamePanel p = null;

	public Game() {
		f.setSize(800, 600);
		f.setUndecorated(false);
		f.setLayout(new BorderLayout());
		// f.setUndecorated(true);

		p = new GamePanel(f, dim);
		f.add(p, BorderLayout.CENTER);
		p.addMouseListener(this);

		f.getContentPane().setBackground(Color.DARK_GRAY);
		f.addKeyListener(this);

		blackScore.setForeground(Color.WHITE);
		whiteScore.setForeground(Color.WHITE);

		/*
		 * south.setLayout(new GridLayout(1,2)); south.add(whiteScore);
		 * south.add(blackScore); f.add(south, BorderLayout.EAST);
		 */

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
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyChar() == 'q') {
			System.exit(0);
		}
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}

}