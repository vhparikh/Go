import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GamePanel extends JPanel {
	JFrame f = new JFrame();

	int l = 9; // lines
	double d = 0; // width and height
	double o = 0; // offset
	double r = 0;
	double w = 0; // width
	double x = 0;

	Point[][] board;

	public double boardWidth = 0;

	public int BLANK = 0;
	public int BLACK = 1;
	public int WHITE = 2;
	public int turn = BLACK;
	
	ArrayList<Point> stack = new ArrayList<Point>();
	
	ArrayList<Point[][]> boards = new ArrayList<Point[][]>();

	public GamePanel(JFrame frame, Dimension dim) {
		super();

		f = frame;

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
	
	public boolean isPlayValid(Point pts, int player) {
		Point[][] copy = board;
		
		for (int i = 0; i < copy.length; i++) {
			for (int j = 0; j < copy[i].length; j++) {
				copy[i][j] = copy[i][j];
				if (copy[i][j].equals(pts)) {
					copy[i][j].setState(player);
					System.out.println("copy setted");
				}
			}
		}
		
		updateBoard(copy);
		System.out.println(boards.size());
		
		for (int i = 0; i < boards.size(); i++) {
			if (boards.get(i).equals(copy)) {
				System.out.println("same!");
				return false;
			}
		}
		return true;
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

	public void cleanStack(Point[][] b) {
		if (b == null) {
			b = board;
		}
		
		for (int i = 0; i < stack.size(); i++) {
			for (int j = 0; j < b.length; j++) {
				for (int k = 0; k < b.length; k++) {
					if (b[j][k].equals(stack.get(i))) {
						b[j][k].setState(BLANK);
					}
				}
			}
		}
	}

	public boolean checkForCapture(Point[][] b, Point p) {
		if (b == null) {
			b = board;
		}
		
		for (int i = 0; i < b.length; i++) {
			for (int j = 0; j < b.length; j++) {
				if (p == b[i][j]) {
					if ((i - 1 >= 0) && (p.getState() == b[i - 1][j].getState())
							&& (!stack.contains(b[i - 1][j]))) {
						stack.add(b[i - 1][j]);
						if (!checkForCapture(b, b[i - 1][j])) {
							return false;
						}
					} else if ((i - 1 >= 0) && (b[i - 1][j].getState() == getBLANK())) {
						return false;
					}
					if ((i + 1 < b.length) && (p.getState() == b[i + 1][j].getState())
							&& (!stack.contains(b[i + 1][j]))) {
						stack.add(b[i + 1][j]);
						if (!checkForCapture(b, b[i + 1][j])) {
							return false;
						}
					} else if ((i + 1 < b.length) && (b[i + 1][j].getState() == getBLANK())) {
						return false;
					}
					if ((j - 1 >= 0) && (p.getState() == b[i][j - 1].getState())
							&& (!stack.contains(b[i][j - 1]))) {
						stack.add(b[i][j - 1]);
						if (!checkForCapture(b, b[i][j - 1])) {
							return false;
						}
					} else if ((j - 1 >= 0) && (b[i][j - 1].getState() == getBLANK())) {
						return false;
					}
					if ((j + 1 < b[0].length) && (p.getState() == b[i][j + 1].getState())
							&& (!stack.contains(b[i][j + 1]))) {
						stack.add(b[i][j + 1]);
						if (!checkForCapture(b, b[i][j + 1])) {
							return false;
						}
					} else if ((j + 1 < b[0].length) && (b[i][j + 1].getState() == getBLANK())) {
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
				if (board[i][j].getState() == player) {
					score++;
				}
			}
		}
		return score;
	}

	public void updateBoard(Point[][] b) {
		if (b == null) {
			b = board;
		}
		
		for (int i = 0; i < b.length; i++) {
			for (int j = 0; j < b.length; j++) {
				if (b[i][j].getState() != BLANK) {
					stack.add(b[i][j]);
					if (checkForCapture(b, b[i][j])) {
						cleanStack(b);
					}
					stack.removeAll(stack);
				}

			}
		}
		
		if (b == board) {
			boards.add(board);
		}
	}

	public void setVars() {
		x = f.getContentPane().getHeight();
		
		for (int i = 0; i < x - x / l; i += l) {
			d = i;
		}
		
		o = d / l;

		r = d - o;
		w = d - o - o;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, f.getWidth(), f.getHeight());

		setVars();

		if (turn == BLACK) {
			g.setColor(Color.BLACK);
		} else if (turn == WHITE) {
			g.setColor(Color.WHITE);
		}
		g.fillRect((int) o / l, (int) o / l, (int) (d + (l-2) * o / l), (int) (d + (l-2) * o / l));
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect((int) o / 2, (int) o / 2, (int) (d), (int) (d));

		g.setColor(Color.WHITE);
		g.drawRect((int)o, (int)o, (int)(d - d / l), (int)(d - d / l));
		for (int i = (int) (o + d / l); i < o + d - d / l; i += d / l) {
			g.drawLine(i, (int) o, i, (int) d);
			g.drawLine((int) o, i, (int) d, i);
		}

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				Point p = board[i][j];

				g.setColor(Color.WHITE);

				p.setX(o + (i) * (d / l));
				p.setY(o + (j) * (d / l));
				p.setD(w / l);

				if (p.getState() == p.getBLACK()) {
					g.setColor(Color.BLACK);
					g.fillOval((int) (p.getX() - p.getD() / 2), (int) (p.getY() - p.getD() / 2), (int) p.getD(),
							(int) p.getD());
				} else if (p.getState() == p.getWHITE()) {
					g.setColor(Color.WHITE);
					g.fillOval((int) (p.getX() - p.getD() / 2), (int) (p.getY() - p.getD() / 2), (int) p.getD(),
							(int) p.getD());
				}
			}
		}
		
		double lx = (d + (l-1) * o / l) - o + (f.getWidth() - (d + (l-1) * o / l)) / 2; //label x
		double ly = (l / 4) * d / l; //label y
		
		g.setColor(Color.WHITE);
		g.fillRect((int)(lx), (int) (ly), (int)o * 2, (int)o * 2);
		
		g.setColor(Color.BLACK);
		g.fillRect((int)(lx), (int) (ly * l / 3), (int)o * 2, (int)o * 2);
		
		g.setColor(Color.WHITE);
		g.drawString(updateScore(BLACK) + "", (int)(lx + o), (int)((ly * l / 3) + o));
		
		g.setColor(Color.BLACK);
		g.drawString(updateScore(WHITE) + "", (int)(lx + o), (int)(ly + o));
		 
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