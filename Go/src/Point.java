public class Point {
	double x = 0;
	double y = 0;
	int cx = 0;
	int cy = 0;
	double d = 0; //radius
	
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