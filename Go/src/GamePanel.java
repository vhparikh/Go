import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GamePanel extends JPanel {
	JFrame f = new JFrame();
	
	int l = 9; //lines
	int d = 0; //width and height
	int o = 0; //offset
	int r = 0;
	int w = 0; //width
	
	Point[][] board;
	
	public GamePanel(JFrame f, int dim) {
		super();
		
		this.f = f;
		//l = dim;
		
		for (int i = 0; i < 100; i += l) {
			o = i;
		}
		
		for (int i = 0; i < 700; i += l) {
			d = i;
		}
		
		r = d - o;
		w = d - o - o;
		
		board = new Point[l][l];
		
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				board[i][j] = new Point();
			}
		}
	}
	
	public Point getPoint(int x, int y) {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				Point p = board[i][j];
				
				if ((Math.abs(x - p.getX()) < p.getD() / 2) && (Math.abs(y - p.getY()) < p.getD() / 2)) {
					return board[i][j];
				}
			}
		}
		
		return null;
	}
	
	public void updateBoard() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				Point p = board[i][j];
				if(p.getState() == p.getBLACK()) {
					Point r = null;
//					if(j+1  board[0].length ||) {
//						r = board[i][j+1];
//					}
//					else {
//						r = new Point();
//						r.setState(r.getWHITE());
//					}
					Point l = board[i][j-1];
					Point u = board[i-1][j];
					Point d = board[i+1][j];
					if(p.getState() == p.getBLACK()) {
						if((r.getState() == r.getWHITE()) && (l.getState() == l.getWHITE()) &&
								(u.getState() == u.getWHITE()) && (d.getState() == d.getWHITE())) {
							p.setCptd(true);
							p.setState(p.getBLANK());
						}
					}
				}
			}
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, f.getWidth(), f.getHeight());
		
		g.setColor(Color.WHITE);
		for (int i = o + d / l; i < d + o + d / l; i += d / l) {
			g.drawLine(i, o + d / l, i, d + o);
			g.drawLine(o + d / l, i, d + o, i);
		}
		
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				Point p = board[i][j];
				
				g.setColor(Color.WHITE);
				
				p.setX(o + (i+1) * (d / l));
				p.setY(o + (j+1) * (d / l));
				p.setD(w/l);
				
				if (p.getState() == p.getBLACK()) {
					g.setColor(Color.BLACK);
					g.fillOval(p.getX()-p.getD() / 2, p.getY()-p.getD() / 2, p.getD(), p.getD());
				} else if (p.getState() == p.getWHITE()) {
					g.setColor(Color.WHITE);
					g.fillOval(p.getX()-p.getD() / 2, p.getY()-p.getD() / 2, p.getD(), p.getD());
				}
			}
		}
		
	}
	
}
