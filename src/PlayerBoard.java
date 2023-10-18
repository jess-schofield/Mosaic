import java.util.ArrayList;

/**
 * Each player will have a player board. This serves as the space to store the game accumulated tiles,
 * game score, and tiles in flux before they get scored.
 *
 */
public class PlayerBoard {
	private int overallPoints;
	private boolean firstPlayerNextRound;
	// Identifier is equal to position in player array, what users see is identifier +1
	int identifier;
	// When rows are complete, they go onto confirmed game board, row always listed first
	protected Tile[][] confirmed;
	// Temp arrays to fill during the round, cary over between rounds if incomplete
	protected Tile[] row1;
	protected Tile[] row2;
	protected Tile[] row3;
	protected Tile[] row4;
	protected Tile[] row5;
	// Overflow tiles will get added to this array that gets tallied and cleared each round
	protected ArrayList<Tile> negativePoints;
	
	
	//CONSTRUCTOR
	public PlayerBoard(int identifier) {
		this.identifier = identifier;
		overallPoints = 0;
		confirmed = new Tile[5][5];
		row1 = new Tile[1];
		row2 = new Tile[2];
		row3 = new Tile[3];
		row4 = new Tile[4];
		row5 = new Tile[5];
		negativePoints = new ArrayList<Tile>();
		firstPlayerNextRound = false;
	}
	
	// PUBLIC METHODS
	
	/**
	 * Performs all end of round tasks:
	 * 1. If a temp row is full, adds the tile of that color to confirmed and scores it, clears temp array
	 * 2. Subtracts a point if they were first to grab from center this round
	 * 3. Subtracts appropriate number of points if there are tiles in the neg array, clears
	 * 4. Adjusts overall game total points
	 * @param gameBags - gamebags from center
	 * @return number of points scored this round
	 */
	public int endOfRound(Bags gameBags) {
		int roundTotalPoints = 0;
		// If row is full place tile and add points scored in that row
		if (row1[0] != null) {
			roundTotalPoints = roundTotalPoints + determinePointsScored(0, placeConfirmedTile(row1, gameBags));
		}
		if (row2[1] != null) {
			roundTotalPoints = roundTotalPoints + determinePointsScored(1, placeConfirmedTile(row2, gameBags));
		}
		if (row3[2] != null) {
			roundTotalPoints = roundTotalPoints + determinePointsScored(2, placeConfirmedTile(row3, gameBags));
		}
		if (row4[3] != null) {
			roundTotalPoints = roundTotalPoints + determinePointsScored(3, placeConfirmedTile(row4, gameBags));
		}
		if (row5[4] != null) {
			roundTotalPoints = roundTotalPoints + determinePointsScored(4, placeConfirmedTile(row5, gameBags));
		}
		
		// If they were first to grab from center, add one extra tile
		int negSize = negativePoints.size();
		if (firstPlayerNextRound) {
			negSize = negSize + 1;
			firstPlayerNextRound = false;
		}
		
		// Resolve negative points array
		if (negSize == 1) {
			roundTotalPoints = roundTotalPoints - 1;
		} else if (negSize == 2) {
			roundTotalPoints = roundTotalPoints - 2;
		} else if (negSize == 3) {
			roundTotalPoints = roundTotalPoints - 4;
		} else if (negSize == 4) {
			roundTotalPoints = roundTotalPoints - 6;
		} else if (negSize == 5) {
			roundTotalPoints = roundTotalPoints - 8;
		} else if (negSize == 6) {
			roundTotalPoints = roundTotalPoints - 11;
		} else if (negSize >= 7) {
			roundTotalPoints = roundTotalPoints - 14;
		} 

		// I think I need to add one at a time because that's all my method can handle
		while (!negativePoints.isEmpty()) {
				gameBags.addToDiscard(negativePoints.remove(0));
		}
		
		// Adjust Total Score
		overallPoints = overallPoints + roundTotalPoints;
		
		return roundTotalPoints;
	}
	
