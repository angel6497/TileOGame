/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse223.tileo.model;
import java.io.Serializable;
import java.util.Random;

// line 33 "../../../../../TileOPersistence.ump"
// line 562 "../../../../../TileO(updatedMar22).ump"
public class Die implements Serializable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Die Associations
  private Game game;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Die(Game aGame)
  {
    if (aGame == null || aGame.getDie() != null)
    {
      throw new RuntimeException("Unable to create Die due to aGame");
    }
    game = aGame;
  }

  public Die(int aCurrentConnectionPiecesForGame, Deck aDeckForGame, TileO aTileOForGame)
  {
    game = new Game(aCurrentConnectionPiecesForGame, aDeckForGame, this, aTileOForGame);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public Game getGame()
  {
    return game;
  }

  public void delete()
  {
    Game existingGame = game;
    game = null;
    if (existingGame != null)
    {
      existingGame.delete();
    }
  }

  // line 565 "../../../../../TileO(updatedMar22).ump"
   public int roll(){
    // Default value
		int dieValue = 0;
		Random rand = new Random();
		// Get a random value between 1 and 6 inclusive
		dieValue = rand.nextInt(6) + 1;
		//
		System.out.println("Roll value " + dieValue);
		return dieValue;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 36 ../../../../../TileOPersistence.ump
  private static final long serialVersionUID = -2819346463911992740L ;

  
}