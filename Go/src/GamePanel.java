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
				Point p = new Point();
				p.setX(i);
				p.setCX(i);
				p.setY(j);
				p.setCY(j);
				
				System.out.println(i + ", " + j);
				
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
	
	ArrayList<ArrayList<ArrayList<Point>>> gs = new ArrayList<ArrayList<ArrayList<Point>>>(); //groups
	
//	public void printGl() {
//		System.out.println("<------------------->\n");
//		
//		String output = "";
//		
//		for (int i = 0; i < gs.size(); i++) {
//			for (int j = 0; j < gs.get(i).size(); j++) {
////				output += gl.get(j).get(i).getPts().get(0).getState() + ", ";
//				output += gs.get(j).get(i).size() + ", ";
//			}
//			output += "\n";
//		}
//		
//		System.out.println(output);
//		
//		System.out.println("<------------------->\n");
//	}
	
	public void createGroups() {
		for (int i = 0; i < board.length; i++) {
			gs.add(new ArrayList<ArrayList<Point>>());
			for (int j = 0; j < board[0].length; j++) {
				ArrayList<Point> pts = new ArrayList<Point>();
				pts.add(board[i][j]);
				gs.get(i).add(pts);
			}
		}
	}
	
	public void newMethod() {
		ArrayList<ArrayList<Point>> row= new ArrayList<ArrayList<Point>>();
		ArrayList<ArrayList<Point>> col = new ArrayList<ArrayList<Point>>();
		
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				if (col.size() == 0) {
					ArrayList<Point> pts = new ArrayList<Point>();
					pts.add(board[i][j]);
					col.add(pts);
				} else {
					if (board[i][j].getState() == board[i][j-1].getState()) {
						col.get(col.size() - 1).add(board[i][j]);
					}
				}
				
				if (row.size() == 0) {
					ArrayList<Point> pts = new ArrayList<Point>();
					pts.add(board[i][j]);
					row.add(pts);
				} else {
					if (board[i][j].getState() == board[i-1][j].getState()) {
						row.get(row.size() - 1).add(board[i][j]);
					}
				}
			}
		}
	}
	
	public void countShared() {
		for (int i = 0; i < gs.size(); i++) {
			for (int j = 0; j < gs.get(i).size(); j++) {
				Point p = gs.get(j).get(i).get(0);
				
				if (i - 1 > 0) {
					if (p.getState() == board[j][i - 1].getState()) {
						if (p.getState() != 0) {
							gs.get(j).get(i).add(gs.get(j).get(i - 1).get(0));
						}
					}
				}
				
				if (i + 1 < board.length) {
					if (p.getState() == board[j][i + 1].getState()) {
						if (p.getState() != 0) {
							gs.get(j).get(i).add(gs.get(j).get(i + 1).get(0));
						}
					}
				}
				
				if (j - 1 > 0) {
					if (p.getState() == board[j - 1][i].getState()) {
						if (p.getState() != 0) {
							gs.get(j).get(i).add(gs.get(j - 1).get(i).get(0));
						}
					}
				}
				
				if (j + 1 < board.length) {
					if (p.getState() == board[j + 1][i].getState()) {
						if (p.getState() != 0) {
							gs.get(j).get(i).add(gs.get(j + 1).get(i).get(0));
						}
					}
				}
				
			}
		}
	}
	
	public void mergeGroups() {
		for (int i = 0; i < gs.size(); i++) {
			for (int j = 0; j < gs.get(i).size(); j++) {
				Point p = board[j][i];
				//sdf
			}
		}
	}
	
	public void updateBoard() {
		gs.removeAll(gs);
//		createGroups();
//		//printGl();
//		
//		countShared();
//		mergeGroups();
		//printGl();
		newMethod();
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
