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

public class StartGame implements MouseListener, KeyListener {

	JFrame frame = new JFrame();
	
	Container center = new Container();
	JLabel gridText = new JLabel("Please Enter The Length Of The Board:", SwingConstants.CENTER);
	JTextArea gridArea = new JTextArea("Length");
	JButton start = new JButton("START!");
	
	public int bLength = -1; //board length
	
	public StartGame() {
		frame.setSize(250, 300);
		frame.setResizable(false);
		frame.setLayout(new BorderLayout());
		
		center.setLayout(new GridLayout(3, 1));
		center.add(gridText);
//		gridText.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		center.add(gridArea);
		center.add(start);
		start.addMouseListener(this);
		center.setBounds(100, 100, 100, 100);
		frame.add(center, BorderLayout.CENTER);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println("hello");
		
		if (e.getSource().equals(start)) {
			/*if (gridArea.getText().contains("[^a-zA-Z0-9_-]")) {
				JOptionPane.showMessageDialog(frame, "Please enter a number for the length.");
			}*/
			
			if (Pattern.matches("[a-zA-Z]+", gridArea.getText())) {
				JOptionPane.showMessageDialog(frame, "Please enter a number for the length.");
			}
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyChar() == 'q') {
			System.exit(0);
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {}
	
	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
}
