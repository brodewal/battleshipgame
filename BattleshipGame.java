import java.util.ArrayList;
import java.util.Scanner;

public class BattleshipGame {
	private Map map;
	private ArrayList<Point> shots;
	private ArrayList<Ship> sunkShips;
	private int rounds;
	
	public BattleshipGame() {
		setupGame();
		mainLoop();
	}
	
	/*
	 * Define game logic definitions, such as number of rounds,
	 * map size,
	 * number and length of ships,
	 * location of ships,
	 * and other required business logic
	 */
	
	private void setupGame() {
		this.rounds = 100;
		map = new Map(20,20);
		ArrayList<Integer> intendedSizes = new ArrayList<Integer>();
		intendedSizes.add(5);
		intendedSizes.add(5);
		intendedSizes.add(4);
		intendedSizes.add(4);
		intendedSizes.add(4);
		intendedSizes.add(3);
		while(map.getNumberOfShips() < 6) {
			if (map.placeNewShip(intendedSizes.get(0))) 
				intendedSizes.remove(0);
			//else
				//System.out.println("Collide!");
		}
		this.shots = new ArrayList<Point>();
		this.sunkShips = new ArrayList<Ship>();

	}
	
	/*
	 * Gameplay logic follows after set up:
	 * Repeat until no more ships or shots.
	 * 	Remind player of current game state (ships, locations, rounds) remaining
	 * 	Ask player for coordinate (for shot)
	 * 	Check if invalid shot: out of bounds or already on list of already executed coordinates
	 * 		If so, skip to the next round.
	 * 		
	 * 	Check: If ship at location, 
	 * 		if sunk (all coordinates for a ship have been shot), add ship to ships sunk
	 * 		record location shot, and record, if any, the ship hit
	 * 		Inform player of above
	 * 
	 * 
	 * Once all the shots have been depleted (no more rounds)
	 * 		Compare number of ships sank at end to total number of ships spawned at beginning,
	 * 		The game is won if and only if both are equal.
	 * 		
	 * After finalizing and displaying game status, end game.
	 * */
	
	private void mainLoop() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Welcome to BATTLESHIP!");
		while (rounds > 0 && shipCountRemaining() > 0) {
			System.out.println("There are " + shipCountRemaining() + " ships and " + rounds + " rounds remaining."
					+ "\n" + getSuccessfulAttacks().size() + " locations hit. " + getLocationsRemaining().size() + " locations remain."
					+ "\nPlease enter target coordinate "
					+ "(e.g. " + getLocationsRemaining().get((int)(Math.random()*getLocationsRemaining().size())) + ")");
			
			/* show example of accepted text coordinate format, and maybe give the player a hint */
			
			String coordinate = sc.next();
			Point shot = new Point(coordinate);
			System.out.println("You entered: " + shot);
			if (alreadyShot(shot)) {
				System.out.println("Sorry! You entered this coordinate already. Try again.");
				rounds--;
			}
			else if (!shot.inBounds(map)){
				System.out.println("Sorry! Your coordinates are out of bounds. Try again.");
				rounds--;
			} else {
				shots.add(shot);
				if (map.shipExists(shot)) {
					String shipName = map.getNameOfShipAtPoint(shot);
					Ship attacked = map.getShipAtPoint(shot);
					if (attacked.isSunk(this)) {
						System.out.println("Super! You sank: " + shipName + "!");
						sunkShips.add(attacked);

					}
					else {
						System.out.println("You hit: " + shipName + "!\nThis ship has: " + attacked.hp(this) + " spaces before it sinks.");
					}
				} else {
					System.out.println("You hit the water.");
				}
				rounds--;
			}
		}
		if (won()) {
			System.out.println("Super - congratulations - you won the game - no more ships!!");
		} else {
			System.out.println("Sorry - you have lost the game - some ships still remain...");
		}
		sc.close();
	}
	
	/*
	 * Get points already marked by player in previous rounds.
	 */
	public boolean alreadyShot(Point p) {
		for (int i = 0; i < shots.size(); i++) {
			if (p.equals(shots.get(i))) return true;
		}
		return false;
	}
	
	public ArrayList<Point> getShotsFired() {
		return shots;
	}
	
	/*
	 * Get ship locations successfully attacked by player
	 */
	
	public ArrayList<Point> getSuccessfulAttacks() {
		
		ArrayList<Point> successfulAttacks = new ArrayList<Point>();
		for (Point p : map.getOccupiedSpaces()) {
			for (Point q : this.getShotsFired()) {
				if (p.equals(q)) successfulAttacks.add(p);
			}
		}
		return successfulAttacks;
	}
	
	/*
	 * Infer locations remaining, based on locations successfully hit
	 * Take list of ship locations, and remove locations already input by player.
	 */
	
	public ArrayList<Point> getLocationsRemaining() {
		ArrayList<Point> occupiedSpaces = new ArrayList<Point>();
		occupiedSpaces.addAll(map.getOccupiedSpaces());

		
		for (int i = 0; i < occupiedSpaces.size(); i++) {
			for (int j = 0; j < getSuccessfulAttacks().size(); j++) {
				if (occupiedSpaces.get(i).equals(getSuccessfulAttacks().get(j)))
				{
					occupiedSpaces.remove(occupiedSpaces.get(i));
				}
			}
		}
		
		return occupiedSpaces;
	}
	
	
	
	/*
	 * Win condition definitions below: Number of ships completely covered by shots
	 * must be number of ships spawned at the beginning
	 */
	
	
	public int shipCountRemaining() {
		return map.getNumberOfShips() - sunkShips.size();
	}
	
	public boolean won() {
		return shipCountRemaining() == 0;
	}
	
	
	
	public static void main(String[] args) {
		new BattleshipGame();
		System.out.println("Game over.");
	}
	
}
