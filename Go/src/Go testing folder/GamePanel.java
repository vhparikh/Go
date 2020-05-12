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
	
	public Point[] checkLiberties(Point p) {
		Point[] ls = new Point[4];
		
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (board[i][j].equals(p)) {
					if (i-1 >= 0) {
						ls[0] = board[i-1][j];
					} else {
						ls[0] = null;
					}
					if (i+1 < board.length) {
						ls[1] = board[i+1][j];
					} else {
						ls[1] = null;
					}
					if (j-1 >= 0) {
						ls[2] = board[i][j-1];
					} else {
						ls[2] = null;
					}
					if (j+1 < board[0].length) {
						ls[3] = board[i][j+1];
					} else {
						ls[3] = null;
					}
				}
				return ls;
			}
		}
		
		return null;
	}
	
	ArrayList<Group> groups = new ArrayList<Group>();
	
	public void group() {
		//sdf
	}
	
	public void createGroups() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				Point p = board[i][j];
				Point[] ls = checkLiberties(p);
				
				for (int k = 0; k < ls.length; k++) {
					if(ls[k] != null && !ls[k].isGrouped()) {
						if (ls[k].getState() == p.getState()) {
							Group g = new Group();
							g.getPts().add(p);
							g.getPts().add(ls[k]);
						}
					}
				}
			}
		}
	}
	
	public void mergeGroups() {
		for (int k = 0; k < groups.size(); k++) {
			for (int i = 0; i < groups.get(k).getPts().size(); i++) {
				Point p = groups.get(k).getPts().get(i);
				
				for (int j = 0; j < groups.size(); j++) {
					if (!groups.get(j).equals(groups.get(k))) {
						if (groups.get(j).getPts().contains(p)) {
							for (int j2 = 0; j2 < board.length; j2++) {
								groups.get(k).getPts().add(groups.get(j).getPts().get(j2));
								groups.remove(groups.get(j));
							}
						}
					}
				}
			}
		}
	}
	
	public void updateBoard() {
		groups.removeAll(groups);
		createGroups();
		//mergeGroups();
		
		System.out.println(groups.size());
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