	/**
	 * Checks the player's confirmed board for the win condition (at least one fully complete row)
	 * @return true if player's confirmed game board has at least one row full
	 */
	public boolean didPlayerCompleteGame() {
		// Loop through each row to see if it is full
		for (int i = 0; i < 5; ++i) {
			for (int j = 0; j < 5; ++j) {
				if (confirmed[i][j] == null) {
					// not a complete row
					break;
				} else if (j == 4) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Prints a visualization of the player's game board including tem arrays, confirmed arrays, and 
	 * in flux negative points.
	 */
	public void printGameBoard() {
		// Row 1
		System.out.print("   " + "   " + "   " + "   ");
		if (row1[0] == null) {
			System.out.print(" X ");
		} else {
			System.out.print(" " + row1[0].color + " ");
		}
		System.out.print(" : 1 : ");
		for (int i = 0; i < 5; ++i) {
			if (confirmed[0][i] == null) {
				System.out.print(" X ");
			} else {
				System.out.print(" " + confirmed[0][i].color + " ");
			}
		}
		System.out.println("");
		
		// Row 2
		System.out.print("   " + "   " + "   ");
		for (int i = 0; i < 2; ++i) {
			if (row2[i] == null) {
				System.out.print(" X ");
			} else {
				System.out.print(" " + row2[i].color + " ");
			}
		}
		System.out.print(" : 2 : ");
		for (int i = 0; i < 5; ++i) {
			if (confirmed[1][i] == null) {
				System.out.print(" X ");
			} else {
				System.out.print(" " + confirmed[1][i].color + " ");
			}
		}
		System.out.println("");
		
		// Row 3
		System.out.print("   " + "   ");
		for (int i = 0; i < 3; ++i) {
			if (row3[i] == null) {
				System.out.print(" X ");
			} else {
				System.out.print(" " + row3[i].color + " ");
			}
		}
		System.out.print(" : 3 : ");
		for (int i = 0; i < 5; ++i) {
			if (confirmed[2][i] == null) {
				System.out.print(" X ");
			} else {
				System.out.print(" " + confirmed[2][i].color + " ");
			}
		}
		System.out.println("");
		
		// Row 4
		System.out.print("   ");
		for (int i = 0; i < 4; ++i) {
			if (row4[i] == null) {
				System.out.print(" X ");
			} else {
				System.out.print(" " + row4[i].color + " ");
			}
		}
		System.out.print(" : 4 : ");
		for (int i = 0; i < 5; ++i) {
			if (confirmed[3][i] == null) {
				System.out.print(" X ");
			} else {
				System.out.print(" " + confirmed[3][i].color + " ");
			}
		}
		System.out.println("");
		
		// Row 5
		for (int i = 0; i < 5; ++i) {
			if (row5[i] == null) {
				System.out.print(" X ");
			} else {
				System.out.print(" " + row5[i].color + " ");
			}
		}
		System.out.print(" : 5 : ");
		for (int i = 0; i < 5; ++i) {
			if (confirmed[4][i] == null) {
				System.out.print(" X " );
			} else {
				System.out.print(" " + confirmed[4][i].color + " ");
			}
		}
		System.out.println("");
		
		// Negative Points
		System.out.print("Factory Floor: ");
		for (int i = 0; i < negativePoints.size(); ++i) {
			System.out.print(" " + negativePoints.get(i).color + " ");
		}
		System.out.println("");
		System.out.println("");
	}
	
	/**
	 * Takes in an arrayList representing tiles picked up in a turn and the 
	 * end user readable row number (1-5) the tiles should be placed in. Places
	 * tiles in the temp arrays starting from the 0 position. Places any 
	 * additional tiles in the negative points array.
	 * @param tilesToAdd tiles to add to the temporary gameboard arrays
	 * @param rowNumber row number to add tiles to
	 */
	public void addGrab(ArrayList<Tile> tilesToAdd, int rowNumber) {
		if (tilesToAdd.size() == 0) {
			return;
		}
		
		// Identify the temp array where you should add the tiles
		Tile[] curArray;
		if (rowNumber == 1) {
			curArray = row1;
		} else if (rowNumber == 2) {
			curArray = row2;
		} else if (rowNumber == 3) {
			curArray = row3;
		} else if (rowNumber == 4) {
			curArray = row4;
		} else if (rowNumber == 5) {
			curArray = row5;
		} else {
			throw new IllegalArgumentException("Invalid row number.");
		}
		
		// Verify that that row doesn't already have that color in complete board
		int column = findColorColumn(rowNumber, tilesToAdd.get(0).color);
		if (confirmed[rowNumber - 1][column] != null) {
			throw new IllegalArgumentException("That color has already been completed for the row.");
		}
		
		// Add the tiles to the appropriate array, need to make sure there isn't a different color in there already
		int rowColor = -1;
		for (int i = 0; i < curArray.length; ++i) {
			if (curArray[i] != null) {
				rowColor = curArray[i].color;
			} else if (rowColor == -1 || tilesToAdd.get(0).color == rowColor) {
				curArray[i] = tilesToAdd.remove(0);
				rowColor = curArray[i].color;
				if (tilesToAdd.isEmpty()) {
					break;
				}
			}
		}

		// If there are any remaining tiles in the tile array, add them to the negative array
		for (int i = 0; i < tilesToAdd.size(); ++i) {
			negativePoints.add(tilesToAdd.get(i));
		}
	}
	
	
	// PRIVATE HELPER METHODS
	
	/*
	 * Takes in the end user defined row number (1-5) and the integer representing
	 * a tile's color (1-5) and returns the column within the row that the tile
	 * can be placed in.
	 */
	private int findColorColumn(int row, int color) {
		int column = row + color;
		while (column > 4) {
			column = column - 5;
		}
		return column;
	}
	
	/* 
	 * Takes in the row array for a full array that is ready to be confirmed and
	 * discarded. Returns the column number (0-4) within the row where the tile 
	 * was placed.
	 */
	private int placeConfirmedTile(Tile[] rowArray, Bags gameBags) {
		int column = findColorColumn(rowArray.length, rowArray[0].color);
		int row = rowArray.length - 1;
		
		// Place first tile in the confirmed array
		confirmed[row][column] = rowArray[0];
		rowArray[0] = null;
		
		// Place the rest of the tiles in the discard bag
		for (int i = 1; i < rowArray.length; ++i) {
			gameBags.addToDiscard(rowArray[i]);
			rowArray[i] = null;
		}
		
		return column;
	}
	
	/*
	 * Takes in the array-true (0-4) row and column numbers of a tile that was
	 * placed and determines how many points are scored from the placement based
	 * on the other tiles around it in the confirmed array.
	 */
	private int determinePointsScored(int rowNum, int colNum) {
		int pointsScored = 0;
		
		// Determine Column Points
		int topmostRow = rowNum;
		int current = rowNum - 1;
		while (current >= 0) {
			if (confirmed[current][colNum] != null) {
				topmostRow = current;
				current = current - 1;
			} else {
				current = -1;
			}
		}
		
		int bottommostRow = rowNum;
		current = rowNum + 1;
		while (current <= 4) {
			if (confirmed[current][colNum] != null) {
				bottommostRow = current;
				current = current + 1;
			} else {
				current = 999;
			}
		}
		if (bottommostRow != topmostRow) {
			pointsScored = bottommostRow - topmostRow + 1;
		}
		
		// Determine row points
		int leftmostCol = colNum;
		current = colNum - 1;
		while (current >= 0) {
			if (confirmed[rowNum][current] != null) {
				leftmostCol = current;
				current = current - 1;
			} else {
				current = -1;
			}
		}
		
		int rightmostCol = colNum;
		current = colNum + 1;
		while (current <= 4) {
			if (confirmed[rowNum][current] != null) {
				rightmostCol = current;
				current = current + 1;
			} else {
				current = 999;
			}
		}
		if (rightmostCol != leftmostCol) {
			pointsScored = pointsScored + (rightmostCol - leftmostCol + 1);
		}
		
		// If the score added to 0, return 1 for the one square
		if (pointsScored > 0) {
			return pointsScored;
		} else {
			return 1;
		}	
	}
	
	// GETTERS AND SETTERS
	public int getOverallScore() {
		return overallPoints;
	}
	
	public void setfirstPlayer() {
		firstPlayerNextRound = true;
	}
}
