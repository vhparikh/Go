import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

/*
 * Sunil Jain and Vatsal Parikh's StartGame class for Go Game for Java Class.
 * Edited: 6/4/20.
 * 
 * Summary: 
 * Displays the starting window for the game and lets the player input the length of the go board
 */

public class StartGame implements MouseListener, KeyListener
{
	// jframes
	JFrame frame = new JFrame();

	// center container
	Container center = new Container();
	// label for instructions
	JLabel gridText = new JLabel("Please Enter The Length Of The Board:", SwingConstants.CENTER);
	// user input for the length of the go board
	JTextArea gridArea = new JTextArea("9");
	// button for submitting the length
	JButton start = new JButton("START!");
	// board length
	public int bLength = -1;

	public StartGame()
	{
		// basic setup
		frame.setSize(250, 300);
		frame.setResizable(false);
		frame.setLayout(new BorderLayout());

		// adding the center to the frame
		center.setLayout(new GridLayout(3, 1));
		center.add(gridText);
		center.add(gridArea);
		center.add(start);
		start.addMouseListener(this);
		center.setBounds(100, 100, 100, 100);
		frame.add(center, BorderLayout.CENTER);

		// closing with the bar at the top and visibility
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// did the user submit the length
		if (e.getSource().equals(start))
		{
			// if the input doesnt only contains numbers
			if (Pattern.matches("[a-zA-Z]+", gridArea.getText()))
			{
				JOptionPane.showMessageDialog(frame, "Please enter a number for the length.");
			}
			else
			{ // if the input only contains numbers
				// if the length is less than 9 give error message if not open game window
				if (Integer.parseInt(gridArea.getText()) >= 9)
				{
					new Game(Integer.parseInt(gridArea.getText()));
					frame.setVisible(false);
				}
				else
				{
					JOptionPane.showMessageDialog(frame, "Number has to be at least 9.");
				}
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyChar() == 'q')
		{
			System.exit(0);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
}
