/**
 * Represents a tile with a color (1-5). Implements comparable to make sorting easier.
 * @author jessi
 *
 */
public class Tile implements Comparable<Tile>{
	// Color should be a number 1-5 to represent a color
	int color;
	
	public Tile(int color) {
		this.color = color;
	}

	@Override
	public int compareTo(Tile tile2) {
		return color- tile2.color;
	}

}
