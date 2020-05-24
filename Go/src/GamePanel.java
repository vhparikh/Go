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
	
	public boolean isPlayValid(Point pts, int player) {
		Point[][] copy = new Point[l][l];
		
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
		
		for (int i = 0; i < boards.size() - 1; i++) {
			for (int j = 0; j < boards.get(i).length; j++) {
				for (int k = 0; k < boards.get(i)[0].length; k++) {
					if (boards.get(i)[j][k].equals(copy[j][k])) {
						System.out.println("same!");
						i++;
						j = 0;
						k = 0;
						if (i > boards.size() - 1) {
							return false;
						}
					}
				}
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
		
		dl = d / l;
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

				g.setColor(Color.WHITE);

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
		
		double lx = (d + (l-1) * dl / l) - dl + (f.getWidth() - (d + (l-1) * dl / l)) / 2; //label x
		double ly = (l / 4) * d / l; //label y
		double ll = 200; //label length
		
		g.setColor(Color.WHITE);
		g.fillRect((int)(lx), (int) (ly), (int)(ll), (int)(ll));
		
		g.setColor(Color.BLACK);
		g.fillRect((int)(lx), (int) (ly + 1.5 * ll), (int)(ll), (int)(ll));
		
		g.setColor(Color.WHITE);
		g.drawString(updateScore(BLACK) + "", (int)(lx + ll / 2), (int)(ly + ll * 2));
		
		g.setColor(Color.BLACK);
		g.drawString(updateScore(WHITE) + "", (int)(lx + ll / 2), (int)(ly + ll / 2));
		 
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