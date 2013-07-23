package org.custom.graph.line;

public class Point implements Comparable<Point> {

	private int x;
	private int y;
	
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
	
	@Override
	public int compareTo(Point another) {

		if(x < another.x)
			return -1;
		else if (x > another.x)
			return 1;
		
		return 0;
	}
	
}
