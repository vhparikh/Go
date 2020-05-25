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
	double dl = 0; //d divided by l
	double x = 0; //content pane's height
	
	double lx = 0; //label x
	double ly = 0; //label y
	double ll = 0; //label length

	Point[][] board;

	public double boardWidth = 0;

	public int BLANK = 0;
	public int BLACK = 1;
	public int WHITE = 2;
	public int turn = BLACK;
	
	ArrayList<Point> stack = new ArrayList<Point>();
	
	ArrayList<Point[][]> boards = new ArrayList<Point[][]>();

	public GamePanel(JFrame frame, Dimension dim, int l) {
		super();

		f = frame;
		
		this.l = l;

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
					return p;
				}
			}
		}
		return null;
	}
	
	public boolean getEndGame(int mx, int my) {
		double y = ly + 3 * ll;
		if (mx > lx && mx < lx + ll && my > y && my < y + ll) {
			return true;
		}
		return false;
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

	public int getScore(int player) {
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
	
	public boolean isPlayValid(Point pts, int player) {
		Point[][] copy = new Point[l][l];
		
		//clone board onto copy
		for (int i = 0; i < copy.length; i++) {
			for (int j = 0; j < copy.length; j++) {
				copy[i][j] = new Point();
				copy[i][j].setState(board[i][j].getState());
			}
		}
		
		//make move and update copy
		copy[pts.getCX()][pts.getCY()].setState(player);
		updateBoard(copy);
		
		//check if the boards are different if so then return accordingly
		for (int i = 0; i < l; i++) {
			for (int j = 0; j < l; j++) {
				if (copy[i][j].getState() != board[i][j].getState()) {
					return true;
				}
			}
		}
		return false;
	}

	public void setVars() {
		x = f.getContentPane().getHeight();
		
		for (int i = 0; i < x - x / l; i += l) {
			d = i;
		}
		
		dl = d / l;
		lx = (d + (l-1) * dl / l) - dl + (f.getWidth() - (d + (l-1) * dl / l)) / 2;
		ly = dl; 
		ll = dl * 2;
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
		g.fillRect((int) dl / l, (int) dl / l, (int) (d + (l-2) * dl / l), (int) (d + (l-2) * dl / l));
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect((int) dl / 2, (int) dl / 2, (int) (d), (int) (d));

		g.setColor(Color.WHITE);
		g.drawRect((int)dl, (int)dl, (int)(d - d / l), (int)(d - d / l));
		for (int i = (int) (dl + d / l); i < dl + d - d / l; i += d / l) {
			g.drawLine(i, (int) dl, i, (int) d);
			g.drawLine((int) dl, i, (int) d, i);
		}

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				Point p = board[i][j];

				p.setX(dl + (i) * (d / l));
				p.setY(dl + (j) * (d / l));
				p.setD((d - dl - dl) / l);

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
		
		g.setColor(Color.WHITE);
		g.fillRect((int)(lx), (int) (ly), (int)(ll), (int)(ll));
		
		g.setColor(Color.BLACK);
		g.fillRect((int)(lx), (int) (ly + 1.5 * ll), (int)(ll), (int)(ll));
		
		g.setColor(Color.RED);
		g.fillRect((int)(lx), (int) (ly + 3 * ll), (int)(ll), (int)(ll));
		
		g.setColor(Color.WHITE);
		g.drawString(getScore(BLACK) + "", (int)(lx + ll / 2), (int)(ly + ll * 2));
		
		g.setColor(Color.BLACK);
		g.drawString(getScore(WHITE) + "", (int)(lx + ll / 2), (int)(ly + ll / 2));
		g.drawString("End Game", (int)(lx + ll / 3), (int)(ly + ll * 3.5));
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