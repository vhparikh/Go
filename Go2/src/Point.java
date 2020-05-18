

public class Point {
	
	int x = 0;
	int y = 0;

	int cx = 0;
	int cy = 0;
	int d = 0; //radius
	
	public final int BLANK = 0;
	public final int BLACK = 1;
	public final int WHITE = 2;
	public int state = BLANK;
	
	public int owner = BLANK;
	
	public int getOwner() {
		return owner;
	}
	
	public void setOwner(int owner) {
		this.owner = owner;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
		
	public int getD() {
		return d;
	}

	public void setD(int r) {
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
