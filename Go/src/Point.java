
/*
 * Sunil Jain and Vatsal Parikh's Point class for Go Game for Java Class.
 * Edited: 6/4/20.
 * 
 * Summary: 
 * Holds all of the point's nessesary data and all of the getters and setters.
 */

public class Point
{
	double x = 0; // x in the board
	double y = 0; // y in the board
	int cx = 0; // x in the screen
	int cy = 0; // y in the screen
	double d = 0; // radius

	public final int BLANK = 0; // blank state
	public final int BLACK = 1; // black state
	public final int WHITE = 2; // white state
	public int state = BLANK; // what state is it in

	// getters and setters
	public double getX() {
		return x;
	}

	public void setX(double e) {
		this.x = e;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getD() {
		return d;
	}

	public void setD(double r) {
		this.d = r;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getCX() {
		return cx;
	}

	public void setCX(int x) {
		this.cx = x;
	}

	public int getCY() {
		return cy;
	}

	public void setCY(int y) {
		this.cy = y;
	}

	public int getBLANK() {
		return BLANK;
	}

	public int getBLACK() {
		return BLACK;
	}

	public int getWHITE() {
		return WHITE;
	}
}