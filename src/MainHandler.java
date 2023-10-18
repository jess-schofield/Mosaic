import java.util.ArrayList;
import java.util.Scanner;

/*
 * Class that creates a game and cycles through rounds/turns until there is a winner.
 */
public class MainHandler {

	public static void main(String[] args) {
		Scanner scnr = new Scanner(System.in);
		
		// Continually ask end user for number of Players until they enter an int between 2 and 4
		int numPlayers = 0;	
		while (numPlayers < 2 || numPlayers > 4) {
			System.out.println("How many players (2-4) will join this game?");
			 while (true)
		            try {
		            	numPlayers = Integer.parseInt(scnr.nextLine());
		                break;
		            } catch (NumberFormatException nfe) {
		                System.out.print("Try again: ");
		            }
		}
		
		// Game setup with number of players entered
		System.out.println("Creating a game with " + numPlayers + " players.");
		Center gameCenter = new Center(numPlayers);
		int winner = -1;
		
		// Declaring all the variables I will use in the loops below 
		int curPlayer = 0;
		int grabFrom = 0;
		int grabColor = 0;
		int grabRow = -1;
		Disc thisDisc = null;
		int nextRoundFirstPlayer = -1;
		ArrayList<Tile> grab = new ArrayList<Tile>();
		
		// If there is a winner, the winner variable will be updated to the player number
		while (winner == -1) {
			gameCenter.newRound();
			
			while (gameCenter.validRoundMovesRemaining()) {
				// Who's turn and print their board, print the shared center of options for their turn
				System.out.println("\nPlayer " + (curPlayer + 1) + "'s Turn\n");
				gameCenter.playerArray.get(curPlayer).printGameBoard();
				gameCenter.printCenter();
				
				// Take turns until there are no more moves left per round
				System.out.println("Where would you like to grab tiles from?");
				 while (true)
			            try {
			            	grabFrom = Integer.parseInt(scnr.nextLine());
			                break;
			            } catch (NumberFormatException nfe) {
			                System.out.print("Try again: ");
			            }
											
				try {
					// Big ol' try block so that if disk entered is invalid it skips the rest 
					// of the code and starts the turn again for the same player
					thisDisc = gameCenter.getDisc(grabFrom);
					
					// If the color entered is invalid it will also throw an exception and restart the turn
					System.out.println("What color would you like to grab?");
					while (true)
			            try {
			            	grabColor = Integer.parseInt(scnr.nextLine());
			                break;
			            } catch (NumberFormatException nfe) {
			                System.out.print("Try again: ");
			            }
					grab = thisDisc.grabColor(grabColor, gameCenter.getCenter());
					
					// If they entered a bad row they shouldn't have to restart whole turn because we've
					// already adjusted the center and i don't want to have to put it back to how it was
					grabRow = -1;
					while (grabRow < 1 || grabRow > 5) {
						System.out.println("You grabbed " + grab.size() + " tiles. Which row would you like to add them to?");
						while (true)
				            try {
				            	grabRow = Integer.parseInt(scnr.nextLine());
				                break;
				            } catch (NumberFormatException nfe) {
				                System.out.print("Try again: ");
				            }
						try {
							gameCenter.playerArray.get(curPlayer).addGrab(grab, grabRow);
						} catch (IllegalArgumentException e) {
							System.out.println(e.getMessage());
						}
					}
					
					// Determine if this was the first player to pick from the center so we can set them up as the
					// first player next round. Also set first so they get a -1 this round.
					if (nextRoundFirstPlayer == -1 && grabFrom == 0) {
						nextRoundFirstPlayer = curPlayer;
						gameCenter.setCenterGrabbed(curPlayer);
					}
					
					// Set next player in turn order
					curPlayer = curPlayer + 1;
					if (curPlayer >= numPlayers) {
						curPlayer = 0;
					}		
				} catch (IllegalArgumentException e) {
					System.out.println(e.getMessage());
					// Since we haven't changed curPlayer yet it will re-do this player's turn
				}
			} // end round while loop
			
			//Tally round points and set up next round
			System.out.println("Round Complete!");
			gameCenter.tallyRoundPoints();
			
			// Determine if there is a winner
			winner = gameCenter.determineWinner();
			
			curPlayer = nextRoundFirstPlayer;
		} // end full game while loop
		
		System.out.println("Player " + winner + " won!!!");
		scnr.close();
	}

}
