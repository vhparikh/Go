import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 * Sunil Jain and Vatsal Parikh's GamePanel class for Go Game for Java Class. 
 * Edited: 6/4/20.
 * 
 * Summary: 
 * Holds all of the logic of the game and is used by the game class to advance the game.
 * This also draws the game.
 */

@SuppressWarnings("serial")
public class GamePanel extends JPanel
{
	JFrame f = new JFrame();

	int l = 9; // lines
	double d = 0; // width and height
	double dl = 0; // d divided by l
	double x = 0; // content pane's height

	double lx = 0; // label x
	double ly = 0; // label y
	double ll = 0; // label length

	Point[][] board; // holds all the points

	public double boardWidth = 0;

	// point states
	public int BLANK = 0;
	public int BLACK = 1;
	public int WHITE = 2;

	public int turn = BLACK; // whose turn is it

	// Array list to keep track of pieces that are part of a group which is potentially captured
	ArrayList<Point> stack = new ArrayList<Point>();

	public GamePanel(JFrame frame, Dimension dim, int l)
	{
		super();
		f = frame; // get frame
		this.l = l; // get the length of the board

		// create the board
		board = new Point[l][l];
		for (int i = 0; i < board.length; i++)
		{
			for (int j = 0; j < board[0].length; j++)
			{
				// create new point
				Point p = new Point();
				p.setCX(i);
				p.setCY(j);
				board[i][j] = p;
			}
		}
	}

	public Point getPoint(int x, int y) { // if the player click on a point if so then return it
		for (int i = 0; i < board.length; i++)
		{
			for (int j = 0; j < board[0].length; j++)
			{
				Point p = board[i][j];
				if ((Math.abs(x - p.getX()) < p.getD() / 2) && (Math.abs(y - p.getY()) < p.getD() / 2))
				{
					return p;
				}
			}
		}
		return null;
	}

	public boolean getEndGame(int mx, int my) { // player clicks on end game then return true
		double y = ly + 3 * ll; // y value of the button
		if (mx > lx && mx < lx + ll && my > y && my < y + ll)
		{
			return true;
		}
		return false;
	}

	// removes points which were captured
	public void removePoints(Point[][] b) {
		// default to board
		if (b == null)
		{
			b = board;
		}

		// goes through the stack array list
		for (int i = 0; i < stack.size(); i++)
		{
			// goes through each point on the board
			for (int j = 0; j < b.length; j++)
			{
				for (int k = 0; k < b.length; k++)
				{
					if (b[j][k].equals(stack.get(i)))
					{ // if the point is in the stack then:
						b[j][k].setState(BLANK); // clear it from the board as it was captured
					}
				}
			}
		}
	}

	// check to see if a piece or if a group of pieces are captured by the opponent
	public boolean checkForCapture(Point[][] b, Point p) {
		// default to board
		if (b == null)
		{
			b = board;
		}

		// go through the board to find the point
		for (int i = 0; i < b.length; i++)
		{
			for (int j = 0; j < b.length; j++)
			{
				if (p == b[i][j])
				{ // if the point is found then:
					if ((i - 1 >= 0) && (p.getState() == b[i - 1][j].getState()) && (!stack.contains(b[i - 1][j])))
					{ // if the point above point p is the same color and isn't already in the stack then:
						stack.add(b[i - 1][j]); // add the point to the stack array list
						if (!checkForCapture(b, b[i - 1][j]))
						{ // run checkcapture on that point and if it returns false then:
							return false; // return and end check
						}
					}
					else if ((i - 1 >= 0) && (b[i - 1][j].getState() == getBLANK()))
					{ // otherwise if the point above is blank then return false
						return false;
					}
					if ((i + 1 < b.length) && (p.getState() == b[i + 1][j].getState())
							&& (!stack.contains(b[i + 1][j])))
					{ // same as first one except for the point below
						stack.add(b[i + 1][j]);
						if (!checkForCapture(b, b[i + 1][j]))
						{
							return false;
						}
					}
					else if ((i + 1 < b.length) && (b[i + 1][j].getState() == getBLANK()))
					{
						return false;
					}
					if ((j - 1 >= 0) && (p.getState() == b[i][j - 1].getState()) && (!stack.contains(b[i][j - 1])))
					{ // same as first one except for the point to the left
						stack.add(b[i][j - 1]);
						if (!checkForCapture(b, b[i][j - 1]))
						{
							return false;
						}
					}
					else if ((j - 1 >= 0) && (b[i][j - 1].getState() == getBLANK()))
					{
						return false;
					}
					if ((j + 1 < b[0].length) && (p.getState() == b[i][j + 1].getState())
							&& (!stack.contains(b[i][j + 1])))
					{ // same as first on except for the point to the right
						stack.add(b[i][j + 1]);
						if (!checkForCapture(b, b[i][j + 1]))
						{
							return false;
						}
					}
					else if ((j + 1 < b[0].length) && (b[i][j + 1].getState() == getBLANK()))
					{
						return false;
					}
					return true;
				}
			}
		}
		return false;
	}

