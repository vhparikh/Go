
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
import javax.swing.JOptionPane;

public class Game implements MouseListener, KeyListener {

	JFrame f = new JFrame();
	Container south = new Container();
	JLabel whiteScore = new JLabel("White Score: ");
	JLabel blackScore = new JLabel("Black Score: ");

	Dimension dim = new Dimension(800, 600);

	// GamePanel p = new GamePanel(f, dim);
	GamePanel p = null;

	public Game(int length) {
		f.setLayout(new BorderLayout()); //border layout
		f.setExtendedState(JFrame.MAXIMIZED_BOTH); //full screen
		f.getContentPane().setBackground(Color.DARK_GRAY);
		f.addKeyListener(this);
		
		p = new GamePanel(f, dim, length);
		f.add(p, BorderLayout.CENTER);
		p.addMouseListener(this);

		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}

	public void mousePressed(MouseEvent e) {
		Point pt = p.getPoint(e.getX(), e.getY()); //get the point
		if (pt != null && pt.getState() == p.getBLANK()) { //if the point is valid
			if (p.isPlayValid(pt, p.getTurn())) { //only play if the move is valid
				if (p.getTurn() == p.getBLACK()) { //make valid black turn
					pt.setState(p.getBLACK());
					p.setTurn(p.getWHITE());
				} else if (p.getTurn() == p.getWHITE()) { //make valid white turn
					pt.setState(p.getWHITE());
					p.setTurn(p.getBLACK());
				}
				p.updateBoard(null); //update board
			} else {
				//error message
				JOptionPane.showMessageDialog(f, "Move is invalid, please choose another move!");
			}
		}
		f.repaint(); //update screen
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		//exit when q is pressed (this is for sunil's convenience)
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