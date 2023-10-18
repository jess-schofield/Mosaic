import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AzulTests {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test1_DiscFill() {
		Tile tile1 = new Tile(1);
		Tile tile2 = new Tile(1);
		Tile tile3 = new Tile(1);
		Tile tile4 = new Tile(1);
		Tile[] tileArray = new Tile[] {tile1, tile2, tile3, tile4};
		
		Disc disc1 = new Disc(false);
		Disc center = new Disc(true);
		disc1.fillDisc(tileArray);
		assertEquals(4,disc1.grabColor(1, center).size());
	}
	
	@Test
	void test2_DiscGrab() {
		Tile tile1 = new Tile(1);
		Tile tile2 = new Tile(1);
		Tile tile3 = new Tile(3);
		Tile tile4 = new Tile(4);
		Tile[] tileArray = new Tile[] {tile1, tile2, tile3, tile4};
		
		Disc disc1 = new Disc(false);
		Disc center = new Disc(true);
		disc1.fillDisc(tileArray);
		assertEquals(2,disc1.grabColor(1, center).size());
		assertEquals(false,disc1.centerDisc);
		assertEquals(1,center.getNumberOfColor(3));
		assertEquals(1,center.getNumberOfColor(4));
	}
	
	@Test
	void test3_DiscGrabCenter() {
		Tile tile1 = new Tile(1);
		Tile tile2 = new Tile(1);
		Tile tile3 = new Tile(3);
		Tile tile4 = new Tile(4);
		Tile[] tileArray = new Tile[] {tile1, tile2, tile3, tile4};
		
		Disc disc1 = new Disc(false);
		Disc center = new Disc(true);
		disc1.fillDisc(tileArray);
		disc1.grabColor(1, center);
		assertEquals(1,center.grabColor(3, center).size());
		assertEquals(0,center.getNumberOfColor(3));
		assertEquals(1,center.getNumberOfColor(4));
		assertEquals(1,center.grabColor(4, center).size());
		assertEquals(0,center.getNumberOfColor(3));
		assertEquals(0,center.getNumberOfColor(4));
	}

	@Test
	void test4_BagsInitialize() {
		Bags gameBags = new Bags();
		assertEquals(100,gameBags.drawBag.size());
		assertEquals(0,gameBags.discardBag.size());
	}
	
	@Test
	void test5_BagsRemoveFromDraw() {
		Bags gameBags = new Bags();
		@SuppressWarnings("unused")
		Tile tile1 = gameBags.removeFromDraw();
		assertEquals(99,gameBags.drawBag.size());
		assertEquals(0,gameBags.discardBag.size());
		tile1 = gameBags.removeFromDraw();
		tile1 = gameBags.removeFromDraw();
		tile1 = gameBags.removeFromDraw();
		tile1 = gameBags.removeFromDraw();
		tile1 = gameBags.removeFromDraw();
		assertEquals(94,gameBags.drawBag.size());
		assertEquals(0,gameBags.discardBag.size());
	}
	
	@Test
	void test6_BagsRemoveAndDiscard() {
		Bags gameBags = new Bags();
		Tile tile1 = gameBags.removeFromDraw();
		gameBags.addToDiscard(tile1);
		assertEquals(99,gameBags.drawBag.size());
		assertEquals(1,gameBags.discardBag.size());
		tile1 = gameBags.removeFromDraw();
		gameBags.addToDiscard(tile1);
		tile1 = gameBags.removeFromDraw();
		gameBags.addToDiscard(tile1);
		tile1 = gameBags.removeFromDraw();
		gameBags.addToDiscard(tile1);
		tile1 = gameBags.removeFromDraw();
		gameBags.addToDiscard(tile1);
		tile1 = gameBags.removeFromDraw();
		gameBags.addToDiscard(tile1);
		assertEquals(94,gameBags.drawBag.size());
		assertEquals(6,gameBags.discardBag.size());
	}
	
	@Test
	void test7_BagsRemove100Refresh6() {
		Bags gameBags = new Bags();
		Tile tile1 = gameBags.removeFromDraw();
		gameBags.addToDiscard(tile1);
		assertEquals(99,gameBags.drawBag.size());
		assertEquals(1,gameBags.discardBag.size());
		tile1 = gameBags.removeFromDraw();
		gameBags.addToDiscard(tile1);
		tile1 = gameBags.removeFromDraw();
		gameBags.addToDiscard(tile1);
		tile1 = gameBags.removeFromDraw();
		gameBags.addToDiscard(tile1);
		tile1 = gameBags.removeFromDraw();
		gameBags.addToDiscard(tile1);
		tile1 = gameBags.removeFromDraw();
		gameBags.addToDiscard(tile1);
		for (int i = 0; i < 95; ++i) {
			tile1 = gameBags.removeFromDraw();
		}
		assertEquals(5,gameBags.drawBag.size());
		
	}
	
	@Test
	void test8_CenterConstructor() {
		Center two = new Center(2);
		Center three = new Center(3);
		Center four = new Center(4);
		assertEquals(5,two.factoryTiles);
		assertEquals(7,three.factoryTiles);
		assertEquals(9,four.factoryTiles);
		
	}
	
	@Test
	void test9_CenterNewRound() {
		Center three = new Center(3);
		three.newRound();
		assertEquals(0,three.center.tiles.size());
		// 8 Because center is included in array now
		assertEquals(8,three.gameDiscs.length);
		assertEquals(4, (three.gameDiscs[7].tiles.size()));
		assertEquals(4, (three.gameDiscs[6].tiles.size()));
		assertEquals(4, (three.gameDiscs[5].tiles.size()));
		assertEquals(4, (three.gameDiscs[4].tiles.size()));
		assertEquals(4, (three.gameDiscs[3].tiles.size()));
		assertEquals(4, (three.gameDiscs[2].tiles.size()));
		assertEquals(4, (three.gameDiscs[1].tiles.size()));
		assertEquals(0, (three.gameDiscs[0].tiles.size()));
		assertEquals(0, (three.center.tiles.size()));
		assertEquals(72,three.gameBags.drawBag.size());
		assertEquals(0,three.gameBags.discardBag.size());
	}
	
	@Test
	void test10_PlayerBoardAddGrabSimple() {
		ArrayList<Tile> threeTiles = new ArrayList<Tile>();
		Tile tile1 = new Tile(1);
		Tile tile2 = new Tile(1);
		Tile tile3 = new Tile(1);
		threeTiles.add(tile1);
		threeTiles.add(tile2);
		threeTiles.add(tile3);
		PlayerBoard board1 = new PlayerBoard(1);
		board1.addGrab(threeTiles, 3);
		
		assertEquals(0,board1.negativePoints.size());
		assertEquals(tile1,board1.row3[0]);
		assertEquals(tile2,board1.row3[1]);
		assertEquals(tile3,board1.row3[2]);
	}
	
	@Test
	void test11_PlayerBoardAddGrabOneTurnOverflow() {
		ArrayList<Tile> threeTiles = new ArrayList<Tile>();
		Tile tile1 = new Tile(1);
		Tile tile2 = new Tile(1);
		Tile tile3 = new Tile(1);
		threeTiles.add(tile1);
		threeTiles.add(tile2);
		threeTiles.add(tile3);
		PlayerBoard board1 = new PlayerBoard(1);
		board1.addGrab(threeTiles, 2);
		
		assertEquals(1,board1.negativePoints.size());
		assertEquals(tile1,board1.row2[0]);
		assertEquals(tile2,board1.row2[1]);
		assertEquals(tile3,board1.negativePoints.get(0));
	}
	
	@Test
	void test12_PlayerBoardAddGrabTwoTurnOverflow() {
		ArrayList<Tile> threeTiles = new ArrayList<Tile>();
		ArrayList<Tile> twoTiles = new ArrayList<Tile>();
		Tile tile1 = new Tile(1);
		Tile tile2 = new Tile(1);
		Tile tile3 = new Tile(1);
		Tile tile4 = new Tile(1);
		Tile tile5 = new Tile(1);
		threeTiles.add(tile1);
		threeTiles.add(tile2);
		threeTiles.add(tile3);
		twoTiles.add(tile4);
		twoTiles.add(tile5);
		PlayerBoard board1 = new PlayerBoard(1);
		board1.addGrab(threeTiles, 4);
		board1.addGrab(twoTiles, 4);
		
		assertEquals(1,board1.negativePoints.size());
		assertEquals(tile1,board1.row4[0]);
		assertEquals(tile2,board1.row4[1]);
		assertEquals(tile3,board1.row4[2]);
		assertEquals(tile4,board1.row4[3]);
		assertEquals(tile5,board1.negativePoints.get(0));
	}
	
	@Test
	void test13_PlayerBoardAddGrabInconsistentRowColor() {
		ArrayList<Tile> threeTiles = new ArrayList<Tile>();
		Tile tile1 = new Tile(1);
		Tile tile2 = new Tile(1);
		Tile tile3 = new Tile(1);
		threeTiles.add(tile1);
		threeTiles.add(tile2);
		threeTiles.add(tile3);
		
		ArrayList<Tile> twoTiles = new ArrayList<Tile>();
		Tile tile4 = new Tile(2);
		Tile tile5 = new Tile(2);
		twoTiles.add(tile4);
		twoTiles.add(tile5);
		assertEquals(2,twoTiles.size());
		
		PlayerBoard board1 = new PlayerBoard(1);
		board1.addGrab(threeTiles, 4);
		board1.addGrab(twoTiles, 4);
		
		assertEquals(2,board1.negativePoints.size());
		assertEquals(tile1,board1.row4[0]);
		assertEquals(tile2,board1.row4[1]);
		assertEquals(tile3,board1.row4[2]);
		assertEquals(null,board1.row4[3]);
		assertEquals(tile4,board1.negativePoints.get(0));
		assertEquals(tile5,board1.negativePoints.get(1));
	}
		
	@Test
	void test14_PlayerBoardAddGrabInconsistentRowColor() {
		ArrayList<Tile> threeTiles = new ArrayList<Tile>();
		Tile tile1 = new Tile(2);
		Tile tile2 = new Tile(2);
		Tile tile3 = new Tile(2);
		threeTiles.add(tile1);
		threeTiles.add(tile2);
		threeTiles.add(tile3);
		
		ArrayList<Tile> twoTiles = new ArrayList<Tile>();
		Tile tile4 = new Tile(2);
		Tile tile5 = new Tile(2);
		twoTiles.add(tile4);
		twoTiles.add(tile5);
		assertEquals(2,twoTiles.size());
		
		ArrayList<Tile> oneTile = new ArrayList<Tile>();
		Tile tile6 = new Tile(1);
		oneTile.add(tile6);
		
		PlayerBoard board1 = new PlayerBoard(1);
		board1.addGrab(threeTiles, 4);
		board1.addGrab(oneTile, 4);
		board1.addGrab(twoTiles, 4);
		
		assertEquals(2,board1.negativePoints.size());
		assertEquals(tile1,board1.row4[0]);
		assertEquals(tile2,board1.row4[1]);
		assertEquals(tile3,board1.row4[2]);
		assertEquals(tile4,board1.row4[3]);
		assertEquals(tile6,board1.negativePoints.get(0));
		assertEquals(tile5,board1.negativePoints.get(1));
	}
	
	@Test
	void test15_PlayerBoardPlaceConfirmedTile() {
		ArrayList<Tile> threeTiles = new ArrayList<Tile>();
		Tile tile1 = new Tile(2);
		Tile tile2 = new Tile(2);
		Tile tile3 = new Tile(2);
		threeTiles.add(tile1);
		threeTiles.add(tile2);
		threeTiles.add(tile3);
		
		ArrayList<Tile> twoTiles = new ArrayList<Tile>();
		Tile tile4 = new Tile(2);
		Tile tile5 = new Tile(2);
		twoTiles.add(tile4);
		twoTiles.add(tile5);
		assertEquals(2,twoTiles.size());
		
		ArrayList<Tile> oneTile = new ArrayList<Tile>();
		Tile tile6 = new Tile(1);
		oneTile.add(tile6);
		
		PlayerBoard board1 = new PlayerBoard(1);
		board1.addGrab(threeTiles, 4);
		board1.addGrab(oneTile, 4);
		board1.addGrab(twoTiles, 4);
		// Row 4 full, one negative tile
		
		// Create board so you can score end of round
		Bags gameBags = new Bags();
		int score = board1.endOfRound(gameBags);
		board1.printGameBoard();
		
		assertEquals(-1,score);
		assertEquals(null,board1.row4[0]);
		assertEquals(null,board1.row4[1]);
		assertEquals(null,board1.row4[2]);
		assertEquals(null,board1.row4[3]);
		assertEquals(0,board1.negativePoints.size());
		assertEquals(-1,board1.getOverallScore());
	}
	
	@Test
	void test16_PlayerBoardCantAddToRowWhereColorAlreadyExists() {
		ArrayList<Tile> fourTiles = new ArrayList<Tile>();
		Tile tile1 = new Tile(2);
		Tile tile2 = new Tile(2);
		Tile tile3 = new Tile(2);
		Tile tile4 = new Tile(2);
		fourTiles.add(tile1);
		fourTiles.add(tile2);
		fourTiles.add(tile3);
		fourTiles.add(tile4);
		
		ArrayList<Tile> oneTile = new ArrayList<Tile>();
		Tile tile5 = new Tile(2);
		oneTile.add(tile5);
		
		PlayerBoard board1 = new PlayerBoard(1); 
		board1.addGrab(fourTiles, 4);
		board1.addGrab(oneTile, 4);
		
		// Create bags so you can score end of round
		Bags gameBags = new Bags();
		int score = board1.endOfRound(gameBags);
		board1.printGameBoard();
		
		assertEquals(0,score);
		assertEquals(null,board1.row4[0]);
		assertEquals(null,board1.row4[1]);
		assertEquals(null,board1.row4[2]);
		assertEquals(null,board1.row4[3]);
		assertEquals(0,board1.negativePoints.size());
		assertEquals(0,board1.getOverallScore());
	}
}
