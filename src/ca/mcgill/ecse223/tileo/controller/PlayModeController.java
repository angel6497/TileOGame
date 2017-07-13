/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.25.0-9e8af9e modeling language!*/

package ca.mcgill.ecse223.tileo.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.*;
import ca.mcgill.ecse223.tileo.application.TileOApplication;
import ca.mcgill.ecse223.tileo.model.*;
import ca.mcgill.ecse223.tileo.model.Game.Mode;
import ca.mcgill.ecse223.tileo.view.*;
import javax.swing.*;

// line 3 "../../../../../PlayModeController.ump"
public class PlayModeController
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //PlayModeController State Machines
  public enum Mode { Idle, RollDie, Move, GameOver, Action, RevealTile, AddConnection, RemoveConnection }
  private Mode mode;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PlayModeController()
  {
    setMode(Mode.Idle);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public String getModeFullName()
  {
    String answer = mode.toString();
    return answer;
  }

  public Mode getMode()
  {
    return mode;
  }

  public boolean startGame()
  {
    boolean wasEventProcessed = false;
    
    Mode aMode = mode;
    switch (aMode)
    {
      case Idle:
        // line 18 "../../../../../PlayModeController.ump"
        doStartGame();
        setMode(Mode.RollDie);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean reStartGame()
  {
    boolean wasEventProcessed = false;
    
    Mode aMode = mode;
    switch (aMode)
    {
      case Idle:
        // line 22 "../../../../../PlayModeController.ump"
        
        setMode(Mode.RollDie);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean dieRolled()
  {
    boolean wasEventProcessed = false;
    
    Mode aMode = mode;
    switch (aMode)
    {
      case RollDie:
        exitMode();
        // line 36 "../../../../../PlayModeController.ump"
        doRollDie();
        setMode(Mode.Move);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean land(Tile aTile)
  {
    boolean wasEventProcessed = false;
    
    Mode aMode = mode;
    switch (aMode)
    {
      case Move:
        if (isNormalTile(aTile))
        {
          exitMode();
        // line 43 "../../../../../PlayModeController.ump"
          doLand(aTile);
          setMode(Mode.RollDie);
          wasEventProcessed = true;
          break;
        }
        if (isWinTile(aTile))
        {
          exitMode();
        // line 47 "../../../../../PlayModeController.ump"
          doLand(aTile);
          setMode(Mode.GameOver);
          wasEventProcessed = true;
          break;
        }
        if (isActiveActionTile(aTile))
        {
          exitMode();
        // line 51 "../../../../../PlayModeController.ump"
          doLand(aTile);
          setMode(Mode.Action);
          wasEventProcessed = true;
          break;
        }
        if (isInactiveActionTile(aTile))
        {
          exitMode();
        // line 55 "../../../../../PlayModeController.ump"
          doLand(aTile);
        	setNextPlayer();
          setMode(Mode.RollDie);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean skipTurn()
  {
    boolean wasEventProcessed = false;
    
    Mode aMode = mode;
    switch (aMode)
    {
      case Move:
        exitMode();
        // line 60 "../../../../../PlayModeController.ump"
        setNextPlayer();
        setMode(Mode.RollDie);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean getActionCard()
  {
    boolean wasEventProcessed = false;
    
    Mode aMode = mode;
    switch (aMode)
    {
      case Action:
        if (isCurrentCardRollDie())
        {
          exitMode();
        // line 73 "../../../../../PlayModeController.ump"
          displayCard();
          setMode(Mode.RollDie);
          wasEventProcessed = true;
          break;
        }
        if (isCurrentCardAddConnection())
        {
          exitMode();
        // line 77 "../../../../../PlayModeController.ump"
          displayCard();
          setMode(Mode.AddConnection);
          wasEventProcessed = true;
          break;
        }
        if (isCurrentCardRemoveConnection())
        {
          exitMode();
        // line 81 "../../../../../PlayModeController.ump"
          displayCard();
          setMode(Mode.RemoveConnection);
          wasEventProcessed = true;
          break;
        }
        if (isCurrentCardTeleport())
        {
          exitMode();
        // line 85 "../../../../../PlayModeController.ump"
          displayCard();
      doTeleport();
          setMode(Mode.Move);
          wasEventProcessed = true;
          break;
        }
        if (isCurrentCardLoseTurn())
        {
          exitMode();
        // line 91 "../../../../../PlayModeController.ump"
          displayCard();
      doLoseTurn();
          setMode(Mode.RollDie);
          wasEventProcessed = true;
          break;
        }
        if (isCurrentCardChooseMove())
        {
          exitMode();
        // line 96 "../../../../../PlayModeController.ump"
          displayCard();
      doChooseMove();
          setMode(Mode.Move);
          wasEventProcessed = true;
          break;
        }
        if (isCurrentCardResetTile())
        {
          exitMode();
        // line 101 "../../../../../PlayModeController.ump"
          displayCard();
      doResetTile();
          setMode(Mode.RollDie);
          wasEventProcessed = true;
          break;
        }
        if (isCurrentCardShowTiles())
        {
          exitMode();
        // line 106 "../../../../../PlayModeController.ump"
          displayCard();
      doShowTiles();
          setMode(Mode.RollDie);
          wasEventProcessed = true;
          break;
        }
        if (isCurrentCardLoseRandomTurns())
        {
          exitMode();
        // line 111 "../../../../../PlayModeController.ump"
          displayCard();
      doLoseRandomTurns();
          setMode(Mode.RollDie);
          wasEventProcessed = true;
          break;
        }
        if (isCurrentCardRevealTile())
        {
          exitMode();
        // line 116 "../../../../../PlayModeController.ump"
          displayCard();
      //doRevealTile();
          setMode(Mode.RevealTile);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean revealTile(Tile aTile)
  {
    boolean wasEventProcessed = false;
    
    Mode aMode = mode;
    switch (aMode)
    {
      case RevealTile:
        exitMode();
        // line 131 "../../../../../PlayModeController.ump"
        doRevealTile(aTile);
        setMode(Mode.RollDie);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean addConnection(Tile tile1,Tile tile2)
  {
    boolean wasEventProcessed = false;
    
    Mode aMode = mode;
    switch (aMode)
    {
      case AddConnection:
        exitMode();
        // line 143 "../../../../../PlayModeController.ump"
        if(!isTileValid(tile1, tile2)){
        	System.out.println("Made it here");
        	int choice = JOptionPane.showConfirmDialog(null, "That connection was invalid, Try Again?", "Choose one", JOptionPane.YES_NO_OPTION);
        	if(choice == JOptionPane.YES_OPTION){
        		TileOApplication.getBoard().setMode(BoardPanel.Mode.ADD_CONNECTION_ACTION_CARD);
        		return false;
        	}else{
        		setNextPlayer();
        	}
        }
          doAddConnection(tile1, tile2);
        setMode(Mode.RollDie);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean removeConnection(Tile tile1,Tile tile2)
  {
    boolean wasEventProcessed = false;
    
    Mode aMode = mode;
    switch (aMode)
    {
      case RemoveConnection:
        exitMode();
        // line 166 "../../../../../PlayModeController.ump"
        doRemoveConnection(tile1, tile2);
        setMode(Mode.RollDie);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private void exitMode()
  {
    switch(mode)
    {
      case RollDie:
        // line 34 "../../../../../PlayModeController.ump"
        enableRollDieButton(false);
        break;
      case Move:
        // line 62 "../../../../../PlayModeController.ump"
        TileOApplication.getGamePage().enableSkipTurnButton(false);
        break;
      case Action:
        // line 71 "../../../../../PlayModeController.ump"
        enableGetActionCardButton(false);
        break;
      case RevealTile:
        // line 127 "../../../../../PlayModeController.ump"
        TileOApplication.getBoard().setMode(BoardPanel.Mode.GAME);
        break;
      case AddConnection:
        // line 140 "../../../../../PlayModeController.ump"
        TileOApplication.getBoard().setMode(BoardPanel.Mode.GAME);
        break;
      case RemoveConnection:
        // line 163 "../../../../../PlayModeController.ump"
        TileOApplication.getBoard().setMode(BoardPanel.Mode.GAME);
        break;
    }
  }

  private void setMode(Mode aMode)
  {
    mode = aMode;

    // entry actions and do activities
    switch(mode)
    {
      case RollDie:
        // line 30 "../../../../../PlayModeController.ump"
        enableRollDieButton(true);
        break;
      case GameOver:
        // line 66 "../../../../../PlayModeController.ump"
        endGame();
        break;
      case Action:
        // line 70 "../../../../../PlayModeController.ump"
        enableGetActionCardButton(true);
        break;
      case RevealTile:
        // line 124 "../../../../../PlayModeController.ump"
        TileOApplication.getBoard().setMode(BoardPanel.Mode.REVEAL_TILE_ACTION_CARD);
        break;
      case AddConnection:
        // line 137 "../../../../../PlayModeController.ump"
        TileOApplication.getBoard().setMode(BoardPanel.Mode.ADD_CONNECTION_ACTION_CARD);
        break;
      case RemoveConnection:
        // line 160 "../../../../../PlayModeController.ump"
        TileOApplication.getBoard().setMode(BoardPanel.Mode.REMOVE_CONNECTION_ACTION_CARD);
        break;
    }
  }

  public void delete()
  {}


  /**
   * 
   * 
   * ----------------------------------------------------*
   * 
   * Guard methods 				    *
   * 
   * ----------------------------------------------------
   * line 123 "../../../../../PlayModeController.ump"
   * line 128 "../../../../../PlayModeController.ump"
   */
  // line 187 "../../../../../PlayModeController.ump"
   public boolean isNormalTile(Tile aTile){
    if(aTile instanceof NormalTile){
      return true;
    }

    return false;
  }


  /**
   * 
   * line 132 "../../../../../PlayModeController.ump"
   * line 137 "../../../../../PlayModeController.ump"
   */
  // line 199 "../../../../../PlayModeController.ump"
   public boolean isActiveActionTile(Tile aTile){
    if(aTile instanceof ActionTile){
    	ActionTile at = (ActionTile) aTile;
    	if(at.getActionTileStatus() == ActionTile.ActionTileStatus.Active){
      		return true;
      	}
    }

    return false;
  }

  // line 210 "../../../../../PlayModeController.ump"
   public boolean isInactiveActionTile(Tile aTile){
    return !isActiveActionTile(aTile);
  }

  // line 214 "../../../../../PlayModeController.ump"
   private boolean isTileValid(Tile tile1, Tile tile2){
    Game game = TileOApplication.getCurrentGame();

			// Valid input checks
			if (!(game.getTiles().contains(tile1)) || !(game.getTiles().contains(tile2))) {
				return false;
			}

			if (!areAdjacent(tile1, tile2)) {
				System.out.println("Tile one and two : " +  tile1.getX() + " "+ tile1.getY() + " " + tile2.getX() + " " + tile2.getY());
				return false;
			}

			if (!(game.getCurrentConnectionPieces() > 0)) {
				return false;
			}
			for(Connection c : tile1.getConnections()){
				if(c.getTiles().contains(tile2)){
					return false;
				}
			}
	return true;
  }


  /**
   * 
   * line 141 "../../../../../PlayModeController.ump"
   * line 146 "../../../../../PlayModeController.ump"
   */
  // line 242 "../../../../../PlayModeController.ump"
   public boolean isWinTile(Tile aTile){
    if(aTile instanceof WinTile){
      return true;
    }

    return false;
  }

  // line 250 "../../../../../PlayModeController.ump"
   public boolean isCurrentCardLoseRandomTurns(){
    if(TileOApplication.getCurrentGame().getDeck().getCurrentCard() instanceof LoseRandomTurnsActionCard){
      return true;
    }
    return false;
  }


  /**
   * 
   * line 149 "../../../../../PlayModeController.ump"
   * line 155 "../../../../../PlayModeController.ump"
   */
  // line 262 "../../../../../PlayModeController.ump"
   public boolean isCurrentCardRollDie(){
    if(TileOApplication.getCurrentGame().getDeck().getCurrentCard() instanceof RollDieActionCard){
      return true;
    }
    return false;
  }

  // line 269 "../../../../../PlayModeController.ump"
   public boolean isCurrentCardResetTile(){
    if(TileOApplication.getCurrentGame().getDeck().getCurrentCard() instanceof ResetActionTilesActionCard){
      return true;
    }
    return false;
  }

  // line 276 "../../../../../PlayModeController.ump"
   public boolean isCurrentCardShowTiles(){
    if(TileOApplication.getCurrentGame().getDeck().getCurrentCard() instanceof ShowTilesActionCard){
      return true;
    }
    return false;
  }


  /**
   * 
   * line 156 "../../../../../PlayModeController.ump"
   * line 163 "../../../../../PlayModeController.ump"
   */
  // line 287 "../../../../../PlayModeController.ump"
   public boolean isCurrentCardAddConnection(){
    if(TileOApplication.getCurrentGame().getDeck().getCurrentCard() instanceof ConnectTilesActionCard){
      return true;
    }
    return false;
  }


  /**
   * 
   * line 163 "../../../../../PlayModeController.ump"
   * line 171 "../../../../../PlayModeController.ump"
   */
  // line 299 "../../../../../PlayModeController.ump"
   public boolean isCurrentCardRemoveConnection(){
    if(TileOApplication.getCurrentGame().getDeck().getCurrentCard() instanceof RemoveConnectionActionCard){
      return true;
    }
    return false;
  }


  /**
   * 
   * line 170 "../../../../../PlayModeController.ump"
   * line 179 "../../../../../PlayModeController.ump"
   */
  // line 311 "../../../../../PlayModeController.ump"
   public boolean isCurrentCardTeleport(){
    if(TileOApplication.getCurrentGame().getDeck().getCurrentCard() instanceof TeleportActionCard){
      return true;
    }
    return false;
  }


  /**
   * 
   * line 177 "../../../../../PlayModeController.ump"
   * line 187 "../../../../../PlayModeController.ump"
   */
  // line 323 "../../../../../PlayModeController.ump"
   public boolean isCurrentCardLoseTurn(){
    if(TileOApplication.getCurrentGame().getDeck().getCurrentCard() instanceof LoseTurnActionCard){
      return true;
    }
    return false;
  }

  // line 330 "../../../../../PlayModeController.ump"
   public boolean isCurrentCardChooseMove(){
    if(TileOApplication.getCurrentGame().getDeck().getCurrentCard() instanceof ChooseMoveActionCard){
	      return true;
	    }
	    return false;
  }

  // line 337 "../../../../../PlayModeController.ump"
   public boolean isCurrentCardRevealTile(){
    if(TileOApplication.getCurrentGame().getDeck().getCurrentCard() instanceof RevealTileActionCard){
	      return true;
	    }
	    return false;
  }


  /**
   * Manually Implemented Methods
   */
  // line 346 "../../../../../PlayModeController.ump"
   public ActionCard pickActionCard(Game game){
    game = TileOApplication.getCurrentGame();
		Deck deck = game.getDeck();
		ActionCard newCard;
		if(deck.indexOfCard(deck.getCurrentCard()) == Deck.maximumNumberOfCards()){
			deck.shuffle();
			newCard = deck.getCurrentCard();
		}
		else{
			newCard = deck.getCurrentCard();
		}
		if (newCard instanceof RollDieActionCard) {
            game.setMode(Game.Mode.GAME_ROLLDIEACTIONCARD);
        } else if (newCard instanceof ConnectTilesActionCard) {
            game.setMode(Game.Mode.GAME_CONNECTTILESACTIONCARD);
        } else if (newCard instanceof RemoveConnectionActionCard) {
            game.setMode(Game.Mode.GAME_REMOVECONNECTIONACTIONCARD);
        } else if (newCard instanceof TeleportActionCard) {
            game.setMode(Game.Mode.GAME_TELEPORTACTIONCARD);
        } else if (newCard instanceof LoseTurnActionCard) {
            game.setMode(Game.Mode.GAME_LOSETURNACTIONCARD);
        }
			
		newCard = deck.getCard(deck.indexOfCard(deck.getCurrentCard())+1);
		deck.setCurrentCard(newCard);
		return newCard;
  }


  /**
   * 
   * 
   * 
   * 
   * public void action(){
   * Game game = TileOApplication.getCurrentGame();
   * Game.Mode mode = game.getMode();
   * 
   * if(mode == Mode.GAME_CONNECTTILESACTIONCARD){
   * 
   * }
   * 
   * else if(mode == Mode.GAME_LOSETURNACTIONCARD){
   * 
   * }
   * 
   * else if(mode == Mode.GAME_REMOVECONNECTIONACTIONCARD){
   * 
   * }
   * 
   * else if(mode == Mode.GAME_ROLLDIEACTIONCARD){
   * 
   * }
   * else if(mode == Mode.GAME_TELEPORTACTIONCARD){
   * 
   * }
   * 
   * line 221 "../../../../../PlayModeController.ump"
   * line 239 "../../../../../PlayModeController.ump"
   * line 252 "../../../../../PlayModeController.ump"
   * line 345 "../../../../../PlayModeController.ump"
   */
  // line 407 "../../../../../PlayModeController.ump"
   public List<Tile> playRollDieActionCard() throws InvalidInputException{
    Game game = TileOApplication.getCurrentGame();
		Deck deck = game.getDeck();
		ActionCard card = deck.getCurrentCard();

		// Check if the current card is a Roll Die Card
		if (!(card instanceof RollDieActionCard)) {
			throw new InvalidInputException("The current card is not a Roll Die Action Card");
		}

		RollDieActionCard rollDieCard = (RollDieActionCard) card;

		List<Tile> tiles = new ArrayList<Tile>();
		tiles = rollDieCard.play();

		advanceCurrentCard(deck);

		game.setMode(Game.Mode.GAME);
		
		TileOApplication.save();

		return tiles;
  }


  /**
   * 
   * 
   * 
   * line 245 "../../../../../PlayModeController.ump"
   * line 264 "../../../../../PlayModeController.ump"
   * line 281 "../../../../../PlayModeController.ump"
   * line 376 "../../../../../PlayModeController.ump"
   */
  // line 440 "../../../../../PlayModeController.ump"
   public void playConnectTilesActionCard(Tile tile1, Tile tile2) throws Exception{
    Game game = TileOApplication.getCurrentGame();

		// Valid input checks
		if (!(game.getTiles().contains(tile1)) || !(game.getTiles().contains(tile2))) {
			throw new Exception("That tile is not in the game.");
		}

		if (!areAdjacent(tile1, tile2)) {
			System.out.println("Tile one and two : " +  tile1.getX() + " "+ tile1.getY() + " " + tile2.getX() + " " + tile2.getY());
			throw new Exception("The tiles are not adjacent.");
		}

		if (!(game.getCurrentConnectionPieces() > 0)) {
			throw new Exception("There are no spare connection pieces left.");
		}

		Deck deck = game.getDeck();
		ActionCard card = deck.getCurrentCard();

		// Check if current card is Connect Tiles Card
		if (!(card instanceof ConnectTilesActionCard)) {
			throw new InvalidInputException("The current card is not a Connect Tiles Action Card");
		}

		ConnectTilesActionCard connectTilesCard = (ConnectTilesActionCard) card;

		connectTilesCard.play(tile1, tile2);
		
		setNextPlayer();

		advanceCurrentCard(deck);

		game.setMode(Game.Mode.GAME);

		
		TileOApplication.save();
  }


  /**
   * 
   * 
   * 
   * line 287 "../../../../../PlayModeController.ump"
   * line 304 "../../../../../PlayModeController.ump"
   * line 325 "../../../../../PlayModeController.ump"
   * line 422 "../../../../../PlayModeController.ump"
   */
  // line 488 "../../../../../PlayModeController.ump"
   public void playRemoveConnectionActionCard(Connection aConnection) throws InvalidInputException{
    Game game = TileOApplication.getCurrentGame();

		if (!(game.getConnections().contains(aConnection))) {
			throw new InvalidInputException("The connections did not exist.");
		}

		Deck deck = game.getDeck();
		ActionCard card = deck.getCurrentCard();

		if (!(card instanceof RemoveConnectionActionCard)) {
			throw new InvalidInputException("Current card is not a Remove Connection Action Card");
		}

		RemoveConnectionActionCard removeConnectionCard = (RemoveConnectionActionCard) card;

		removeConnectionCard.play(aConnection);

		setNextPlayer();

		advanceCurrentCard(deck);

		game.setMode(Game.Mode.GAME);
		
		TileOApplication.save();
  }


  /**
   * 
   * 
   * 
   * line 315 "../../../../../PlayModeController.ump"
   * line 332 "../../../../../PlayModeController.ump"
   * line 357 "../../../../../PlayModeController.ump"
   * line 456 "../../../../../PlayModeController.ump"
   */
  // line 524 "../../../../../PlayModeController.ump"
   public void playTeleportActionCard() throws InvalidInputException{
    Game game = TileOApplication.getCurrentGame();
    Tile tile = game.getCurrentPlayer().getCurrentTile();

		if (!(game.getTiles().contains(tile))) {
			throw new InvalidInputException("The tile is not in the game.");
		}

		Deck deck = game.getDeck();

		ActionCard card = deck.getCurrentCard();

		advanceCurrentCard(deck);

		if (!(card instanceof TeleportActionCard)) {
			throw new InvalidInputException("Current card is not a Teleport Action Card");
		}

		TeleportActionCard teleportCard = (TeleportActionCard) card;

		tile.setHasBeenVisited(true);

		game.setMode(Game.Mode.GAME);
		
		TileOApplication.save();
  }


  /**
   * line 482 "../../../../../PlayModeController.ump"
   */
  // line 552 "../../../../../PlayModeController.ump"
   public void playLoseTurnActionCard() throws InvalidInputException{
    Game game = TileOApplication.getCurrentGame();

      Deck deck = game.getDeck();

      ActionCard card = deck.getCurrentCard();

      advanceCurrentCard(deck);

      if (!(card instanceof LoseTurnActionCard)) {
        throw new InvalidInputException("Current card is not a Lose Turn Action Card");
      }

      LoseTurnActionCard loseTurnCard = (LoseTurnActionCard) card;
      
      loseTurnCard.play();
      System.out.println(game.getCurrentPlayer().getTurnsUntilActive());
      setNextPlayer();
      advanceCurrentCard(deck);

      game.setMode(Game.Mode.GAME);
  }


  /**
   * line 505 "../../../../../PlayModeController.ump"
   */
  // line 576 "../../../../../PlayModeController.ump"
   public List<Tile> playChooseMoveActionCard(int n) throws InvalidInputException{
    Game game = TileOApplication.getCurrentGame();
      Deck deck = game.getDeck();
      ActionCard card = deck.getCurrentCard();

      // Check if the current card is a Choose Move Card
      if (!(card instanceof ChooseMoveActionCard)) {
        throw new InvalidInputException("The current card is not a Choose Move Action Card");
      }

      ChooseMoveActionCard chooseMoveActionCard = (ChooseMoveActionCard) card;
      List<Tile> tiles = new ArrayList<>();
      tiles = chooseMoveActionCard.play(n);

      advanceCurrentCard(deck);

      game.setMode(Game.Mode.GAME);
      
      TileOApplication.save();

      return tiles;
  }


  /**
   * line 528 "../../../../../PlayModeController.ump"
   */
  // line 600 "../../../../../PlayModeController.ump"
   public void doChooseMove(){
    GamePage gamePage = TileOApplication.getGamePage();
      int num = gamePage.showChooseMovePopup();
      try{
        List<Tile> tiles = playChooseMoveActionCard(num);
        TileOApplication.refreshDie(num);
        System.out.println(num*10);
        gamePage.setPossibleMoves((ArrayList)tiles);
        TileOApplication.getBoard().setMode(BoardPanel.Mode.MOVE_PLAYER);
        TileOApplication.getBoard().refreshBoard();
      }catch(InvalidInputException e){
        return;
      }
  }


  /**
   * line 544 "../../../../../PlayModeController.ump"
   */
  // line 616 "../../../../../PlayModeController.ump"
   public void doResetTile(){
    try {
		playResetTilesActionCard();
	} catch (InvalidInputException e) {
		// TODO Auto-generated catch block
		System.out.println("The current card is not an instance of a reset action tiles card");
	}
  }

  // line 625 "../../../../../PlayModeController.ump"
   public void playResetTilesActionCard() throws InvalidInputException{
    Game game = TileOApplication.getCurrentGame();
			Deck deck = game.getDeck();
			ActionCard card = deck.getCurrentCard();

			advanceCurrentCard(deck);

			if (!(card instanceof ResetActionTilesActionCard)) {
				throw new InvalidInputException("Current card is not a Reset Tiles Action Card");
			}

			ResetActionTilesActionCard resetTilesCard = (ResetActionTilesActionCard) card;
				
			
			resetTilesCard.play();
		    
			advanceCurrentCard(deck);

		    game.setMode(Game.Mode.GAME);
			TileOApplication.save();
  }

  // line 647 "../../../../../PlayModeController.ump"
   public void doLoseRandomTurns(){
    Deck deck = TileOApplication.getCurrentGame().getDeck();
    ((LoseRandomTurnsActionCard)deck.getCurrentCard()).play();
    setNextPlayer();
    advanceCurrentCard(deck);
    TileOApplication.getCurrentGame().setMode(Game.Mode.GAME);
  }


  /**
   * 
   * 
   * 
   * 
   * public List<Tile> generateMoves(Tile origin, int numberOfMoves){
   * Deque<Tile> toVisit = new ArrayDeque<>();
   * List<Tile> visited[] = new List[6];
   * visited[0] = new ArrayList<>();
   * visited[0].add(origin);
   * int layer = 0;
   * toVisit.add(origin);
   * while(!toVisit.isEmpty() && layer < 5){
   * Tile current = toVisit.poll();
   * layer++;
   * visited[layer] = new ArrayList<Tile>();
   * for(Tile t : getNeighbours(current)){
   * visited[layer].add(t);
   * toVisit.add(t);
   * }
   * }
   * return visited[numberOfMoves-1];
   * 
   * Helper methods
   * Sets the current player to the next player
   * line 364 "../../../../../PlayModeController.ump"
   * line 384 "../../../../../PlayModeController.ump"
   * line 412 "../../../../../PlayModeController.ump"
   * line 581 "../../../../../PlayModeController.ump"
   */
  // line 684 "../../../../../PlayModeController.ump"
   public void setNextPlayer(){
    Game currentGame = TileOApplication.getCurrentGame();
    Player currentPlayer = currentGame.getCurrentPlayer();
    int nextPlayerIndex = currentGame.indexOfPlayer(currentPlayer) + 1;
    // Loop back if it is the last player
    int nextPlayerNumber = nextPlayerIndex % currentGame.numberOfPlayers();
    // Get the next player
    Player nextPlayer = currentGame.getPlayer(nextPlayerNumber);
    while(nextPlayer.getPlayerStatus() == Player.PlayerStatus.Inactive){
      TileOApplication.getGamePage()
                      .showMessage(
                      String.format("Player %d' turn was lost, skipping to next player!", nextPlayerIndex));
      nextPlayer.takeTurn();
      nextPlayerIndex = currentGame.indexOfPlayer(nextPlayer) + 1;
      nextPlayerNumber = nextPlayerIndex % currentGame.numberOfPlayers();
      nextPlayer = currentGame.getPlayer(nextPlayerNumber);
    }
    // Set the next player as the current player
    currentGame.setCurrentPlayer(nextPlayer);
    int num = TileOApplication.getCurrentGame().getCurrentPlayer().getNumber() % 4;
    if(num == 0){
      num = 4;
    }
    TileOApplication.getGamePage().setCurrentPlayerLabel(num);
    
   
    for(Tile t: TileOApplication.getCurrentGame().getTiles()){
    	if(t instanceof ActionTile){
    		((ActionTile) t).takeTurn();
    	}
    }
  }


  /**
   * 
   * 
   * 
   * 
   * public List<Tile> getNeighbours(Tile a){
   * List<Tile> neighbours = new ArrayList<>();
   * 
   * for(Connection c : a.getConnections()){
   * for(Tile t : c.getTiles()){
   * if(!(t.getX() == a.getX() && t.getY() == a.getY())){
   * neighbours.add(t);
   * }
   * }
   * }
   * return neighbours;
   * 
   * Checks if two tiles are adjacent (connected) to each other
   * line 389 "../../../../../PlayModeController.ump"
   * line 413 "../../../../../PlayModeController.ump"
   * line 448 "../../../../../PlayModeController.ump"
   * line 632 "../../../../../PlayModeController.ump"
   */
  // line 740 "../../../../../PlayModeController.ump"
   public boolean areAdjacent(Tile tile1, Tile tile2){
    int xOne = tile1.getX();
			int xTwo = tile2.getX();
			int yOne = tile1.getY();
			int yTwo = tile2.getY();
			boolean adjacent = false;
			if((Math.abs(xOne - xTwo) == 1) && (yOne == yTwo))
			{
				adjacent = true;
			}
			if((Math.abs(yOne - yTwo) == 1) && (xOne == xTwo))
			{
				adjacent = true;
			}
			return adjacent;
  }


  /**
   * 
   * 
   * 
   * 
   * Sets the current card on the deck to the next one
   * line 413 "../../../../../PlayModeController.ump"
   * line 441 "../../../../../PlayModeController.ump"
   * line 478 "../../../../../PlayModeController.ump"
   * line 664 "../../../../../PlayModeController.ump"
   */
  // line 768 "../../../../../PlayModeController.ump"
   public void advanceCurrentCard(Deck deck){
    ActionCard card = deck.getCurrentCard();
		int index = deck.indexOfCard(card);
		try{
			ActionCard nextCard = deck.getCard(index+1);
			deck.setCurrentCard(nextCard);
		}catch(IndexOutOfBoundsException e){
			deck.shuffle();
			index=0;
			ActionCard nextCard = deck.getCard(index);
			deck.setCurrentCard(nextCard);
		}
  }


  /**
   * 
   * 
   * 
   * 
   * Thomas
   * Returns a list of possible moves the current player can make based on the
   * number they roll
   * line 428 "../../../../../PlayModeController.ump"
   * line 462 "../../../../../PlayModeController.ump"
   * line 501 "../../../../../PlayModeController.ump"
   * line 689 "../../../../../PlayModeController.ump"
   */
  // line 795 "../../../../../PlayModeController.ump"
   public void doRollDie(){
    Game game = TileOApplication.getCurrentGame();
	   List<Tile> tiles = game.rollDie();
	   
	   if(tiles.size() == 0 || tiles == null){
		   TileOApplication.getGamePage().showMessage("No possible moves!");
		   //TODO: Add event to sm to allow to go back to ROLL DIE without landing
		   TileOApplication.getGamePage().enableSkipTurnButton(true);
	   }
	   else{
		   ArrayList tilesArray = new ArrayList<Tile>(tiles);
		   GamePage gamePage = TileOApplication.getGamePage();
		   gamePage.setPossibleMoves(tilesArray);
		   
		   BoardPanel board = TileOApplication.getBoard();
		   board.setMode(BoardPanel.Mode.MOVE_PLAYER);
		   board.refreshBoard();
	   }
  }


  /**
   * 
   * line 522 "../../../../../PlayModeController.ump"
   * line 710 "../../../../../PlayModeController.ump"
   */
  // line 820 "../../../../../PlayModeController.ump"
   public void doTeleport(){
    GamePage gamePage = TileOApplication.getGamePage();
    	boolean isValid = true;
    	
    	try{
    		playTeleportActionCard();
    	}
    	catch(InvalidInputException e){
    		isValid = false;
    	}
    	
    	if(isValid){
    		gamePage.setAllTilesToPossible();
    		TileOApplication.getBoard().setMode(BoardPanel.Mode.MOVE_PLAYER);
    	}
  }


  /**
   * line 726 "../../../../../PlayModeController.ump"
   */
  // line 838 "../../../../../PlayModeController.ump"
   public void doLoseTurn(){
    try{
        playLoseTurnActionCard();
        TileOApplication.getGamePage().getDeckPanel().setToDefault();
      }catch(InvalidInputException e){
        System.out.println("There was an error");
      }
  }


  /**
   * 
   * 
   * 
   * 
   * Thomas
   * line 436 "../../../../../PlayModeController.ump"
   * line 489 "../../../../../PlayModeController.ump"
   * line 535 "../../../../../PlayModeController.ump"
   * line 743 "../../../../../PlayModeController.ump"
   */
  // line 859 "../../../../../PlayModeController.ump"
   public void doStartGame(){
    /* VARIABLES */
	   	Game selectedGame=TileOApplication.getCurrentGame();
		List<Tile> tiles;
		List<Player> players;
		Tile startingTile;
		Deck deck;
		TileO tileo;
		
		// Get the TileO instance
		tileo = TileOApplication.getTileO();
		// Set the game to the selected game
		// tileo.setCurrentGame(selectedGame);
		
		/* GET */
		// Get the deck in the game
		deck = selectedGame.getDeck();
		// Get all tiles in current game
		tiles = selectedGame.getTiles();
		// Get all players in current game
		players = selectedGame.getPlayers();

		/* ACTION */
		// Shuffle the deck
		deck.shuffle();
		

		// Set all tiles in selectedGame to unvisited
		for (Tile tile : tiles) {
			tile.setHasBeenVisited(false);
		}

		// Place all players on board, and set their starting tiles to visited
		for (Player player : players) {
			startingTile = player.getStartingTile();
			player.setCurrentTile(startingTile);
			startingTile.setHasBeenVisited(true);
			TileOApplication.save();
		}	

		/* SET */
		// Set the first player as the current player
		selectedGame.setCurrentPlayer(selectedGame.getPlayers().get(0));
		
		// Set the number of connection pieces to the default value
		selectedGame.setCurrentConnectionPieces(Game.SpareConnectionPieces);
		
		TileOApplication.changeGameMode();
  }


  /**
   * 
   * 
   * 
   * 
   * Thomas
   * line 503 "../../../../../PlayModeController.ump"
   * line 566 "../../../../../PlayModeController.ump"
   * line 614 "../../../../../PlayModeController.ump"
   * line 825 "../../../../../PlayModeController.ump"
   */
  // line 920 "../../../../../PlayModeController.ump"
   public void doLand(Tile tile){
    tile.land();
			
			BoardPanel board = TileOApplication.getBoard();
			board.movePlayer(tile);
			
			board.setMode(BoardPanel.Mode.GAME);	
			board.resetTileColor();
			board.refreshBoard();
			
			if(!(tile instanceof ActionTile)){
				System.out.println("Player set to next");
				setNextPlayer();
			}
  }


  /**
   * 
   * 
   * line 580 "../../../../../PlayModeController.ump"
   * line 633 "../../../../../PlayModeController.ump"
   * line 848 "../../../../../PlayModeController.ump"
   */
  // line 943 "../../../../../PlayModeController.ump"
   public void doAddConnection(Tile tile1, Tile tile2){
    boolean isValid;
	  try{
		  playConnectTilesActionCard(tile1, tile2);
		  isValid = true;
	  }
	  catch(Exception e){
		  TileOApplication.getGamePage().showMessage("You cannot connect those tiles!");
		  isValid = false;
	  }
	  
	  if(isValid){
		  TileOApplication.getBoard().addConnectionAction(tile1, tile2);
	  }
	  
	  TileOApplication.getGamePage().getDeckPanel().setToDefault();
  }


  /**
   * line 866 "../../../../../PlayModeController.ump"
   */
  // line 962 "../../../../../PlayModeController.ump"
   public void doShowTiles(){
    Game game = TileOApplication.getCurrentGame();
	   game.setMode(Game.Mode.GAME_SHOWTILESACTIONCARD);
	   boolean isValid = true;
	   
	   try{
		   playShowTilesActionCard();
	   }
	   catch (InvalidInputException ex){
		   ex.printStackTrace();
		   isValid = false;
		   game.setMode(Game.Mode.GAME);
	   }
	   if (isValid){
		   	TileOApplication.getBoard().showActionTiles();
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					TileOApplication.getGamePage().getDeckPanel().setToDefault();
					setNextPlayer();
				}
			});
	   }
	   
	   game.setMode(Game.Mode.GAME);
  }


  /**
   * line 888 "../../../../../PlayModeController.ump"
   */
  // line 989 "../../../../../PlayModeController.ump"
   public void playShowTilesActionCard() throws InvalidInputException{
    Game game = TileOApplication.getCurrentGame();
		Deck deck = game.getDeck();
		ActionCard card = deck.getCurrentCard();

		advanceCurrentCard(deck);

		if (!(card instanceof ShowTilesActionCard)) {
			throw new InvalidInputException("Current card is not a Show Tiles Action Card");
		}

		ShowTilesActionCard showTilesCard = (ShowTilesActionCard) card;
			
		TileOApplication.save();
  }


  /**
   * 
   * @author Thomas
   */
  // line 1008 "../../../../../PlayModeController.ump"
   public void doRevealTile(Tile aTile){
    Game game = TileOApplication.getCurrentGame();
	   game.setMode(Game.Mode.GAME_REVEALTILEACTIONCARD);
	   boolean isValid = true;
	   char tileType = 'N';
	   
	   try{
		   tileType = playRevealTileActionCard(aTile);
	   }
	   catch (Exception ex){
		   ex.printStackTrace();
		   isValid = false;
		   game.setMode(Game.Mode.GAME);
	   }
	   if (isValid){
		   TileOApplication.getBoard().revealTile(tileType);
	   }
	   
	   TileOApplication.getGamePage().getDeckPanel().setToDefault();
		advanceCurrentCard(game.getDeck());
	   //setNextPlayer();
	   game.setMode(Game.Mode.GAME);
  }


  /**
   * 
   * @author Thomas
   */
  // line 1035 "../../../../../PlayModeController.ump"
   public char playRevealTileActionCard(Tile aTile) throws Exception{
    Game game = TileOApplication.getCurrentGame();
		Deck deck = game.getDeck();
		ActionCard card = deck.getCurrentCard();
		char tileType = 'N';

		advanceCurrentCard(deck);

		if (!(card instanceof RevealTileActionCard)) {
			throw new InvalidInputException("Current card is not a Reveal Tile Action Card");
		}

		RevealTileActionCard revealTileCard = (RevealTileActionCard) card;

		tileType = revealTileCard.play(aTile);
		
		setNextPlayer();

		//advanceCurrentCard(deck);

		game.setMode(Game.Mode.GAME);
		
		return tileType;
			
		//TileOApplication.save();
  }


  /**
   * 
   * 
   * line 599 "../../../../../PlayModeController.ump"
   * line 653 "../../../../../PlayModeController.ump"
   * line 909 "../../../../../PlayModeController.ump"
   */
  // line 1068 "../../../../../PlayModeController.ump"
   public void displayCard(){
    //Display the current Action 
	  ActionCard c = TileOApplication.getCurrentGame().getDeck().getCurrentCard();
	  TileOApplication.getGamePage().getDeckPanel().setCardInfo(c);
  }


  /**
   * 
   * 
   * line 608 "../../../../../PlayModeController.ump"
   * line 660 "../../../../../PlayModeController.ump"
   * line 920 "../../../../../PlayModeController.ump"
   */
  // line 1081 "../../../../../PlayModeController.ump"
   public void doRemoveConnection(Tile tile1, Tile tile2){
    ArrayList<Connection> connections = new ArrayList<Connection>(TileOApplication.getCurrentGame().getConnections());
	   Connection current = null;
	   for(Connection c: connections){
		   if((c.getTile(0) == tile1 && c.getTile(1) == tile2) || (c.getTile(0) == tile2 && c.getTile(1) == tile1)){
			   current = c;
		   }
	   }
	   
	    boolean isValid;
		  try{
			  playRemoveConnectionActionCard(current);
			  isValid = true;
		  }
		  catch(InvalidInputException e){
			  TileOApplication.getGamePage().showMessage("Those tiles are not connected.");
			  isValid = false;
		  }
		  
		  if(isValid){
			  TileOApplication.getBoard().removeConnectionAction(tile1, tile2);
		  }
		  	  
		  TileOApplication.getGamePage().getDeckPanel().setToDefault();
  }


  /**
   * 
   * 
   * line 611 "../../../../../PlayModeController.ump"
   * line 687 "../../../../../PlayModeController.ump"
   * line 951 "../../../../../PlayModeController.ump"
   */
  // line 1114 "../../../../../PlayModeController.ump"
   public void enableRollDieButton(boolean flag){
    TileOApplication.enableRollDieButton(flag);
  }


  /**
   * 
   * 
   * line 616 "../../../../../PlayModeController.ump"
   * line 692 "../../../../../PlayModeController.ump"
   * line 960 "../../../../../PlayModeController.ump"
   */
  // line 1125 "../../../../../PlayModeController.ump"
   public void endGame(){
    TileOApplication.getGamePage().refresh();
	TileOApplication.getMainMenu().refresh();
	TileOApplication.getGamePage().showMessage("You have found the hidden tile and won the game!");
	save();
	TileOApplication.deleteGame();
  }


  /**
   * 
   * 
   * line 619 "../../../../../PlayModeController.ump"
   * line 697 "../../../../../PlayModeController.ump"
   * line 969 "../../../../../PlayModeController.ump"
   */
  // line 1140 "../../../../../PlayModeController.ump"
   public void showMessage(String s){
    JOptionPane.showMessageDialog(null, s);
  }


  /**
   * 
   * 
   * line 623 "../../../../../PlayModeController.ump"
   * line 702 "../../../../../PlayModeController.ump"
   * line 978 "../../../../../PlayModeController.ump"
   */
  // line 1151 "../../../../../PlayModeController.ump"
   public void enableGetActionCardButton(boolean enable){
    TileOApplication.getGamePage().enableGetActionCardButton(enable);
  }


  /**
   * 
   * 
   * 
   * line 516 "../../../../../PlayModeController.ump"
   * line 628 "../../../../../PlayModeController.ump"
   * line 711 "../../../../../PlayModeController.ump"
   * line 989 "../../../../../PlayModeController.ump"
   */
  // line 1164 "../../../../../PlayModeController.ump"
   public void save(){
    TileOApplication.save();
  }

}