
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/*
 * Sunil Jain and Vatsal Parikh's Game class for Go Game for Java Class.
 * Edited: 6/4/20.
 * 
 * Summary: 
 * sets up the frame and game, it also takes all user input from the player
 * it also makes moves and advances the game by calling on the GamePanel's logic.
 */

public class Game implements MouseListener, KeyListener
{

	JFrame f = new JFrame("Go");

	Dimension dim = new Dimension(800, 600);
	GamePanel p = null; // panel

	public Game(int length)
	{
		f.setLayout(new BorderLayout()); // border layout
		f.setExtendedState(JFrame.MAXIMIZED_BOTH); // full screen
		f.addKeyListener(this);
		f.setResizable(true);

		// add panel to frame and add mouse listener to panel
		p = new GamePanel(f, dim, length);
		f.add(p, BorderLayout.CENTER);
		p.addMouseListener(this);

		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}

	public void mousePressed(MouseEvent e) {
		Point pt = p.getPoint(e.getX(), e.getY()); // get the point
		if (pt != null && pt.getState() == p.getBLANK())
		{ // if the point is valid
			if (!p.checkKO(pt, p.getTurn()))
			{ // only play if the move is valid
				if (p.getTurn() == p.getBLACK())
				{ // make valid black turn
					pt.setState(p.getBLACK());
					p.setTurn(p.getWHITE());
				}
				else if (p.getTurn() == p.getWHITE())
				{ // make valid white turn
					pt.setState(p.getWHITE());
					p.setTurn(p.getBLACK());
				}
				p.updateBoard(null); // update board
			}
			else
			{
				// error message
				JOptionPane.showMessageDialog(f, "Move is invalid, please choose another move!");
			}
		}
		else if (p.getEndGame(e.getX(), e.getY()))
		{ // if the player clicked on endgame button
			int yesno = -1; // did the player select yes or no
			if (p.getTurn() == 1)
			{
				yesno = JOptionPane.showConfirmDialog(f, "Black wants to end the game, End Game?");
			}
			else if (p.getTurn() == 2)
			{
				yesno = JOptionPane.showConfirmDialog(f, "White wants to end the game, End Game?");
			}
			if (yesno == JOptionPane.YES_OPTION)
			{ // if the player chose white
				// display who won and exit the game
				if (p.getScore(1) > p.getScore(2))
				{
					JOptionPane.showMessageDialog(f, "Black wins!");
				}
				else if (p.getScore(1) < p.getScore(2))
				{
					JOptionPane.showMessageDialog(f, "White wins!");
				}
				else if (p.getScore(1) == p.getScore(2))
				{
					JOptionPane.showMessageDialog(f, "Tie Game!");
				}
				System.exit(0);
			}
		}
		f.repaint(); // update screen
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// exit when q is pressed (this is for sunil's convenience)
		if (e.getKeyChar() == 'q')
		{
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
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

}