import java.util.ArrayList;

/**
 * This class should take in the number of players, and the bag objects 
 * and maintain the center of the game board (discs, center)
 */
public class Center {
	// Keeps track of whether anyone has grabbed from the center already each round
	boolean centerGrabThisRound;
	int factoryTiles;
	Disc[] gameDiscs;
	Disc center; //COuld probs get rid of this because it is gameDiscs[0] too but lazy
	ArrayList<PlayerBoard> playerArray;
	Bags gameBags;
	
	
	//CONSTRUCTOR, Assume #Players Validated 2-4
	public Center(int numPlayers) {
		// Determine number of Factory Tiles
		if (numPlayers == 2) {
			factoryTiles = 5;
		} else if (numPlayers == 3) {
			factoryTiles = 7;
		} else if (numPlayers == 4) {
			factoryTiles = 9;
		} else {
			new IllegalArgumentException("Incorrect Number of Players in Center Constructor");
		}
		
		// Create a center "Disc"
		center = new Disc(true);
		centerGrabThisRound = false;
		
		// Create a disc for each factory
		gameDiscs = new Disc[factoryTiles + 1];
		gameDiscs[0] = center;
		for (int i = 1; i <= factoryTiles; ++i) {
			Disc newDisc = new Disc(false);
			gameDiscs[i] = newDisc;
		}
		
		// Create Bags for the game
		gameBags = new Bags();
		
		// Create a Player Board for Each Player
		playerArray = new ArrayList<PlayerBoard>();
		for (int i = 0; i < numPlayers; ++i) {
			PlayerBoard newPlayer = new PlayerBoard(i + 1);
			playerArray.add(newPlayer);
		}
	}
	
	// PUBLIC METHODS
	
	/**
	 * Triggers each player board to preform all end of round tasks.
	 */
	public void tallyRoundPoints() {
		// Loop through players, add points and print them
		int roundScore = 0;
		for (int i = 0; i < playerArray.size(); ++i) {
			roundScore = playerArray.get(i).endOfRound(gameBags);
			System.out.println("Player " + (i +1) + " scored " + roundScore + " points this round.");
			System.out.println("Player " + (i +1) + " has " + playerArray.get(i).getOverallScore() + " points overall.");
			System.out.println("");
		}
	}
	
	/**
	 * Loops through player array to determine if any players have met the win condition.
	 * @return true if any players have met the game win condition
	 */
	private boolean checkForCompleteGame() {
		for (int i = 0; i < playerArray.size(); ++i) {
			if (playerArray.get(i).didPlayerCompleteGame()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Loops through all players to find the one with the highesr overall score
	 * @return the end user readable player nember (identifier + 1)
	 */
	public int determineWinner() {
		int winner = -1;
		int highScore = -1;
		if (checkForCompleteGame()) {
			for (int i = 0; i < playerArray.size(); ++i) {
				if (playerArray.get(i).getOverallScore() > highScore) {
					highScore = playerArray.get(i).getOverallScore();
					winner = i + 1;
				}
			}
		}
		return winner;
	}

	/**
	 * Completes all tasks to stand up a new round
	 * 1. Re-sets round specific variables to starting values
	 * 2. Re-Fills the center discs with tiles from the draw bag
	 */
	public void newRound() {
		// Takes in the state of the bags and the number of players, sorts tiles onto discs
		// and clears the center
		centerGrabThisRound = false;
		center.clearDisc(); 
		
		// Fill all of the Discs
		Tile[] discTiles = new Tile[4];
		Disc currentDisc = null;
		for (int i = 1; i <= factoryTiles; ++i) {
			currentDisc = gameDiscs[i];
			// Fill the tile array with random tile from draw bag
			for (int j = 0; j < 4; ++j) {
				discTiles[j] = gameBags.removeFromDraw();
			}
			
			// Add tiles to Disc
			currentDisc.fillDisc(discTiles);
		}
	}
	
	/**
	 * Determines if there are any valid moves left in the round (center or factory
	 * disks still have tiles remaining.
	 * @return true if there are remaining moves this round
	 */
	public boolean validRoundMovesRemaining() {
		for (int i = 0; i < factoryTiles + 1; ++i) {
			if (gameDiscs[i].inUse) {
				return true;
			}
		}
		
		if (center.inUse) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Prints a visualization of the tiles available on the factory discs and center 
	 */
	public void printCenter() {
		System.out.println("Factory Tiles");
		Disc current = null;
		for (int i = 0; i < factoryTiles + 1; ++i) {
			// Disc Identifier
			if (i == 0) {
				// Asterik denotes you'll get -1 for selecting from center
				if (!centerGrabThisRound) {
					System.out.print("Center (0)* : -1 ");
				} else {
					System.out.print("Center (0) : ");
				}
			} else {
				System.out.print(i + ": ");
			}
			
			// Disc Tiles
			current = gameDiscs[i];
			for (int j = 0; j < current.tiles.size(); ++j) {
				System.out.print(" " + current.tiles.get(j).color + " ");
			}
			System.out.println("");
		}
	}
	
	// GETTERS AND SETTERS
	public boolean wasCenterGrabbedThisRound() {
		return centerGrabThisRound;
	}
	
	public void setCenterGrabbed(int curPlayer) {
		playerArray.get(curPlayer).setfirstPlayer();
		this.centerGrabThisRound = true;
	}
	
	public Disc getDisc(int grabFrom) {
		if (grabFrom < 0 || grabFrom > factoryTiles) {
			throw new IllegalArgumentException("Attempted to grab from invalid Factory Tile");
		}
		if (!gameDiscs[grabFrom].inUse) {
			throw new IllegalArgumentException("That factory has no tiles.");
		}
		return gameDiscs[grabFrom];
	}

	public Disc getCenter() {
		return center;
	}
}
