
public class Point {
	
	/*
	 * 2-D coordinate location `representation class
	 */
	
	public final int x, y;
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/*
	 * Converts a string coordinate format into this class format
	 * String coordinate format is inverted from class x,y format as per project requirements.
	 * Also, textual format is one indexed and class's format is zero indexed.
	 * A2 is (1,0) and D1 is (0,3) for example
	 */
	
	public Point(String coordinates) {
		char letterCoordinate = Character.toUpperCase(coordinates.charAt(0));
		int numberCoordinate;
		try {
			numberCoordinate = Integer.parseInt(coordinates.substring(1)) - 1;
		} catch (NumberFormatException e) {
			numberCoordinate = 0;
		}
		if (letterCoordinate >= 'A' && letterCoordinate <= 'Z')
			this.y = letterCoordinate - 'A';
		else
			this.y = 0;
		this.x = numberCoordinate;
		
	}
	
	/* Two Points are equal if and only if the x and y coordinates for both points match.
	 * 
	 */
	
	public boolean equals(Point p) {
		return p.toString().equals(this.toString());
	}
	
	/*
	 * Text converter for above equals method
	 */
	public boolean equalsTextCoordinate(String textCoordinate) {
		return (new Point(textCoordinate)).equals(this);
	}
	
	/*
	 * Converts class representation of a (x,y) point into a (alphabet y+1, numerical x+1)
	 * human readable format, same as in constructor from String
	 */
	
	public String toString() {
		return (char)('A' +y) + "" + (int)(x+1);
	}
	
	/*
	 * Checks if point is located in a Map, or, strictly speaking, 
	 * a valid location a Ship can appear in for a given Map.
	 */
	
	public boolean inBounds(Map m) {
		return (x < m.xsize) && (y < m.ysize) && (x >= 0) && (y >= 0);
	}
	
	public static void main(String[] args) {
		Map map = new Map(20,20);
		Point point = new Point("t20");
		System.out.println((point).inBounds(map));
	}
	
}

