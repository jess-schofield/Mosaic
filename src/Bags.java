import java.util.ArrayList;
import java.util.Random;

/**
 * Creates a draw bag and discard bag to spawn tiles from and return them to. Ensures proper probabilities
 * because if a tile is used in confirmed there should be fewer of that color in rotation. 
 * @author jessi
 *
 */
public class Bags {
	ArrayList<Tile> drawBag;
	ArrayList<Tile> discardBag;
	
	
	// CONSTRUCTOR
	public Bags() {
		// Initialize ArrayLists
		drawBag = new ArrayList<Tile>();
		discardBag = new ArrayList<Tile>();
		
		// Add 20 Tiles of Each Color to drawBag
		for (int i = 0; i < 20; ++i) {
			Tile tile1 = new Tile(1);
			Tile tile2 = new Tile(2);
			Tile tile3 = new Tile(3);
			Tile tile4 = new Tile(4);
			Tile tile5 = new Tile(5);
			
			drawBag.add(tile1);
			drawBag.add(tile2);
			drawBag.add(tile3);
			drawBag.add(tile4);
			drawBag.add(tile5);
		}
	}
	
	// PUBLIC METHODS
	
	/**
	 * Removes a tile from the draw bag at random.
	 * @return A random tile from the draw bag.
	 */
	public Tile removeFromDraw() {
		// If bag is empty, refill
		if (drawBag.isEmpty()) {
			refillDraw();
		}
		
		// Remove a tile at random
		Random rand = new Random();
		int randIndex = rand.nextInt(drawBag.size());
		return drawBag.remove(randIndex);
	}
	
	/**
	 * Adds a used tile to the discard bag so it can be used to refresh the 
	 * draw bag when it becomes empty
	 * @param discardTile the tile to be added to the discard bag
	 */
	public void addToDiscard(Tile discardTile) {
		discardBag.add(discardTile);
	}
	
	/**
	 * Takes all of the tiles from the discard bag and adds them to the draw bag
	 */
	private void refillDraw() {
		Tile current = null;
		while (discardBag.isEmpty() == false) {
			current = discardBag.remove(0);
			drawBag.add(current);
		}
	}

}
