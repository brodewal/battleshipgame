import java.util.ArrayList;

public class Map {
	public final int xsize;
	public final int ysize;
	private ArrayList<Ship> ships;
	
	
	/*
	 * Map contains:
	 * horizontal and vertical size
	 * ship coordinates and definitions
	 * 
	 */
	
	public Map(int xsize, int ysize) {
		this.xsize = xsize;
		this.ysize = ysize;
		ships = new ArrayList<Ship>();
	}
	
	public int getNumberOfShips() {return ships.size();}
	
	public ArrayList<Ship> getShips() {return ships;}
	
	public ArrayList<Point> getOccupiedSpaces() {
		ArrayList<Point> totalOccupiedSpaces = new ArrayList<Point>();
		for (int i = 0; i < ships.size(); i++) {
			totalOccupiedSpaces.addAll(ships.get(i).getOccupiedSpaces());
		}
		return totalOccupiedSpaces;
	}
	
	public boolean shipExists(Point point) {
		for (Ship sh : ships) {
			for (Point p : sh.getOccupiedSpaces()) {
				if (p.equals(point)) return true;
			}
		}
		return false;
	}
	
	public Ship getShipAtPoint(Point point) {
		for (Ship sh : ships) {
			for (Point p : sh.getOccupiedSpaces()) {
				if (p.equals(point)) return sh;
			}
		}
		return null;
	}
	
	public String getNameOfShipAtPoint(Point point) {
		Ship sh = getShipAtPoint(point);
		if (sh != null) {
			return sh.getName();
		}
		return "no";
	}
	
	/*
	 * Places one ship of caller-defined length inside the bounds
	 * of a pre-defined map. Return true if successful
	 * Return false if any proposed space already occupied by ship
	 * 
	 * */
	
	public boolean placeNewShip(int length) {
		String orientation = ((int)(Math.random()*2) == 1) ? "Vertical" : "Horizontal";
		
		int maxX = (orientation == "Horizontal" ? (xsize - length) : xsize);
		int maxY = (orientation == "Vertical" ? (ysize - length) : ysize);
		
		Point head = new Point((int)(Math.random() * maxX), (int)(Math.random() * maxY));
		Ship ship = new Ship("Ship" + (int)(ships.size()+1), head, length, orientation);
		for (int i = 0; i < ships.size(); i++) {
			if (Ship.collides(ship, ships.get(i))) return false;
		}
		ships.add(ship);
		return true;
	}
	
	public String toString() {
		String s = "";
		for (int i = 0; i < ships.size(); i++) {
			s += ships.get(i);
		}
		
		return s;
	}
	
	public static void main(String[] args) {

	}
}
