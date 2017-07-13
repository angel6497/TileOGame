/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse223.tileo.model;
import ca.mcgill.ecse223.tileo.model.Game.Mode;
import java.util.Random;

// line 541 "../../../../../TileO(updatedMar22).ump"
public class LoseRandomTurnsActionCard extends ActionCard
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public LoseRandomTurnsActionCard(String aInstructions, Deck aDeck)
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

  // line 547 "../../../../../TileO(updatedMar22).ump"
   public void play(){
    Game game = this.getDeck().getGame();
		Random rand = new Random();

		for(Player p : game.getPlayers()){
			int numberOfTurnsLost = rand.nextInt(3);
			p.loseTurns(numberOfTurnsLost);
		}
  }

  // line 556 "../../../../../TileO(updatedMar22).ump"
   public void changeGameModeToActionCard(){
    Game game = this.getDeck().getGame();
		game.setMode(Mode.GAME_LOSERANDOMTURNSACTIONCARD);
  }

}