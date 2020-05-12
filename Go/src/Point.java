
public class Point {
	
	int x = 0;
	int y = 0;
	int d = 0; //radius
	
	public final int BLANK = 0;
	public final int BLACK = 1;
	public final int WHITE = 2;
	public int state = BLANK;
	
	public boolean cpt = false; //captured or nah
	
	public boolean isCptd() {
		return cpt;
	}

	public void setCptd(boolean cpt) {
		this.cpt = cpt;
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