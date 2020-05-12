import java.awt.Color;
import java.awt.Graphics;

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
	
	public int BLANK = 0;
	public int BLACK = 1;
	public int WHITE = 2;
	public int turn = BLACK;
	
	public GamePanel(JFrame frame, int dim) {
		super();
		
		f = frame;
		
		for (int i = 0; i < 100; i += l) {
			o = i;
		}
		
		for (int i = 0; i < dim; i += l) {
			d = i;
		}
		
		System.out.println(dim);
		
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
	
	public void checkLiberties(int player) {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				Point p = board[i][j];
				Point r = null;
				Point l = null;
				Point u = null;
				Point d = null;
				
				int op = -1;
				
				if (player == BLACK) {
					op = WHITE;
				} else if (player == WHITE) {
					op = BLACK;
				}
				
				if(j + 1 < board[0].length) {
					r = board[i][j + 1];
				} else {
					r = new Point();
					r.setState(op);
				}
				if (j - 1 >= 0) {
					l = board[i][j - 1];
				} else {
					l = new Point();
					l.setState(op);
				}
				if(i + 1 < board[0].length) {
					u = board[i + 1][j];
				} else {
					u = new Point();
					u.setState(op);
				}
				if (i - 1 >= 0) {
					d = board[i - 1][j];
				} else {
					d = new Point();
					d.setState(op);
				}
				
				if((r.getState() == op) && (l.getState() == op) && 
						(u.getState() == op) && (d.getState() == op)) {
					p.setCptd(true);
					p.setState(p.getBLANK());
				}
			}
		}
	}
	
	public void updateBoard() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				Point p = board[i][j];
				checkLiberties(p.getState());
			}
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, f.getWidth(), f.getHeight());
		
		if (turn == BLACK) {
			g.setColor(Color.BLACK);
		} else if (turn == WHITE) {
			g.setColor(Color.WHITE);
		}
		g.fillRect(o, o, d + d / l, d + d / l);
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(o + d / l, o + d / l, d - d / l, d - d / l);
		
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

	public int getBLANK() {
		return BLANK;
	}

	public int getTurn() {
		return turn;
	}

	public int getBLACK() {
		return BLACK;
	}

	public int getWHITE() {
		return WHITE;
	}

	public void setTurn(int p) {
		turn = p;
	}
	
	
	
}
