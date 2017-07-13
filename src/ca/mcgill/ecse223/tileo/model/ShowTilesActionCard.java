/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse223.tileo.model;
import java.io.Serializable;
import ca.mcgill.ecse223.tileo.model.Game.Mode;

// line 81 "../../../../../TileOPersistence.ump"
// line 524 "../../../../../TileO(updatedMar22).ump"
public class ShowTilesActionCard extends ActionCard
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ShowTilesActionCard(String aInstructions, Deck aDeck)
  {
    super(aInstructions, aDeck);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {
    super.delete();
  }

  // line 529 "../../../../../TileO(updatedMar22).ump"
   public void play(){
    Game game = this.getDeck().getGame();
		game.setMode(Game.Mode.GAME_SHOWTILESACTIONCARD);
  }


  /**
   * Change the mode to the current action card
   */
  // line 535 "../../../../../TileO(updatedMar22).ump"
   public void changeGameModeToActionCard(){
    Deck deck = this.getDeck();
		Game game = deck.getGame();
		game.setMode(Mode.GAME_SHOWTILESACTIONCARD);
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 84 ../../../../../TileOPersistence.ump
  private static final long serialVersionUID = 5824246698234043234L ;

  
}