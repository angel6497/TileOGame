package ca.mcgill.ecse223.tileo.view;

import ca.mcgill.ecse223.tileo.application.TileOApplication;
import ca.mcgill.ecse223.tileo.controller.DesignModeController;
import ca.mcgill.ecse223.tileo.controller.InvalidInputException;
import ca.mcgill.ecse223.tileo.controller.PlayModeController;
import ca.mcgill.ecse223.tileo.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.List;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;

public class BoardPanel extends JPanel {

	// Constants
	public static final int HEIGHT = 30;
	public static final int WIDTH = 30;
	public static final int GAP = 15;
	public static final int HORIZONTAL_RECTANGLES = 14;
	public static final int VERTICAL_RECTANGLES = 14;
	public static final int C_WIDTH = 10;
	public static final int C_LENGTH = 15;

	// Attributes
	public Game.Mode m;
	public ArrayList<Rectangle2DCoord> rectangles = new ArrayList<Rectangle2DCoord>();
	public HashMap<Rectangle2DCoord, Tile> boardTiles = new HashMap<Rectangle2DCoord, Tile>();

	public HashMap<Integer, Ellipse2DCoord> playerTiles = new HashMap<Integer, Ellipse2DCoord>();

	public ArrayList<Player> gamePlayers = new ArrayList<Player>();
	public ArrayList<Connection> gameConnections = new ArrayList<Connection>();
	public ArrayList<Connector2D> connectors = new ArrayList<Connector2D>();
	public HashMap<Connector2D, Connection> boardConnections = new HashMap<Connector2D, Connection>();

	public ArrayList<Rectangle2DCoord> visitedTiles = new ArrayList<Rectangle2DCoord>();
	public ArrayList<Rectangle2DCoord> revealedTiles = new ArrayList<Rectangle2DCoord>();
	public String revealedTileValue = "";

	boolean showingActionTiles = false;

	Mode mode;
	TileType tileType = TileType.NORMAL;
	int inactiveTurns = 1;
	int playerNumber = 1;
	Rectangle2DCoord currentWinRectangle = null;
	Rectangle2DCoord prev = null;

	Rectangle2DCoord curr = null;

	Connection currentConnection = null;

	TileO tileo = new TileO();

	Game game = TileOApplication.getCurrentGame();

	public void initComponents() {

		// Set initial location
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

		// Initialize the rectangles list to cover the whole board
		for (int i = 0; i < HORIZONTAL_RECTANGLES; i++) {
			for (int j = 0; j < VERTICAL_RECTANGLES; j++) {
				Rectangle2D r2d = new Rectangle2D.Float(GAP * (j + 1) + WIDTH * j, GAP * (i + 1) + HEIGHT * i, WIDTH,
						HEIGHT);
				Rectangle2DCoord currentRect = new Rectangle2DCoord(r2d, j, i);
				rectangles.add(currentRect);
			}
		}

		// Initialize the hashmap to match every tile to a rectangle
		for (Tile t : game.getTiles()) {
			Rectangle2DCoord r = getRectangle(t.getX(), t.getY());
			boardTiles.put(r, t);
		}

		for (Rectangle2DCoord rect : boardTiles.keySet()) {
			rect.setColor(Color.WHITE);
		}

		if (game.getMode() == Game.Mode.DESIGN) {
			for (Rectangle2DCoord rect : boardTiles.keySet()) {
				if (boardTiles.get(rect) instanceof ActionTile) {
					rect.setColor(Color.pink);
				}
			}
		}

		for (Rectangle2DCoord rect : boardTiles.keySet()) {
			if (boardTiles.get(rect) instanceof WinTile) {
				currentWinRectangle = rect;
			}
		}

		if (game.hasConnections()) {
			for (Connection c : game.getConnections()) {
				gameConnections.add(c);
			}
		}

		initConnections(gameConnections);

		if (game.hasPlayers()) {
			for (Player p : game.getPlayers()) {
				gamePlayers.add(p);
			}

			initPlayers(gamePlayers);

			for (Rectangle2DCoord r : boardTiles.keySet()) {
				if (boardTiles.get(r).getHasBeenVisited()) {
					visitedTiles.add(r);
				}
			}
			/*
			 * for (Rectangle2DCoord r : boardTiles.keySet()) { if
			 * (boardTiles.get(r).getHasBeenRevealed()) { revealedTiles.add(r);
			 * } else { revealedTiles.remove(r); } }
			 */
		}
	}

