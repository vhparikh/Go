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
	
	public int BLANK = 0;
	public int BLACK = 1;
	public int WHITE = 2;
	public int turn = BLACK;
	ArrayList<Point> stack = new ArrayList<Point>();
	
	public GamePanel(JFrame frame, int dim) {
		super();
		
		f = frame;
		
		for (int i = 0; i < 100; i += l) {
			o = i;
		}
		
		for (int i = 0; i < dim; i += l) {
			d = i;
		}
		
		r = d - o;
		w = d - o - o;
		
		board = new Point[l][l];
		
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				Point p = new Point();
				p.setX(i);
				p.setCX(i);
				p.setY(j);
				p.setCY(j);

				board[i][j] = p;
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
	
	public void travelBoard() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				if(board[i][j].getState() != BLANK) {
					stack.add(board[i][j]);
					if(checkForCapture(board[i][j])) {
						cleanStack();
					}
					stack.removeAll(stack);
				}
				
			}
		}
	}
	
	public void cleanStack() {
		for (int i = 0; i < stack.size(); i++) {
			for (int j = 0; j < board.length; j++) {
				for (int k = 0; k < board.length; k++) {
					if (board[j][k].equals(stack.get(i))) {
						board[j][k].setState(BLANK);
					}
				}
			}
		}
	}
	
	public boolean checkForCapture(Point p) {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				if(p == board[i][j]) {
					if((i-1 >= 0) && (p.getState() == board[i-1][j].getState()) && (!stack.contains(board[i-1][j]))) {
						stack.add(board[i-1][j]);
						if(!checkForCapture(board[i-1][j])) {
							return false;
						}
					}else if((i-1 >= 0) && (board[i-1][j].getState() == getBLANK())) {
						return false;
					}
					if((i+1 < board.length) && (p.getState() == board[i+1][j].getState()) && (!stack.contains(board[i+1][j]))) {
						stack.add(board[i+1][j]);
						if(!checkForCapture(board[i+1][j])) {
							return false;
						}
					}else if((i+1 < board.length) && (board[i+1][j].getState() == getBLANK())) {
						return false;
					}
					if((j-1 >= 0) && (p.getState() == board[i][j-1].getState()) && (!stack.contains(board[i][j-1]))) {
						stack.add(board[i][j-1]);
						if(!checkForCapture(board[i][j-1])) {
							return false;
						}
					}else if((j-1 >= 0) && (board[i][j-1].getState() == getBLANK())) {
						return false;
					}
					if((j+1 < board[0].length) && (p.getState() == board[i][j+1].getState()) && (!stack.contains(board[i][j+1]))) {
						stack.add(board[i][j+1]);
						if(!checkForCapture(board[i][j+1])) {
							return false;
						}
					}else if((j+1 < board[0].length) && (board[i][j+1].getState() == getBLANK())) {
						return false;
					}
					return true;
				}
			}
		}		
		return false;
	}
	
	public int updateScore(int player) {
		int score = 0; 
		
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				if(board[i][j].getState() == player) {
					score++;
				}
			}
		}
		
		return score;
		
	}
	
	public void updateBoard() {
		travelBoard();
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
		g.fillRect(o + d / l / 2, o + d / l / 2, d, d);
		
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
