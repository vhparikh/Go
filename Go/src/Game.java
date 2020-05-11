import javax.swing.JFrame;

public class Game {
	
	JFrame f = new JFrame();
	
	GamePanel p = new GamePanel(f);
	
	public Game() {
		f.setSize(800, 800);
//		f.setUndecorated(true);
		
		
		f.add(p);
		
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
	
}
