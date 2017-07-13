package ca.mcgill.ecse223.tileo.controller;

import ca.mcgill.ecse223.tileo.application.TileOApplication;
import ca.mcgill.ecse223.tileo.model.*;
import ca.mcgill.ecse223.tileo.model.Game.Mode;
import ca.mcgill.ecse223.tileo.persistence.PersistenceObjectStream;
import ca.mcgill.ecse223.tileo.util.Cloner;
import ca.mcgill.ecse223.tileo.view.BoardPanel;
import ca.mcgill.ecse223.tileo.view.GamePage;
import ca.mcgill.ecse223.tileo.view.TileOPage;

import java.security.InvalidParameterException;
import java.util.List;

import javax.swing.JOptionPane;

public class DesignModeController {
	/*
	 * Creates a game, adds a number of players and sets it in the controller
	 *
	 * @param numberOfPlayers An integer value corresponding to the number of
	 * players in the game
	 *
	 * @throws InvalidInputException thrown if the number of players are less
	 * than the minimum or greater than the maximum.
	 */
	public Game createGame(int numberOfPlayers) throws InvalidInputException {
		TileO app = TileOApplication.getTileO();
		int numberOfGames = app.getGames().size();
		Game game = null;
		game = new Game(Game.SpareConnectionPieces, app);

		System.out.println(game.getPlayers());

		if (numberOfPlayers < Game.minimumNumberOfPlayers() || numberOfPlayers > Game.maximumNumberOfPlayers()) {
			throw new InvalidInputException("Invalid Number of Players");
		}

		int j = (4 * numberOfGames) + 1;
		Player[] players = new Player[numberOfPlayers + 1];
		for (int i = 1; i <= numberOfPlayers; i++) {
			players[i] = game.addPlayer(j);
			System.out.println("set" + i);
			switch (i) {
			case 1:
				players[i].setColor(Player.Color.RED);
				break;
			case 2:
				players[i].setColor(Player.Color.BLUE);
				break;
			case 4:
				players[i].setColor(Player.Color.GREEN);
				break;
			case 3:
				players[i].setColor(Player.Color.YELLOW);
				break;
			}
			j++;
		}
		app.setCurrentGame(game);

		return game;

	}
	/*
	 * Adds a Regular tile into the game board at the specified location
	 *
	 * @param X an integer for the correspending x coordinate for the tile
	 * 
	 * @param Y an integer for the corresponding y coordinate for the tile
	 * 
	 * @throws InvalidInputException if there is a tile at the location or if x
	 * and y are out of range
	 */

	public NormalTile addNormalTile(int X, int Y) throws InvalidInputException {
		Game game = TileOApplication.getCurrentGame();
		List<Tile> tiles = game.getTiles();
		if (getTileFromBoard(X, Y, tiles) != null) {
			throw new InvalidInputException("Tile already exists at that location");
		}
		return new NormalTile(X, Y, game);
	}

	/*
	 * Removes a tile from the game board at the specified location
	 *
	 * @param X an integer for the correspending x coordinate for the tile
	 * 
	 * @param Y an integer for the corresponding y coordinate for the tile
	 * 
	 * @throws InvalidInputException if there is no tile at the location
	 */
	public void removeTile(int X, int Y) throws InvalidInputException {
		Game game = TileOApplication.getCurrentGame();
		List<Tile> tiles = game.getTiles();
		Tile tile = getTileFromBoard(X, Y, tiles);
		if (tile == null) {
			throw new InvalidInputException("No tile at that location");
		} else {
			tile.delete();
		}
	}

	private Tile getTileFromBoard(int x, int y, List<Tile> board) {
		for (Tile t : board) {
			if (t.getX() == x && t.getY() == y) {
				return t;
			}
		}
		return null;
	}

	public ActionTile addActionTile(int x, int y, int numTurns) throws InvalidInputException {
		TileO tileO = TileOApplication.getTileO();
		Game game = TileOApplication.getCurrentGame();
		List<Tile> list = game.getTiles();
		if (getTileFromBoard(x, y, list) != null) {
			throw new InvalidInputException("Tile already exists at that location");
		}
		ActionTile t = new ActionTile(x, y, game, numTurns);
		game.setMode(Mode.DESIGN);
		return t;
	}

	public Tile assignStartingTile(int x, int y, int playerNumber) throws InvalidInputException {
		Game game = TileOApplication.getCurrentGame();
		Player player = null;
		try {
			player = game.getPlayer(playerNumber - 1);
		} catch (Exception e) {
			System.out.println("assignstartingtile -- player not found");
			player = new Player(playerNumber, game);
		}
		for (Tile tile : game.getTiles()) {
			if (tile.getX() == x && tile.getY() == y) {
				player.setStartingTile(tile);
				return tile;
			}
		}
		throw new InvalidInputException("The tile could not be found");
	}