	public int getScore(int player) { // returns the inputed player's score by going through the board
		int score = 0;

		for (int i = 0; i < board.length; i++)
		{
			for (int j = 0; j < board.length; j++)
			{
				if (board[i][j].getState() == player)
				{
					score++;
				}
			}
		}
		return score;
	}

	// updates the board
	public void updateBoard(Point[][] b) {
		// default to board
		if (b == null)
		{
			b = board;
		}

		// goes through the board
		for (int i = 0; i < b.length; i++)
		{
			for (int j = 0; j < b.length; j++)
			{
				if (b[i][j].getState() != BLANK)
				{ // if the point is not blank then:
					stack.add(b[i][j]); // add it to the stack
					if (checkForCapture(b, b[i][j]))
					{ // run checkcapture if it is true remove all the points that are in the stack array list
						removePoints(b);
					}
					stack.removeAll(stack); // remove all the values within array list
				}
			}
		}
	}

	// if the move doesnt contradict the ko rule then return false
	public boolean checkKO(Point pts, int player) {
		Point[][] copy = new Point[l][l];

		// clone board onto copy
		for (int i = 0; i < copy.length; i++)
		{
			for (int j = 0; j < copy.length; j++)
			{
				copy[i][j] = new Point();
				copy[i][j].setState(board[i][j].getState());
			}
		}

		// make move and update copy
		copy[pts.getCX()][pts.getCY()].setState(player);
		updateBoard(copy);

		// check if the boards are different if so then return accordingly
		for (int i = 0; i < l; i++)
		{
			for (int j = 0; j < l; j++)
			{
				if (copy[i][j].getState() != board[i][j].getState())
				{
					return false;
				}
			}
		}
		return true;
	}

	// update all the grahpics variables based on the screen height
	public void updateVars() {
		x = f.getContentPane().getHeight(); // get the height without the top bar

		// get as close to x - x / l to create a margin
		for (int i = 0; i < x - x / l; i += l)
		{
			d = i;
		}
		dl = d / l;

		// label variables
		lx = (d + (l - 1) * dl / l) - dl + (f.getWidth() - (d + (l - 1) * dl / l)) / 2;
		ly = dl;
		ll = dl * 2;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// set background to dark gray
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, f.getWidth(), f.getHeight());

		updateVars();

		// color the border according to the player
		if (turn == BLACK)
		{
			g.setColor(Color.BLACK);
		}
		else if (turn == WHITE)
		{
			g.setColor(Color.WHITE);
		}
		g.fillRect((int) dl / l, (int) dl / l, (int) (d + (l - 2) * dl / l), (int) (d + (l - 2) * dl / l));

		// make the board's background dark gray
		g.setColor(Color.DARK_GRAY);
		g.fillRect((int) dl / 2, (int) dl / 2, (int) (d), (int) (d));

		// draw the white lines of the board
		g.setColor(Color.WHITE);
		g.drawRect((int) dl, (int) dl, (int) (d - d / l), (int) (d - d / l));
		for (int i = (int) (dl + d / l); i < dl + d - d / l; i += d / l)
		{
			g.drawLine(i, (int) dl, i, (int) d);
			g.drawLine((int) dl, i, (int) d, i);
		}

		// draw the "stones" on the board
		for (int i = 0; i < board.length; i++)
		{
			for (int j = 0; j < board[0].length; j++)
			{
				Point p = board[i][j]; // get the point

				// set the x and y and diameter values of the point
				p.setX(dl + (i) * (d / l));
				p.setY(dl + (j) * (d / l));
				p.setD((d - dl - dl) / l);

				// if the point is occupied then color accordingly
				if (p.getState() == p.getBLACK())
				{
					g.setColor(Color.BLACK);
					g.fillOval((int) (p.getX() - p.getD() / 2), (int) (p.getY() - p.getD() / 2), (int) p.getD(),
							(int) p.getD());
				}
				else if (p.getState() == p.getWHITE())
				{
					g.setColor(Color.WHITE);
					g.fillOval((int) (p.getX() - p.getD() / 2), (int) (p.getY() - p.getD() / 2), (int) p.getD(),
							(int) p.getD());
				}
			}
		}

		// white score box
		g.setColor(Color.WHITE);
		g.fillRect((int) (lx), (int) (ly), (int) (ll), (int) (ll));

		// black score box
		g.setColor(Color.BLACK);
		g.fillRect((int) (lx), (int) (ly + 1.5 * ll), (int) (ll), (int) (ll));

		// end game button
		g.setColor(Color.RED);
		g.fillRect((int) (lx), (int) (ly + 3 * ll), (int) (ll), (int) (ll));

		// black score text
		g.setColor(Color.WHITE);
		g.drawString(getScore(BLACK) + "", (int) (lx + ll / 2), (int) (ly + ll * 2));

		// white score text
		g.setColor(Color.BLACK);
		g.drawString(getScore(WHITE) + "", (int) (lx + ll / 2), (int) (ly + ll / 2));
		g.drawString("End Game", (int) (lx + ll / 3), (int) (ly + ll * 3.5));
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