import java.util.ArrayList;

/**
 * Represents a holder of tiles that can be grabbed during a round.
 * Got rid of individual 
 * @author jessi
 *
 */
public class Disc {
	boolean inUse;
	boolean centerDisc;
	
	// ArrayList because center could have more than 4
	ArrayList<Tile> tiles;
	
	public Disc(boolean centerDisc) {
		this.centerDisc = centerDisc;
		//Want this to be true if it is center disc, so this should work
		inUse = false;

		
		tiles = new ArrayList<Tile>();
	}
	
	public void fillDisc(Tile[] tileArr) {
		if (tileArr.length != 4) {
			throw new IllegalArgumentException("Invalid number of tiles in tile array.");
		} else if (tileArr[0] == null || tileArr[1] == null || tileArr[2] == null || tileArr[3] == null) {
			throw new IllegalArgumentException("Tile array cannot include null tiles.");
		}
		
		clearDisc();
		inUse = true;
		
		//Add each element to arraylist
		for (int i = 0; i < tileArr.length; ++i) {
			tiles.add(i, tileArr[i]);
		}
		tiles.sort(null);
	}
	
	public void clearDisc() {
		inUse = false;
		tiles.clear();
	}
	
	public ArrayList<Tile> grabColor(int color, Disc center) {
		if (!center.centerDisc) {
			throw new IllegalArgumentException("The disc passed in as the center is not a center disc.");
		} else if (color < 1 || color > 5) {
			throw new IllegalArgumentException("Attempted to grab an invalid color.");
		}
		
		// Sort tiles into center or grab arrayList
		ArrayList<Tile> grab = new ArrayList<Tile>();
		ArrayList<Tile> centerAdd = new ArrayList<Tile>();
		while (!tiles.isEmpty()) {
			if (this.tiles.get(0).color == color) {
				grab.add(tiles.remove(0));
			} else {
				centerAdd.add(tiles.remove(0));
			}
		}
		
		// Adjust Center Tile Array after to prevent infinite loop if being run on the center disc
		center.tiles.addAll(centerAdd);
		center.tiles.sort(null);
		
		// Push remaining tiles to center disc, unless this is the center disc
		if (!centerDisc) {
			center.inUse = true;
			clearDisc();
		} else if (tiles.isEmpty()) {
			// If there are no more tiles remaining in the center, set to not in use
			// which will trigger that this is the end of a round if all discs are also clear
			inUse = false;
		}
		
		return grab;
	}
	
	/**
	 * Created just to use in unit tests
	 * @param color color to count up
	 * @return number of tiles of specified color on the disk
	 */
	protected int getNumberOfColor(int color) {
		int count = 0 ;
		for (int i = 0; i < tiles.size(); ++i) {
			if (tiles.get(i).color == color) {
				count = count +1;
			}
		}
		return count;
	}
}