	public void buildDeck(int rollDie, int addConnect, int removeConnect, int teleport, int loseTurn, int chooseTurn, int resetTile, int showAction, int revealTile, int sendPlayerToStart, int swapPosition, int allPlayersLoseTurn) throws InvalidInputException {
		Game game = TileOApplication.getCurrentGame();
		Deck deck = game.getDeck();
		deck.clearDeck();
		if (rollDie + addConnect + removeConnect + teleport + loseTurn + chooseTurn + resetTile + showAction + revealTile + sendPlayerToStart + swapPosition + allPlayersLoseTurn!= 32) {
			throw new InvalidInputException("Please make sure the total number of cards adds up to 32");
		}
		for (int i = 0; i < rollDie; i++) {
			new RollDieActionCard("You have the opportunity to roll again", deck);
		}
		for (int i = 0; i < addConnect; i++) {
			new ConnectTilesActionCard("Please connect two tiles", deck);
		}
		for (int i = 0; i < removeConnect; i++) {
			new RemoveConnectionActionCard("Please choose a connection to remove", deck);
		}
		for (int i = 0; i < teleport; i++) {
			new TeleportActionCard("Please choose a tile to teleport to", deck);
		}
		for (int i = 0; i < loseTurn; i++) {
			new LoseTurnActionCard("You have lost your turn", deck);
		}
		for (int i = 0; i < chooseTurn; i++) {
			new ChooseMoveActionCard("Please choose your die roll", deck);
		}
		for (int i = 0; i < resetTile; i++) {
			new ResetActionTilesActionCard("All active action tiles will be reset", deck);
		}
		//new
		for (int i = 0; i < showAction; i++) {
			new ShowTilesActionCard("All Action Tiles on the board are shown for 5 seconds", deck);
		}
		//Thomas : done
		for (int i = 0; i < revealTile; i++) {
			new RevealTileActionCard("Click a tile to reveal its type (action, normal, or win) for your turn. <<Hover to see type>>", deck);
		}
		for (int i = 0; i < sendPlayerToStart; i++) {
			new SendBackToStartActionCard("You can send a player back to it's start position", deck);
		}
		//TODO: Change to the card class when it exist
		for (int i = 0; i < swapPosition; i++) {
			new ResetActionTilesActionCard("You can swap positions with another player", deck);
		}
		//TODO: Change to the card class when it exist
		for (int i = 0; i < allPlayersLoseTurn; i++) {
			new LoseRandomTurnsActionCard("All players lose different numbers of turns", deck);
		}
	}

	public Connection connectTiles(Tile tileOne, Tile tileTwo) throws Exception {
		Game game = TileOApplication.getCurrentGame();
		Connection c = null;
		if (game.checkAdjacentTiles(tileOne, tileTwo) == true) {
			for(Connection connect : tileOne.getConnections()){
				if(connect.getTiles().contains(tileTwo)){
					System.out.println("Tiles have a connection");
					throw new InvalidInputException("The tiles already have a connection");
				}
			}
			c = game.connectTiles(tileOne, tileTwo);
			return c;
		} else {
			throw new InvalidInputException("The two tiles are not adjacent.");	
		}
	}

	public boolean checkAdjacentTiles(Tile tile1, Tile tile2) {
		Game game = TileOApplication.getCurrentGame();
		return game.checkAdjacentTiles(tile1, tile2);

	}

	public Connection deleteConnection(Connection connector) {
		Game game = TileOApplication.getCurrentGame();
		Connection c = game.deleteConnection(connector);
		return c;
	}

	public Tile chooseHiddenTile(int x, int y) {
		Game game = TileOApplication.getCurrentGame();
		WinTile hiddenTile = new WinTile(x, y, game);

		game.setWinTile(hiddenTile);
		return hiddenTile;
	}

	public static void save() {
		TileOApplication.save();
	}

	public static TileO load(String filename) {

		PersistenceObjectStream.setFilename(filename);
		TileO tileO = (TileO) PersistenceObjectStream.deserialize();
		if (tileO == null) {
			tileO = new TileO();
		}

		return tileO;
	}

	public void setTileOApplicationCurrentGame(Game g) {
		TileOApplication.setCurrentGame(g);
	}

	public Tile getTile(int x, int y) throws InvalidInputException {
		Game game = TileOApplication.getCurrentGame();
		for (Tile tile : game.getTiles()) {
			if (tile.getX() == x && tile.getY() == y) {
				return tile;
			}
		}
		throw new InvalidInputException("No tile exists");
	}

	public void goToGameMode() {
		Game selectedGame = TileOApplication.getCurrentGame();
		boolean validGame = true;
		
		// Get the deck in the game
		Deck deck = selectedGame.getDeck();
		// Get all tiles in current game
		List<Tile> tiles = selectedGame.getTiles();
		// Get all players in current game
		List <Player> players = selectedGame.getPlayers();

		/* VALIDATION */
		// Check there are the right number of cards in the deck
		if (deck.numberOfCards() != selectedGame.NumberOfActionCards) {
			showMessage("The deck has the wrong number of Action Cards");
			validGame = false;
		}
		// Check the game has a specified Win Tile
		if (!selectedGame.hasWinTile()) {
			showMessage("The game does not have a Win Tile");
			validGame = false;
		}
		if(!selectedGame.hasPlayers()){
			showMessage("The game does not have any added players");
			validGame = false;
		}
		for (Player player : players) {
			// Check the starting tile has been set for each player
			if (!player.hasStartingTile()) {
				showMessage("The starting position is not set for a player");
				validGame = false;
				break;
			}
		}
		
		if(validGame){
		   	//Clone game before it is set into game mode
			cloneGame();
			PlayModeController pmc = new PlayModeController();
			boolean flag = pmc.startGame();
			if(flag){
				BoardPanel bp = new BoardPanel(Game.Mode.GAME);
				TileOApplication.setBoard(bp);
				GamePage gamePage = new GamePage(bp, pmc);
				TileOApplication.setGamePage(gamePage);
				TileOApplication.setPlayModeController(pmc);
				gamePage.setVisible(true);
				TileOApplication.getDesignPage().dispose();
				pmc.save();
			}
		}
	}
	
	private void cloneGame() {
		// TODO the following may have to be adapted to your specific implementation
		TileO tileO = TileOApplication.getTileO();
		Game cloned = (Game) Cloner.clone(tileO.getCurrentGame());
		tileO.addGame(cloned);
		tileO.setCurrentGame(cloned);
	}
	
	public void showMessage(String s){
		JOptionPane.showMessageDialog(null, s);
	}

}