	public void resetTileColor() {
		if (game.getMode() != Game.Mode.DESIGN) {
			for (Rectangle2DCoord rect : boardTiles.keySet()) {
				rect.setColor(Color.WHITE);
			}
		}
	}

	public void paintAllPink() {
		if (game.getMode() != Game.Mode.DESIGN) {
			for (Rectangle2DCoord rect : boardTiles.keySet()) {
				rect.setColor(Color.pink);
			}
		}
	}

	// This shows all the action cards in blue for 5 seconds
	public void showActionTiles() {
		for (Rectangle2DCoord r : boardTiles.keySet()) {
			if (boardTiles.get(r) instanceof ActionTile) {
				r.setColor(Color.CYAN);
			}
		}
		showingActionTiles = true;
		repaint();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				resetTileColor();
				showingActionTiles = false;
				repaint();
			}
		});
	}

	// TODO thomas
	public void revealTile(char tileType) {
		// Graphics2D g2d = (Graphics2D) g;
		// PlayModeController pmc = TileOApplication.getPlayModeController();

		for (Rectangle2DCoord r : boardTiles.keySet()) {
			if (boardTiles.get(r).getHasBeenRevealed()) {
				r.setColor(Color.CYAN);
				// g.drawString("", r.getX(), r.getY());
				//showMessage("" + tileType);
				revealedTileValue = ""+tileType;
				// TODO
			}
		}
	}

	public void initConnections(ArrayList<Connection> gameConnections) {
		for (Connection c : gameConnections) {
			Tile tile1 = c.getTile(0);
			Tile tile2 = c.getTile(1);
			Connector2D connector;
			Rectangle2D connect = null;
			if (tile1.getX() == tile2.getX()) {
				connect = getVerticalConnectionRect(tile1, tile2);
			} else if (tile1.getY() == tile2.getY()) {
				connect = getHorizontalConnectionRect(tile1, tile2);
			}
			System.out.println("if entered");
			if (connect != null) {
				System.out.println("2nd if entered");
				connector = new Connector2D(tile1, tile2, connect, c);
				connectors.add(connector);
				boardConnections.put(connector, c);
			}
			repaint();

		}
	}

	// Helper method
	public Rectangle2DCoord getRectangle(int x, int y) {
		for (Rectangle2DCoord rect : rectangles) {
			if (rect.coordX == x && rect.coordY == y) {
				return rect;
			}
		}
		return null;
	}

	public void setMode(Mode m) {
		this.mode = m;
	}

	// Constructor
	public BoardPanel(Game.Mode m) {
		game.setMode(m);
		initComponents();
		this.mode = BoardPanel.Mode.GAME;
		addMouseListener(new MouseSelectionListener());
	}

	public BoardPanel(BoardPanel oldBoard) {
		this.boardConnections = oldBoard.boardConnections;
		this.boardTiles = oldBoard.boardTiles;
		this.connectors = oldBoard.connectors;
		this.rectangles = oldBoard.rectangles;
		this.playerTiles = oldBoard.playerTiles;
		this.mode = BoardPanel.Mode.GAME;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		doDrawing(g);
	}

	public void doDrawing(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;

		// Background
		RoundRectangle2D contour = new RoundRectangle2D.Float(0, 0, 646, 646, 10, 10);
		g2d.setColor(new Color(195, 195, 195));
		g2d.fill(contour);
		g2d.setColor(new Color(170, 170, 170));
		g2d.draw(contour);

		if (game.getMode() == Game.Mode.DESIGN) {
			for (Rectangle2DCoord rectangle : rectangles) {
				g2d.setColor(new Color(230, 230, 230));
				g2d.draw(rectangle.coordRectangle);
			}
		}

		// Paint currently existing tiles
		for (Rectangle2DCoord rectangle : boardTiles.keySet()) {
			g2d.setColor(rectangle.color);
			g2d.fill(rectangle.coordRectangle);
			g2d.setColor(Color.GRAY);
			g2d.draw(rectangle.coordRectangle);
		}

		// Paint the connections
		for (Connector2D connector : connectors) {
			g2d.setColor(Color.DARK_GRAY);
			g2d.fill(connector.c);
		}

		// Paint visited tiles
		for (Rectangle2DCoord rect : visitedTiles) {
			g2d.setColor(Color.GRAY);
			g2d.fill(rect.coordRectangle);
		}

		// Paint revealed tiles (only revealed for one turn) for
		for (Rectangle2DCoord rect : revealedTiles) {
			g2d.setColor(Color.CYAN);
			g2d.fill(rect.coordRectangle);
			g2d.setColor(Color.black);
			g2d.setFont(new Font("ARIAL", Font.PLAIN, 35));
			g2d.drawString(revealedTileValue, (int)rect.coordRectangle.getX()+5, (int)rect.coordRectangle.getY()+30);
		}

		for (Rectangle2DCoord rectangle : boardTiles.keySet()) {
			if (rectangle.color == Color.pink) {
				g2d.setColor(rectangle.color);
				g2d.fill(rectangle.coordRectangle);
				g2d.setColor(Color.GRAY);
				g2d.draw(rectangle.coordRectangle);
			}
		}

		if (currentWinRectangle != null && game.getMode() == Game.Mode.DESIGN) {
			g2d.setColor(Color.DARK_GRAY);
			g2d.fill(currentWinRectangle.coordRectangle);
			g2d.setColor(Color.BLACK);
			g2d.draw(currentWinRectangle.coordRectangle);
		}

		if (prev != null) {
			g2d.setColor(Color.BLUE);
			g2d.fill(prev.coordRectangle);
			g2d.setColor(Color.GRAY);
			g2d.draw(prev.coordRectangle);
		}

		// Paint players
		for (Ellipse2DCoord circle : playerTiles.values()) {
			Ellipse2D player = new Ellipse2D.Float(GAP * (circle.coordX + 1) + WIDTH * (circle.coordX),
					GAP * (circle.coordY + 1) + HEIGHT * (circle.coordY), WIDTH, HEIGHT);
			g2d.setColor(circle.color);
			g2d.fill(player);
		}

		// This is for showing action tiles on Show Tiles Action Card
		if (showingActionTiles) {
			for (Rectangle2DCoord rect : boardTiles.keySet()) {
				if (boardTiles.get(rect) instanceof ActionTile) {
					g2d.setColor(rect.color);
					g2d.fill(rect.coordRectangle);
					g2d.setColor(Color.GRAY);
					g2d.draw(rect.coordRectangle);
				}
			}
		}

	}

	public Rectangle2D getHorizontalConnectionRect(Tile tile1, Tile tile2) {
		Tile tile;
		if (tile1.getX() < tile2.getX()) {
			tile = tile1;
		} else {
			tile = tile2;
		}
		Rectangle2D rect = new Rectangle2D.Float((tile.getX() + 1) * WIDTH + (tile.getX() + 1) * GAP,
				(tile.getY()) * HEIGHT + (tile.getY() + 1) * GAP + 10, C_LENGTH, C_WIDTH);
		return rect;
	}

	public Rectangle2D getVerticalConnectionRect(Tile tile1, Tile tile2) {
		Tile tile;
		if (tile1.getY() < tile2.getY()) {
			tile = tile1;
		} else {
			tile = tile2;
		}
		Rectangle2D rect = new Rectangle2D.Float((tile.getX()) * WIDTH + (tile.getX() + 1) * GAP + 10,
				(tile.getY() + 1) * HEIGHT + (tile.getY() + 1) * GAP, C_WIDTH, C_LENGTH);
		return rect;
	}

	public ArrayList<Connector2D> getConnector(Tile tile1, Tile tile2) {
		ArrayList<Connector2D> list = new ArrayList<Connector2D>();
		for (Connector2D connect : connectors) {
			if ((tile1.getX() == connect.tileOne.getX() && tile1.getY() == connect.tileOne.getY()
					&& tile2.getX() == connect.tileTwo.getX() && tile2.getY() == connect.tileTwo.getY())
					|| (tile1.getY() == connect.tileTwo.getY() && tile1.getX() == connect.tileTwo.getX()
							&& tile2.getY() == connect.tileOne.getY() && tile2.getX() == connect.tileOne.getX())) {
				list.add(connect);
			}
		}
		return list;
	}

	// This add connection is used for design
	public void addConnection(Rectangle2DCoord rect1, Rectangle2DCoord rect2) {
		if (boardTiles.containsKey(rect1) && boardTiles.containsKey(rect2)) {
			Tile tile1 = boardTiles.get(rect1);
			Tile tile2 = boardTiles.get(rect2);
			Connection c = null;
			boolean enter = true;

			DesignModeController dmc = new DesignModeController();

			try {
				if (tile1.equals(tile2)) {
					throw new InvalidInputException("They are the same tile");
				} else if (!dmc.checkAdjacentTiles(tile1, tile2)) {
					throw new InvalidInputException("The tiles are not adjacent!");
				}
				c = dmc.connectTiles(tile1, tile2);
				
			} catch (Exception e) {
				enter = false;
				prev = null;
				repaint();
				System.out.println("Tiles must be adjacent.");
			}
			if (enter) {
				Connector2D connector;
				Rectangle2D connect = null;
				if (tile1.getX() == tile2.getX()) {
					connect = getVerticalConnectionRect(tile1, tile2);
				} else if (tile1.getY() == tile2.getY()) {
					connect = getHorizontalConnectionRect(tile1, tile2);
				}
				System.out.println("if entered");
				if (connect != null) {
					System.out.println("2nd if entered");
					connector = new Connector2D(tile1, tile2, connect, c);
					connectors.add(connector);
					boardConnections.put(connector, c);
				}
				new DesignModeController().save();
				repaint();
			}
		}
	}

	// This add connection is used for game mode
	public void addConnectionAction(Tile tile1, Tile tile2) {

		Connector2D connector;
		Rectangle2D connectRectangle = null;

		if (tile1.getX() == tile2.getX()) {
			connectRectangle = getVerticalConnectionRect(tile1, tile2);
		} else if (tile1.getY() == tile2.getY()) {
			connectRectangle = getHorizontalConnectionRect(tile1, tile2);
		}

		if (connectRectangle != null) {
			connector = new Connector2D(tile1, tile2, connectRectangle, null);
			connectors.add(connector);
			// boardConnections.put(connector, c);
		}

		TileOApplication.getPlayModeController().save();
		repaint();
	}

	// This method is used for design mode
	public void removeConnection(Rectangle2DCoord rect1, Rectangle2DCoord rect2) {
		if (boardTiles.containsKey(rect1) && boardTiles.containsKey(rect2)) {
			Tile tile1 = boardTiles.get(rect1);
			Tile tile2 = boardTiles.get(rect2);

			DesignModeController dmc = new DesignModeController();

			ArrayList<Connector2D> connector = getConnector(tile1, tile2);

			for (Connector2D connect : connector) {
				dmc.deleteConnection(connect.connect);
				connectors.remove(connect);
			}
			new DesignModeController().save();
			repaint();
		}
	}

	// This method is used for the remove connnection action card
	public void removeConnectionAction(Tile tile1, Tile tile2) {

		ArrayList<Connector2D> connector = getConnector(tile1, tile2);

		for (Connector2D connect : connector) {
			connectors.remove(connect);
			if (boardConnections.get(connector) != null) {
				currentConnection = boardConnections.get(connect);
			}
		}

		System.out.println("Setting conntetion");
		System.out.println(currentConnection);

		TileOApplication.getPlayModeController().save();
		repaint();
	}

	public void addTile(Rectangle2DCoord rect) {
		DesignModeController toc = new DesignModeController();
		if (!(boardTiles.keySet().contains(rect))) {
			if (tileType == TileType.NORMAL) {
				try {
					NormalTile t = toc.addNormalTile(rect.coordX, rect.coordY);
					boardTiles.put(rect, t);
					repaint();
					new DesignModeController().save();
				} catch (InvalidInputException e) {
					System.out.println("Tile exists at that location: Exception catched");
				}

			} else if (tileType == TileType.ACTION) {
				ActionTile t = null;
				try {
					t = toc.addActionTile(rect.coordX, rect.coordY, inactiveTurns);
					boardTiles.put(rect, t);
					rect.setColor(Color.pink);
					repaint();
				} catch (InvalidInputException e) {
					// TODO Auto-generated catch block
					System.out.println("Tile already here");
				}
			} else if (tileType == TileType.WIN) {
				if (currentWinRectangle != null) {
					removeTile(currentWinRectangle);
				}
				Tile t = null;
				t = toc.chooseHiddenTile(rect.coordX, rect.coordY);
				currentWinRectangle = rect;
				boardTiles.put(rect, t);
				repaint();
			}
		}
	}

	public void removeTile(Rectangle2DCoord rect) {
		DesignModeController toc = new DesignModeController();
		if (boardTiles.keySet().contains(rect)) {
			try {
				try {
					for (Player player : game.getPlayers()) {
						if (player.getStartingTile().getX() == rect.coordX
								&& player.getStartingTile().getY() == rect.coordY) {
							System.out.println("Player exists on tile");
							player.setStartingTile(null);
						}
					}
				} catch (Exception e) {

				}
				System.out.println(connectors.size());
				ArrayList<Connector2D> temp = new ArrayList<Connector2D>();
				for (Connector2D connect : connectors) {
					System.out.println("Inside Loop");
					if ((connect.tileOne.getX() == rect.coordX && connect.tileOne.getY() == rect.coordY)
							|| (connect.tileTwo.getX() == rect.coordX && connect.tileTwo.getY() == rect.coordY)) {
						temp.add(connect);
					}
				}
				for (Connector2D connect : temp) {
					connectors.remove(connect);
					boardConnections.remove(connect.connect);
					toc.deleteConnection(connect.connect);
				}
				for (Ellipse2DCoord circle : playerTiles.values()) {
					if (circle.coordX == rect.coordX && circle.coordY == rect.coordY) {
						playerTiles.remove(circle);
					}
				}
				toc.removeTile(rect.coordX, rect.coordY);
				toc.save();
			} catch (InvalidInputException e) {
				System.out.println("Tile does not exist within game");
			}
			rect.setColor(Color.WHITE);
			boardTiles.remove(rect);

			repaint();
		}
	}

	// This method calls land on the controller when the board is in move player
	// mode
	public void landPlayer(Rectangle2DCoord rect) {
		PlayModeController pmc = TileOApplication.getPlayModeController();
		if (rect.color.equals(Color.pink)) {
			Tile t = boardTiles.get(rect);
			boolean flag = pmc.land(t);
			System.out.println(pmc.getMode());
		}
	}

	// This method changes the position of the circle that represents a player
	public void movePlayer(Tile aTile) {
		Integer num = TileOApplication.getCurrentGame().getCurrentPlayer().getNumber();
		System.out.println(num + "The number");
		Ellipse2DCoord player = playerTiles.get(num);
		System.out.println(player);
		player.coordX = aTile.getX();
		player.coordY = aTile.getY();

		Rectangle2DCoord rect = findRectangleFromBoard(aTile);
		visitedTiles.add(rect);
	}

	public void showMessage(String s) {
		JOptionPane.showMessageDialog(this, s);
	}

	public ArrayList<Rectangle2DCoord> getRectangles() {
		return rectangles;
	}

	public void initPlayers(ArrayList<Player> players) {
		System.out.println(players);
		int i = 1;
		for (Player current : players) {
			TileO tileO = TileOApplication.getTileO();
			int gameIndex = tileO.indexOfGame(game);
			playerNumber = current.getNumber();
			if (current.hasStartingTile()) {
				int x = 0;
				int y = 0;
				if (game.getMode() == Game.Mode.DESIGN) {
					x = current.getStartingTile().getX();
					y = current.getStartingTile().getY();
				} else {
					x = current.getCurrentTile().getX();
					y = current.getCurrentTile().getY();
				}
				Ellipse2DCoord circle = new Ellipse2DCoord(x, y);
				playerTiles.put(playerNumber, circle);

				if (playerNumber % 4 == 1) {
					circle.setColor(Color.RED);
				} else if (playerNumber % 4 == 2) {
					circle.setColor(Color.BLUE);
				} else if (playerNumber % 4 == 3) {
					circle.setColor(Color.YELLOW);
				} else if (playerNumber % 4 == 0) {
					circle.setColor(Color.GREEN);
				}
			}
			i++;
		}
	}

	public void refreshBoard() {
		repaint();
	}

	// Work in progress on this one
	public void addPlayer(Rectangle2DCoord rect) {
		DesignModeController toc = new DesignModeController();
		if (boardTiles.keySet().contains(rect)) {
			try {

				if (playerTiles.containsKey(playerNumber)) {
					playerTiles.remove(playerNumber);
				}

				Ellipse2DCoord circle = new Ellipse2DCoord(rect.coordX, rect.coordY);
				playerTiles.put(playerNumber, circle);

				TileO tileO = TileOApplication.getTileO();
				int gameIndex = tileO.indexOfGame(game);
				int c1 = (gameIndex * 4) + 1;
				int c2 = (gameIndex * 4) + 2;
				int c3 = (gameIndex * 4) + 3;
				int c4 = (gameIndex * 4) + 4;

				System.out.println(gameIndex + ", " + c1 + ", " + c2);
				System.out.println(playerNumber);

				int playerIndex = 0;
				int totalPlayers = 0;

				if (playerNumber == c1) {
					circle.setColor(Color.RED);
					playerIndex = 1;
				}

				else if (playerNumber == c2) {
					circle.setColor(Color.BLUE);
					playerIndex = 2;
				} else if (playerNumber == c3) {
					circle.setColor(Color.YELLOW);
					playerIndex = 3;
				} else if (playerNumber == c4) {
					circle.setColor(Color.GREEN);
					playerIndex = 4;
				}

				Tile t = toc.assignStartingTile(rect.coordX, rect.coordY, playerIndex);
				new DesignModeController().save();
				repaint();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Please choose a valid tile");
		}
	}

	// Helper method
	public Tile getTileFromBoard(Rectangle2DCoord rect) {
		for (Tile t : boardTiles.values()) {
			if (rect.coordX == t.getX() && rect.coordY == t.getY()) {
				return t;
			}
		}
		return null;
	}

	public Rectangle2DCoord findRectangleFromBoard(Tile aTile) {
		for (Rectangle2DCoord rect : boardTiles.keySet()) {
			if (aTile.getX() == rect.coordX && aTile.getY() == rect.coordY) {
				return rect;
			}
		}
		return null;
	}

	class MouseSelectionListener implements MouseListener {
		public void mouseClicked(MouseEvent ev) {
			int x = ev.getX();
			int y = ev.getY();
			for (Rectangle2DCoord rect : getRectangles()) {
				if (rect.coordRectangle.contains(x, y)) {
					if (mode == Mode.ADD_TILE) {
						prev = null;
						addTile(rect);
					} else if (mode == Mode.REMOVE_TILE) {
						prev = null;
						removeTile(rect);
					} else if (mode == Mode.PLACE_PLAYER) {
						prev = null;
						addPlayer(rect);
					} else if (mode == Mode.ADD_CONNECTION) {
						if (prev == null) {
							if (boardTiles.containsKey(rect)) {
								prev = rect;
								repaint();
							}

						} else {
							addConnection(prev, rect);
							prev = null;
						}
					} else if (mode == Mode.REMOVE_CONNECTION) {
						if (prev == null) {
							if (boardTiles.containsKey(rect)) {
								System.out.println(rect.coordX + " " + rect.coordY);
								prev = rect;
								repaint();
							}

						} else {
							removeConnection(prev, rect);
							prev = null;
						}
					} else if (mode == Mode.MOVE_PLAYER) {
						landPlayer(rect);
						TileOApplication.getGamePage().getDeckPanel().setToDefault();
						// resetTileColor();
						// repaint();
					} else if (mode == Mode.ADD_CONNECTION_ACTION_CARD) {
						if (prev == null) {
							if (boardTiles.containsKey(rect)) {
								prev = rect;
								repaint();
							}
						} else {
							if (boardTiles.containsKey(rect)) {
								curr = rect;

								Tile tile1 = getTileFromBoard(prev);
								Tile tile2 = getTileFromBoard(curr);

								PlayModeController pmc = TileOApplication.getPlayModeController();
								pmc.addConnection(tile1, tile2);
								prev = null;
								curr = null;
								repaint();
							}
						}
					} else if (mode == Mode.REMOVE_CONNECTION_ACTION_CARD) {
						if (prev == null) {
							if (boardTiles.containsKey(rect)) {
								prev = rect;
								repaint();
							}
						} else {
							if (boardTiles.containsKey(rect)) {
								curr = rect;

								Tile tile1 = getTileFromBoard(prev);
								Tile tile2 = getTileFromBoard(curr);

								PlayModeController pmc = TileOApplication.getPlayModeController();
								pmc.removeConnection(tile1, tile2);
								prev = null;
								curr = null;
								repaint();
							}
						}
						/*
						 * if(prev == null){ prev = rect; repaint(); } else{
						 * curr = rect; GamePage gamePage =
						 * TileOApplication.getGamePage();
						 * System.out.println("first call"); gamePage.refresh();
						 * System.out.println("second call");
						 * System.out.println(currentConnection);
						 * gamePage.refresh(); mode = mode.GAME;
						 * currentConnection = null; prev = null; curr = null;
						 * repaint(); }
						 */
					} else if (mode == Mode.ROLL_DIE) {
						mode = Mode.MOVE_PLAYER;
						// movePlayer(rect);
						resetTileColor();
						TileOApplication.getGamePage().flag = false;
						repaint();
					} else if (mode == Mode.REVEAL_TILE_ACTION_CARD) {
						// Thomas Action Card
						// TODO
						if (boardTiles.containsKey(rect)) {
							PlayModeController pmc = TileOApplication.getPlayModeController();
							Tile aTile = getTileFromBoard(rect);
							if (revealedTiles.size() < 1) {
								pmc.revealTile(aTile);
								revealedTiles.add(rect);
							}
							else{
								revealedTiles.clear();
							}
							repaint();
						}
					}
				}
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}
	}

	public enum Mode {

		ADD_TILE, REMOVE_TILE, PLACE_PLAYER, ADD_CONNECTION, REMOVE_CONNECTION, GAME, MOVE_PLAYER, ADD_CONNECTION_ACTION_CARD, REMOVE_CONNECTION_ACTION_CARD, ROLL_DIE, REVEAL_TILE_ACTION_CARD

	}

	public enum TileType {
		NORMAL, ACTION, WIN
	}

}
