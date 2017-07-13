/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse223.tileo.model;

import ca.mcgill.ecse223.tileo.model.Game.Mode;
import java.util.*;

// line 462 "../../../../../TileO(updatedMar22).ump"
public class RevealTileActionCard extends ActionCard {

	// ------------------------
	// MEMBER VARIABLES
	// ------------------------

	// ------------------------
	// CONSTRUCTOR
	// ------------------------

	public RevealTileActionCard(String aInstructions, Deck aDeck) {
		super(aInstructions, aDeck);
	}

	// ------------------------
	// INTERFACE
	// ------------------------

	public void delete() {
		super.delete();
	}

	/**
	 * 
	 * @author thomashillyer
	 * @param tile
	 * @return
	 * @throws InvalidInputException
	 */
	// line 474 "../../../../../TileO(updatedMar22).ump"
	public char play(Tile tile) throws Exception {
		char type = 'N';
		String error = "";

		changeGameModeToActionCard();
		// player clicks tile
		// tile is revealed

		// revealed so that the board draws it a different color
		tile.setHasBeenRevealed(true);

		// return the type the tile is
		try {
			if (tile instanceof NormalTile) {
				type = 'N';
			} else if (tile instanceof ActionTile) {
				type = 'A';
			} else if (tile instanceof WinTile) {
				type = 'W';
			}
			return type;
		} catch (Exception e) {
			error = e.getMessage();
			throw new Exception(error);
		}
	}

	// line 502 "../../../../../TileO(updatedMar22).ump"
	public void changeGameModeToActionCard() {
		Deck deck = this.getDeck();
		Game game = deck.getGame();
		game.setMode(Mode.GAME_REVEALTILEACTIONCARD);
	}

}